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

public class CsvTableWriter implements ITableWriter {

    private final StringBuilder out = new StringBuilder();

    /**
     * Determines if first cell in row.
     */
    private boolean firstCell = true;

    @Override
    public void beginTable(ITableSource source, RenderOptions options) {
    }

    @Override
    public void endTable() {
    }

    @Override
    public void beginHeader() {
        firstCell = true;
    }

    @Override
    public void endHeader() {
        out.append('\n');
    }

    @Override
    public void beginRow() {
        firstCell = true;
    }

    @Override
    public void endRow() {
        out.append('\n');
    }

    @Override
    public void writeCell(String what) {
        if (firstCell) {
            firstCell = false;
        } else {
            out.append(',');
        }
        out.append(what);
    }

    @Override
    public String getTable() {
        return out.toString();
    }
}
