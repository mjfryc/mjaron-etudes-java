/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
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

package pl.mjaron.etudes.table;

import pl.mjaron.etudes.Str;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class StringSeries {

    public static <T> List<String> from(final List<T> objects) {
        final List<String> series = new ArrayList<>(objects.size());
        for (final T obj : objects) {
            series.add(Str.orEmpty(obj));
        }
        return series;
    }

    public static <T> List<String> from(final T[] objects) {
        final List<String> series = new ArrayList<>(objects.length);
        for (final T obj : objects) {
            series.add(Str.orEmpty(obj));
        }
        return series;
    }

    public static <T> List<String> from(final int[] objects) {
        final List<String> series = new ArrayList<>(objects.length);
        for (final int obj : objects) {
            series.add(String.valueOf(obj));
        }
        return series;
    }
}

