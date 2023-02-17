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

    private RenderContext context = null;

    /**
     * TODO Move it to RenderContext.
     */
    private final String indentationUnit = "    ";

    @Override
    public void beginTable(RenderContext context) {
        this.context = context;
        context.appendLine("<table>");
    }

    @Override
    public void endTable() {
        context.appendLine("</table>");
        context = null;
    }

    @Override
    public void beginHeader() {
        context.append(indentationUnit);
        context.appendLine("<tr>");
    }

    @Override
    public void endHeader() {
        context.append(indentationUnit);
        context.appendLine("</tr>");
    }

    @Override
    public void beginRow() {
        context.append(indentationUnit);
        context.appendLine("<tr>");
    }

    @Override
    public void endRow() {
        context.append(indentationUnit);
        context.appendLine("</tr>");
    }

    /**
     * Appends the style attribute for currently processed table column. The appended value depends on the current
     * column vertical align.
     * <p>
     * For internal use.
     *
     * @see #writeCell(String)
     * @see VerticalAlign
     * @see VerticalAlignContext#getCurrentColumnVerticalAlign()
     * @see RenderContext#getColumnIdx()
     * @since 0.2.1
     */
    public void writeColumnStyle() {
        final VerticalAlign verticalAlign = context.getVerticalAlignContext().getCurrentColumnVerticalAlign();
        if (verticalAlign == null) {
            return;
        }

        switch (verticalAlign) {
            case Left: {
                context.append(" style=\"text-align: left;\"");
                return;
            }
            case Right: {
                context.append(" style=\"text-align: right;\"");
                return;
            }
            case Center: {
                context.append(" style=\"text-align: center;\"");
                return;
            }
            default: {
                throw new RuntimeException("Unexpected vertical align value: " + verticalAlign);
            }
        }
    }

    @Override
    public void writeCell(String what) {
        context.append(indentationUnit);
        context.append(indentationUnit);
        if (context.isHeaderState()) {
            context.append("<th");
            writeColumnStyle();
            context.append(">");
            context.append(what);
            context.append("</th>");
        } else {
            context.append("<td");
            writeColumnStyle();
            context.append(">");
            context.append(what);
            context.append("</td>");
        }
        context.appendLine();
    }
}
