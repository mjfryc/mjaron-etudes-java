/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
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

/**
 * Simple object for storing two related objects called key and value.
 *
 * @param <T> First object type.
 * @param <U> Second object type.
 */
public class Pair<T, U> {
    final private T key;
    final private U value;

    /**
     * Initializes pair with key and value.
     *
     * @param key   First object instance. It may be `null`.
     * @param value Second object instance. It may be `null`.
     */
    public Pair(final T key, final U value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Provides first object instance.
     *
     * @return First object instance. It may be `null`.
     */
    public T getKey() {
        return key;
    }

    /**
     * Provides second object instance.
     *
     * @return Second object instance. It may be `null`.
     */
    public U getValue() {
        return value;
    }
}
