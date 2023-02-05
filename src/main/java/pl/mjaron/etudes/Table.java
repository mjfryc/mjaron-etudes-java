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
     * @since 0.2.0
     */
    public static <SourceT extends ITableSource> void renderWith(final SourceT source, final RenderContext options) {
        source.readTo(options);
    }

    /**
     * Creates table from any iterable.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param options  Rendering options
     * @param <T>      Element type
     * @since 0.2.0
     */
    public static <T> void render(final Iterable<T> iterable, final Class<T> tClass, final RenderContext options) {
        renderWith(new BeanTableSource<>(iterable, tClass), options);
    }

    /**
     * Creates table from any iterable.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param writer   Writer implementation instance
     * @param <T>      Element type
     * @since 0.2.0
     */
    public static <T> void render(final Iterable<T> iterable, final Class<T> tClass, final ITableWriter writer) {
        render(iterable, tClass, RenderContext.make().withWriter(writer));
    }

    /**
     * Creates table from any iterable.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param <T>      Element type
     * @since 0.2.0
     */
    public static <T> void render(final Iterable<T> iterable, final Class<T> tClass) {
        render(iterable, tClass, RenderContext.make());
    }

    /**
     * Creates table from any array.
     *
     * @param array   Any array
     * @param tClass  Element class
     * @param options Rendering options
     * @param <T>     Element type
     * @since 0.2.0
     */
    public static <T> void render(final T[] array, final Class<T> tClass, final RenderContext options) {
        render(Arrays.asList(array), tClass, options);
    }

    /**
     * Creates table from any array.
     *
     * @param array  Any array
     * @param tClass Element class
     * @param writer Writer implementation instance
     * @param <T>    Element type
     * @since 0.2.0
     */
    public static <T> void render(final T[] array, final Class<T> tClass, final ITableWriter writer) {
        render(array, tClass, RenderContext.make().withWriter(writer));
    }

    /**
     * Creates table from any array.
     *
     * @param array  Any array
     * @param tClass Element class
     * @param <T>    Element type
     * @since 0.2.0
     */
    public static <T> void render(final T[] array, final Class<T> tClass) {
        render(array, tClass, RenderContext.make());
    }

    /**
     * Creates {@link String} with rendered table.
     *
     * @param source    Table source.
     * @param options   Rendering options.
     * @param <SourceT> Source type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <SourceT extends ITableSource> String toString(final SourceT source, final RenderContext options) {
        StringBuilder stringBuilder = new StringBuilder();
        if (options.out() != null) {
            throw new IllegalArgumentException("Cannot render to String: Invalid render context: Appender already set with " + RenderContext.class.getSimpleName() + "::to(...) method.");
        }
        options.to(stringBuilder);
        source.readTo(options);
        return stringBuilder.toString();
    }

    /**
     * Creates {@link String} with rendered table.
     *
     * @param iterable Any iterable type. Each iteration describes single table row.
     * @param tClass   Iterable entry class.
     * @param options  Rendering options.
     * @param <T>      Iterable entry type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <T> String toString(final Iterable<T> iterable, final Class<T> tClass, final RenderContext options) {
        return toString(new BeanTableSource<>(iterable, tClass), options);
    }

    /**
     * Creates {@link String} with rendered table.
     *
     * @param iterable Any iterable type. Each iteration describes single table row.
     * @param tClass   Iterable entry class.
     * @param writer   Writer used for table rendering.
     * @param <T>      Iterable entry type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <T> String toString(final Iterable<T> iterable, final Class<T> tClass, final ITableWriter writer) {
        return toString(iterable, tClass, RenderContext.make().withWriter(writer));
    }

    /**
     * Creates {@link String} with rendered Markdown table.
     *
     * @param iterable Any iterable type. Each iteration describes single table row.
     * @param tClass   Iterable entry class.
     * @param <T>      Iterable entry type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <T> String toString(final Iterable<T> iterable, final Class<T> tClass) {
        return toString(iterable, tClass, RenderContext.make());
    }

    /**
     * Creates {@link String} with rendered table.
     *
     * @param array   Any array type. Each iteration describes single table row.
     * @param tClass  Array entry class.
     * @param options Rendering options.
     * @param <T>     Array entry type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <T> String toString(final T[] array, final Class<T> tClass, final RenderContext options) {
        return toString(Arrays.asList(array), tClass, options);
    }

    /**
     * Creates {@link String} with rendered table.
     *
     * @param array  Any array type. Each iteration describes single table row.
     * @param tClass Array entry class.
     * @param writer Writer used to render the table.
     * @param <T>    Array entry type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <T> String toString(final T[] array, final Class<T> tClass, final ITableWriter writer) {
        return toString(array, tClass, RenderContext.make().withWriter(writer));
    }

    /**
     * Creates {@link String} with rendered Markdown table.
     *
     * @param array  Any array type. Each iteration describes single table row.
     * @param tClass Array entry class.
     * @param <T>    Array entry type.
     * @return {@link String} containing rendered table.
     * @since 0.2.0
     */
    public static <T> String toString(final T[] array, final Class<T> tClass) {
        return toString(array, tClass, RenderContext.make());
    }
}
