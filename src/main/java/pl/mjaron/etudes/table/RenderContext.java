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

import org.jetbrains.annotations.Nullable;
import pl.mjaron.etudes.PureAppendable;
import pl.mjaron.etudes.Str;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * Common table rendering options.
 *
 * @since 0.1.12
 */
public class RenderContext {

    public static RenderContext make() {
        return new RenderContext();
    }

    private ITableSource source = null;

    private ITableWriter writer = null;

    /**
     * Used to create the rendered table.
     */
    private PureAppendable out = null;

    private IEscaper escaper = null;

    private int[] columnWidths = null;

    private Boolean computeColumnWidths = null;

    /**
     * Allows using custom cell delimiter if related {@link ITableWriter} and optionally {@link IEscaper} supports it.
     * Usually used with the CSV format.
     *
     * @since 0.1.13
     */
    @Nullable
    private String cellDelimiter = null;

    private String lineBreak = System.lineSeparator();

    private boolean lastLineBreak = true;

    /**
     * Updated internally by {@link RenderOperation} when visiting related cells.
     */
    public int columnIdx = 0;

    public RenderContext() {
    }

    /**
     * Allow setting the rendered text destination.
     *
     * @param out Where to write the rendered table.
     * @return This reference.
     */
    public RenderContext to(PureAppendable out) {
        this.out = out;
        return this;
    }

    /**
     * Allow setting the rendered text destination.
     *
     * @param out Where to write the rendered table.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext to(PrintStream out) {
        return to(PureAppendable.from(out));
    }

    /**
     * Allow setting the rendered text destination.
     *
     * @param out     Where to write the rendered table.
     * @param charset Encoding.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext to(OutputStream out, Charset charset) {
        return to(PureAppendable.from(out, charset));
    }

    /**
     * Allow setting the rendered text destination with platform default text encoding.
     *
     * @param out Where to write the rendered table.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext to(OutputStream out) {
        return to(PureAppendable.from(out));
    }

    /**
     * Allow setting the rendered text destination.
     *
     * @param out Where to write the rendered table.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext to(StringBuilder out) {
        return to(PureAppendable.from(out));
    }

    /**
     * Allow setting the rendered text destination.
     *
     * @param out Where to write the rendered table.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext to(Appendable out) {
        if (out instanceof PureAppendable) {
            return to((PureAppendable) out);
        }
        return to(PureAppendable.from(out));
    }

    /**
     * Provides {@link ITableWriter} used for table rendering.
     *
     * @return {@link ITableWriter} used for table rendering.
     * @since 0.2.0
     */
    public ITableWriter getWriter() {
        if (writer == null) {
            writer = new MarkdownTableWriter(); // Markdown table writer by default.
        }
        return writer;
    }

    /**
     * Selects the writer used to render the table. Single writer instance should be used for single render operation.
     *
     * @param writer {@link ITableWriter} instance.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext withWriter(ITableWriter writer) {
        this.writer = writer;
        return this;
    }

    /**
     * Use {@link MarkdownTableWriter} for render operation.
     *
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext withMarkdownWriter() {
        return this.withWriter(new MarkdownTableWriter());
    }

    /**
     * Use {@link CsvTableWriter} for render operation.
     *
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext withCsvWriter() {
        return this.withWriter(new CsvTableWriter());
    }

    /**
     * Provides the {@link IEscaper} used for rendering operation.
     *
     * @return {@link IEscaper} used for rendering operation.
     * @since 0.2.0
     */
    public IEscaper getEscaper() {
        return escaper;
    }

    /**
     * Sets the {@link IEscaper} used for rendering operation.
     *
     * @param escaper {@link IEscaper} used for rendering operation.
     * @return This reference.
     * @since 0.2.0
     */
    public RenderContext withEscaper(IEscaper escaper) {
        this.escaper = escaper;
        return this;
    }

    public RenderContext withMarkdownEscaper() {
        return withEscaper(MarkdownEscaper.getDefaultInstance());
    }

    public int[] getColumnWidths() {
        return columnWidths;
    }

    public int getColumnsCount() {
        return source.getColumnsCount();
    }

    public boolean hasColumnWidths() {
        return columnWidths != null;
    }

    public Boolean isComputeColumnWidths() {
        return computeColumnWidths;
    }

    public RenderContext withArbitraryColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
        this.computeColumnWidths = false;
        return this;
    }

    public RenderContext withAlignedColumnWidths() {
        this.columnWidths = null;
        this.computeColumnWidths = true;
        return this;
    }

    public RenderContext withAlignedColumnWidths(boolean alignedColumnWidths) {
        if (alignedColumnWidths) {
            return withAlignedColumnWidths();
        }
        return withoutAlignedColumnWidths();
    }

    public RenderContext withoutAlignedColumnWidths() {
        this.columnWidths = null;
        this.computeColumnWidths = false;
        return this;
    }

    /**
     * Allows setting custom cell delimiter if related {@link ITableWriter} and optionally {@link IEscaper} supports it.
     * Usually used with the CSV format.
     *
     * @param what Custom delimiter.
     * @return This reference.
     * @since 0.1.13
     */
    public RenderContext withCellDelimiter(String what) {
        this.cellDelimiter = what;
        return this;
    }

    public RenderContext withDefaultCellDelimiter() {
        return withCellDelimiter(null);
    }

    public RenderContext withoutCellDelimiter() {
        return withCellDelimiter("");
    }

    /**
     * Provides custom cell delimiter or null if default delimiter should be used.
     *
     * @return Custom cell delimiter or null if default delimiter should be used.
     * @since 0.1.13
     */
    @Nullable
    public String getCellDelimiter() {
        return this.cellDelimiter;
    }

    public RenderContext withLineBreak(String lineBreak) {
        this.lineBreak = lineBreak;
        return this;
    }

    public String getLineBreak() {
        return lineBreak;
    }

    public RenderContext withLastLineBreak(boolean lastLineBreak) {
        this.lastLineBreak = lastLineBreak;
        return this;
    }

    public boolean getLastLineBreak() {
        return lastLineBreak;
    }

    public PureAppendable out() {
        return this.out;
    }

    public void append(String what) {
        out.append(what);
    }

    public void append(char what) {
        out.append(what);
    }

    public void appendLine() {
        append(getLineBreak());
    }

    public void appendPadded(String what, char fillChar) {
        if (this.hasColumnWidths()) {
            Str.padLeft(what, this.getColumnWidths()[columnIdx], ' ', this.out());
        } else {
            this.append(what);
        }
    }

    public void appendIfNotFirstColumn(String what) {
        if (columnIdx != 0) {
            this.append(what);
        }
    }

    public void appendIfNotFirstColumn(char what) {
        if (columnIdx != 0) {
            this.append(what);
        }
    }

    public void appendPadded(String what) {
        this.appendPadded(what, ' ');
    }

    public ITableSource getSource() {
        return source;
    }

    public void setSource(ITableSource source) {
        this.source = source;
    }
}
