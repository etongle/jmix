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

package io.jmix.flowui.menu;

import com.google.common.base.Strings;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import io.jmix.core.AccessManager;
import io.jmix.core.MessageTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.accesscontext.FlowUiMenuContext;
import io.jmix.flowui.screen.ScreenInfo;
import io.jmix.flowui.screen.ScreenRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nullable;
import java.util.List;

// todo rp name
@org.springframework.stereotype.Component("flowui_ListMenuBuilder")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ListMenuBuilder {

    /* Lumo styles */
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

    protected MenuConfig menuConfig;
    protected ScreenRegistry screenRegistry;
    protected UiComponents uiComponents;
    protected MessageTools messageTools;
    protected AccessManager accessManager;

    @Autowired
    public ListMenuBuilder(MenuConfig menuConfig,
                           ScreenRegistry screenRegistry,
                           UiComponents uiComponents,
                           MessageTools messageTools,
                           AccessManager accessManager) {
        this.menuConfig = menuConfig;
        this.screenRegistry = screenRegistry;
        this.uiComponents = uiComponents;
        this.messageTools = messageTools;
        this.accessManager = accessManager;
    }

    public Component build() {
        return build(menuConfig.getRootItems());
    }

    protected Component build(List<MenuItem> rootItems) {
        UnorderedList menuList = uiComponents.create(UnorderedList.class);
        menuList.addClassNames(JMIX_LIST_MENU_STYLE_NAME, LIST_NONE_STYLE_NAME);

        for (MenuItem menuItem : rootItems) {
            Component component = createMenu(menuItem);
            if (component != null) {
                menuList.add(new ListItem(component));
            }
        }

        return menuList;
    }

    protected Details createMenuBarComponent(MenuItem menuItem) {
        Details menuItemComponent = new Details();
        menuItemComponent.addClassName(JMIX_MENUBAR_ITEM_STYLE_NAME);
        menuItemComponent.addClassNames(getClassNames(menuItem));
        menuItemComponent.setOpened(menuItem.isOpened());

        Span summary = new Span();
        summary.setText(menuConfig.getItemTitle(menuItem));
        summary.addClassNames(MENUBAR_SUMMARY_STYLE_NAME, TEXT_SMALL_STYLE_NAME);

        Icon icon = null;
        if (!Strings.isNullOrEmpty(menuItem.getIcon())) {
            icon = new Icon(VaadinIcon.valueOf(menuItem.getIcon()));
            icon.addClassName(MENUBAR_ICON_STYLE_NAME);
        }

        if (icon != null) {
            Div div = new Div();
            div.add(icon, summary);
            div.addClassName(JMIX_MENUBAR_SUMMARY_ICON_CONTAINER_STYLE_NAME);
            div.setTitle(getDescription(menuItem));
            menuItemComponent.setSummary(div);
        } else {
            summary.setTitle(getDescription(menuItem));
            menuItemComponent.setSummary(summary);
        }

        UnorderedList menuList = new UnorderedList();
        menuList.addClassNames(MENUBAR_LIST_STYLE_NAME, LIST_NONE_STYLE_NAME, MARGIN_NONE_STYLE_NAME,
                PADDING_NONE_STYLE_NAME);

        menuItemComponent.setContent(menuList);

        return menuItemComponent;
    }

    @Nullable
    protected Component createMenu(MenuItem menuItem) {
        if (menuItem.isMenu()) {
            if (menuItem.getChildren().isEmpty()) {
                return null;
            }
            Details menuBar = createMenuBarComponent(menuItem);
            UnorderedList content = getMenuBarContent(menuBar);
            for (MenuItem childItem : menuItem.getChildren()) {
                Component component = createMenu(childItem);
                if (component != null) {
                    content.add(new ListItem(component));
                }
            }
            // do not return empty menu bar
            if (!content.getChildren().findAny().isPresent()) {
                return null;
            }
            return menuBar;
        } else if (!Strings.isNullOrEmpty(menuItem.getScreen())) {
            return createMenuItemComponent(menuItem);
        } else {
            return null;
        }
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

    @Nullable
    protected RouterLink createMenuItemComponent(MenuItem menuItem) {
        if (!isPermitted(menuItem)) {
            return null;
        }

        ScreenInfo screenInfo = screenRegistry.getScreenInfo(menuItem.getScreen());

        RouterLink routerLink = new RouterLink();
        routerLink.addClassNames(JMIX_MENU_ITEM_LINK_STYLE_NAME, FLEX_STYLE_NAME);
        routerLink.addClassNames(getClassNames(menuItem));
        routerLink.setRoute(screenInfo.getControllerClass());

        if (!Strings.isNullOrEmpty(menuItem.getIcon())) {
            Icon icon = new Icon(VaadinIcon.valueOf(menuItem.getIcon()));
            icon.addClassName(LINK_ICON_STYLE_NAME);
            routerLink.add(icon);
        }

        Span text = new Span(menuConfig.getItemTitle(menuItem));
        text.addClassNames(LINK_TEXT_STYLE_NAME, FONT_MEDIUM_STYLE_NAME, TEXT_SMALL_STYLE_NAME);
        text.setTitle(getDescription(menuItem));

        routerLink.add(text);

        return routerLink;
    }


/*    protected void removeExtraSeparators(UnorderedList menuBar) {
        List<SideMenu.MenuItem> menuItems = menuBar.getMenuItems();
        for (SideMenu.MenuItem item : menuItems.toArray(new SideMenu.MenuItem[0])) {
            removeExtraSeparators(item);
            if (isMenuItemEmpty(item)) {
                menuBar.removeMenuItem(item);
            }
        }
    }*/

    /*protected void removeExtraSeparators(SideMenu.MenuItem item) {
        if (!item.hasChildren())
            return;

        // SideMenu does not support separator elements
        if (item.hasChildren()) {
            SideMenu.MenuItem[] menuItems =
                    item.getChildren().toArray(new SideMenu.MenuItem[0]);

            for (SideMenu.MenuItem child : menuItems) {
                removeExtraSeparators(child);
                if (isMenuItemEmpty(child)) {
                    item.removeChildItem(child);
                }
            }
        }
    }*/

    /*protected void createSubMenu(UnorderedList menu, MenuItem vItem, MenuItem parentItem) {
        if (isPermitted(parentItem)) {
            for (MenuItem child : parentItem.getChildren()) {
                *//*if (child.isSeparator()) {
                    continue;
                }*//*

                if (isPermitted(child)) {
                    SideMenu.MenuItem menuItem = menu.createMenuItem(child.getId(),
                            menuConfig.getItemCaption(child));

                    assignDescription(menuItem, child);
                    assignIcon(menuItem, child);
                    assignStyleName(menuItem, child);

                    if (child.getChildren().isEmpty()) {
                        menuItem.setCommand(createMenuBarCommand(child));

                        assignShortcut(webWindow, menuItem, child);

                        vItem.addChildItem(menuItem);
                    } else {
                        createSubMenu(webWindow, menu, menuItem, child);

                        assignExpanded(menuItem, child);

                        if (!isMenuItemEmpty(menuItem)) {
                            vItem.addChildItem(menuItem);
                        }
                    }
                }
            }
        }
    }*/

    /*@Nullable
    protected Consumer<SideMenu.MenuItem> createMenuBarCommand(final MenuItem item) {
        if (!item.getChildren().isEmpty() || item.isMenu())     //check item is menu
            return null;

        return createMenuCommandExecutor(item);
    }

    protected Consumer<SideMenu.MenuItem> createMenuCommandExecutor(MenuItem item) {
        return new MenuCommandExecutor(menuItemCommands, item);
    }*/

/*    protected boolean isMenuItemEmpty(SideMenu.MenuItem menuItem) {
        return !menuItem.hasChildren() && menuItem.getCommand() == null;
    }*/

    protected String getDescription(MenuItem menuItem) {
        String description = menuItem.getDescription();
        if (!Strings.isNullOrEmpty(description)) {
            return messageTools.loadString(description);
        }
        return "";
    }

    protected String[] getClassNames(MenuItem menuItem) {
        if (Strings.isNullOrEmpty(menuItem.getClassName())) {
            return new String[0];
        }

        return menuItem.getClassName().split(",");
    }

    // todo rp shortcut
    /*protected void assignShortcut(Window webWindow, SideMenu.MenuItem menuItem, MenuItem item) {
        KeyCombination itemShortcut = item.getShortcut();
        if (itemShortcut != null) {
            ShortcutListener shortcut = new SideMenuShortcutListener(menuItem, item);

            AbstractComponent windowImpl = webWindow.unwrap(AbstractComponent.class);
            windowImpl.addShortcutListener(shortcut);

            if (Strings.isNullOrEmpty(menuItem.getBadgeText())) {
                menuItem.setDescription(itemShortcut.format());
            }
        }
    }*/

    protected boolean isPermitted(MenuItem item) {
        if (Strings.isNullOrEmpty(item.getId())) {
            return true;
        }
        FlowUiMenuContext menuItemContext = new FlowUiMenuContext(item);
        accessManager.applyRegisteredConstraints(menuItemContext);
        return menuItemContext.isPermitted();
    }

    /*protected static class SideMenuShortcutListener extends ShortcutListener {
        protected SideMenu.MenuItem menuItem;

        public SideMenuShortcutListener(SideMenu.MenuItem menuItem, MenuItem item) {
            super("shortcut_" + item.getId(),
                    item.getShortcut().getKey().getCode(),
                    getShortcutModifiers(item.getShortcut().getModifiers()));
            this.menuItem = menuItem;
        }

        @Override
        public void handleAction(Object sender, Object target) {
            com.vaadin.ui.Component menuImpl = menuItem.getMenu().unwrap(com.vaadin.ui.Component.class);
            AppUI ui = (AppUI) menuImpl.getUI();
            if (ui.isAccessibleForUser(menuImpl)) {
                Consumer<SideMenu.MenuItem> command = menuItem.getCommand();
                if (command != null) {
                    command.accept(menuItem);
                }
            } else {
                LoggerFactory.getLogger(SideMenuShortcutListener.class)
                        .debug("Ignoring shortcut action because menu is inaccessible for user");
            }
        }
    }

    public static class MenuCommandExecutor implements Consumer<SideMenu.MenuItem> {
        private final MenuItem item;
        private final MenuItemCommands menuItemCommands;

        public MenuCommandExecutor(MenuItemCommands menuItemCommands, MenuItem item) {
            this.item = item;
            this.menuItemCommands = menuItemCommands;
        }

        @Override
        public void accept(SideMenu.MenuItem menuItem) {
            SideMenu menu = menuItem.getMenu();
            FrameOwner frameOwner = menu.getFrame().getFrameOwner();

            MenuItemCommand command = menuItemCommands.create(frameOwner, item);
            command.run();
        }
    }*/
}
