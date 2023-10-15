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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

import pl.mjaron.etudes.IRandomIterator;

interface IColumnNameGenerator {
    String getColumnName(int columnSourceIndex, String columnSourceName, int columnDestinationIndex);
}

/**
 * Transforms the original table source.
 *
 * @since 0.3.0
 */
public class ManipulatingTableSource implements ITableSource {

    final IRandomIterator<String> headers;
    private final ITableSource underlyingSource;
    private final ArrayList<Integer> columnOrder;
    private final ArrayList<String> columnNames;

    public ManipulatingTableSource(ITableSource underlyingSource, ArrayList<Integer> columnOrder, ArrayList<String> columnNames) {
        this.underlyingSource = underlyingSource;
        this.columnOrder = columnOrder;
        this.columnNames = columnNames;
        this.headers = IRandomIterator.from(underlyingSource.getHeaders());
    }

    @Override
    public int getColumnsCount() {
        return columnOrder.size();
    }

    @Override
    public Iterable<String> getHeaders() {
        return columnNames;
    }

    @Override
    public Iterator<Iterable<Object>> iterator() {
        return new ManipulatingTableSourceIterator(underlyingSource, columnOrder);
    }

    @Override
    public boolean hasHeaders() {
        return underlyingSource.hasHeaders();
    }
}

class ManipulatingTableSourceRow implements Iterable<Object> {

    private final ArrayList<Integer> columnOrder;

    private final IRandomIterator<Object> underlyingRowRandomIterator;

    public ManipulatingTableSourceRow(ArrayList<Integer> columnOrder, Iterable<Object> underlyingRow) {
        this.columnOrder = columnOrder;
        this.underlyingRowRandomIterator = IRandomIterator.from(underlyingRow);
    }

    @NotNull
    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {

            int currentColumnIndex = -1;

            @Override
            public boolean hasNext() {
                return currentColumnIndex + 1 < columnOrder.size();
            }

            @Override
            public Object next() {
                ++currentColumnIndex;
                underlyingRowRandomIterator.setPosition(columnOrder.get(currentColumnIndex));
                return underlyingRowRandomIterator.getCurrent();
            }
        };
    }
}

class ManipulatingTableSourceIterator implements Iterator<Iterable<Object>> {

    private final ArrayList<Integer> columnOrder;
    private final Iterator<Iterable<Object>> underlyingIterator;

    public ManipulatingTableSourceIterator(ITableSource underlyingSource, ArrayList<Integer> columnOrder) {
        this.columnOrder = columnOrder;
        this.underlyingIterator = underlyingSource.iterator();
    }

    /**
     * Tells whether it has next row.
     *
     * @return <code>true</code> if there is next row.
     */
    @Override
    public boolean hasNext() {
        return underlyingIterator.hasNext();
    }

    /**
     * Provides next row.
     *
     * @return Next row.
     */
    @Override
    public Iterable<Object> next() {
        return new ManipulatingTableSourceRow(columnOrder, underlyingIterator.next());
    }
}

