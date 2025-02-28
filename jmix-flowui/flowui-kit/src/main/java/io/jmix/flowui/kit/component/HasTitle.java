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

package io.jmix.flowui.kit.component;

import com.google.common.base.Strings;
import com.vaadin.flow.component.HasElement;

import javax.annotation.Nullable;

public interface HasTitle extends HasElement {

    @Nullable
    default String getTitle() {
        return getElement().getProperty("title");
    }

    default void setTitle(@Nullable String title) {
        String titleValue = Strings.nullToEmpty(title);

        getElement().setProperty("title", titleValue);
        // TODO: gg, leave here?
        getElement().setAttribute("aria-label", titleValue);
    }
}
