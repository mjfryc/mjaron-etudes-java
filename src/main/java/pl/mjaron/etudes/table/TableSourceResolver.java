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
 * Responsible for determining the final table source.
 * <p>
 * It may apply the table source wrapper which modifies original table source behaviour.
 * <p>
 * Useful when table is modified on the fly, e.g. when columns are skipped or renamed.
 *
 * @since 0.3.0
 * @deprecated Use {@link ManipulatingTableSource} directly.
 */
@Deprecated
public class TableSourceResolver {

    /**
     * Source of headers and data used to fill the table.
     *
     * @since 0.3.0
     */
    private ITableSource originalTableSource = null;

    private ITableSource finalTableSource = null;

    private ColumnSelector columnSelector = null;

    public void setTableSource(ITableSource tableSource) {
        this.originalTableSource = tableSource;
    }

    public void setColumnSelector(final ColumnSelector columnSelector) {
        this.columnSelector = columnSelector;
    }

    public void resolve() {
        if (originalTableSource == null) {
            throw new IllegalArgumentException("Cannot write table without table source.");
        }

        if (this.columnSelector != null) {
            /// @todo create the table source wrapper.
        }
        this.finalTableSource = originalTableSource;
    }

    public ITableSource getTableSource() {
        return finalTableSource;
    }
}
