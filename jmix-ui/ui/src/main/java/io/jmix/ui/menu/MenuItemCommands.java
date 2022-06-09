/*
 * Copyright 2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.ui.menu;

import com.google.common.base.Strings;
import io.jmix.core.*;
import io.jmix.core.common.util.ReflectionHelper;
import io.jmix.ui.component.DialogWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.WindowConfig;
import io.jmix.ui.WindowInfo;
import io.jmix.ui.logging.UserActionsLogger;
import io.jmix.ui.monitoring.UiMonitoring;
import io.jmix.ui.screen.*;
import io.jmix.ui.sys.UiControllerProperty;
import io.jmix.ui.sys.UiControllerPropertyInjector;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static io.jmix.ui.screen.UiControllerUtils.getScreenContext;
import static io.jmix.ui.sys.UiControllerProperty.Type.REFERENCE;
import static io.jmix.ui.sys.UiControllerProperty.Type.VALUE;

@Component("ui_MenuItemCommands")
public class MenuItemCommands {

    private static final Logger userActionsLog = LoggerFactory.getLogger(UserActionsLogger.class);
    private static final Logger log = LoggerFactory.getLogger(MenuItemCommands.class);

    @Autowired
    protected DataManager dataManager;
    @Autowired
    protected MenuConfig menuConfig;
    @Autowired
    protected WindowConfig windowConfig;
    @Autowired
    protected ClassManager classManager;
    @Autowired
    protected Metadata metadata;
    @Autowired
    protected MetadataTools metadataTools;
    @Autowired
    protected FetchPlanRepository fetchPlanRepository;
    @Autowired
    protected ScreenBuilders screenBuilders;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected MeterRegistry meterRegistry;

    /**
     * Create menu command.
     *
     * @param item menu item
     * @return command
     */
    @Nullable
    public MenuItemCommand create(FrameOwner origin, MenuItem item) {
        List<MenuItemProperty> properties = loadProperties(item);

        if (StringUtils.isNotEmpty(item.getScreen())) {
            return createScreenCommand(origin, item, convertToUiControllerProperties(properties));
        }

        if (StringUtils.isNotEmpty(item.getRunnableClass())) {
            return createRunnableClassCommand(origin, item, item.getRunnableClass(),
                    convertToMethodParameters(properties));
        }

        if (StringUtils.isNotEmpty(item.getBean())) {
            return new BeanCommand(origin, item, item.getBean(), item.getBeanMethod(),
                    convertToMethodParameters(properties));
        }

        return null;
    }

    protected MenuItemCommand createScreenCommand(FrameOwner origin, MenuItem item,
                                                  List<UiControllerProperty> properties) {
        return new ScreenCommand(origin, item, item.getScreen(), item.getDescriptor(), properties);
    }

    protected MenuItemCommand createRunnableClassCommand(FrameOwner origin, MenuItem item, String runnableClass,
                                                         Map<String, Object> methodParameters) {
        return new RunnableClassCommand(origin, item, runnableClass, methodParameters);
    }

    protected MenuItemCommand createBeanCommand(FrameOwner origin, MenuItem item, String bean, String beanMethod,
                                                Map<String, Object> methodParameters) {
        return new BeanCommand(origin, item, bean, beanMethod, methodParameters);
    }

    protected List<MenuItemProperty> loadProperties(MenuItem item) {
        Element propsEl = item.getDescriptor().element("properties");
        if (propsEl == null) {
            return Collections.emptyList();
        }

        List<Element> elements = propsEl.elements("property");
        List<MenuItemProperty> properties = new ArrayList<>(elements.size());

        for (Element element : elements) {
            properties.add(new MenuItemProperty(applicationContext, element));
        }

        return properties;
    }

    protected Map<String, Object> convertToMethodParameters(List<MenuItemProperty> properties) {
        return properties.stream()
                .collect(Collectors.toMap(
                        MenuItemProperty::getName, MenuItemProperty::getValueOrEntity));
    }

    protected List<UiControllerProperty> convertToUiControllerProperties(List<MenuItemProperty> properties) {
        return properties.stream()
                .map(property -> property.getValue() != null
                        ? new UiControllerProperty(property.getName(), property.getValue(), VALUE)
                        : new UiControllerProperty(property.getName(), property.getValueOrEntity(), REFERENCE))
                .collect(Collectors.toList());
    }

    protected class ScreenCommand implements MenuItemCommand {
        protected FrameOwner origin;
        protected MenuItem item;

        protected String screen;
        protected Element descriptor;
        protected List<UiControllerProperty> controllerProperties;

        protected ScreenCommand(FrameOwner origin, MenuItem item,
                                String screen, Element descriptor, List<UiControllerProperty> controllerProperties) {
            this.origin = origin;
            this.item = item;
            this.screen = screen;
            this.descriptor = descriptor;
            this.controllerProperties = controllerProperties;
        }

        @Override
        public void run() {
            userActionsLog.trace("Menu item {} triggered", item.getId());

            Timer.Sample sample = Timer.start(meterRegistry);

            WindowInfo windowInfo = windowConfig.getWindowInfo(this.screen);
            Screen screen = createScreen(windowInfo, this.screen);

            if (screen != null && screen.getWindow() instanceof DialogWindow) {
                Boolean resizable = getResizable(descriptor);
                if (resizable != null) {
                    ((DialogWindow) screen.getWindow()).setResizable(resizable);
                }
            }

            // inject declarative properties

            List<UiControllerProperty> properties = this.controllerProperties;
            if (windowInfo.getDescriptor() != null) {
                properties = properties.stream()
                        .filter(prop -> !"entityToEdit".equals(prop.getName()))
                        .collect(Collectors.toList());
            }

            UiControllerPropertyInjector propertyInjector = applicationContext.getBean(UiControllerPropertyInjector.class,
                    screen, properties);
            propertyInjector.inject();

            if (screen != null) {
                Screens screens = getScreenContext(origin).getScreens();
                screens.showFromNavigation(screen);
            }

            sample.stop(UiMonitoring.createMenuTimer(meterRegistry, item.getId()));
        }

        protected Object getEntityToEdit(String screenId) {
            Object entityItem;

            Object entityToEdit = controllerProperties.stream()
                    .filter(prop -> "entityToEdit".equals(prop.getName()))
                    .findFirst()
                    .map(UiControllerProperty::getValue)
                    .orElse(null);
            if (entityToEdit instanceof Entity) {
                entityItem = entityToEdit;
            } else {
                String[] strings = screenId.split("[.]");
                String metaClassName;
                if (strings.length == 2) {
                    metaClassName = strings[0];
                } else if (strings.length == 3) {
                    metaClassName = strings[1];
                } else {
                    throw new UnsupportedOperationException("Incorrect screen parameters in menu item " + item.getId());
                }

                entityItem = metadata.create(metaClassName);
            }
            return entityItem;
        }

        @Nullable
        protected Screen createScreen(WindowInfo windowInfo, String screenId) {
            Screens screens = getScreenContext(origin).getScreens();

            Screen screen = screens.create(screenId, getOpenMode(descriptor),
                    new MapScreenOptions(Collections.emptyMap()));
            if (screen instanceof EditorScreen) {
                //noinspection unchecked
                ((EditorScreen<Object>) screen).setEntityToEdit(getEntityToEdit(screenId));
            }
            return screen;
        }

        @Override
        public String getDescription() {
            return String.format("Opening window: \"%s\"", screen);
        }

        protected OpenMode getOpenMode(Element descriptor) {
            String openModeStr = descriptor.attributeValue("openMode");
            if (StringUtils.isNotEmpty(openModeStr)) {
                return OpenMode.valueOf(openModeStr);
            }
            return OpenMode.NEW_TAB;
        }

        @Nullable
        protected Boolean getResizable(Element descriptor) {
            String resizable = descriptor.attributeValue("resizable");
            if (StringUtils.isNotEmpty(resizable)) {
                return Boolean.parseBoolean(resizable);
            }
            return null;
        }
    }

    protected class BeanCommand implements MenuItemCommand {

        protected FrameOwner origin;
        protected MenuItem item;

        protected String bean;
        protected String beanMethod;
        protected Map<String, Object> methodParameters;

        protected BeanCommand(FrameOwner origin, MenuItem item,
                              String bean, String beanMethod, Map<String, Object> methodParameters) {
            this.origin = origin;
            this.item = item;
            this.bean = bean;
            this.beanMethod = beanMethod;
            this.methodParameters = methodParameters;
        }

        @Override
        public void run() {
            userActionsLog.trace("Menu item {} triggered", item.getId());

            Timer.Sample sample = Timer.start(meterRegistry);

            Object beanInstance = applicationContext.getBean(bean);
            try {
                Method methodWithParams = MethodUtils.getAccessibleMethod(beanInstance.getClass(), beanMethod, Map.class);
                if (methodWithParams != null) {
                    methodWithParams.invoke(beanInstance, methodParameters);
                    return;
                }

                Method methodWithScreen = MethodUtils.getAccessibleMethod(beanInstance.getClass(), beanMethod, FrameOwner.class);
                if (methodWithScreen != null) {
                    methodWithScreen.invoke(beanInstance, origin);
                    return;
                }

                MethodUtils.invokeMethod(beanInstance, beanMethod, (Object[]) null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Unable to execute bean method", e);
            }

            sample.stop(UiMonitoring.createMenuTimer(meterRegistry, item.getId()));
        }

        @Override
        public String getDescription() {
            return String.format("Calling bean method: %s#%s", bean, beanMethod);
        }
    }

    protected class RunnableClassCommand implements MenuItemCommand {

        protected FrameOwner origin;
        protected MenuItem item;

        protected String runnableClass;
        protected Map<String, Object> methodParameters;

        protected RunnableClassCommand(FrameOwner origin, MenuItem item,
                                       String runnableClass, Map<String, Object> methodParameters) {
            this.origin = origin;
            this.item = item;
            this.runnableClass = runnableClass;
            this.methodParameters = methodParameters;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            userActionsLog.trace("Menu item {} triggered", item.getId());

            Timer.Sample sample = Timer.start(meterRegistry);

            Class<?> clazz = classManager.findClass(runnableClass);
            if (clazz == null) {
                throw new IllegalStateException(String.format("Can't load class: %s", runnableClass));
            }

            if (!Runnable.class.isAssignableFrom(clazz)
                    && !Consumer.class.isAssignableFrom(clazz)
                    && !MenuItemRunnable.class.isAssignableFrom(clazz)) {

                throw new IllegalStateException(
                        String.format("Class \"%s\" must implement Runnable or Consumer<Map<String, Object>> or MenuItemRunnable",
                                runnableClass));
            }

            Constructor<?> constructor;
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new DevelopmentException(String.format("Unable to get constructor of %s", runnableClass));
            }

            Object classInstance;
            try {
                classInstance = constructor.newInstance();
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new DevelopmentException(String.format("Failed to get a new instance of %s", runnableClass));
            }

            if (classInstance instanceof ApplicationContextAware) {
                ((ApplicationContextAware) classInstance).setApplicationContext(applicationContext);
            }

            if (classInstance instanceof MenuItemRunnable) {
                ((MenuItemRunnable) classInstance).run(origin, item);
            } else if (classInstance instanceof Consumer) {
                ((Consumer<Object>) classInstance).accept(methodParameters);
            } else {
                ((Runnable) classInstance).run();
            }

            sample.stop(UiMonitoring.createMenuTimer(meterRegistry, item.getId()));
        }

        @Override
        public String getDescription() {
            return String.format("Running \"%s\"", runnableClass);
        }
    }

    protected static class MenuItemProperty {

        protected String name;
        protected Object value;
        protected Object entity;

        protected ApplicationContext applicationContext;

        public MenuItemProperty(ApplicationContext applicationContext, Element property) {
            this.applicationContext = applicationContext;

            name = loadPropertyName(property)
                    .orElseThrow(() -> new IllegalStateException("Property cannot have empty name"));

            checkValueOrEntityProvided(property);

            value = loadValue(property);

            if (value == null) {
                entity = loadEntity(property);
            }
        }

        public String getName() {
            return name;
        }

        @Nullable
        public Object getValue() {
            return value;
        }

        @Nullable
        public Object getEntity() {
            return entity;
        }

        public Object getValueOrEntity() {
            return value != null ? value : entity;
        }

        protected Optional<String> loadPropertyName(Element property) {
            String propertyName = property.attributeValue("name");
            return Optional.ofNullable(Strings.emptyToNull(propertyName));
        }

        @Nullable
        protected Object loadValue(Element property) {
            String value = property.attributeValue("value");
            return !Strings.isNullOrEmpty(value) ? getTypedValue(value) : null;
        }

        protected Object getTypedValue(String value) {
            if (Boolean.TRUE.toString().equalsIgnoreCase(value)
                    || Boolean.FALSE.toString().equalsIgnoreCase(value)) {
                return Boolean.valueOf(value);
            }
            return value;
        }

        protected Object loadEntity(Element property) {
            String entityClass = property.attributeValue("entityClass");
            if (StringUtils.isEmpty(entityClass)) {
                throw new IllegalStateException(String.format("Screen property '%s' does not have entity class", name));
            }

            String entityId = property.attributeValue("entityId");
            if (StringUtils.isEmpty(entityId)) {
                throw new IllegalStateException(String.format("Screen entity property '%s' doesn't have entity id", name));
            }

            MetaClass metaClass = applicationContext.getBean(Metadata.class)
                    .getClass(ReflectionHelper.getClass(entityClass));

            Object id = parseEntityId(metaClass, entityId);
            if (id == null) {
                throw new RuntimeException(String.format("Unable to parse id value `%s` for entity '%s'",
                        entityId, entityClass));
            }

            LoadContext<Object> ctx = new LoadContext<>(metaClass)
                    .setId(id);

            String fetchPlan = loadEntityFetchPlan(property);
            if (StringUtils.isNotEmpty(fetchPlan)) {
                ctx.setFetchPlan(applicationContext.getBean(FetchPlanRepository.class)
                        .getFetchPlan(metaClass, fetchPlan));
            }

            Object entity = applicationContext.getBean(DataManager.class)
                    .load(ctx);

            if (entity == null) {
                throw new RuntimeException(String.format("Unable to load entity of class '%s' with id '%s'",
                        entityClass, entityId));
            }
            return entity;
        }

        @Nullable
        protected Object parseEntityId(MetaClass entityMetaClass, String entityId) {
            MetaProperty pkProperty = applicationContext.getBean(MetadataTools.class)
                    .getPrimaryKeyProperty(entityMetaClass);

            if (pkProperty == null) {
                return null;
            }

            Class<?> pkType = pkProperty.getJavaType();

            if (String.class.equals(pkType)) {
                return entityId;
            } else if (UUID.class.equals(pkType)) {
                return UUID.fromString(entityId);
            }

            Object id = null;

            try {
                if (Long.class.equals(pkType)) {
                    id = Long.valueOf(entityId);
                } else if (Integer.class.equals(pkType)) {
                    id = Integer.valueOf(entityId);
                }
            } catch (Exception e) {
                log.debug("Failed to parse entity id: '{}'", entityId, e);
            }

            return id;
        }

        protected String loadEntityFetchPlan(Element propertyElement) {
            String entityFetchPlan = propertyElement.attributeValue("entityFetchPlan");
            return StringUtils.isNotEmpty(entityFetchPlan)
                    ? entityFetchPlan
                    : propertyElement.attributeValue("entityView"); // for backward compatibility
        }

        protected void checkValueOrEntityProvided(Element propertyElement) {
            String value = propertyElement.attributeValue("value");
            String entityClass = propertyElement.attributeValue("entityClass");

            if (Strings.isNullOrEmpty(value) && Strings.isNullOrEmpty(entityClass)) {
                throw new IllegalStateException(
                        String.format("Screen property '%s' has neither value nor entity load info", name));
            }
        }
    }
}
