/*
 * Copyright 2022 Haulmont.
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

package io.jmix.flowui.component.menu;

import com.google.common.base.Strings;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import io.jmix.core.common.datastruct.Pair;
import io.jmix.core.common.util.Preconditions;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.menu.ListMenuBuilder;
import io.jmix.flowui.menu.MenuConfig;
import io.jmix.flowui.screen.Screen;
import io.jmix.flowui.screen.ScreenInfo;
import io.jmix.flowui.screen.ScreenRegistry;
import io.jmix.flowui.screen.UiController;
import io.jmix.flowui.sys.UiDescriptorUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Nullable;
import java.util.*;

public class ListMenu extends Composite<UnorderedList> implements HasSize, HasStyle,
        ApplicationContextAware, InitializingBean {

    protected static final String TEXT_SMALL_STYLE_NAME = "text-s";
    protected static final String LIST_NONE_STYLE_NAME = "list-none";
    protected static final String MARGIN_NONE_STYLE_NAME = "m-0";
    protected static final String PADDING_NONE_STYLE_NAME = "p-0";
    protected static final String FLEX_STYLE_NAME = "flex";
    protected static final String FONT_MEDIUM_STYLE_NAME = "font-medium";

    protected static final String JMIX_LIST_MENU_STYLE_NAME = "jmix-list-menu";

    protected static final String JMIX_MENUBAR_ITEM_STYLE_NAME = "jmix-menubar-item";
    protected static final String JMIX_MENUBAR_SUMMARY_ICON_CONTAINER_STYLE_NAME = "jmix-menubar-summary-icon-container";
    protected static final String MENUBAR_SUMMARY_STYLE_NAME = "menubar-summary";
    protected static final String MENUBAR_ICON_STYLE_NAME = "menubar-icon";
    protected static final String MENUBAR_LIST_STYLE_NAME = "menubar-list";

    protected static final String JMIX_MENU_ITEM_LINK_STYLE_NAME = "jmix-menu-item-link";
    protected static final String LINK_ICON_STYLE_NAME = "link-icon";
    protected static final String LINK_TEXT_STYLE_NAME = "link-text";

    protected ApplicationContext applicationContext;

    protected UiComponents uiComponents;
    protected ScreenRegistry screenRegistry;

    protected List<MenuItem> rootMenuItems = new ArrayList<>();

    protected Map<String, Pair<MenuItem, ListItem>> registrations = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        autowireDependencies();
        initComponent();
    }

    protected void autowireDependencies() {
        uiComponents = applicationContext.getBean(UiComponents.class);
        screenRegistry = applicationContext.getBean(ScreenRegistry.class);
    }

    protected void initComponent() {
        getContent().addClassNames(JMIX_LIST_MENU_STYLE_NAME, LIST_NONE_STYLE_NAME);
    }

    /**
     * Loads menu items from {@link MenuConfig}.
     */
    public void loadMenuConfig() {
        applicationContext.getBean(ListMenuBuilder.class)
                .build(this);
    }

    /**
     * Gets menu item from the menu by its ID.
     *
     * @param id menu item id
     * @return menu item or {@code null} if not found
     */
    @Nullable
    public MenuItem getMenuItem(String id) {
        if (registrations.containsKey(id)) {
            return registrations.get(id).getFirst();
        }
        return null;
    }

    /**
     * @return immutable list of root menu items
     */
    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(rootMenuItems);
    }

    /**
     * Adds menu item and its children to the menu.
     *
     * @param menuItem menu item to add
     */
    public void addMenuItem(MenuItem menuItem) {
        Preconditions.checkNotNullArgument(menuItem);
        checkItemIdDuplicate(menuItem.getId());

        // create and register
        ListItem item = createMenuRecursively(menuItem);

        rootMenuItems.add(menuItem);
        getContent().add(item);

        attachMenuItemRecursively(menuItem);
    }

    /**
     * Adds menu item and its children to the menu in specified index.
     * <p>
     * If root menu items already contain provided menu item, it will be moved to the provided index.
     *
     * @param menuItem menu item to add
     * @param index    index to add menu item in the root items
     */
    public void addMenuItem(MenuItem menuItem, int index) {
        Preconditions.checkNotNullArgument(menuItem);

        MenuItem itemToAdd = menuItem;
        if (rootMenuItems.contains(itemToAdd)) {
            int existingIndex = rootMenuItems.indexOf(itemToAdd);
            if (index > existingIndex) {
                index--;
            }

            //noinspection OptionalGetWithoutIsPresent
            itemToAdd = rootMenuItems.stream()
                    .filter(item -> item.equals(menuItem))
                    .findFirst().get();

            removeMenuItem(itemToAdd);
        }

        // create and register
        ListItem menuItemComponent = createMenuRecursively(itemToAdd);

        rootMenuItems.add(index, itemToAdd);
        getContent().addComponentAtIndex(index, menuItemComponent);

        attachMenuItemRecursively(itemToAdd);
    }

    /**
     * Removes menu item and its children from the menu.
     *
     * @param menuItem menu item to remove
     */
    public void removeMenuItem(MenuItem menuItem) {
        Preconditions.checkNotNullArgument(menuItem);

        if (!menuItem.isAttachedToMenu()
                || menuItem.getMenuComponent() != this) {
            throw new IllegalArgumentException(MenuItem.class.getSimpleName() + "is not attached to the menu");
        }

        Pair<MenuItem, ListItem> menuItemEntry = registrations.get(menuItem.getId());
        if (menuItemEntry == null) {
            return;
        }
        menuItem = menuItemEntry.getFirst();

        // if is root
        rootMenuItems.remove(menuItem);

        // remove component
        menuItemEntry.getSecond().getParent()
                .map(parent -> (UnorderedList) parent)
                .ifPresent(parent -> parent.remove(menuItemEntry.getSecond()));

        detachMenuItemRecursively(menuItem);
        unregisterMenuItemRecursively(menuItem);
    }

    /**
     * Removes root menu item by index.
     *
     * @param index index to remove from root menu items
     */
    public void removeMenuItem(int index) {
        MenuItem menuItem = rootMenuItems.get(index);
        removeMenuItem(menuItem);
    }

    /**
     * Removes all menu items from the menu.
     */
    public void removeAllMenuItems() {
        for (MenuItem menuItem : new ArrayList<>(rootMenuItems)) {
            removeMenuItem(menuItem);
        }
    }

    protected void addChildren(MenuItem parent, MenuItem childItem) {
        UnorderedList content = getMenuBarContent(parent);

        ListItem menuItemComponent = createMenuRecursively(childItem);

        content.add(menuItemComponent);
    }

    protected void addChildren(MenuItem parent, MenuItem childItem, int index) {
        UnorderedList content = getMenuBarContent(parent);

        ListItem menuItemComponent = createMenuRecursively(childItem);

        content.addComponentAtIndex(index, menuItemComponent);
    }

    protected ListItem createMenuRecursively(MenuItem menuItem) {
        checkItemIdDuplicate(menuItem.getId());

        if (menuItem.isMenu()) {
            MenuBarItem menuBarItem = (MenuBarItem) menuItem;

            Details menuBar = createMenuBarComponent(menuBarItem);
            UnorderedList content = getMenuBarContent(menuBar);

            for (MenuItem childItem : menuBarItem.getChildren()) {
                ListItem component = createMenuRecursively(childItem);
                content.add(component);
            }

            ListItem menuBarComponent = new ListItem(menuBar);

            registerMenuItem(menuItem, menuBarComponent);

            return menuBarComponent;
        } else {
            RouterLink link = createMenuItemComponent(menuItem);
            ListItem menuItemComponent = new ListItem(link);

            registerMenuItem(menuItem, menuItemComponent);

            return menuItemComponent;
        }
    }

    protected void attachMenuItemRecursively(MenuItem menuItem) {
        menuItem.setMenuComponent(this);

        if (menuItem.isMenu()) {
            for (MenuItem item : ((MenuBarItem) menuItem).getChildren()) {
                attachMenuItemRecursively(item);
            }
        }
    }

    protected void detachMenuItemRecursively(MenuItem menuItem) {
        menuItem.setMenuComponent(null);

        if (menuItem.isMenu()) {
            for (MenuItem item : ((MenuBarItem) menuItem).getChildren()) {
                detachMenuItemRecursively(item);
            }
        }
    }

    protected void unregisterMenuItemRecursively(MenuItem menuItem) {
        registrations.remove(menuItem.getId());

        if (menuItem.isMenu()) {
            for (MenuItem item : ((MenuBarItem) menuItem).getChildren()) {
                unregisterMenuItemRecursively(item);
            }
        }
    }

    protected void registerMenuItem(MenuItem menuItem, ListItem menuItemComponent) {
        registrations.put(menuItem.getId(), new Pair<>(menuItem, menuItemComponent));
    }

    protected RouterLink createMenuItemComponent(MenuItem menuItem) {
        RouterLink routerLink = new RouterLink();
        routerLink.addClassNames(JMIX_MENU_ITEM_LINK_STYLE_NAME, FLEX_STYLE_NAME);
        routerLink.addClassNames(menuItem.getClassNames().toArray(new String[0]));
        routerLink.setRoute(getControllerClass(menuItem));
        routerLink.setHighlightCondition(HighlightConditions.sameLocation());

        if (menuItem.getIcon() != null) {
            Icon icon = new Icon(menuItem.getIcon());
            icon.addClassName(LINK_ICON_STYLE_NAME);
            routerLink.add(icon);
        }

        Span text = new Span(getTitle(menuItem));
        text.addClassNames(LINK_TEXT_STYLE_NAME, FONT_MEDIUM_STYLE_NAME, TEXT_SMALL_STYLE_NAME);
        text.setTitle(Strings.nullToEmpty(menuItem.getDescription()));

        routerLink.add(text);

        return routerLink;
    }

    protected Details createMenuBarComponent(MenuBarItem menuBarItem) {
        Details menuItemComponent = new Details();
        menuItemComponent.addClassName(JMIX_MENUBAR_ITEM_STYLE_NAME);
        menuItemComponent.addClassNames(menuBarItem.getClassNames().toArray(new String[0]));
        menuItemComponent.setOpened(menuBarItem.isOpened());

        Span summary = new Span();
        summary.setText(getTitle(menuBarItem));
        summary.addClassNames(MENUBAR_SUMMARY_STYLE_NAME, TEXT_SMALL_STYLE_NAME);

        Icon icon = null;
        if (menuBarItem.getIcon() != null) {
            icon = new Icon(menuBarItem.getIcon());
            icon.addClassName(MENUBAR_ICON_STYLE_NAME);
        }

        if (icon != null) {
            Div div = new Div();
            div.add(icon, summary);
            div.addClassName(JMIX_MENUBAR_SUMMARY_ICON_CONTAINER_STYLE_NAME);
            div.setTitle(Strings.nullToEmpty(menuBarItem.getDescription()));
            menuItemComponent.setSummary(div);
        } else {
            summary.setTitle(Strings.nullToEmpty(menuBarItem.getDescription()));
            menuItemComponent.setSummary(summary);
        }

        UnorderedList menuList = new UnorderedList();
        menuList.addClassNames(MENUBAR_LIST_STYLE_NAME, LIST_NONE_STYLE_NAME, MARGIN_NONE_STYLE_NAME,
                PADDING_NONE_STYLE_NAME);

        menuItemComponent.setContent(menuList);

        return menuItemComponent;
    }

    protected Class<? extends Screen<?>> getControllerClass(MenuItem menuItem) {
        Class<? extends Screen<?>> controllerClass = menuItem.getControllerClass();

        if (controllerClass != null && isSupportedScreen(controllerClass)) {
            return menuItem.getControllerClass();
        }
        ScreenInfo screenInfo = screenRegistry.getScreenInfo(menuItem.getId());
        return screenInfo.getControllerClass();
    }

    protected UnorderedList getMenuBarContent(MenuItem menuItem) {
        Pair<MenuItem, ListItem> item = registrations.get(menuItem.getId());

        Details menuBarComponent = item.getSecond().getChildren()
                .findFirst()
                .map(details -> (Details) details)
                .orElseThrow(() -> new IllegalStateException(ListItem.class.getSimpleName() + "cannot be empty"));

        return getMenuBarContent(menuBarComponent);
    }

    protected UnorderedList getMenuBarContent(Details menuBar) {
        return menuBar.getContent()
                .filter(component -> component instanceof UnorderedList)
                .map(component -> (UnorderedList) component)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Menu bar component does not have content component: "
                                + UnorderedList.class.getName()));
    }

    protected String getTitle(MenuItem menuItem) {
        return Strings.isNullOrEmpty(menuItem.getTitle())
                ? menuItem.getId()
                : menuItem.getTitle();
    }

    protected boolean isSupportedScreen(Class<?> targetView) {
        return Screen.class.isAssignableFrom(targetView)
                && targetView.getAnnotation(UiController.class) != null;
    }

    protected void checkItemIdDuplicate(String id) {
        if (registrations.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Menu item with id \"%s\" already exists", id));
        }
    }

    /**
     * Describes menu item that allows user to navigate to screen.
     */
    public static class MenuItem {
        protected String id;
        protected String title;
        protected String description;
        protected VaadinIcon icon;
        protected Class<? extends Screen<?>> controllerClass;
        protected List<String> classNames;

        protected ListMenu menuComponent;

        public MenuItem(String id) {
            this.id = id;
        }

        /**
         * Creates menu item that should navigate to the screen with provided ID.
         *
         * @param screenId screen ID
         * @return menu item
         */
        public static MenuItem create(String screenId) {
            return new MenuItem(screenId);
        }

        /**
         * Creates menu item that should navigate to the screen with provided class.
         *
         * @param controllerClass screen class
         * @return menu item
         */
        public static MenuItem create(Class<? extends Screen<?>> controllerClass) {
            return new MenuItem(UiDescriptorUtils.getInferredScreenId(controllerClass))
                    .withControllerClass(controllerClass);
        }

        /**
         * Creates menu bar that can contains other menu items.
         *
         * @param id menu item id
         * @return menu bar
         */
        public static MenuBarItem createMenuBar(String id) {
            return new MenuBarItem(id);
        }

        public String getId() {
            return id;
        }

        /**
         * @return title or {@code null} if not set
         */
        @Nullable
        public String getTitle() {
            return title;
        }

        /**
         * Sets displayed menu item text.
         *
         * @param title displayed text
         */
        public void setTitle(@Nullable String title) {
            this.title = title;
        }

        /**
         * Sets displayed menu item text.
         *
         * @param title displayed text
         * @return current menu instance
         */
        public MenuItem withTitle(@Nullable String title) {
            this.title = title;
            return this;
        }

        /**
         * @return menu item description or {@code null} if not set.
         */
        @Nullable
        public String getDescription() {
            return description;
        }

        /**
         * Sets menu item description that should be shown when the user moves cursor on item.
         *
         * @param description description to set
         */
        public void setDescription(@Nullable String description) {
            this.description = description;
        }

        /**
         * Sets menu item description that should be shown when the user moves cursor on item.
         *
         * @param description description to set
         * @return current menu instance
         */
        public MenuItem withDescription(@Nullable String description) {
            this.description = description;
            return this;
        }

        /**
         * @return icon or {@code null} if not set
         */
        @Nullable
        public VaadinIcon getIcon() {
            return icon;
        }

        /**
         * Sets icon that should be displayed to the left of the {@link #setTitle(String)}.
         *
         * @param icon icon to set
         */
        public void setIcon(@Nullable VaadinIcon icon) {
            this.icon = icon;
        }

        /**
         * Sets icon that should be displayed to the left of the {@link #setTitle(String)}.
         *
         * @param icon icon to set
         * @return current menu instance
         */
        public MenuItem withIcon(@Nullable VaadinIcon icon) {
            this.icon = icon;
            return this;
        }

        /**
         * @return screen class or {@code null} if not set
         */
        @Nullable
        public Class<? extends Screen<?>> getControllerClass() {
            return controllerClass;
        }

        /**
         * Sets screen class that should be shown when the user clicks on the menu item.
         *
         * @param controllerClass screen class to set
         */
        public void setControllerClass(@Nullable Class<? extends Screen<?>> controllerClass) {
            this.controllerClass = controllerClass;
        }

        /**
         * Sets screen class that should be shown when the user clicks on the menu item. If not set, {@link #getId()}
         * will be used as screen id to navigate.
         *
         * @param controllerClass screen class to set
         * @return current menu instance
         */
        public MenuItem withControllerClass(@Nullable Class<? extends Screen<?>> controllerClass) {
            this.controllerClass = controllerClass;
            return this;
        }

        /**
         * @return class names or empty list
         */
        public List<String> getClassNames() {
            return CollectionUtils.isEmpty(classNames)
                    ? Collections.emptyList()
                    : Collections.unmodifiableList(classNames);
        }

        /**
         * Sets class names that should be added to the menu item.
         *
         * @param classNames class names to add
         */
        public void setClassNames(List<String> classNames) {
            Preconditions.checkNotNullArgument(classNames);
            this.classNames = classNames;
        }

        /**
         * Sets class names that should be added to the menu item.
         *
         * @param classNames class names to add
         * @return current menu instance
         */
        public MenuItem withClassNames(List<String> classNames) {
            Preconditions.checkNotNullArgument(classNames);
            this.classNames = classNames;
            return this;
        }

        /**
         * Adds class names that should be added to the menu item.
         *
         * @param classNames class names to add
         */
        public void addClassNames(String... classNames) {
            Preconditions.checkNotNullArgument(classNames);

            if (this.classNames == null) {
                this.classNames = new ArrayList<>();
            }

            this.classNames.addAll(Arrays.asList(classNames));
        }

        /**
         * @return {@code true} if menu item is {@link MenuBarItem} that contains other items,
         * {@code false} otherwise
         */
        public boolean isMenu() {
            return false;
        }

        /**
         * @return menu component that contains given menu item
         */
        @Nullable
        public ListMenu getMenuComponent() {
            return menuComponent;
        }

        protected void setMenuComponent(@Nullable ListMenu menu) {
            this.menuComponent = menu;
        }

        /**
         * @return {@code true} if menu item is attached to the menu component,
         * {@code false} otherwise
         */
        public boolean isAttachedToMenu() {
            return getMenuComponent() != null;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            return id.equals(((MenuItem) obj).getId());
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public String toString() {
            return "{\"id\": \"" + getId() + "\"}";
        }
    }

    /**
     * Describes menu item that can contain other menu items.
     */
    public static class MenuBarItem extends MenuItem {

        protected List<MenuItem> children;
        protected boolean isOpened;

        public MenuBarItem(String id) {
            super(id);
        }

        @Override
        public MenuBarItem withTitle(@Nullable String title) {
            return (MenuBarItem) super.withTitle(title);
        }

        @Override
        public MenuBarItem withDescription(@Nullable String description) {
            return (MenuBarItem) super.withDescription(description);
        }

        @Override
        public MenuBarItem withIcon(@Nullable VaadinIcon icon) {
            return (MenuBarItem) super.withIcon(icon);
        }

        @Override
        public MenuBarItem withControllerClass(@Nullable Class<? extends Screen<?>> controllerClass) {
            return (MenuBarItem) super.withControllerClass(controllerClass);
        }

        @Override
        public MenuBarItem withClassNames(List<String> classNames) {
            return (MenuBarItem) super.withClassNames(classNames);
        }

        /**
         * @return {@code true} if menu bar item initially should open list of sub menu items,
         * {@code false} otherwise
         */
        public boolean isOpened() {
            return isOpened;
        }

        /**
         * Sets whether menu bar item should open sub menu when it's added to the menu.
         *
         * @param opened open option
         */
        public void setOpened(boolean opened) {
            isOpened = opened;
        }

        /**
         * Sets whether menu bar item should open sub menu when it's added to the menu.
         *
         * @param opened open option
         * @return current menu bar item instance
         */
        public MenuBarItem withOpened(boolean opened) {
            isOpened = opened;
            return this;
        }

        /**
         * Adds menu item to the sub menu list.
         *
         * @param menuItem menu item to add
         */
        public void addChildItem(MenuItem menuItem) {
            Preconditions.checkNotNullArgument(menuItem);

            if (children == null) {
                children = new ArrayList<>();
            }

            if (isAttachedToMenu()) {
                menuComponent.addChildren(this, menuItem);
            }

            children.add(menuItem);
        }

        /**
         * Adds menu item to the sub menu list by index.
         * <p>
         * If child items already contain provided menu item, it will be moved to the provided index.
         *
         * @param menuItem menu item to add
         */
        public void addChildItem(MenuItem menuItem, int index) {
            Preconditions.checkNotNullArgument(menuItem);

            if (children == null) {
                children = new ArrayList<>();
            }

            if (children.contains(menuItem)) {
                int existingIndex = children.indexOf(menuItem);
                if (index > existingIndex) {
                    index--;
                }

                children.remove(menuItem);
                if (isAttachedToMenu()) {
                    menuComponent.removeMenuItem(menuItem);
                }
            }

            children.add(index, menuItem);
            if (isAttachedToMenu()) {
                menuComponent.addChildren(this, menuItem, index);
            }
        }

        /**
         * Removes menu item from the child items.
         *
         * @param menuItem menu item to remove
         */
        public void removeChildItem(MenuItem menuItem) {
            if (CollectionUtils.isEmpty(children)) {
                return;
            }

            if (!menuItem.isAttachedToMenu()
                    || menuItem.getMenuComponent() != menuComponent) {
                throw new IllegalArgumentException(MenuItem.class.getSimpleName() + "is not attached to the menu");
            }

            children.stream()
                    .filter(item -> item.equals(menuItem))
                    .findFirst()
                    .ifPresent((item) -> {
                        if (isAttachedToMenu()) {
                            menuComponent.removeMenuItem(menuItem);
                        }
                        children.remove(menuItem);
                    });
        }

        /**
         * Removes menu item from the child items by index.
         *
         * @param index index to remove
         */
        public void removeChildItem(int index) {
            if (CollectionUtils.isEmpty(children)) {
                return;
            }

            if (isAttachedToMenu()) {
                menuComponent.removeMenuItem(children.get(index));
            }

            children.remove(index);
        }

        /**
         * Removes all child items.
         */
        public void removeAllChildItems() {
            if (CollectionUtils.isEmpty(children)) {
                return;
            }

            if (isAttachedToMenu()) {
                for (MenuItem menuItem : children) {
                    menuComponent.removeMenuItem(menuItem);
                }
            }

            children.clear();
            children = null;
        }

        /**
         * @return immutable list of child items
         */
        public List<MenuItem> getChildren() {
            return CollectionUtils.isEmpty(children)
                    ? Collections.emptyList()
                    : Collections.unmodifiableList(children);
        }

        /**
         * @return {@code true} if menu bar item contains menu items,
         * {@code false} otherwise
         */
        public boolean hasChildren() {
            return CollectionUtils.isNotEmpty(children);
        }

        @Override
        public boolean isMenu() {
            return true;
        }
    }
}
