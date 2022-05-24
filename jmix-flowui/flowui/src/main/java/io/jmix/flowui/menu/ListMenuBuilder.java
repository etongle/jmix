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
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import io.jmix.core.MessageTools;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.screen.Screen;
import io.jmix.flowui.screen.ScreenInfo;
import io.jmix.flowui.screen.ScreenRegistry;
import io.jmix.flowui.sys.FlowuiAccessChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.annotation.Nullable;
import java.util.List;

// todo rp name
@org.springframework.stereotype.Component("flowui_ListMenuBuilder")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ListMenuBuilder {
    private static final Logger log = LoggerFactory.getLogger(ListMenuBuilder.class);

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
    protected FlowuiAccessChecker flowuiAccessChecker;

    @Autowired
    public ListMenuBuilder(MenuConfig menuConfig,
                           ScreenRegistry screenRegistry,
                           UiComponents uiComponents,
                           MessageTools messageTools,
                           FlowuiAccessChecker flowuiAccessChecker) {
        this.menuConfig = menuConfig;
        this.screenRegistry = screenRegistry;
        this.uiComponents = uiComponents;
        this.messageTools = messageTools;
        this.flowuiAccessChecker = flowuiAccessChecker;
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
                log.debug("Menu item '{}' is skipped as it does not have children", menuItem.getId());
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
                log.debug("Menu item '{}' is skipped as it does not have children or they are not permitted by " +
                        "access constraint", menuItem.getId());
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
            log.debug("Menu item '{}' is not permitted by access constraint", menuItem.getId());
            return null;
        }

        RouterLink routerLink = new RouterLink();
        routerLink.addClassNames(JMIX_MENU_ITEM_LINK_STYLE_NAME, FLEX_STYLE_NAME);
        routerLink.addClassNames(getClassNames(menuItem));
        routerLink.setRoute(getControllerClass(menuItem));
        routerLink.setHighlightCondition(HighlightConditions.sameLocation());

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

    protected Class<? extends Screen<?>> getControllerClass(MenuItem menuItem) {
        ScreenInfo screenInfo = screenRegistry.getScreenInfo(menuItem.getScreen());
        return screenInfo.getControllerClass();
    }

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

    protected boolean isPermitted(MenuItem menuItem) {
        return flowuiAccessChecker.isMenuPermitted(menuItem);
    }
}
