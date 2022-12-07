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

import static org.junit.jupiter.api.Assertions.*;

class StrTest {

    @Test
    void charsCount() {
        assertEquals(2, Str.charsCount("commit", 'm'));
    }

    @Test
    void capitalize() {
        assertEquals("String", Str.capitalize("string"));
    }

    @Test
    void padLeft() {
        assertEquals("  3", Str.padLeft("3", 3));
    }

    @Test
    void padRight() {
        assertEquals("3  ", Str.padRight("3", 3));
    }

    @Test
    void lastIndexAnyOf() {
        assertEquals(5, Str.lastIndexAnyOf("geolocation", "xyc"));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void isEmpty() {
        assertTrue(Str.isEmpty(null));
        assertTrue(Str.isEmpty(""));
        assertFalse(Str.isEmpty("a"));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void notEmpty() {
        assertFalse(Str.notEmpty(null));
        assertFalse(Str.notEmpty(""));
        assertTrue(Str.notEmpty("a"));
    }

    @Test
    void ifEmpty() {
        assertEquals("a", Str.ifEmpty("a", "b"));
        assertEquals("b", Str.ifEmpty("", "b"));
        assertEquals("b", Str.ifEmpty(null, "b"));
    }

    @Test
    void hexInt() {
        final int[] arr = {0xAF, 0x01, 0x00, 0xF0};
        assertEquals("AF 01 00 F0", Str.hex(arr));
    }

    @Test
    void hexByte() {
        final byte[] arr = {(byte) 0xAF, (byte) 0x01, (byte) 0x00, (byte) 0xF0};
        assertEquals("AF 01 00 F0", Str.hex(arr));
    }

    @Test
    void hexByteMultiLine() {
        final byte[] arr = {(byte) 0xAF, (byte) 0x01, (byte) 0x00, (byte) 0xF0};
        assertEquals("AF 01\n00 F0", Str.hex(arr, 0, arr.length, 2, " "));
    }

    @Test
    void hexStrToByteArr() {
        final byte[] expectedArray = {(byte) 0xAF, (byte) 0x01, (byte) 0x00, (byte) 0xF0};
        final String hStr = "AF0100F0";
        final byte[] arr = Str.hex(hStr);
        assertArrayEquals(expectedArray, arr);
    }
}
