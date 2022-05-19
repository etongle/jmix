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
import io.jmix.core.MessageTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.screen.ScreenInfo;
import io.jmix.flowui.screen.ScreenRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nullable;
import java.util.List;

@org.springframework.stereotype.Component("flowui_ListMenuBuilder")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ListMenuBuilder {

    protected MenuConfig menuConfig;
    protected ScreenRegistry screenRegistry;
    protected UiComponents uiComponents;
    protected MessageTools messageTools;

    @Autowired
    public ListMenuBuilder(MenuConfig menuConfig,
                           ScreenRegistry screenRegistry,
                           UiComponents uiComponents,
                           MessageTools messageTools) {
        this.menuConfig = menuConfig;
        this.screenRegistry = screenRegistry;
        this.uiComponents = uiComponents;
        this.messageTools = messageTools;
    }

    public UnorderedList build() {
        return build(menuConfig.getRootItems());
    }

    protected UnorderedList build(List<MenuItem> rootItems) {
        UnorderedList menuList = uiComponents.create(UnorderedList.class);
        // todo rp
        menuList.addClassNames("jmix-list-menu", "list-none");

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
        menuItemComponent.addClassName("jmix-menubar-item");
        menuItemComponent.addClassNames(getClassNames(menuItem));
        menuItemComponent.setOpened(menuItem.isOpened());

        Span summary = new Span();
        summary.setText(menuConfig.getItemTitle(menuItem));
        summary.addClassNames("menubar-summary", "text-s");

        Icon icon = null;
        if (!Strings.isNullOrEmpty(menuItem.getIcon())) {
            icon = new Icon(VaadinIcon.valueOf(menuItem.getIcon()));
            icon.addClassName("menubar-icon");
        }

        if (icon != null) {
            Div div = new Div();
            div.add(icon, summary);
            div.addClassName("jmix-menubar-summary-icon-container");
            div.setTitle(getDescription(menuItem));
            menuItemComponent.setSummary(div);
        } else {
            summary.setTitle(getDescription(menuItem));
            menuItemComponent.setSummary(summary);
        }

        UnorderedList menuList = new UnorderedList();
        // todo rp classes
        menuList.addClassNames("menubar-list", "list-none", "m-0", "p-0");

        menuItemComponent.setContent(menuList);

        return menuItemComponent;
    }

    @Nullable
    protected Component createMenu(MenuItem menuItem) {
        if (menuItem.isMenu()) {
            Details menuBar = createMenuBarComponent(menuItem);
            if (!menuItem.getChildren().isEmpty()) {
                UnorderedList content = getMenuBarContent(menuBar);
                for (MenuItem childItem : menuItem.getChildren()) {
                    Component component = createMenu(childItem);
                    if (component != null) {
                        content.add(new ListItem(component));
                    }
                }
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

    protected RouterLink createMenuItemComponent(MenuItem menuItem) {
        ScreenInfo screenInfo = screenRegistry.getScreenInfo(menuItem.getScreen());

        RouterLink routerLink = new RouterLink();
        // todo rp
        routerLink.addClassNames("jmix-menu-item-link", "flex", "relative", "text-secondary");
        routerLink.addClassNames(getClassNames(menuItem));
        routerLink.setRoute(screenInfo.getControllerClass());

        if (!Strings.isNullOrEmpty(menuItem.getIcon())) {
            Icon icon = new Icon(VaadinIcon.valueOf(menuItem.getIcon()));
            icon.addClassName("link-icon");
            routerLink.add(icon);
        }

        Span text = new Span(menuConfig.getItemTitle(menuItem));
        text.addClassNames("link-text", "font-medium", "text-s");
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

    protected void createMenuBarItem(UnorderedList menuList, MenuItem item) {
//        if (isPermitted(item)) {

        Component menuItem = createMenuListItem(item);

//            assignShortcut(webWindow, menuItem, item);

/*            if (!isMenuItemEmpty(menuItem)) {
                menuList.add(menuItem);
            }*/

        menuList.add(menuItem);
//        }
    }

    protected Component createMenuListItem(MenuItem item) {
        if (item.isMenu()) {
            Span menuItem = new Span();
            menuItem.setText(item.getTitle());
            return menuItem;
        } else {
            ScreenInfo screenInfo = screenRegistry.getScreenInfo(item.getScreen());
            RouterLink link = new RouterLink(item.getTitle(), screenInfo.getControllerClass());
            return link;
        }
    }

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

    // todo rp expanded
    /*protected void assignExpanded(SideMenu.MenuItem menuItem, MenuItem item) {
        menuItem.setExpanded(item.isExpanded());
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

    // todo rp security
    /*protected boolean isPermitted(MenuItem item) {
        if (Strings.isNullOrEmpty(item.getId()) || item.isSeparator()) {
            return true;
        }
        UiMenuContext menuItemContext = new UiMenuContext(item);
        accessManager.applyRegisteredConstraints(menuItemContext);
        return menuItemContext.isPermitted();
    }*/

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
