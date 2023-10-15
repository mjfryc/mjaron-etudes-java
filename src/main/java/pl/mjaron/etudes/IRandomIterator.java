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

import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.Vector;

import pl.mjaron.etudes.iterator.CachingRandomIteratorWrapper;
import pl.mjaron.etudes.iterator.RandomAccessIteratorWrapper;

/**
 * Specifies the iterator which allows accessing the random positions.
 *
 * @param <T> Container element type.
 * @since 0.3.0
 */
public interface IRandomIterator<T> extends ICurrIterator<T> {

    /**
     * Defines the initial random iterator position which points before the first element. Calling
     * {@link Iterator#next()} increments this value.
     */
    int FLOOR = -1;

    /**
     * Creates the iterator which caches the iterated values to achieve good performance for objects which doesn't
     * implement {@link RandomAccess}.
     *
     * @param it  Underlying iterator instance.
     * @param <U> Container element type.
     * @return Appropriate {@link IRandomIterator} instance.
     * @since 0.3.0
     */
    @NotNull
    static <U> IRandomIterator<U> from(Iterator<U> it) {
        return new CachingRandomIteratorWrapper<>(it);
    }

    /**
     * Determines whether given {@link List} implements {@link RandomAccess} and provides the best implementation of
     * {@link IRandomIterator}.
     *
     * @param list Any list.
     * @param <U>  Container element type.
     * @return Appropriate {@link IRandomIterator} instance.
     * @since 0.3.0
     */
    @NotNull
    static <U> IRandomIterator<U> from(List<U> list) {
        if (list instanceof RandomAccess) {
            return new RandomAccessIteratorWrapper<>(IRandomAccess.from(list));
        }
        return new CachingRandomIteratorWrapper<>(list.iterator());
    }

    /**
     * Creates {@link IRandomIterator} from {@link Vector} object.
     *
     * @param vector {@link Vector} object instance.
     * @param <U>    Container element type.
     * @return Appropriate {@link IRandomIterator} instance.
     * @since 0.3.0
     */
    @NotNull
    static <U> IRandomIterator<U> from(Vector<U> vector) {
        return new RandomAccessIteratorWrapper<>(IRandomAccess.from(vector));
    }

    /**
     * Creates {@link IRandomIterator} from any {@link Iterable} object.
     *
     * @param it  Any iterable object.
     * @param <U> Container element type.
     * @return Appropriate {@link IRandomIterator} instance.
     * @since 0.3.0
     */
    @NotNull
    static <U> IRandomIterator<U> from(Iterable<U> it) {
        return new CachingRandomIteratorWrapper<>(it.iterator());
    }

    /**
     * Tells whether this iterator is at the {@link #FLOOR} position.
     *
     * @return <code>true</code> when this iterator is at the {@link #FLOOR} position.
     * @since 0.3.0
     */
    default boolean isFloorPosition() {
        return getPosition() == FLOOR;
    }

    default void setFloorPosition() {
        this.setPosition(FLOOR);
    }

    /**
     * Provides current element position.
     *
     * @return Current element position.
     * @since 0.3.0
     */
    @Contract(pure = true)
    int getPosition();

    /**
     * Sets the current element position.
     *
     * @param position Requested position to set.
     * @since 0.3.0
     */
    void setPosition(int position);

    /**
     * Increases the current element position.
     *
     * @param count Tells how much to increase the position.
     * @since 0.3.0
     */
    void increment(int count);

    /**
     * Increases the current element position by one.
     *
     * @since 0.3.0
     */
    void increment();

    /**
     * Decreases the current element position.
     *
     * @param count Tells how much to decrease the position.
     * @since 0.3.0
     */
    void decrement(int count);

    /**
     * Decreases the current element position by one.
     *
     * @since 0.3.0
     */
    void decrement();
}
