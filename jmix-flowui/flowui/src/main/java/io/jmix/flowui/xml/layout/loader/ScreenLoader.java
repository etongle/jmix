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
package io.jmix.flowui.xml.layout.loader;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import io.jmix.core.common.util.Preconditions;
import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.model.ScreenData;
import io.jmix.flowui.model.impl.ScreenDataXmlLoader;
import io.jmix.flowui.screen.Screen;
import io.jmix.flowui.screen.ScreenActions;
import io.jmix.flowui.screen.UiControllerUtils;
import io.jmix.flowui.xml.layout.ComponentRootLoader;
import io.jmix.flowui.xml.layout.loader.container.AbstractContainerLoader;
import io.jmix.flowui.xml.layout.support.ActionLoaderSupport;
import org.dom4j.Element;

public class ScreenLoader extends AbstractScreenLoader<Screen<?>> {

    public static final String SCREEN_ROOT = "screen";
    public static final String CONTENT_NAME = "layout";

    @Override
    public void createContent() {
        Element content = element.element(CONTENT_NAME);
        if (content == null) {
            throw new GuiDevelopmentException("Required '" + CONTENT_NAME + "' element is not found", context);
        }

        if (resultComponent.getContent() instanceof HasComponents) {
            createSubComponents(((HasComponents) resultComponent.getContent()), content);
        } else {
            // TODO: gg, throw an exception?
        }
    }

    @Override
    public void loadComponent() {
        getScreenLoader().loadScreenData(element);
        getScreenLoader().loadScreenActions(element);

//        loadDialogOptions(resultComponent, element);

//        assignXmlDescriptor(resultComponent, element);
//        loadCaption(resultComponent, element);
//        loadDescription(resultComponent, element);
//        loadIcon(resultComponent, element);

        Element layoutElement = element.element("layout");
        if (layoutElement == null) {
            throw new GuiDevelopmentException("Required 'layout' element is not found", context);
        }

        Component screenRootComponent = resultComponent.getContent();

        if (screenRootComponent instanceof ThemableLayout) {
            componentLoader().loadThemableAttributes(((ThemableLayout) screenRootComponent), layoutElement);
        }

        if (screenRootComponent instanceof FlexComponent) {
            componentLoader().loadFlexibleAttributes(((FlexComponent) screenRootComponent), layoutElement);
        }

        if (screenRootComponent instanceof HasEnabled) {
            componentLoader().loadEnabled(((HasEnabled) screenRootComponent), layoutElement);
        }

        if (screenRootComponent instanceof FlexComponent) {
            loadSubComponentsAndExpand(((FlexComponent) screenRootComponent), layoutElement);
        } else {
            loadSubComponents();;
        }
    }

    /*protected void loadFocusedComponent(Window window, Element element) {
        String focusMode = element.attributeValue("focusMode");
        String componentId = element.attributeValue("focusComponent");
        if (!"NO_FOCUS".equals(focusMode)) {
            window.setFocusComponent(componentId);
        }
    }*/

    /*protected void loadFacets(Window resultComponent, Element windowElement) {
        Element facetsElement = windowElement.element("facets");
        if (facetsElement != null) {
            List<Element> facetElements = facetsElement.elements();

            for (Element facetElement : facetElements) {
                FacetLoader loader = applicationContext.getBean(FacetLoader.class);
                Facet facet = loader.load(facetElement, getComponentContext());

                resultComponent.addFacet(facet);
            }
        }
    }*/

    /*@Override
    protected Action loadDeclarativeAction(ActionsHolder actionsHolder, Element element) {
        Action action = loadDeclarativeActionByType(actionsHolder, element);
        if (action != null) {
            return action;
        }

        return super.loadDeclarativeAction(actionsHolder, element);
    }*/
}
