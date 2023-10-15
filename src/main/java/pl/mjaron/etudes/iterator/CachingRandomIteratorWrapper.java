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

package pl.mjaron.etudes.iterator;

import java.util.ArrayList;
import java.util.Iterator;

import pl.mjaron.etudes.IRandomIterator;

/**
 * {@link IRandomIterator} implementation designed for objects which doesn't provide the
 * {@link java.util.RandomAccess}.
 * <p>
 * It caches the already accessed values to skip object iteration every time when random position is accessed.
 *
 * @param <T> Iterated container element type.
 * @since 0.3.0
 */
public class CachingRandomIteratorWrapper<T> implements IRandomIterator<T> {

    private final Iterator<T> it;

    private final ArrayList<T> cache = new ArrayList<>();

    private int pos = FLOOR;

    public CachingRandomIteratorWrapper(Iterator<T> it) {
        this.it = it;
    }

    public boolean tryReadToCache() {
        if (!it.hasNext()) {

            return false;
        }
        cache.add(it.next());
        return true;
    }

    public boolean tryReadToCache(final int pos) {
        while (cache.size() <= pos) {
            if (!tryReadToCache()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getPosition() {
        return this.pos;
    }

    @Override
    public void setPosition(final int position) {
        if (position < FLOOR) {
            throw new RuntimeException("Cannot set position with index lower than " + FLOOR + ". Current position: " + this.pos + ", requested position:" + position);
        }
        if (!tryReadToCache(position)) {
            throw new RuntimeException("Cannot increment the " + CachingRandomIteratorWrapper.class.getSimpleName() + " object: Underlying object doesn't have such elements count. Current position: " + pos + ", requested position: " + position + ", cached elements count: " + cache.size());
        }
        this.pos = position;
    }

    @Override
    public void increment(final int count) {
        setPosition(this.pos + count);
    }

    @Override
    public void increment() {
        this.increment(1);
    }

    @Override
    public void decrement(final int count) {
        setPosition(this.pos - count);
    }

    @Override
    public void decrement() {
        decrement(1);
    }

    @Override
    public T getCurrent() {
        return cache.get(this.pos);
    }

    @Override
    public boolean hasNext() {
        return tryReadToCache(this.pos + 1);
    }

    @Override
    public T next() {
        increment();
        return getCurrent();
    }
}
