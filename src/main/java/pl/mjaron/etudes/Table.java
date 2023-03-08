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

import org.jetbrains.annotations.Contract;
import pl.mjaron.etudes.table.*;

import java.util.Arrays;

/**
 * A set of methods used to generate table, e.g, any {@link Iterable} or array may be converted to following
 * {@link String}:
 *
 * <pre>
 * | name | legsCount | lazy | topSpeed |
 * |------|-----------|------|----------|
 * | John |         4 | true |    35.24 |
 * </pre>
 *
 * @since 0.2.0
 */
public abstract class Table {

    /**
     * Used to generate the table. Creates the table {@link RenderContext} from any {@link ITableSource}.
     *
     * @param source The {@link ITableSource} which provides table headers and data.
     * @return New instance of {@link RenderContext}.
     * <p>Call the {@link RenderContext#run()} or {@link RenderContext#runToString()} to generate the table.</p>
     * @since 0.2.0
     */
    @Contract(pure = true)
    public static RenderContext render(final ITableSource source) {
        RenderContext context = RenderContext.make();
        context.setSource(source);
        return context;
    }

    /**
     * Used to generate the table. Creates the table {@link RenderContext} from any {@link Iterable}.
     *
     * @param iterable Any iterable
     * @param tClass   Element class
     * @param <T>      Element type
     * @return New instance of {@link RenderContext}.
     * <p>Call the {@link RenderContext#run()} or {@link RenderContext#runToString()} to generate the table.</p>
     * @since 0.2.0
     */
    @Contract(pure = true)
    public static <T> RenderContext render(final Iterable<T> iterable, final Class<T> tClass) {
        return render(new BeanTableSource<>(iterable, tClass));
    }

    /**
     * Used to generate the table. Creates the table {@link RenderContext} from any array.
     *
     * @param array  Any array
     * @param tClass Element class
     * @param <T>    Element type
     * @return New instance of {@link RenderContext}.
     * <p>Call the {@link RenderContext#run()} or {@link RenderContext#runToString()} to generate the table.</p>
     * @since 0.2.0
     */
    @Contract(pure = true)
    public static <T> RenderContext render(final T[] array, final Class<T> tClass) {
        return render(Arrays.asList(array), tClass);
    }
}
