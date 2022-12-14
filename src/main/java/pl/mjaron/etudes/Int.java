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

public class Int {

    /**
     * Provides high four bits of given byte.
     *
     * @param what Byte with bits to read.
     * @return Value in range <code>0..15</code> representing high four bits of given byte.
     */
    public static int getHNibble(final byte what) {
        return (what >>> 4) & 0x0F;
    }

    /**
     * Provides high four bits of given byte.
     *
     * @param what Value in range <code>0..255</code>. Other bits are ignored.
     * @return Value in range <code>0..15</code> representing high four bits of given byte.
     */
    public static int getHNibble(final int what) {
        return (what >>> 4) & 0x0F;
    }

    /**
     * Sets the high nibble of byte.
     *
     * @param what  Value in range <code>0..255</code>. Other bits are ignored.
     * @param value Value in range <code>0..15</code> representing high four bits to update.
     * @return Updated value in range <code>0..255</code>.
     */
    public static int setHNibble(final byte what, final int value) {
        final int lowNibble = what & 0x0F;
        final int highNibble = (value & 0x0F) << 4;
        return lowNibble | highNibble;
    }

    public static int setHNibble(final int what, final int value) {
        final int lowNibble = what & 0x0F;
        final int highNibble = (value & 0x0F) << 4;
        return lowNibble | highNibble;
    }

    /**
     * Provides low four bits of given byte.
     *
     * @param what Byte with bits to read.
     * @return Value in range <code>0..15</code> representing low four bits of given byte.
     */
    public static int getLNibble(final byte what) {
        return what & 0x0F;
    }

    /**
     * Provides low four bits of given byte.
     *
     * @param what Byte with bits to read.
     * @return Value in range <code>0..15</code> representing low four bits of given byte.
     */
    public static int getLNibble(final int what) {
        return what & 0x0F;
    }

    /**
     * Sets the low nibble of byte.
     *
     * @param what  Value in range <code>0..255</code>. Other bits are ignored.
     * @param value Value in range <code>0..15</code> representing low four bits to update.
     * @return Updated value in range <code>0..255</code>.
     */
    public static int setLNibble(final byte what, final int value) {
        final int lowNibble = value & 0x0F;
        final int highNibble = what & 0xF0;
        return lowNibble | highNibble;
    }

    /**
     * Extracts nth unsigned byte from given number in little-endian order.
     *
     * @param number     Number from where single byte (octet) will be extracted.
     * @param byteNumber Byte number to extract where byte 0 is the least significant byte and byte 3 is the most
     *                   significant byte (little-endian).
     * @return Value from range <code>0..255</code> with single byte extracted in little-endian order.
     */
    public static int getNthUByteLE(final int number, final int byteNumber) {
        return (number >> (byteNumber * 8)) & 0xFF;
    }

    /**
     * Converts Integer to big-endian byte array where arr[0] is the most significant byte and arr[3] is the least
     * significant byte.
     *
     * @param what 32-bit number which will be stored as four 8-bit bytes.
     * @param arr  Array where results will be stored.
     */
    public static void toByteArrayBE(final int what, byte[] arr) {
        arr[0] = (byte) ((what & 0xFF000000) >> 24);
        arr[1] = (byte) ((what & 0x00FF0000) >> 16);
        arr[2] = (byte) ((what & 0x0000FF00) >> 8);
        arr[3] = (byte) ((what & 0x000000FF));
    }

    /**
     * Converts Integer to big-endian byte array where arr[0] is the most significant byte and arr[3] is the least
     * significant byte.
     *
     * @param what 32-bit number which will be stored as four 8-bit bytes.
     * @return Array where results will be stored.
     */
    public static byte[] toByteArrayBE(int what) {
        byte[] result = new byte[4];
        toByteArrayBE(what, result);
        return result;
    }

    /**
     * Converts Integer to little-endian byte array where arr[0] is the least significant byte and arr[3] is the most
     * significant byte.
     *
     * @param what 32-bit number which will be stored as four 8-bit bytes.
     * @param arr  Array where results will be stored.
     */
    public static void toByteArrayLE(final int what, byte[] arr) {
        arr[0] = (byte) ((what & 0x000000FF));
        arr[1] = (byte) ((what & 0x0000FF00) >> 8);
        arr[2] = (byte) ((what & 0x00FF0000) >> 16);
        arr[3] = (byte) ((what & 0xFF000000) >> 24);
    }

    /**
     * Converts Integer to little-endian byte array where arr[0] is the least significant byte and arr[3] is the most
     * significant byte.
     *
     * @param what 32-bit number which will be stored as four 8-bit bytes.
     * @return Array where results will be stored.
     */
    public static byte[] toByteArrayLE(int what) {
        byte[] result = new byte[4];
        toByteArrayLE(what, result);
        return result;
    }
}
