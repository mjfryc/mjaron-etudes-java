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

package pl.mjaron.etudes.table.property;

import org.jetbrains.annotations.Range;

import java.util.Map;
import java.util.TreeMap;

public class RandomSingleDimensionPropertyProvider<T> {

    private T tableValue = null;
    Map<Integer, T> columnValues = null;

    public T get(@Range(from = 0, to = Integer.MAX_VALUE) int index) {
        if (columnValues == null) {
            return tableValue;
        }
        T columnValue = columnValues.get(index);
        if (columnValue != null) {
            return columnValue;
        }
        return tableValue;
    }

    public void setValue(T value) {
        this.tableValue = value;
        this.columnValues = null;
    }

    public void setValue(@Range(from = 0, to = Integer.MAX_VALUE) int index, T value) {
        if (columnValues == null) {
            columnValues = new TreeMap<>();
        }
        columnValues.put(index, value);
    }
}