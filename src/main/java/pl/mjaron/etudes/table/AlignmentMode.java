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

package pl.mjaron.etudes.table;

/**
 * Tells how to align the columns.
 *
 * @since 0.3.0
 */
public enum AlignmentMode {

    /**
     * Use the value from {@link ITableWriter#getDefaultAlignedColumnWidths()}.
     *
     * @since 0.3.0
     */
    DEFAULT,

    /**
     * Use the fixed width values.
     * @since 0.3.0
     */
    ARBITRARY,

    /**
     * Do not align the columns. Each row will have different cell widths. Good for CSV format.
     *
     * @since 0.3.0
     */
    NOT_ALIGNED,

    /**
     * Align the row's columns.
     *
     * @since 0.3.0
     */
    ALIGNED,

    /**
     * All columns will have the same width.
     *
     * @since 0.3.0
     */
    EQUAL
}
