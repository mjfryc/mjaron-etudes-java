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
import pl.mjaron.etudes.Arr;

import java.util.ArrayList;

public class ArraySingleDimensionPropertyProvider<T> {

    private T rootValue = null;

    private ArrayList<T> arrayList = null;

    public @Nullable T get(@Range(from = 0, to = Integer.MAX_VALUE) final int index) {
        if (arrayList == null || arrayList.size() <= index) {
            return rootValue;
        }
        final T indexValue = arrayList.get(index);
        if (indexValue == null) {
            return rootValue;
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

    @Nullable
    public ArrayList<T> getArrayList() {
        return arrayList;
    }
}
