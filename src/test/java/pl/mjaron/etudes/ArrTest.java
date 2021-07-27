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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrTest {

    @Test
    void add() {

        int[] myArray = new int[10];
        int[] extendedArray = Arr.add(myArray, 1);
        assertEquals(1, extendedArray[10]);
    }

    @Test
    void add1() {
        int[] a = new int[]{1, 2, 3};
        int[] b = new int[]{6, 7, 5};
        int[] c = Arr.add(a, b);
        assertArrayEquals(c, new int[]{1, 2, 3, 6, 7, 5});
    }

    @Test
    void add2() {
        char[] a = new char[]{};
        char[] b = new char[]{};
        char[] c = Arr.add(a, b);
        assertArrayEquals(c, new char[]{});
    }

    @Test
    void add3() {
        boolean[] a = new boolean[]{false};
        boolean[] b = new boolean[]{};
        boolean[] c = Arr.add(a, b);
        assertArrayEquals(c, new boolean[]{false});
    }
}
