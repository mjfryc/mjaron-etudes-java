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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mjaron.etudes.container.ListRandomAccess;
import pl.mjaron.etudes.container.VectorRandomAccess;

import java.util.List;
import java.util.Vector;

/**
 * Unifying interface for objects implementing {@link java.util.RandomAccess} interface. Creation of
 * {@link IRandomAccess} object should fail if object is not a {@link java.util.RandomAccess}.
 *
 * @param <T> Container element type.
 * @since 0.3.0
 */
public interface IRandomAccess<T> {

    /**
     * Provides container elements count.
     *
     * @return Elements count.
     * @since 0.3.0
     */
    @Contract(pure = true)
    int size();

    /**
     * Provides element with given index.
     *
     * @param position Element position counting from <code>0</code>.
     * @return Element at given position. It may be <code>null</code>.
     * @since 0.3.0
     */
    @Contract(pure = true)
    @Nullable T get(int position);

    /**
     * Creates appropriate {@link IRandomAccess} instance.
     *
     * @param list List implementing {@link java.util.RandomAccess}.
     * @param <U>  List element type.
     * @return Appropriate {@link IRandomAccess} instance.
     * @throws IllegalArgumentException when given list is not a {@link java.util.RandomAccess}.
     * @since 0.3.0
     */
    @NotNull
    static <U> IRandomAccess<U> from(@NotNull List<U> list) {
        return new ListRandomAccess<>(list);
    }

    /**
     * Creates appropriate {@link IRandomAccess} instance.
     *
     * @param vector {@link Vector} instance.
     * @param <U>    Vector element type.
     * @return Appropriate {@link IRandomAccess} instance.
     * @since 0.3.0
     */
    @NotNull
    static <U> IRandomAccess<U> from(@NotNull Vector<U> vector) {
        return new VectorRandomAccess<>(vector);
    }
}
