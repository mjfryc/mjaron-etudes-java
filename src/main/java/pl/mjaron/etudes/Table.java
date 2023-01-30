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

import pl.mjaron.etudes.table.*;

import java.util.Arrays;

/**
 * A set of methods used to generate table, e.g, any {@link Iterable} or array may be converted to following
 * {@link String}:
 *
 * <pre>
 * | name | legsCount | lazy | topSpeed |
 * | ---- | --------- | ---- | -------- |
 * | John |         4 | true |    35.24 |
 * </pre>
 */
public abstract class Table {

    /**
     * Read from table source and write to table writer.
     *
     * @param source    Table source.
     * @param options   Rendering options
     * @param <SourceT> Type of table source.
     * @param <WriterT> Type of table destination.
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <SourceT extends ITableSource, WriterT extends ITableWriter> String renderWith(final SourceT source, final RenderOptions options) {
        source.readTo(options);
        return options.getWriter().getTable();
    }

    /**
     * Creates table from any iterable.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param options  Rendering options
     * @param <T>      Element type
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <T> String render(final Iterable<T> iterable, final Class<T> tClass, final RenderOptions options) {
        return renderWith(new BeanTableSource<>(iterable, tClass), options);
    }

    /**
     * Creates table from any iterable.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param writer   Writer implementation instance
     * @param <T>      Element type
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <T> String render(final Iterable<T> iterable, final Class<T> tClass, final ITableWriter writer) {
        return render(iterable, tClass, RenderOptions.make().withWriter(writer));
    }

    /**
     * Creates table from any iterable.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param <T>      Element type
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <T> String render(final Iterable<T> iterable, final Class<T> tClass) {
        return render(iterable, tClass, RenderOptions.make());
    }

    /**
     * Creates table from any array.
     *
     * @param array   Any array
     * @param tClass  Element class
     * @param options Rendering options
     * @param <T>     Element type
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <T> String render(final T[] array, final Class<T> tClass, final RenderOptions options) {
        return render(Arrays.asList(array), tClass, options);
    }

    /**
     * Creates table from any array.
     *
     * @param array  Any array
     * @param tClass Element class
     * @param writer Writer implementation instance
     * @param <T>    Element type
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <T> String render(final T[] array, final Class<T> tClass, final ITableWriter writer) {
        return render(array, tClass, RenderOptions.make().withWriter(writer));
    }

    /**
     * Creates table from any array.
     *
     * @param array  Any array
     * @param tClass Element class
     * @param <T>    Element type
     * @return Table rendered as text
     * @since 0.1.12
     */
    public static <T> String render(final T[] array, final Class<T> tClass) {
        return render(array, tClass, RenderOptions.make());
    }
}
