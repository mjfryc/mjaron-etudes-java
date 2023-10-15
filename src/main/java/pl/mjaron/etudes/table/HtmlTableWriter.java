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
 * Writes the table as HTML code.
 *
 * @since 0.2.0
 */
public class HtmlTableWriter implements ITableWriter {

    /**
     * TODO Move it to RenderContext.
     */
    private final String indentationUnit = "    ";
    private final HtmlOptions htmlOptions;
    private RenderRuntime runtime = null;

    public HtmlTableWriter(HtmlOptions htmlOptions) {
        this.htmlOptions = htmlOptions;
    }

    public HtmlTableWriter() {
        this(new HtmlOptions());
    }

    @Override
    public void beginTable(RenderRuntime runtime) {
        this.runtime = runtime;
        runtime.append("<table");
        if (htmlOptions.getTableId() != null) {
            runtime.append(" id=\"");
            runtime.append(htmlOptions.getTableId());
            runtime.append('\"');
        }

        if (htmlOptions.getTableClass() != null) {
            runtime.append(" class=\"");
            runtime.append(htmlOptions.getTableClass());
            runtime.append('\"');
        }
        runtime.appendLine('>');
    }

    @Override
    public void endTable() {
        runtime.appendLine("</table>");
        runtime = null;
    }

    @Override
    public void beginHeader() {
        runtime.append(indentationUnit);
        runtime.appendLine("<tr>");
    }

    @Override
    public void endHeader() {
        runtime.append(indentationUnit);
        runtime.appendLine("</tr>");
    }

    @Override
    public void beginRow() {
        runtime.append(indentationUnit);
        runtime.appendLine("<tr>");
    }

    @Override
    public void endRow() {
        runtime.append(indentationUnit);
        runtime.appendLine("</tr>");
    }

    /**
     * Appends the style attribute for currently processed table column. The appended value depends on the current
     * column vertical align.
     * <p>
     * For internal use.
     *
     * @see #writeCell(String)
     * @see VerticalAlign
     * @see RenderRuntime#getColumnIdx()
     * @since 0.2.1
     */
    public void writeColumnStyle() {
        final VerticalAlign verticalAlign = runtime.getCurrentColumnVerticalAlign();
        if (verticalAlign == null) {
            return;
        }

        switch (verticalAlign) {
            case Left: {
                runtime.append(" style=\"text-align: left;\"");
                return;
            }
            case Right: {
                runtime.append(" style=\"text-align: right;\"");
                return;
            }
            case Center: {
                runtime.append(" style=\"text-align: center;\"");
                return;
            }
            default: {
                throw new RuntimeException("Unexpected vertical align value: " + verticalAlign);
            }
        }
    }

    @Override
    public void writeCell(String what) {
        runtime.append(indentationUnit);
        runtime.append(indentationUnit);
        if (runtime.isHeaderState()) {
            runtime.append("<th");
            writeColumnStyle();
            runtime.append(">");
            runtime.append(what);
            runtime.append("</th>");
        } else {
            runtime.append("<td");
            writeColumnStyle();
            runtime.append(">");
            runtime.append(what);
            runtime.append("</td>");
        }
        runtime.appendLine();
    }
}
