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

package io.jmix.flowui.screen.impl;

import io.jmix.flowui.facet.Facet;
import io.jmix.flowui.screen.Screen;
import io.jmix.flowui.screen.ScreenFacets;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static io.jmix.core.common.util.Preconditions.checkNotNullArgument;

@Component("flowui_ScreenFacets")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScreenFacetsImpl implements ScreenFacets {

    protected final Screen<?> screen;

    protected Set<Facet> facets = null; // lazily initialized linked hash set

    public ScreenFacetsImpl(Screen<?> screen) {
        this.screen = screen;
    }

    @Override
    public void addFacet(Facet facet) {
        checkNotNullArgument(facet);

        if (facets == null) {
            facets = new HashSet<>();
        }

        if (!facets.contains(facet)) {
            facets.add(facet);
            facet.setOwner(screen);
        }
    }

    @Nullable
    @Override
    public Facet getFacet(String id) {
        checkNotNullArgument(id);

        if (facets == null) {
            return null;
        }

        return facets.stream()
                .filter(f -> id.equals(f.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeFacet(Facet facet) {
        checkNotNullArgument(facet);

        if (facets != null
                && facets.remove(facet)) {
            facet.setOwner(null);
        }
    }

    @Override
    public Stream<Facet> getFacets() {
        return facets == null
                ? Stream.empty()
                : facets.stream();
    }
}
