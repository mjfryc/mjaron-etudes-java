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

import pl.mjaron.etudes.Container;
import pl.mjaron.etudes.IRandomIterator;
import pl.mjaron.etudes.Pair;
import pl.mjaron.etudes.Str;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class used to build {@link ManipulatingTableSource}.
 *
 * @since 0.2.2
 */
public class ManipulatingTableSourceBuilder {

    private final List<ColumnEntry> finalColumns = new ArrayList<>();
    private ITableSource underlyingSource;

    private ColumnSelector columnSelector;

    private IRandomIterator<String> headers = null;
    private boolean allColumns = true;

    public ITableSource getUnderlyingSource() {
        return this.underlyingSource;
    }

    public ManipulatingTableSourceBuilder setUnderlyingSource(ITableSource underlyingSource) {
        this.underlyingSource = underlyingSource;
        if (underlyingSource.hasHeaders()) {
            headers = IRandomIterator.from(underlyingSource.getHeaders());
        }
        return this;
    }

    public ManipulatingTableSourceBuilder setColumnSelector(final ColumnSelector columnSelector) {
        this.columnSelector = columnSelector;
        return this;
    }

    public void setAllColumns(boolean allColumns) {
        this.allColumns = allColumns;
    }

    /**
     * Selects the columns provided by {@link ColumnSelector}.
     * <p>
     * Uses the {@link ColumnSelector} columns order.
     */
    public void selectColumns() {
        // For each requested column.
        for (final Pair<String, String> entry : columnSelector.getEntries()) {
            headers.setFloorPosition();

            // Find related column in table.
            final IRandomIterator<String> entryIt = Container.find(headers, entry.getKey());
            if (entryIt == null) {
                throw new IllegalArgumentException("Cannot resolve column name for requested column: [" + entry + "]: Given column name not found. All column identifiers: " + Str.join(underlyingSource.getHeaders(), ", "));
            }
            ColumnEntry columnEntry = new ColumnEntry();
            columnEntry.sourceColumnIndex = entryIt.getPosition();
            columnEntry.sourceColumnName = entryIt.getCurrent();
            columnEntry.columnNameAlias = entry.getValue();
            finalColumns.add(columnEntry);
        }
    }

    /**
     * Selects all columns even if some of them are not described by {@link ColumnSelector}.
     * <p>
     * Uses the original source columns order.
     */
    public void selectAllColumns() {
        headers.setFloorPosition();
        while (headers.hasNext()) {
            ColumnEntry columnEntry = new ColumnEntry();
            columnEntry.sourceColumnName = headers.next();
            columnEntry.sourceColumnIndex = headers.getPosition();
            columnEntry.columnNameAlias = columnSelector.getColumnAlias(columnEntry.sourceColumnName);
            finalColumns.add(columnEntry);
        }
    }

    /**
     * Updates the missing column name aliases from the original source.
     */
    public void determineMissingColumnNameAliases() {
        for (ColumnEntry columnEntry : finalColumns) {
            if (columnEntry.columnNameAlias == null) {
                columnEntry.columnNameAlias = columnEntry.sourceColumnName;
            }
        }
    }

    /**
     * Allows generating the column name aliases.
     *
     * @param generator Custom name generator.
     */
    public void determineColumnNameAliases(IColumnNameGenerator generator) {
        for (int i = 0; i < finalColumns.size(); ++i) {
            ColumnEntry columnEntry = finalColumns.get(i);
            columnEntry.columnNameAlias = generator.getColumnName(columnEntry.sourceColumnIndex, columnEntry.sourceColumnName, i);
        }
    }

    /**
     * Allows generating the column name aliases only for missing aliases.
     *
     * @param generator Custom name generator.
     */
    public void determineMissingColumnNameAliases(IColumnNameGenerator generator) {
        for (int i = 0; i < finalColumns.size(); ++i) {
            ColumnEntry columnEntry = finalColumns.get(i);
            if (columnEntry.columnNameAlias == null) {
                columnEntry.columnNameAlias = generator.getColumnName(columnEntry.sourceColumnIndex, columnEntry.sourceColumnName, i);
            }
        }
    }

    public ITableSource build() {
        if (finalColumns.isEmpty()) {
            if (columnSelector == null) {
                return underlyingSource;
            }
        }

        if (allColumns) {
            selectAllColumns();
        } else {
            selectColumns();
        }
        determineMissingColumnNameAliases();

        ArrayList<Integer> columnOrder = new ArrayList<>(finalColumns.size());
        ArrayList<String> columnNames = new ArrayList<>(finalColumns.size());
        for (ColumnEntry columnEntry : finalColumns) {
            columnOrder.add(columnEntry.sourceColumnIndex);
            columnNames.add(columnEntry.columnNameAlias);
        }
        return new ManipulatingTableSource(underlyingSource, columnOrder, columnNames);
    }

    /**
     * Keeps the column metadata consistency.
     *
     * @since 0.2.2
     */
    public static class ColumnEntry {
        int sourceColumnIndex = -1;
        String sourceColumnName = null;

        String columnNameAlias = null;
    }
}
