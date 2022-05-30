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

package io.jmix.flowui.kit;

import com.vaadin.flow.component.html.*;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.meta.StudioFlowComponent;
import io.jmix.flowui.kit.meta.StudioFlowComponents;
import io.jmix.flowui.kit.meta.StudioFlowProperty;
import io.jmix.flowui.kit.meta.StudioFlowPropertyType;

@StudioFlowComponents
public interface StudioFlowUiKit {

    @StudioFlowComponent(
            name = "Button",
            category = "Components",
            xmlElement = "button",
            icon = "io/jmix/flowui/kit/meta/component/button.svg",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "autofocus", type = StudioFlowPropertyType.BOOLEAN,
                            defaultValue = "false"),
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "disableOnClick", type = StudioFlowPropertyType.BOOLEAN,
                            defaultValue = "false"),
                    @StudioFlowProperty(xmlAttribute = "enable", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "icon", type = StudioFlowPropertyType.ICON),
                    @StudioFlowProperty(xmlAttribute = "iconAfterText", type = StudioFlowPropertyType.BOOLEAN,
                            defaultValue = "false"),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.COMPONENT_ID),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"small", "large", "tertiary", "tertiary-inline", "primary", "success", "error",
                                    "contrast", "icon", "contained", "outlined"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    JmixButton button();

    //HTML
    @StudioFlowComponent(
            name = "Anchor",
            category = "Components",
            xmlElement = "anchor",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "href", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "target", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.html.AnchorTarget", defaultValue = "DEFAULT",
                            options = {"DEFAULT", "SELF", "BLANK", "PARENT", "TOP"}),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Anchor anchor();

    @StudioFlowComponent(
            name = "Article",
            category = "Components",
            xmlElement = "article",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Article article();


    @StudioFlowComponent(
            name = "Aside",
            category = "Components",
            xmlElement = "aside",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Aside aside();

    @StudioFlowComponent(
            name = "DescriptionList",
            category = "Components",
            xmlElement = "descriptionList",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    DescriptionList descriptionList();

    @StudioFlowComponent(
            name = "Term",
            category = "Components",
            xmlElement = "term",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    DescriptionList.Term term();

    @StudioFlowComponent(
            name = "Description",
            category = "Components",
            xmlElement = "description",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    DescriptionList.Description description();

    @StudioFlowComponent(
            name = "Div",
            category = "Components",
            xmlElement = "div",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Div div();

    @StudioFlowComponent(
            name = "Emphasis",
            category = "Components",
            xmlElement = "emphasis",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Emphasis emphasis();

    @StudioFlowComponent(
            name = "Footer",
            category = "Components",
            xmlElement = "footer",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Footer footer();

    @StudioFlowComponent(
            name = "H1",
            category = "Components",
            xmlElement = "h1",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    H1 h1();

    @StudioFlowComponent(
            name = "H2",
            category = "Components",
            xmlElement = "h2",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    H2 h2();

    @StudioFlowComponent(
            name = "H3",
            category = "Components",
            xmlElement = "h3",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    H3 h3();

    @StudioFlowComponent(
            name = "H4",
            category = "Components",
            xmlElement = "h4",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    H4 h4();

    @StudioFlowComponent(
            name = "H5",
            category = "Components",
            xmlElement = "h5",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    H5 h5();

    @StudioFlowComponent(
            name = "H6",
            category = "Components",
            xmlElement = "h6",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    H6 h6();

    @StudioFlowComponent(
            name = "Header",
            category = "Components",
            xmlElement = "header",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Header header();

    @StudioFlowComponent(
            name = "Hr",
            category = "Components",
            xmlElement = "hr",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Hr hr();

    @StudioFlowComponent(
            name = "HtmlObject",
            category = "Components",
            xmlElement = "htmlObject",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "data", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "type", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    HtmlObject htmlObject();

    @StudioFlowComponent(
            name = "IFrame",
            category = "Components",
            xmlElement = "iframe",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "allow", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "importance", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.html.IFrame.ImportanceType", defaultValue = "AUTO",
                            options = {"AUTO", "HIGH", "LOW"}),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "name", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "resource", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "resourceDoc", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "sandbox", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.html.IFrame.SandboxType",
                            defaultValue = "RESTRICT_ALL",
                            options = {"RESTRICT_ALL", "ALLOW_FORMS", "ALLOW_MODALS", "ALLOW_ORIENTATION_LOCK",
                                    "ALLOW_POINTER_LOCK", "ALLOW_POPUPS", "ALLOW_POPUPS_TO_ESCAPE_SANDBOX",
                                    "ALLOW_PRESENTATION", "ALLOW_SAME_ORIGIN", "ALLOW_SCRIPTS",
                                    "ALLOW_STORAGE_ACCESS_BY_USER_ACTIVATION", "ALLOW_TOP_NAVIGATION",
                                    "ALLOW_TOP_NAVIGATION_BY_USER_ACTIVATION"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    IFrame iframe();

    @StudioFlowComponent(
            name = "Image",
            category = "Components",
            xmlElement = "image",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "alternativeText", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "ariaLabel", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "resource", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Image image();

    @StudioFlowComponent(
            name = "Input",
            category = "Components",
            xmlElement = "input",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "ariaLabel", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "placeholder", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "type", type = StudioFlowPropertyType.STRING,
                            options = {"button", "checkbox", "color", "date", "datetime-local", "file", "hidden",
                                    "image", "month", "number", "password", "radio", "range", "reset", "search",
                                    "submit", "tel", "text", "time", "url", "week"}),
                    @StudioFlowProperty(xmlAttribute = "valueChangeMode", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.data.value.ValueChangeMode",
                            options = {"EAGER", "LAZY", "TIMEOUT", "ON_BLUR", "ON_CHANGE"}),
                    @StudioFlowProperty(xmlAttribute = "valueChangeTimeout", type = StudioFlowPropertyType.INTEGER),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Input input();

    @StudioFlowComponent(
            name = "ListItem",
            category = "Components",
            xmlElement = "listItem",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    ListItem listItem();

    @StudioFlowComponent(
            name = "Main",
            category = "Components",
            xmlElement = "main",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "ariaLabel", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Main main();

    @StudioFlowComponent(
            name = "Nav",
            category = "Components",
            xmlElement = "nav",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "ariaLabel", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Nav nav();

    @StudioFlowComponent(
            name = "OrderedList",
            category = "Components",
            xmlElement = "orderedList",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "numberingType", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.html.OrderedList.NumberingType",
                            options = {"NUMBER", "UPPERCASE_LETTER", "LOWERCASE_LETTER", "UPPERCASE_ROMAN",
                                    "LOWERCASE_ROMAN"}),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    OrderedList orderedList();

    @StudioFlowComponent(
            name = "Paragraph",
            category = "Components",
            xmlElement = "p",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Paragraph paragraph();

    @StudioFlowComponent(
            name = "Param",
            category = "Components",
            xmlElement = "param",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "name", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "value", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Param param();

    @StudioFlowComponent(
            name = "Pre",
            category = "Components",
            xmlElement = "pre",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Pre pre();

    @StudioFlowComponent(
            name = "Section",
            category = "Components",
            xmlElement = "section",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Section section();

    @StudioFlowComponent(
            name = "Span",
            category = "Components",
            xmlElement = "span",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    Span span();

    @StudioFlowComponent(
            name = "UnorderedList",
            category = "Components",
            xmlElement = "unorderedList",
//            icon = "",
            properties = {
                    @StudioFlowProperty(xmlAttribute = "className", type = StudioFlowPropertyType.VALUES_LIST),
                    @StudioFlowProperty(xmlAttribute = "enabled", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setEnabled", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "height", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "id", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "maxHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "maxWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minHeight", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "minWidth", type = StudioFlowPropertyType.SIZE),
                    @StudioFlowProperty(xmlAttribute = "text", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "themeName", type = StudioFlowPropertyType.VALUES_LIST,
                            options = {"normal", "success", "error", "contrast", "primary", "small", "pill"}),
                    @StudioFlowProperty(xmlAttribute = "title", type = StudioFlowPropertyType.STRING),
                    @StudioFlowProperty(xmlAttribute = "visible", type = StudioFlowPropertyType.BOOLEAN,
                            setMethod = "setVisible", defaultValue = "true"),
                    @StudioFlowProperty(xmlAttribute = "whiteSpace", type = StudioFlowPropertyType.ENUMERATION,
                            classFqn = "com.vaadin.flow.component.HasText$WhiteSpace", defaultValue = "NORMAL",
                            options = {"NORMAL", "NOWRAP", "PRE", "PRE_WRAP", "PRE_LINE", "BREAK_SPACES", "INHERIT",
                                    "INITIAL"}),
                    @StudioFlowProperty(xmlAttribute = "width", type = StudioFlowPropertyType.SIZE)
            }
    )
    UnorderedList unorderedList();


}
