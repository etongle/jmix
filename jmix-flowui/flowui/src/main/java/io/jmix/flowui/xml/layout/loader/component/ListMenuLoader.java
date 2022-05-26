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

import com.google.common.base.Strings;
import io.jmix.flowui.component.menu.ListMenu;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import org.dom4j.Element;

import java.util.Optional;

public class ListMenuLoader extends AbstractComponentLoader<ListMenu> {

    @Override
    public void loadComponent() {
        componentLoader().loadSizeAttributes(resultComponent, element);
        componentLoader().loadClassName(resultComponent, element);

        loadLoadMenuConfig(element)
                .ifPresent(val -> resultComponent.loadMenuConfig());
    }

    @Override
    protected ListMenu createComponent() {
        return factory.create(ListMenu.class);
    }

    protected Optional<Boolean> loadLoadMenuConfig(Element element) {
        String loadMenuConfig = Strings.emptyToNull(element.attributeValue("loadMenuConfig"));
        if (loadMenuConfig == null) {
            return Optional.of(true);
        }
        return Optional.of(Boolean.parseBoolean(loadMenuConfig));
    }
}
