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

package pl.mjaron.etudes.container;

import java.util.List;
import java.util.RandomAccess;

import pl.mjaron.etudes.IRandomAccess;

/**
 * Random access designed for {@link java.util.ArrayList}.
 *
 * @param <T> List element type.
 * @since 0.3.0
 */
public class ListRandomAccess<T> implements IRandomAccess<T> {

    private final List<T> arrayList;

    public ListRandomAccess(final List<T> list) {
        if (!(list instanceof RandomAccess)) {
            throw new IllegalArgumentException("Cannot create " + ListRandomAccess.class.getSimpleName() + " object: Given list instance is not a " + RandomAccess.class.getSimpleName() + ": " + list.getClass().getSimpleName());
        }
        this.arrayList = list;
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public T get(int position) {
        return arrayList.get(position);
    }
}
