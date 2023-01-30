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

    public static RenderOptions makeMarkdown() {
        return new RenderOptions(new MarkdownTableWriter(), MarkdownEscaper.getDefaultInstance(), null, true);
    }

    public static RenderOptions make() {
        return new RenderOptions(new MarkdownTableWriter(), null, null, true);
    }

    private ITableWriter writer = null;

    private IEscaper escaper = null;

    private int[] columnWidths = null;

    private boolean computeColumnWidths = true;

    public RenderOptions() {
    }

    public RenderOptions(ITableWriter writer, IEscaper escaper, int[] columnWidths, boolean computeColumnWidths) {
        this.writer = writer;
        this.escaper = escaper;
        this.columnWidths = columnWidths;
        this.computeColumnWidths = computeColumnWidths;
    }

    public ITableWriter getWriter() {
        if (writer == null) {
            writer = new MarkdownTableWriter(); // Markdown table writer by default.
        }
        return writer;
    }

    public RenderOptions withWriter(ITableWriter writer) {
        this.writer = writer;
        return this;
    }

    public IEscaper getEscaper() {
        if (escaper == null) {
            escaper = DummyEscaper.getInstance();
        }
        return escaper;
    }

    public RenderOptions withEscaper(IEscaper escaper) {
        this.escaper = escaper;
        return this;
    }

    public RenderOptions withMarkdownEscaper() {
        return withEscaper(MarkdownEscaper.getDefaultInstance());
    }

    public int[] getColumnWidths() {
        return columnWidths;
    }

    public boolean hasColumnWidths() {
        return columnWidths != null;
    }

    public boolean isComputeColumnWidths() {
        return computeColumnWidths;
    }

    public RenderOptions withArbitraryColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
        this.computeColumnWidths = false;
        return this;
    }

    public RenderOptions withAlignedColumnWidths() {
        this.columnWidths = null;
        this.computeColumnWidths = true;
        return this;
    }

    public RenderOptions withUnalignedColumnWidths() {
        this.columnWidths = null;
        this.computeColumnWidths = false;
        return this;
    }
}
