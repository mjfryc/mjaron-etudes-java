/*
 * Copyright  2022  Michał Jaroń <m.jaron@protonmail.com>
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IntTest {

    @Test
    void getHNibble() {
        assertEquals(0xA, Int.getHNibble((byte) 0xAB));
        assertEquals(0x0, Int.getHNibble((byte) 0x0B));
        assertEquals(0xA, Int.getHNibble(0xAB));
        assertEquals(0x0, Int.getHNibble(0x0B));
    }

    @Test
    void setHNibble() {
        assertEquals(0xAB, Int.setHNibble((byte) 0xFB, 0xA));
        assertEquals(0xAB, Int.setHNibble(0xFB, 0xA));
    }

    @Test
    void getLNibble() {
        assertEquals(0x0C, Int.getLNibble((byte) 0xAC));
        assertEquals(0x0C, Int.getLNibble(0xAC));
    }

    @Test
    void setLNibble() {
        assertEquals(0xC1, Int.setLNibble((byte) 0xCC, 0x1));
    }

    @Test
    void getNthUByteLE() {
        assertEquals(0xCD, Int.getNthUByteLE(0xABCDEF01, 2));
    }

    @Test
    void toByteArrayBE() {
        assertArrayEquals(new byte[]{(byte) 0xDD, (byte) 0x01, (byte) 0x10, (byte) 0x11}, Int.toByteArrayBE(0xDD011011));
    }

    @Test
    void toByteArrayLE() {
        assertArrayEquals(new byte[]{(byte) 0x11, (byte) 0x10, (byte) 0x01, (byte) 0xDD}, Int.toByteArrayLE(0xDD011011));
    }
}
