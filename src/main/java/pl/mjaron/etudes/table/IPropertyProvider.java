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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides information about property value used by each table cell.
 *
 * @param <T> Property type.
 * @since 0.3.0
 */
public interface IPropertyProvider<T> {

    /**
     * Resolves the value for given table cell.
     *
     * @param column Table column index.
     * @param row    Table row index.
     * @param clazz  Class of cell entry.
     * @return Property value or null when cannot determine the value.
     * @since 0.3.1
     */
    @Contract(pure = true)
    @Nullable T get(final int column, final int row, final @Nullable Class<?> clazz);

    /**
     * Resolves the value for given table cell.
     *
     * @param column Table column index.
     * @param row    Table row index.
     * @return Property value or null when cannot determine the value.
     * @since 0.3.0
     */
    @Contract(pure = true)
    default @Nullable T get(final int column, final int row) {
        return get(column, row, null);
    }

    @Contract(pure = true)
    @NotNull
    default T getOrDefault(final int column, final int row, final @Nullable Class<?> clazz, @NotNull final T defaultValue) {
        final T nullableValue = get(column, row, clazz);
        if (nullableValue == null) {
            return defaultValue;
        }
        return nullableValue;
    }

    default T getOrDefault(final int column, final int row, @NotNull final T defaultValue) {
        return getOrDefault(column, row, null, defaultValue);
    }
}
