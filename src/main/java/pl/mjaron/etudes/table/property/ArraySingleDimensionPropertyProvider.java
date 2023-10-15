/*
 * Copyright  2023  Michał Jaroń <m.jaron@protonmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pl.mjaron.etudes.table.property;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.mjaron.etudes.Arr;

public class ArraySingleDimensionPropertyProvider<T> {

    @Nullable
    Map<Class<?>, T> byClassProperties = null;
    private T rootValue = null;
    private ArrayList<T> arrayList = null;

    private T getByClassOrRootValue(final @Nullable Class<?> clazz) {
        if (clazz != null && byClassProperties != null) {
            final T byClassValue = byClassProperties.get(clazz);
            if (byClassValue != null) {
                return byClassValue;
            }
        }
        return rootValue;
    }

    public @Nullable T get(@Range(from = 0, to = Integer.MAX_VALUE) final int index, final Class<?> clazz) {
        if (arrayList == null || arrayList.size() <= index) {
            return getByClassOrRootValue(clazz);
        }
        final T indexValue = arrayList.get(index);
        if (indexValue == null) {
            return getByClassOrRootValue(clazz);
        }
        return indexValue;
    }

    public void setValue(@Nullable final T value) {
        this.rootValue = value;
        this.arrayList = null;
    }

    public void setValue(@Range(from = 0, to = Integer.MAX_VALUE) final int index, @Nullable final T value) {
        if (arrayList == null) {
            arrayList = new ArrayList<>(index + 8);
        }
        Arr.ensureSize(arrayList, index + 1);
        arrayList.set(index, value);
    }

    public void setByClass(final Class<?> clazz, final T value) {
        if (byClassProperties == null) {
            byClassProperties = new HashMap<>();
        }
        byClassProperties.put(clazz, value);
    }

    @Nullable
    public ArrayList<T> getArrayList() {
        return arrayList;
    }
}
