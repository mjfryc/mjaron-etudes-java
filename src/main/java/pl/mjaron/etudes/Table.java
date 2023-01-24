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

package pl.mjaron.etudes;

import pl.mjaron.etudes.table.BeanTableSource;
import pl.mjaron.etudes.table.ITableSource;
import pl.mjaron.etudes.table.ITableWriter;
import pl.mjaron.etudes.table.MarkdownTableWriter;

import java.util.Arrays;

public abstract class Table {

    /**
     * Prints iterable as a table, e.g:
     *
     * <pre>
     * | name | legsCount | lazy | topSpeed |
     * | ---- | --------- | ---- | -------- |
     * | John |         4 | true |    35.24 |
     * </pre>
     *
     * @param iterable Any iterable collection.
     * @param tClass   Class of iterated entries.
     * @param <T>      Type of iterated entries.
     * @return Markdown table.
     */
    public static <T> String render(final Iterable<T> iterable, final Class<T> tClass) {
        return renderAs(iterable, tClass, new MarkdownTableWriter());
    }

    public static <T> String render(final T[] array, final Class<T> tClass) {
        return renderAs(array, tClass, new MarkdownTableWriter());
    }

    public static <T> String renderAs(final Iterable<T> iterable, final Class<T> tClass, final ITableWriter writer) {
        return renderWith(new BeanTableSource<>(iterable, tClass), writer);
    }

    public static <T> String renderAs(final T[] array, final Class<T> tClass, final ITableWriter writer) {
        return renderAs(Arrays.asList(array), tClass, writer);
    }

    /**
     * Read from table source and write to table writer.
     *
     * @param source    Table source.
     * @param writer    Table destination.
     * @param <SourceT> Type of table source.
     * @param <WriterT> Type of table destination.
     * @return Table written as a String.
     */
    public static <SourceT extends ITableSource, WriterT extends ITableWriter> String renderWith(final SourceT source, final WriterT writer) {
        source.readTo(writer);
        return writer.getTable();
    }
}
