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

import org.jetbrains.annotations.NotNull;

/**
 * Determines the widths of table columns.
 */
public abstract class TableColumnsWidthDetector {

    /**
     * Updates the column widths (character counts) when current width values are bigger than already stored max column
     * widths.
     *
     * @param widths array of maximum column widths.
     * @param series Single row (record) of data. Used to check the cell width.
     */
    private static void applyRow(final int[] widths, final int row, final Iterable<?> series, final RenderRuntime runtime) {
        int i = 0;
        for (final Object entry : series) {
            final int oldEntryWidth = widths[i];
            final String rendered = runtime.renderCell(i, row, entry);
            final int newEntryWidth = Integer.max(oldEntryWidth, rendered.length());
            widths[i] = newEntryWidth;
            ++i;
        }
    }

    private static void applyRow(final int[] widths, final Iterable<String> series, final IEscaper escaper) {
        int i = 0;
        for (final String entry : series) {
            final int oldEntryWidth = widths[i];
            final String escaped = escaper.escape(entry);
            final int newEntryWidth = Integer.max(oldEntryWidth, escaped.length());
            widths[i] = newEntryWidth;
            ++i;
        }
    }

    /**
     * Detects the maximum values of each column's cell width
     *
     * @param runtime Instance of {@link RenderRuntime}
     * @return Array of max widths of corresponding columns
     */
    public static int[] compute(@NotNull final RenderRuntime runtime) {
        final int[] widths = new int[runtime.getSource().getColumnsCount()];
        if (runtime.getSource().hasHeaders()) {
            applyRow(widths, runtime.getSource().getHeaders(), runtime.getEscaper());
        }
        int rowIndex = 0;
        for (final Iterable<Object> row : runtime.getSource()) {
            applyRow(widths, rowIndex, row, runtime);
            ++rowIndex;
        }
        return widths;
    }
}
