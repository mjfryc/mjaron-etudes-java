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

import org.jetbrains.annotations.Range;
import pl.mjaron.etudes.IPureAppendable;
import pl.mjaron.etudes.Str;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RenderRuntime implements AutoCloseable {

    private final RenderContext context;

    private final ITableSource tableSource;

    private final IEscaper escaper;

    OutputStream internalOutputStream = null;

    private final IPureAppendable out;

    private final String cellDelimiter;

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

    public RenderRuntime(RenderContext context) {
        this.context = context;
        this.tableSource = context.getTableSourceBuilder().build();
        this.escaper = IEscaper.dummyOr(context.getEscaper());

        IPureAppendable tmpOut = null;
        if (context.getOutFile() != null) {
            try {
                internalOutputStream = new FileOutputStream(context.getOutFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Failed to create output stream.", e);
            }
            tmpOut = IPureAppendable.from(internalOutputStream);
        }

        if (context.getWriter() == null) {
            context.withWriter(new MarkdownTableWriter());
        }

        this.out = (tmpOut != null) ? tmpOut : ((context.getOut() != null) ? context.getOut() : IPureAppendable.from(System.out));

        this.cellDelimiter = (context.getCellDelimiter() != null) ? context.getCellDelimiter() : ((getWriter().getDefaultDelimiter() != null) ? getWriter().getDefaultDelimiter() : null);
    }

    public RenderContext getContext() {
        return context;
    }

    public ITableSource getSource() {
        return tableSource;
    }

    public IPropertyProvider<IFormatter> getFormatters() {
        return context.getCellFormatterPropertyProvider();
    }

    public IPureAppendable getOut() {
        return out;
    }

    public IEscaper getEscaper() {
        return escaper;
    }

    public ITableWriter getWriter() {
        return getContext().getWriter();
    }

    public String getCellDelimiter() {
        return cellDelimiter;
    }

    public String getLineBreak() {
        return context.getLineBreak();
    }

    public boolean hasColumnWidths() {
        return context.getColumnWidthResolver().hasWidths();
    }

    public int getColumnWidth(int columnIndex) {
        return context.getColumnWidthResolver().getWidth(columnIndex);
    }

    /**
     * Provides the table columns count.
     *
     * @return Count of table columns.
     * @since 0.2.0
     */
    public int getColumnsCount() {
        return getSource().getColumnsCount();
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
    }

    /**
     * Resets to <code>0</code> currently rendered column index.
     *
     * @since 0.2.1
     */
    public void resetColumn() {
        this.columnIdx = 0;
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
     * Provides {@link VerticalAlign} related to currently processed column.
     *
     * @return {@link VerticalAlign} related to currently processed column.
     * @since 0.3.0
     */
    public VerticalAlign getCurrentColumnVerticalAlign() {
        return getVerticalAlign(columnIdx);
    }

    /**
     * Provides {@link VerticalAlign} related to given <code>column</code> parameter.
     *
     * @param column Requested column index.
     * @return {@link VerticalAlign} related to given <code>column</code> parameter.
     * @since 0.3.0
     */
    public VerticalAlign getVerticalAlign(@Range(from = 0, to = Integer.MAX_VALUE) final int column) {
        return getContext().getVerticalAlignPropertyProvider().get(column, -1);
    }

    /**
     * Appends given {@link String} to the table rendering output.
     *
     * @param what {@link String} to append.
     * @since 0.2.0
     */
    public void append(String what) {
        getOut().append(what);
    }

    /**
     * Appends given <code>char</code> to the table rendering output.
     *
     * @param what <code>char</code> to append.
     * @since 0.2.0
     */
    public void append(final char what) {
        getOut().append(what);
    }

    /**
     * Appends new line characters to the table rendering output.
     *
     * @since 0.2.0
     */
    public void appendLine() {
        append(getContext().getLineBreak());
    }

    /**
     * Appends given {@link String} and new line characters to the table rendering output.
     *
     * @param what {@link String} to append.
     * @since 0.2.0
     */
    public void appendLine(String what) {
        append(what);
        appendLine();
    }

    /**
     * Appends given {@code char} and new line characters to the table rendering output.
     *
     * @param what {@code char} to append.
     * @since 0.3.0
     */
    public void appendLine(final char what) {
        append(what);
        appendLine();
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
     * @see VerticalAlign
     * @since 0.2.0
     */
    public void appendPadded(String what, final char fillChar) {
        final VerticalAlign currentAlign = getContext().getVerticalAlignPropertyProvider().get(columnIdx, -1);
        if (getContext().getColumnWidthResolver().hasWidths()) {
            if (currentAlign == null || currentAlign == VerticalAlign.Left) {
                //noinspection ConstantConditions
                Str.padRight(what, getContext().getColumnWidthResolver().getWidth(columnIdx), fillChar, getOut());
            } else if (currentAlign == VerticalAlign.Right) {
                Str.padLeft(what, getContext().getColumnWidthResolver().getWidth(columnIdx), fillChar, getOut());
            } else if (currentAlign == VerticalAlign.Center) {
                Str.padCenter(what, getContext().getColumnWidthResolver().getWidth(columnIdx), fillChar, getOut());
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

    public String renderCell(final int column, final int row, final Object value) {
        final IFormatter formatter = getFormatters().getOrDefault(column, row, IFormatter.dummy());
        final String formatted = formatter.format(value);
        return getEscaper().escape(formatted);
    }

    @Override
    public void close() {
        if (internalOutputStream != null) {
            try {
                internalOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to close output stream.", e);
            }
        }
    }
}
