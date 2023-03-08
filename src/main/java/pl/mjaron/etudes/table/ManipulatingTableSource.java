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
import pl.mjaron.etudes.IRandomIterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Transforms the original table source.
 *
 * @since 0.2.2
 */
public class ManipulatingTableSource implements ITableSource {

    private final ITableSource underlyingSource;

    private final ArrayList<Integer> columnOrder;

    private final ArrayList<String> columnNames;

    final IRandomIterator<String> headers;

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
    public Iterator<Iterable<String>> iterator() {
        return new ManipulatingTableSourceIterator(underlyingSource, columnOrder);
    }

    @Override
    public boolean hasHeaders() {
        return underlyingSource.hasHeaders();
    }
}

class ManipulatingTableSourceRow implements Iterable<String> {

    private final ArrayList<Integer> columnOrder;

    private final IRandomIterator<String> underlyingRowRandomIterator;

    public ManipulatingTableSourceRow(ArrayList<Integer> columnOrder, Iterable<String> underlyingRow) {
        this.columnOrder = columnOrder;
        this.underlyingRowRandomIterator = IRandomIterator.from(underlyingRow);
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            int currentColumnIndex = -1;

            @Override
            public boolean hasNext() {
                return currentColumnIndex + 1 < columnOrder.size();
            }

            @Override
            public String next() {
                ++currentColumnIndex;
                underlyingRowRandomIterator.setPosition(columnOrder.get(currentColumnIndex));
                return underlyingRowRandomIterator.getCurrent();
            }
        };
    }
}

class ManipulatingTableSourceIterator implements Iterator<Iterable<String>> {

    private final ArrayList<Integer> columnOrder;
    private final Iterator<Iterable<String>> underlyingIterator;

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
    public Iterable<String> next() {
        return new ManipulatingTableSourceRow(columnOrder, underlyingIterator.next());
    }
}

interface IColumnNameGenerator {
    String getColumnName(int columnSourceIndex, String columnSourceName, int columnDestinationIndex);
}

