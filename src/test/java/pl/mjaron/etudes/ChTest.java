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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChTest {

    @Test
    void isCntrl() {
        assertTrue(Ch.isCntrl('\0'));
        assertTrue(Ch.isCntrl('\t'));
        assertFalse(Ch.isCntrl(' '));
        assertTrue(Ch.isCntrl(Ch.DEL)); // 127 DEC.
        assertFalse(Ch.isCntrl(':'));
    }

    @Test
    void isPrint() {
        assertFalse(Ch.isPrint('\t'));
        assertTrue(Ch.isPrint(' '));
        assertTrue(Ch.isPrint('!'));
        assertTrue(Ch.isPrint('1'));
        assertTrue(Ch.isPrint('@'));
        assertTrue(Ch.isPrint('B'));
        assertTrue(Ch.isPrint('R'));
        assertTrue(Ch.isPrint('`'));
        assertTrue(Ch.isPrint('c'));
        assertTrue(Ch.isPrint('z'));
        assertTrue(Ch.isPrint('~'));
        assertFalse(Ch.isPrint(Ch.DEL)); // 127 DEC.
    }

    @Test
    void isSpace() {
        assertFalse(Ch.isSpace('\0'));
        assertTrue(Ch.isSpace('\t'));
        assertTrue(Ch.isSpace('\n'));
        assertTrue(Ch.isSpace('\f'));
        assertTrue(Ch.isSpace('\r'));
        assertFalse(Ch.isSpace((char) 14));
        assertFalse(Ch.isSpace((char) 31));
        assertTrue(Ch.isSpace(' '));
        assertFalse(Ch.isSpace('!'));
        assertFalse(Ch.isSpace('1'));
        assertFalse(Ch.isSpace('~'));
        assertFalse(Ch.isSpace(Ch.DEL));
    }

    @Test
    void isBlank() {
        assertFalse(Ch.isBlank('\0'));
        assertTrue(Ch.isBlank('\t'));
        assertFalse(Ch.isBlank('\r'));
        assertFalse(Ch.isBlank((char) 31));
        assertTrue(Ch.isBlank(' '));
        assertFalse(Ch.isBlank('!'));
        assertFalse(Ch.isBlank(Ch.DEL));
    }

    @Test
    void isGraph() {
        assertFalse(Ch.isGraph('\0'));
        assertFalse(Ch.isGraph('\t'));
        assertFalse(Ch.isGraph('\r'));
        assertFalse(Ch.isGraph((char) 31));
        assertFalse(Ch.isGraph(' '));
        assertTrue(Ch.isGraph('!'));
        assertTrue(Ch.isGraph('T'));
        assertTrue(Ch.isGraph('~'));
        assertFalse(Ch.isGraph(Ch.DEL));
    }

    @Test
    void isPunct() {
        assertFalse(Ch.isPunct('\0'));
        assertFalse(Ch.isPunct('\t'));
        assertFalse(Ch.isPunct('\r'));
        assertFalse(Ch.isPunct((char) 31));
        assertFalse(Ch.isPunct(' '));
        assertTrue(Ch.isPunct('!'));
        assertFalse(Ch.isPunct('0'));
        assertFalse(Ch.isPunct('9'));
        assertTrue(Ch.isPunct(':'));
        assertTrue(Ch.isPunct('@'));
        assertFalse(Ch.isPunct('A'));
        assertFalse(Ch.isPunct('Z'));
        assertTrue(Ch.isPunct('['));
        assertFalse(Ch.isPunct('a'));
        assertFalse(Ch.isPunct('z'));
        assertTrue(Ch.isPunct('~'));
        assertFalse(Ch.isPunct(Ch.DEL));
    }

    @Test
    void isAlnum() {
        assertFalse(Ch.isAlnum('\0'));
        assertFalse(Ch.isAlnum('\t'));
        assertFalse(Ch.isAlnum('\r'));
        assertFalse(Ch.isAlnum((char) 31));
        assertFalse(Ch.isAlnum(' '));
        assertFalse(Ch.isAlnum('!'));
        assertTrue(Ch.isAlnum('0'));
        assertTrue(Ch.isAlnum('9'));
        assertFalse(Ch.isAlnum(':'));
        assertFalse(Ch.isAlnum('@'));
        assertTrue(Ch.isAlnum('A'));
        assertTrue(Ch.isAlnum('Z'));
        assertFalse(Ch.isAlnum('['));
        assertTrue(Ch.isAlnum('a'));
        assertTrue(Ch.isAlnum('z'));
        assertFalse(Ch.isAlnum('~'));
        assertFalse(Ch.isAlnum(Ch.DEL));
    }

    @Test
    void isAlpha() {
        assertFalse(Ch.isAlpha('\0'));
        assertFalse(Ch.isAlpha('\t'));
        assertFalse(Ch.isAlpha('\r'));
        assertFalse(Ch.isAlpha((char) 31));
        assertFalse(Ch.isAlpha(' '));
        assertFalse(Ch.isAlpha('!'));
        assertFalse(Ch.isAlpha('0'));
        assertFalse(Ch.isAlpha('9'));
        assertFalse(Ch.isAlpha(':'));
        assertFalse(Ch.isAlpha('@'));
        assertTrue(Ch.isAlpha('A'));
        assertTrue(Ch.isAlpha('Z'));
        assertFalse(Ch.isAlpha('['));
        assertTrue(Ch.isAlpha('a'));
        assertTrue(Ch.isAlpha('z'));
        assertFalse(Ch.isAlpha('~'));
        assertFalse(Ch.isAlpha(Ch.DEL));
    }

    @Test
    void isUpper() {
        assertFalse(Ch.isUpper('\0'));
        assertFalse(Ch.isUpper('\t'));
        assertFalse(Ch.isUpper('\r'));
        assertFalse(Ch.isUpper((char) 31));
        assertFalse(Ch.isUpper(' '));
        assertFalse(Ch.isUpper('!'));
        assertFalse(Ch.isUpper('0'));
        assertFalse(Ch.isUpper('9'));
        assertFalse(Ch.isUpper(':'));
        assertFalse(Ch.isUpper('@'));
        assertTrue(Ch.isUpper('A'));
        assertTrue(Ch.isUpper('Z'));
        assertFalse(Ch.isUpper('['));
        assertFalse(Ch.isUpper('a'));
        assertFalse(Ch.isUpper('z'));
        assertFalse(Ch.isUpper('~'));
        assertFalse(Ch.isUpper(Ch.DEL));
    }

    @Test
    void isLower() {
        assertFalse(Ch.isLower('\0'));
        assertFalse(Ch.isLower('\t'));
        assertFalse(Ch.isLower('\r'));
        assertFalse(Ch.isLower((char) 31));
        assertFalse(Ch.isLower(' '));
        assertFalse(Ch.isLower('!'));
        assertFalse(Ch.isLower('0'));
        assertFalse(Ch.isLower('9'));
        assertFalse(Ch.isLower(':'));
        assertFalse(Ch.isLower('@'));
        assertFalse(Ch.isLower('A'));
        assertFalse(Ch.isLower('Z'));
        assertFalse(Ch.isLower('['));
        assertTrue(Ch.isLower('a'));
        assertTrue(Ch.isLower('z'));
        assertFalse(Ch.isLower('~'));
        assertFalse(Ch.isLower(Ch.DEL));
    }

    @Test
    void isDigit() {
        assertFalse(Ch.isDigit('\0'));
        assertFalse(Ch.isDigit('\t'));
        assertFalse(Ch.isDigit('\r'));
        assertFalse(Ch.isDigit((char) 31));
        assertFalse(Ch.isDigit(' '));
        assertFalse(Ch.isDigit('!'));
        assertTrue(Ch.isDigit('0'));
        assertTrue(Ch.isDigit('9'));
        assertFalse(Ch.isDigit(':'));
        assertFalse(Ch.isDigit('@'));
        assertFalse(Ch.isDigit('A'));
        assertFalse(Ch.isDigit('Z'));
        assertFalse(Ch.isDigit('['));
        assertFalse(Ch.isDigit('a'));
        assertFalse(Ch.isDigit('z'));
        assertFalse(Ch.isDigit('~'));
        assertFalse(Ch.isDigit(Ch.DEL));
    }

    @Test
    void isXdigit() {
        assertFalse(Ch.isXdigit('\0'));
        assertFalse(Ch.isXdigit('\t'));
        assertFalse(Ch.isXdigit('\r'));
        assertFalse(Ch.isXdigit((char) 31));
        assertFalse(Ch.isXdigit(' '));
        assertFalse(Ch.isXdigit('!'));
        assertTrue(Ch.isXdigit('0'));
        assertTrue(Ch.isXdigit('9'));
        assertFalse(Ch.isXdigit(':'));
        assertFalse(Ch.isXdigit('@'));
        assertTrue(Ch.isXdigit('A'));
        assertTrue(Ch.isXdigit('F'));
        assertFalse(Ch.isXdigit('Z'));
        assertFalse(Ch.isXdigit('['));
        assertTrue(Ch.isXdigit('a'));
        assertTrue(Ch.isXdigit('f'));
        assertFalse(Ch.isXdigit('g'));
        assertFalse(Ch.isXdigit('z'));
        assertFalse(Ch.isXdigit('~'));
        assertFalse(Ch.isXdigit(Ch.DEL));
    }

    @Test
    void toHexValue() {
        assertEquals(0x00, Ch.toHexValue('0'));
        assertEquals(0x09, Ch.toHexValue('9'));
        assertEquals(0x0A, Ch.toHexValue('a'));
        assertEquals(0x0A, Ch.toHexValue('A'));
        assertEquals(0x0F, Ch.toHexValue('f'));
        assertEquals(0x0F, Ch.toHexValue('F'));
    }
}