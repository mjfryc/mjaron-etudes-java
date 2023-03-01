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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mjaron.etudes.PureAppendable;
import pl.mjaron.etudes.Str;

import java.io.File;
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

    /**
     * Source of headers and data used to fill the table.
     *
     * @since 0.2.0
     */
    private ITableSource source = null;

    /**
     * {@link ITableWriter} responsible for formatting and writing the headers and data.
     *
     * @since 0.2.0
     */
    private ITableWriter writer = null;

    /**
     * Used to create the rendered table.
     *
     * @since 0.2.0
     */
    private PureAppendable out = null;

    /**
     * Use output file by path instead of PureAppendable. {@link RenderOperation} will be responsible for closing
     * temporary {@link java.io.FileOutputStream}.
     *
     * @since 0.2.0
     */
    private File outFile = null;

    /**
     * {@link IEscaper} used to format the special characters.
     *
     * @since 0.2.0
     */
    private IEscaper escaper = null;

    /**
     * Requested widths of all columns used during the table rendering.
     *
     * @since 0.2.0
     */
    private int[] columnWidths = null;

    /**
     * Determines whether {@link #columnWidths} must be computed automatically, fixed values should be used or default
     * value, depending on the {@link ITableWriter#getDefaultAlignedColumnWidths()}.
     *
     * @see ITableWriter#getDefaultAlignedColumnWidths()
     * @see #isComputeColumnWidths()
     * @see #getColumnWidths()
     * @see #withAlignedColumnWidths()
     * @see #withAlignedColumnWidths(Boolean)
     * @see #withArbitraryColumnWidths(int[])
     * @since 0.2.0
     */
    private Boolean computeColumnWidths = null;

    /**
     * Allows using custom cell delimiter if related {@link ITableWriter} and optionally {@link IEscaper} supports it.
     * Usually used with the CSV format.
     *
     * @since 0.1.13
     */
    @Nullable
    private String cellDelimiter = null;

    /**
     * Defines how the lines should be separated.
     *
     * @since 0.2.0
     */
    private String lineBreak = System.lineSeparator();

    /**
     * Updated internally by {@link RenderOperation} when visiting related cells.
     *
     * @since 0.2.0
     */
    private int columnIdx = 0;

    /**
     * Updated internally by {@link RenderOperation}. Defines whether currently the headers are rendered or not.
     * <p>
     * If false, the table body is rendered.
     *
     * @since 0.2.0
     */
    private boolean headerState = false;

    /**
     * Used to determine column's vertical align.
     */
    private final VerticalAlignContext verticalAlignContext = new VerticalAlignContext(this);

    /**
     * Default constructor. By convention, use {@link #make()} to create the object.
     *
     * @see #make()
     * @since 0.1.12
     */
    public RenderContext() {
    }

    /**
     * Allow setting the rendered text destination.
     *
     * @param out Where to write the rendered table.
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    @Contract("_ -> this")
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
    @NotNull
    @Contract("_ -> this")
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
    @NotNull
    @Contract("_, _-> this")
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
    @NotNull
    @Contract("_ -> this")
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
    @NotNull
    @Contract("_ -> this")
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
    @NotNull
    @Contract("_ -> this")
    public RenderContext to(Appendable out) {
        if (out instanceof PureAppendable) {
            return to((PureAppendable) out);
        }
        return to(PureAppendable.from(out));
    }

    /**
     * Set the render output to the file.
     *
     * @param file Destination file.
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    @Contract("_ -> this")
    public RenderContext to(File file) {
        this.out = null;
        this.outFile = file;
        return this;
    }

    /**
     * Allows setting the file destination using the path defines as {@link String}.
     * <p>
     * The same result may be achieved by calling: <pre>.to(new File(path));</pre>
     *
     * @param path The rendering destination file path. It may be relative or absolute.
     * @return This reference.
     * @see #to(File)
     * @since 0.2.0
     */
    @NotNull
    @Contract("_ -> this")
    public RenderContext toFile(String path) {
        return this.to(new File(path));
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
    @NotNull
    @Contract("_ -> this")
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
    @NotNull
    @Contract("-> this")
    public RenderContext withMarkdownWriter() {
        return this.withWriter(new MarkdownTableWriter());
    }

    /**
     * Use {@link CsvTableWriter} for render operation.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    @Contract("-> this")
    public RenderContext withCsvWriter() {
        return this.withWriter(new CsvTableWriter());
    }

    /**
     * Use {@link HtmlTableWriter} for render operation.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withHtmlWriter() {
        return this.withWriter(new HtmlTableWriter());
    }

    /**
     * Use {@link BlankTableWriter} for render operation.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withBlankTableWriter() {
        return this.withWriter(new BlankTableWriter());
    }

    /**
     * Provides the {@link IEscaper} used for rendering operation.
     *
     * @return {@link IEscaper} used for rendering operation.
     * @since 0.2.0
     */
    @Nullable
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
    @NotNull
    public RenderContext withEscaper(IEscaper escaper) {
        this.escaper = escaper;
        return this;
    }

    /**
     * Turns off the escaper so cell content will not be customized to the table format.
     *
     * @return This reference.
     * @see #withEscaper(IEscaper)
     * @see #withMarkdownEscaper()
     * @see #withCsvEscaper()
     * @see #withHtmlEscaper()
     * @see IEscaper
     * @since 0.2.1
     */
    @NotNull
    public RenderContext withoutEscaper() {
        return withEscaper(null);
    }

    /**
     * Sets the {@link MarkdownEscaper} used for special characters escaping.
     *
     * @return This reference.
     * @see #markdown()
     * @see MarkdownEscaper
     * @since 0.2.0
     */
    @NotNull
    @Contract("-> this")
    public RenderContext withMarkdownEscaper() {
        return withEscaper(MarkdownEscaper.getDefaultInstance());
    }

    /**
     * Sets the {@link CsvEscaper} used for special characters escaping.
     *
     * @return This reference.
     * @see #csv()
     * @see CsvEscaper
     * @since 0.2.0
     */
    @NotNull
    @Contract("-> this")
    public RenderContext withCsvEscaper() {
        return this.withEscaper(new CsvEscaper());
    }

    /**
     * Sets the {@link HtmlEscaper} used for special characters escaping.
     *
     * @return This reference.
     * @see #html()
     * @see HtmlEscaper
     * @since 0.2.1
     */
    @NotNull
    @Contract("-> this")
    public RenderContext withHtmlEscaper() {
        return this.withEscaper(new HtmlEscaper());
    }

    /**
     * Provides the requested column widths or <code>null</code> if not specified.
     *
     * @return Requested column widths or <code>null</code>.
     * @since 0.2.0
     */
    public int[] getColumnWidths() {
        return columnWidths;
    }

    /**
     * Provides the table columns count.
     *
     * @return Count of table columns.
     * @since 0.2.0
     */
    public int getColumnsCount() {
        return source.getColumnsCount();
    }

    /**
     * Tells whether column widths are specified or not.
     *
     * @return <code>true</code> when column widths are specified, <code>false</code> otherwise.
     * @see #getColumnWidths()
     * @see #withArbitraryColumnWidths(int[])
     * @see #withAlignedColumnWidths(Boolean)
     * @see #withAlignedColumnWidths()
     * @see #withoutAlignedColumnWidths()
     * @since 0.2.0
     */
    public boolean hasColumnWidths() {
        return columnWidths != null;
    }

    /**
     * Tells whether column widths must be computed.
     * <p>
     * If <code>true</code>, the {@link RenderOperation} will compute column widths and set this value to false to
     * indicate that it is already done.
     *
     * @return <code>true</code> when column widths must be computed during rendering. <code>false</code> when column
     * widths must not be computed during rendering. <code>null</code> when the default value should be used.
     * @see ITableWriter#getDefaultAlignedColumnWidths()
     * @see #withArbitraryColumnWidths(int[])
     * @see #withAlignedColumnWidths(Boolean)
     * @see #withAlignedColumnWidths()
     * @see #withoutAlignedColumnWidths()
     * @since 0.2.0
     */
    @Nullable
    public Boolean isComputeColumnWidths() {
        return computeColumnWidths;
    }

    /**
     * Allows specifying each column width.
     *
     * @param columnWidths Array of widths where nth value indicates the nth column width (minimal count of
     *                     characters).
     * @return This reference.
     * @see #withAlignedColumnWidths()
     * @see #withAlignedColumnWidths(Boolean)
     * @see #withoutAlignedColumnWidths()
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withArbitraryColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
        this.computeColumnWidths = false;
        return this;
    }

    /**
     * Aligns the columns, so each row has the same column width. When not set, the
     * {@link ITableWriter#getDefaultAlignedColumnWidths()} value will be used.
     * <p>
     * When true, it may break the .CSV format compatibility with some applications like Only Office.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withAlignedColumnWidths() {
        this.columnWidths = null;
        this.computeColumnWidths = true;
        return this;
    }

    /**
     * <ul>
     * <li>If {@code true}, aligns the columns, so each row has the same column width. It may break the .CSV format compatibility
     * with some applications like Only Office.</li>
     * <li>If {@code false}, columns will not be aligned.</li>
     * <li>If {@code null}, the {@link ITableWriter#getDefaultAlignedColumnWidths()} value will be used by {@link RenderOperation}.</li>
     * </ul>
     *
     * @param alignedColumnWidths Should column widths be aligned or not. Pass {@code null} to use the
     *                            {@link ITableWriter} default value.
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withAlignedColumnWidths(final Boolean alignedColumnWidths) {
        if (alignedColumnWidths == null) {
            this.columnWidths = null;
            this.computeColumnWidths = null;
        }
        if (Boolean.TRUE.equals(alignedColumnWidths)) {
            return withAlignedColumnWidths();
        }
        return withoutAlignedColumnWidths();
    }

    /**
     * Columns will not be aligned.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
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
    @NotNull
    public RenderContext withCellDelimiter(String what) {
        this.cellDelimiter = what;
        return this;
    }

    /**
     * Allows setting custom cell delimiter if related {@link ITableWriter} and optionally {@link IEscaper} supports it.
     * Usually used with the CSV format.
     *
     * @param what Custom delimiter.
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withCellDelimiter(char what) {
        this.cellDelimiter = String.valueOf(what);
        return this;
    }

    /**
     * Use the cell delimiter provided by {@link ITableWriter#getDefaultDelimiter()}.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withDefaultCellDelimiter() {
        return withCellDelimiter(null);
    }

    /**
     * Sets empty cell delimiter, so values will not be disjointed.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
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

    /**
     * Use custom line break.
     *
     * @param lineBreak Line break.
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withLineBreak(String lineBreak) {
        this.lineBreak = lineBreak;
        return this;
    }

    /**
     * Using {@code LF} - {@code "\n"} as a new line separator. This is Linux default line separator.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withLineBreakLF() {
        return this.withLineBreak(Str.LF);
    }

    /**
     * Using {@code CR LF} - {@code "\r\n"} as a new line separator. This is Windows default line separator.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withLineBreakCRLF() {
        return this.withLineBreak(Str.CRLF);
    }

    /**
     * Using {@code CR} - {@code "\r"} as a new line separator. This is the default line separator in Mac OS before X.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    public RenderContext withLineBreakCR() {
        return this.withLineBreak(Str.CR);
    }

    /**
     * Sets the cell {@link VerticalAlign}.
     *
     * @param align The cell {@link VerticalAlign}.
     * @return This reference.
     * @since 0.2.1
     */
    @NotNull
    public RenderContext withAlign(@Nullable VerticalAlign align) {
        verticalAlignContext.setGeneralVerticalAlign(align);
        return this;
    }

    /**
     * Use predefined Markdown configuration, shortcut of:
     * <pre>{@code
     *     withMarkdownWriter().withMarkdownEscaper();
     * }</pre>
     *
     * @return This reference.
     * @since 0.2.1
     */
    @NotNull
    public RenderContext markdown() {
        return withMarkdownWriter().withMarkdownEscaper();
    }

    /**
     * Use predefined CSV configuration, shortcut of:
     * <pre>{@code
     *     withCsvWriter().withCsvEscaper().withLineBreakCRLF();
     * }</pre>
     *
     * @return This reference.
     * @since 0.2.1
     */
    @NotNull
    public RenderContext csv() {
        return withCsvWriter().withCsvEscaper().withLineBreakCRLF();
    }

    /**
     * Use predefined HTML configuration, shortcut of:
     * <pre>{@code
     *     withHtmlWriter().withHtmlEscaper();
     * }</pre>
     *
     * @return This reference.
     * @since 0.2.1
     */
    @NotNull
    public RenderContext html() {
        return this.withHtmlWriter().withHtmlEscaper();
    }

    @Contract(pure = true)
    @NotNull
    public String getLineBreak() {
        return lineBreak;
    }

    /**
     * Used by {@link ITableWriter} during {@link RenderOperation#execute(RenderContext)}.
     *
     * @return Any {@link PureAppendable} used to render the table.
     * @since 0.2.0
     */
    @Contract(pure = true)
    @Nullable
    public PureAppendable out() {
        return this.out;
    }

    /**
     * Provides the output file if it set.
     *
     * @return {@link File} where rendered table will be written or {@code null} if the file is not set.
     * @since 0.2.0
     */
    @Contract(pure = true)
    @Nullable
    public File getOutFile() {
        return this.outFile;
    }

    /**
     * Appends given {@link String} to the table rendering output.
     *
     * @param what {@link String} to append.
     * @since 0.2.0
     */
    public void append(String what) {
        out.append(what);
    }

    /**
     * Appends given <code>char</code> to the table rendering output.
     *
     * @param what <code>char</code> to append.
     * @since 0.2.0
     */
    public void append(char what) {
        out.append(what);
    }

    /**
     * Appends new line characters to the table rendering output.
     *
     * @since 0.2.0
     */
    public void appendLine() {
        append(getLineBreak());
    }

    /**
     * Appends given {@link String} and new line characters to the table rendering output.
     *
     * @param what {@link String} to append.
     * @since 0.2.0
     */
    public void appendLine(String what) {
        append(what);
        append(getLineBreak());
    }

    /**
     * Appends given {@link String} to the table rendering output only when current column is not first column (not with
     * index <code>0</code>).
     *
     * @param what {@link String} to append.
     * @since 0.2.0
     */
    public void appendIfNotFirstColumn(String what) {
        if (columnIdx != 0) {
            this.append(what);
        }
    }

    /**
     * Appends given <code>char</code> to the table rendering output only when current column is not first column (not
     * with index <code>0</code>).
     *
     * @param what <code>char</code> to append.
     * @since 0.2.0
     */
    public void appendIfNotFirstColumn(char what) {
        if (columnIdx != 0) {
            this.append(what);
        }
    }

    /**
     * Pads given String by filling with given character and next appends to rendered table output.
     *
     * @param what     String to append.
     * @param fillChar Character to fill the String during padding.
     * @see VerticalAlignContext
     * @see VerticalAlign
     * @since 0.2.0
     */
    public void appendPadded(String what, final char fillChar) {
        final VerticalAlign currentAlign = verticalAlignContext.getCurrentColumnVerticalAlign();
        if (this.hasColumnWidths()) {
            if (currentAlign == null || currentAlign == VerticalAlign.Left) {
                //noinspection ConstantConditions
                Str.padRight(what, this.getColumnWidths()[columnIdx], fillChar, this.out());
            } else if (currentAlign == VerticalAlign.Right) {
                Str.padLeft(what, this.getColumnWidths()[columnIdx], fillChar, this.out());
            } else if (currentAlign == VerticalAlign.Center) {
                Str.padCenter(what, this.getColumnWidths()[columnIdx], fillChar, this.out());
            } else {
                throw new RuntimeException("Unsupported vertical align value: " + currentAlign);
            }
        } else {
            this.append(what);
        }
    }

    /**
     * Pads given String by filling with space character and next appends to rendered table output.
     *
     * @param what String to append.
     * @since 0.2.0
     */
    public void appendPadded(String what) {
        this.appendPadded(what, ' ');
    }

    /**
     * Getter of {@link ITableSource} used to read the table data.
     *
     * @return {@link ITableSource} used to read the table data.
     * @since 0.2.0
     */
    @Contract(pure = true)
    public ITableSource getSource() {
        return source;
    }

    /**
     * Setter of {@link ITableSource} used to read the table data.
     *
     * @param source {@link ITableSource} used to read the table data.
     * @since 0.2.0
     */
    public void setSource(ITableSource source) {
        this.source = source;
    }

    /**
     * Provides currently rendered column index, counting from <code>0</code>.
     *
     * @return Currently rendered column index.
     * @since 0.2.1
     */
    public int getColumnIdx() {
        return this.columnIdx;
    }

    /**
     * Increments by one currently rendered column index.
     *
     * @since 0.2.1
     */
    public void nextColumn() {
        ++this.columnIdx;
        verticalAlignContext.onCurrentColumnChanged();
    }

    /**
     * Resets to <code>0</code> currently rendered column index.
     *
     * @since 0.2.1
     */
    public void resetColumn() {
        this.columnIdx = 0;
        verticalAlignContext.onCurrentColumnChanged();
    }

    /**
     * Tells whether currently rendered row is the table header.
     *
     * @return <code>true</code> when table rendering is in header state.
     * @since 0.2.1
     */
    public boolean isHeaderState() {
        return headerState;
    }

    /**
     * Updates the table rendering header state. Called by {@link RenderOperation}.
     *
     * @param headerState New value of rendering header state.
     * @see RenderOperation
     * @since 0.2.1
     */
    public void setHeaderState(boolean headerState) {
        this.headerState = headerState;
    }

    /**
     * Provides {@link VerticalAlignContext} for vertical align related options.
     *
     * @return {@link VerticalAlignContext} used by this rendering context.
     * @see VerticalAlignContext
     * @see VerticalAlign
     * @since 0.2.1
     */
    public VerticalAlignContext getVerticalAlignContext() {
        return verticalAlignContext;
    }

    /**
     * Performs rendering.
     *
     * @see #runToString()
     * @since 0.2.0
     */
    public void run() {
        RenderOperation.execute(this);
    }

    /**
     * Performs rendering and returns the result to the {@link String}.
     *
     * @return {@link String} containing rendered table.
     * @see #run()
     * @since 0.2.1
     */
    @Contract(pure = true)
    public String runToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.out() != null) {
            throw new IllegalArgumentException("Cannot render to String: Invalid render context: Appender already set with " + RenderContext.class.getSimpleName() + "::to(...) method.");
        }
        this.to(stringBuilder);
        RenderOperation.execute(this);
        return stringBuilder.toString();
    }

    /**
     * Performs rendering and returns the result to the {@link String}.
     *
     * @return {@link String} containing rendered table.
     * @see #runToString()
     * @since 0.2.0
     * @deprecated Use {@link #runToString()}
     */
    @Deprecated
    @Contract(pure = true)
    public String runString() {
        return this.runToString();
    }
}
