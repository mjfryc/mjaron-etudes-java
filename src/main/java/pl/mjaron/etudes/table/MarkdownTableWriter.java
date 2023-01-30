/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
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

import pl.mjaron.etudes.Str;

/**
 * Writes table in Markdown style.
 */
public class MarkdownTableWriter implements ITableWriter {

    RenderOptions options = null;

    private final StringBuilder out = new StringBuilder();

    private int columnIdx = 0;

    public MarkdownTableWriter() {
    }

    @Override
    public String getTable() {
        return out.toString();
    }

    @Override
    public void beginTable(ITableSource source, RenderOptions options) {
        this.options = options;
    }

    @Override
    public void endTable() {
    }

    @Override
    public void beginHeader() {
    }

    @Override
    public void endHeader() {
        out.append("|\n");
        for (final int w : options.getColumnWidths()) {
            out.append("| ");
            Str.pad(out, w, '-');
            out.append(' ');
        }
        out.append("|\n");
    }

    @Override
    public void beginRow() {
        columnIdx = 0;
    }

    @Override
    public void endRow() {
        out.append("|\n");
    }

    @Override
    public void writeCell(final String what) {
        out.append("| ");
        if (options.hasColumnWidths()) {
            Str.padLeft(what, options.getColumnWidths()[columnIdx], ' ', out);
        }
        else {
            out.append(what);
        }
        out.append(' ');
        ++columnIdx;
    }
}
