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
import org.jetbrains.annotations.Range;
import pl.mjaron.etudes.IPureAppendable;
import pl.mjaron.etudes.Str;
import pl.mjaron.etudes.table.property.ByColumnPropertyProvider;
import pl.mjaron.etudes.table.property.ColumnOnlyPropertyProvider;

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

    /**
     * Source of headers and data used to fill the table.
     *
     * @since 0.3.0
     */
    private final ManipulatingTableSourceBuilder tableSourceBuilder = new ManipulatingTableSourceBuilder();

    private final ByColumnPropertyProvider<IFormatter> cellFormatterPropertyProvider = new ByColumnPropertyProvider<>();
    /**
     * Determines the column widths depending on {@link AlignmentMode}.
     *
     * @since 0.3.0
     */
    private final ColumnWidthResolver columnWidthResolver = new ColumnWidthResolver();
    /**
     * Used to determine column's vertical align.
     *
     * @see VerticalAlign
     * @since 0.3.0
     */
    private final ColumnOnlyPropertyProvider<VerticalAlign> verticalAlignPropertyProvider = new ColumnOnlyPropertyProvider<>();
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
    private IPureAppendable out = null;

    /**
     * Use output file by path instead of IPureAppendable. {@link RenderOperation} will be responsible for closing
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
     * Default constructor. By convention, use {@link #make()} to create the object.
     *
     * @see #make()
     * @since 0.1.12
     */
    public RenderContext() {
    }

    /**
     * Initializes the {@link RenderContext} object.
     *
     * @return New instance of {@link RenderContext}.
     * @since 0.2.0
     */
    @NotNull
    public static RenderContext make() {
        return new RenderContext();
    }

    /**
     * Provides the {@link ColumnSelector} object initialized with first column id.
     *
     * @param id Identifier of first column. It is the name of column provided by {@link ITableSource}.
     * @return New instance of {@link ColumnSelector}.
     * @since 0.3.0
     */
    @NotNull
    public static ColumnSelector col(final String id) {
        return new ColumnSelector().col(id);
    }

    /**
     * Provides the {@link ColumnSelector} object initialized with first column id and name alias.
     *
     * @param id    Identifier of first column. It is the name of column provided by {@link ITableSource}.
     * @param alias Alias of first column name. If {@code null}, the column id will be used as column name.
     * @return New instance of {@link ColumnSelector}.
     * @since 0.3.0
     */
    @NotNull
    public static ColumnSelector col(final String id, final String alias) {
        return new ColumnSelector().col(id).as(alias);
    }

    /**
     * Provides {@link ManipulatingTableSourceBuilder}.
     *
     * @return {@link ManipulatingTableSourceBuilder} instance.
     * @since 0.3.0
     */
    @NotNull
    public ManipulatingTableSourceBuilder getTableSourceBuilder() {
        return this.tableSourceBuilder;
    }

    @NotNull
    public ByColumnPropertyProvider<IFormatter> getCellFormatterPropertyProvider() {
        return this.cellFormatterPropertyProvider;
    }

    /**
     * Provides {@link ColumnOnlyPropertyProvider} which determines the columns' width.
     *
     * @return {@link ColumnOnlyPropertyProvider} which determines the columns' width.
     * @since 0.3.0
     */
    @NotNull
    public ColumnOnlyPropertyProvider<VerticalAlign> getVerticalAlignPropertyProvider() {
        return verticalAlignPropertyProvider;
    }

    public RenderContext withFormatter(IFormatter formatter) {
        getCellFormatterPropertyProvider().put(formatter);
        return this;
    }

    public RenderContext withFormatter(final int column, IFormatter formatter) {
        getCellFormatterPropertyProvider().put(column, formatter);
        return this;
    }

    public RenderContext withFormatter(final int column, final int row, IFormatter formatter) {
        getCellFormatterPropertyProvider().put(column, row, formatter);
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
    public RenderContext to(IPureAppendable out) {
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
        return to(IPureAppendable.from(out));
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
        return to(IPureAppendable.from(out, charset));
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
        return to(IPureAppendable.from(out));
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
        return to(IPureAppendable.from(out));
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
        if (out instanceof IPureAppendable) {
            return to((IPureAppendable) out);
        }
        return to(IPureAppendable.from(out));
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
     * @throws NullPointerException If the {@code path} argument is {@code null}.
     * @see #to(File)
     * @since 0.2.0
     */
    @NotNull
    @Contract("_ -> this")
    public RenderContext toFile(@NotNull final String path) {
        return this.to(new File(path));
    }

    /**
     * Provides {@link ITableWriter} used for table rendering.
     *
     * @return {@link ITableWriter} used for table rendering.
     * @since 0.2.0
     */
    @Nullable
    @Contract(pure = true)
    public ITableWriter getWriter() {
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
     * Use {@link HtmlTableWriter} for render operation.
     *
     * @param htmlOptions HTML special options.
     * @return This reference.
     * @since 0.3.0
     */
    @NotNull
    public RenderContext withHtmlWriter(final HtmlOptions htmlOptions) {
        return this.withWriter(new HtmlTableWriter(htmlOptions));
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
     * Returns the helper object which determines the column widths.
     *
     * @return Helper object which determines the column widths.
     * @since 0.3.0
     */
    @NotNull
    @Contract(pure = true)
    public ColumnWidthResolver getColumnWidthResolver() {
        return columnWidthResolver;
    }

    /**
     * Configures the column widths mode.
     *
     * @param mode   Requested column widths mode.
     * @param widths Column widths values. It <strong>must</strong> be set when mode is {@link AlignmentMode#ARBITRARY}.
     *               In other cases it must be <code>null</code>.
     * @return This reference.
     * @throws IllegalArgumentException When <code>widths</code> value is unexpected, depending on <code>mode</code>
     *                                  parameter.
     * @see ColumnWidthResolver#configure(AlignmentMode, int[])
     * @since 0.3.0
     */
    @NotNull
    @Contract("_,_-> this")
    public RenderContext withColumnWidths(final AlignmentMode mode, final int[] widths) {
        getColumnWidthResolver().configure(mode, widths);
        return this;
    }

    /**
     * Allows specifying each column width.
     *
     * @param widths Array of widths where nth value indicates the nth column width (minimal count of characters).
     * @return This reference.
     * @see #withAlignedColumnWidths()
     * @see #withAlignedColumnWidths(Boolean)
     * @see #withoutAlignedColumnWidths()
     * @since 0.2.0
     */
    @NotNull
    @Contract("_ -> this")
    public RenderContext withArbitraryColumnWidths(final int[] widths) {
        return withColumnWidths(AlignmentMode.ARBITRARY, widths);
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
    @Contract("-> this")
    public RenderContext withAlignedColumnWidths() {
        return withColumnWidths(AlignmentMode.ALIGNED, null);
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
     * @deprecated Use {@link RenderContext#withColumnWidths(AlignmentMode, int[])} instead.
     */
    @NotNull
    @Contract("_ -> this")
    @Deprecated
    public RenderContext withAlignedColumnWidths(final Boolean alignedColumnWidths) {
        if (alignedColumnWidths == null) {
            getColumnWidthResolver().configure(AlignmentMode.DEFAULT);
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
    @Contract("-> this")
    @NotNull
    public RenderContext withoutAlignedColumnWidths() {
        return withColumnWidths(AlignmentMode.NOT_ALIGNED, null);
    }

    /**
     * All columns will have the width of the wider column.
     *
     * @return This reference.
     * @see AlignmentMode#EQUAL
     * @since 0.3.0
     */
    @Contract("-> this")
    @NotNull
    public RenderContext withEqualColumnWidths() {
        return withColumnWidths(AlignmentMode.EQUAL, null);
    }

    /**
     * Allows setting custom cell delimiter if related {@link ITableWriter} and optionally {@link IEscaper} supports it.
     * Usually used with the CSV format.
     *
     * @param delimiter Custom delimiter.
     * @return This reference.
     * @since 0.1.13
     */
    @Contract("_-> this")
    @NotNull
    public RenderContext withCellDelimiter(final String delimiter) {
        this.cellDelimiter = delimiter;
        return this;
    }

    /**
     * Allows setting custom cell delimiter if related {@link ITableWriter} and optionally {@link IEscaper} supports it.
     * Usually used with the CSV format.
     *
     * @param delimiter Custom delimiter.
     * @return This reference.
     * @since 0.2.0
     */
    @Contract("_-> this")
    @NotNull
    public RenderContext withCellDelimiter(final char delimiter) {
        this.cellDelimiter = String.valueOf(delimiter);
        return this;
    }

    /**
     * Use the cell delimiter provided by {@link ITableWriter#getDefaultDelimiter()}.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    @Contract("-> this")
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
    @Contract("-> this")
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
    @Contract(pure = true)
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
    @Contract("_-> this")
    public RenderContext withLineBreak(@NotNull final String lineBreak) {
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
    @Contract("-> this")
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
    @Contract("-> this")
    public RenderContext withLineBreakCRLF() {
        return this.withLineBreak(Str.CRLF);
    }

    /**
     * Using {@code CR} - {@code "\r"} as a new line separator. This is the default line separator in macOS before X.
     *
     * @return This reference.
     * @since 0.2.0
     */
    @NotNull
    @Contract("-> this")
    public RenderContext withLineBreakCR() {
        return this.withLineBreak(Str.CR);
    }

    /**
     * Sets the whole table {@link VerticalAlign}. Overrides particular cells vertical align values, previously set with
     * {@link #withAlign(int, VerticalAlign)}.
     *
     * @param align The {@link VerticalAlign} applied to whole table.
     * @return This reference.
     * @see #withAlign(int, VerticalAlign)
     * @see VerticalAlign
     * @since 0.2.1
     */
    @NotNull
    @Contract("_-> this")
    public RenderContext withAlign(@Nullable VerticalAlign align) {
        verticalAlignPropertyProvider.put(align);
        return this;
    }

    /**
     * Sets the single column {@link VerticalAlign}.
     *
     * @param column Related column.
     * @param align  The cell {@link VerticalAlign}.
     * @return This reference.
     * @since 0.3.0
     */
    @NotNull
    @Contract("_, _-> this")
    public RenderContext withAlign(@Range(from = 0, to = Integer.MAX_VALUE) final int column, @Nullable VerticalAlign align) {
        verticalAlignPropertyProvider.put(column, align);
        return this;
    }

    /**
     * Selects the columns used for rendering. Overwrites previous call of this method.
     *
     * @param columnSelector {@link ColumnSelector} instance.
     * @return This reference.
     * @since 0.3.0
     */
    @Contract("_-> this")
    public RenderContext withColumns(ColumnSelector columnSelector) {
        getTableSourceBuilder().setColumnSelector(columnSelector);
        getTableSourceBuilder().setAllColumns(false);
        return this;
    }

    /**
     * Rename the column names. Missing column names will use original column names.
     *
     * @param columnSelector {@link ColumnSelector} instance.
     * @return This reference.
     * @since 0.3.0
     */
    @Contract("_-> this")
    public RenderContext withColumnNames(ColumnSelector columnSelector) {
        getTableSourceBuilder().setColumnSelector(columnSelector);
        getTableSourceBuilder().setAllColumns(true);
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
    @Contract("-> this")
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
    @Contract("-> this")
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
    @Contract("-> this")
    public RenderContext html() {
        return this.withHtmlWriter().withHtmlEscaper();
    }

    /**
     * Use predefined HTML configuration, shortcut of:
     * <pre>{@code
     *     withHtmlWriter(htmlOptions).withHtmlEscaper();
     * }</pre>
     *
     * @param htmlOptions HTML-related options.
     * @return This reference.
     * @see HtmlOptions
     * @since 0.3.0
     */
    @NotNull
    @Contract("_-> this")
    public RenderContext html(@NotNull final HtmlOptions htmlOptions) {
        return this.withHtmlWriter(htmlOptions).withHtmlEscaper();
    }

    @NotNull
    @Contract(pure = true)
    public String getLineBreak() {
        return lineBreak;
    }

    /**
     * Used by {@link ITableWriter} during {@link RenderOperation#execute(RenderContext)}.
     *
     * @return Any {@link IPureAppendable} used to render the table.
     * @since 0.2.0
     */
    @Nullable
    @Contract(pure = true)
    public IPureAppendable getOut() {
        return this.out;
    }

    /**
     * Provides the output file if it set.
     *
     * @return {@link File} where rendered table will be written or {@code null} if the file is not set.
     * @since 0.2.0
     */
    @Nullable
    @Contract(pure = true)
    public File getOutFile() {
        return this.outFile;
    }

    /**
     * Setter of {@link ITableSource} used to read the table data.
     *
     * @param source {@link ITableSource} used to read the table data.
     * @since 0.2.0
     */
    public void setSource(ITableSource source) {
        getTableSourceBuilder().setUnderlyingSource(source);
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
    @NotNull
    @Contract(pure = true)
    public String runToString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getOut() != null) {
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
    @NotNull
    @Contract(pure = true)
    public String runString() {
        return this.runToString();
    }
}
