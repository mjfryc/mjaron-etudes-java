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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class RenderOperation {

    public static void execute(final RenderContext context) {
        OutputStream internalOutputStream = null;
        try {
            if (context.getSource() == null) {
                throw new NullPointerException("Cannot write table without table source.");
            }
            if (context.getOutFile() != null) {
                //noinspection IOStreamConstructor
                internalOutputStream = new FileOutputStream(context.getOutFile());
                context.to(internalOutputStream);
            }
            if (context.out() == null) {
                context.to(System.out);
            }
            if (context.getEscaper() == null) {
                context.withEscaper(DummyEscaper.getInstance());
            }
            if (context.isComputeColumnWidths() == null) {
                if (context.getWriter().getDefaultAlignedColumnWidths()) {
                    context.withAlignedColumnWidths();
                } else {
                    context.withoutAlignedColumnWidths();
                }
            }
            if (context.isComputeColumnWidths()) {
                final int[] widths = TableColumnsWidthDetector.compute(context.getSource(), context.getEscaper());
                context.withArbitraryColumnWidths(widths);
            }
            final ITableWriter writer = context.getWriter();

            if (context.getCellDelimiter() == null && writer.getDefaultDelimiter() != null) {
                context.withCellDelimiter(writer.getDefaultDelimiter());
            }
            context.getEscaper().beginTable(context);
            writer.beginTable(context);
            if (context.getSource().hasHeaders()) {
                context.headerState = true;
                writer.beginHeader();
                context.columnIdx = 0;
                for (final String header : context.getSource().getHeaders()) {
                    writer.writeCell(context.getEscaper().escape(header));
                    ++context.columnIdx;
                }
                writer.endHeader();
                context.headerState = false;
            }

            for (final Iterable<String> row : context.getSource()) {
                writer.beginRow();
                context.columnIdx = 0;
                for (final String cell : row) {
                    writer.writeCell(context.getEscaper().escape(cell));
                    ++context.columnIdx;
                }
                writer.endRow();
            }
            writer.endTable();
        } catch (Exception e) {
            throw new RuntimeException("Render operation failed.", e);
        } finally {
            if (internalOutputStream != null) {
                try {
                    internalOutputStream.close();
                } catch (final IOException ignored) {
                }
            }
        }
    }
}
