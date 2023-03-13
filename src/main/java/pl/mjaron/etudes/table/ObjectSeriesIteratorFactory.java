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

package pl.mjaron.etudes.table;

import java.util.Iterator;

public abstract class ObjectSeriesIteratorFactory {

    public static Iterator<Object> from(final byte[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final short[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final int[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final long[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final float[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final double[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final boolean[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

    public static Iterator<Object> from(final char[] src) {
        return new Iterator<Object>() {
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < src.length;
            }

            @Override
            public Object next() {
                return src[idx++];
            }
        };
    }

}
