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
 * Csv specification: <a href="https://www.rfc-editor.org/rfc/rfc4180">https://www.rfc-editor.org/rfc/rfc4180</a>
 */
public class CsvTableWriter implements ITableWriter {

    public static final String DEFAULT_DELIMITER = ",";

    private RenderContext context = null;

    @Override
    public String getDefaultDelimiter() {
        return DEFAULT_DELIMITER;
    }

    @Override
    public boolean getDefaultAlignedColumnWidths() {
        return false;
    }

    @Override
    public void beginTable(RenderContext options) {
        this.context = options;
    }

    @Override
    public void endTable() {
    }

    @Override
    public void beginHeader() {
    }

    @Override
    public void endHeader() {
        context.appendLine();
    }

    @Override
    public void beginRow() {
    }

    @Override
    public void endRow() {
        context.appendLine();
    }

    @Override
    public void writeCell(String what) {
        context.appendIfNotFirstColumn(context.getCellDelimiter());
        context.appendPadded(what);
    }
}
