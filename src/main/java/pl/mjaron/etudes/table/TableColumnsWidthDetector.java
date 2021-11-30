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

public abstract class TableColumnsWidthDetector {

    private static void apply(final int[] widths, final Iterable<String> series) {
        int i = 0;
        for (final String entry : series) {
            final int oldEntryWidth = widths[i];
            final int newEntryWidth = Integer.max(oldEntryWidth, entry.length());
            widths[i] = newEntryWidth;
            ++i;
        }
    }

    public static int[] compute(final ITableSource source) {
        final int[] widths = new int[source.getColumnsCount()];
        if (source.hasHeaders()) {
            apply(widths, source.getHeaders());
        }
        for (final Iterable<String> row : source) {
            apply(widths, row);
        }
        return widths;
    }
}
