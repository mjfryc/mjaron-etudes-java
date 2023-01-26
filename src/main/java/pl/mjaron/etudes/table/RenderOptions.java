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
 * Common table rendering options.
 *
 * @since 0.1.12
 */
public class RenderOptions {

    public static final RenderOptions FIXED_WIDTH = new RenderOptions(null, ColumnWidth.aligned());

    public static final RenderOptions MARKDOWN_ESCAPER = new RenderOptions(MarkdownEscaper.getDefaultInstance(), ColumnWidth.aligned());

    public static final RenderOptions DEFAULT = FIXED_WIDTH;

    public static RenderOptions make() {
        return new RenderOptions(null, ColumnWidth.aligned());
    }

    private IEscaper escaper;

    private ColumnWidth columnWidth;

    public RenderOptions() {
    }

    public RenderOptions(IEscaper escaper, ColumnWidth columnWidth) {
        this.escaper = escaper;
        this.columnWidth = columnWidth;
    }

    public IEscaper getEscaper() {
        return escaper;
    }

    public RenderOptions setEscaper(IEscaper escaper) {
        this.escaper = escaper;
        return this;
    }

    public ColumnWidth getColumnWidth() {
        return columnWidth;
    }

    public RenderOptions setColumnWidth(ColumnWidth columnWidth) {
        this.columnWidth = columnWidth;
        return this;
    }

    public RenderOptions withMarkdownEscaper() {
        this.escaper = MarkdownEscaper.getDefaultInstance();
        return this;
    }

    public RenderOptions withColumnsAligned() {
        this.columnWidth = ColumnWidth.aligned();
        return this;
    }
}
