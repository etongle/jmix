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

package io.jmix.flowui.model.impl;

import com.google.common.collect.ForwardingIterator;
import io.jmix.flowui.SameAsUi;

import java.util.Iterator;

@SameAsUi
class ObservableIterator<T> extends ForwardingIterator<T> {

    private Iterator<T> delegate;

    protected ObservableIterator(Iterator<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected Iterator<T> delegate() {
        return delegate;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("ObservableIterator does not support 'remove' operation");
    }
}