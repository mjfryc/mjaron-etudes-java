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

import org.junit.jupiter.api.Test;
import pl.mjaron.etudes.iterator.CachingRandomIteratorWrapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CachingRandomIteratorWrapperTest {

    private static final List<Integer> list = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17);

    @Test
    void getPosition() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        assertEquals(IRandomIterator.FLOOR, it.getPosition());
    }

    @Test
    void setPosition() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        it.setPosition(2);
        assertEquals(2, it.getPosition());
        assertEquals(12, it.getCurrent());
    }

    @Test
    void increment() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        it.increment();
        it.increment(2);
        it.increment(2);
        assertEquals(4, it.getPosition());
        assertEquals(14, it.getCurrent());
    }

    @Test
    void decrement() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        it.setPosition(5);
        it.decrement();
        it.decrement(2);
        assertEquals(5 - 1 - 2, it.getPosition());
        assertEquals(12, it.getCurrent());
    }

    @Test
    void curr() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        it.setPosition(list.size() - 1);
        assertEquals(list.get(list.size() - 1), it.getCurrent());
    }

    @Test
    void hasNext() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        it.setPosition(list.size() - 1);
        assertFalse(it.hasNext());
    }

    @Test
    void next() {
        final CachingRandomIteratorWrapper<Integer> it = new CachingRandomIteratorWrapper<>(list.iterator());
        it.next();
        assertEquals(0, it.getPosition());
        assertEquals(10, it.getCurrent());
    }
}
