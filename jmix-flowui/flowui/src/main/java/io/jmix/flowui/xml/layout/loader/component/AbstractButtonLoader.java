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

package io.jmix.flowui.xml.layout.loader.component;

import com.vaadin.flow.component.button.Button;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;

public abstract class AbstractButtonLoader<T extends Button> extends AbstractComponentLoader<T> {

    @Override
    public void loadComponent() {
        loadIcon();
        loadBoolean(element, "autofocus", resultComponent::setAutofocus);
        loadBoolean(element, "iconAfterText", resultComponent::setIconAfterText);
        loadBoolean(element, "disableOnClick", resultComponent::setDisableOnClick);

        componentLoader().loadText(resultComponent, element);
        componentLoader().loadWhiteSpace(resultComponent, element);
        componentLoader().loadEnabled(resultComponent, element);
        componentLoader().loadClassName(resultComponent, element);
        componentLoader().loadThemeName(resultComponent, element);
        componentLoader().loadSizeAttributes(resultComponent, element);
    }

    protected void loadIcon() {
        componentLoader().loadIcon(resultComponent, element)
                .ifPresent(icon -> resultComponent.setIcon(icon.create()));
    }
}
