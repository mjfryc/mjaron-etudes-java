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

/**
 * Character utils. Based on: <a href="https://en.cppreference.com/w/cpp/string/byte#Character_classification">C++
 * Character classification</a>
 */
public abstract class Ch {

    /**
     * ASCII backspace character, 127 DEC, 0x7F HEX.
     */
    public static final char DEL = '\177';

    /**
     * Checks if the given character is a control character. Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/iscntrl">std::iscntrl</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is a control character.
     */
    public static boolean isCntrl(final char ch) {
        return ch <= 31 || ch == 127;
    }

    /**
     * Checks if ch is a printable character. There are following printable characters:
     * <ul>
     *     <li>digits</li>
     *     <li>uppercase letters</li>
     *     <li>lowercase letters</li>
     *     <li>punctuation characters punctuation characters: <pre>!&quot;#$%&amp;'()*+,-./:;&lt;=&gt;?@[\]^_`{|}~</pre></li>
     *     <li>space</li>
     * </ul>
     *
     * @param ch Character to classify.
     * @return True if ch is a printable character.
     */
    public static boolean isPrint(final char ch) {
        return ch >= 32 && ch <= 126;
    }

    /**
     * Checks if the given character is whitespace. The whitespace characters are the following:
     * <ul>
     *     <li>space (0x20, ' ')</li>
     *     <li>form feed (0x0c, '\f')</li>
     *     <li>line feed (0x0a, '\n')</li>
     *     <li>carriage return (0x0d, '\r')</li>
     *     <li>horizontal tab (0x09, '\t')</li>
     *     <li>vertical tab (0x0b, '\v')</li>
     * </ul>
     * Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isspace">std::isspace</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is whitespace.
     */
    public static boolean isSpace(final char ch) {
        return ch >= 9 && (ch <= 13 || ch == 32);
    }

    /**
     * Checks if the given character is blank. Blank characters are whitespace characters used to separate words within
     * a sentence. There are following blank characters:
     * <ul>
     *     <li>space (0x20, ' ')</li>
     *     <li>horizontal tab (0x09, '\t')</li>
     * </ul>
     * Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isblank">std::isblank</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is blank.
     */
    public static boolean isBlank(final char ch) {
        return ch == 32 || ch == 9;
    }

    /**
     * Checks if the given character is graphic (has a graphical representation). There are following graphic
     * characters:
     * <ul>
     *     <li>digits</li>
     *     <li>uppercase letters</li>
     *     <li>lowercase letters</li>
     *     <li>punctuation characters punctuation characters: <pre>!&quot;#$%&amp;'()*+,-./:;&lt;=&gt;?@[\]^_`{|}~</pre></li>
     * </ul>
     * Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isgraph">std::isgraph</a>
     *
     * @param ch Character to classify.
     * @return True if the given character has a graphical representation.
     */
    public static boolean isGraph(final char ch) {
        return ch >= 33 && ch <= 126;
    }

    /**
     * Checks if the given character is a punctuation character. There are following punctuation characters:
     * <pre>!&quot;#$%&amp;'()*+,-./:;&lt;=&gt;?@[\]^_`{|}~</pre> Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/ispunct">std::ispunct</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is a punctuation character.
     */
    public static boolean isPunct(final char ch) {
        return ch >= 33 && (ch <= 47 || (ch >= 58 && (ch <= 64 || (ch >= 91 && (ch <= 96 || (ch >= 123 && ch <= 126))))));
    }

    /**
     * Checks if the given character is an alphanumeric character. The following characters are alphanumeric:
     * <ul>
     *     <li>digits</li>
     *     <li>uppercase letters</li>
     *     <li>lowercase letters</li>
     * </ul>
     * Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isalnum">std::isalnum</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is an alphanumeric character.
     */
    public static boolean isAlnum(final char ch) {
        return ch >= 48 && (ch <= 57 || isAlpha(ch));
    }

    /**
     * Checks if the given character is an alphabetic character. The following characters are alphabetic:
     * <ul>
     *     <li>uppercase letters</li>
     *     <li>lowercase letters</li>
     * </ul>
     * Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isalpha">std::isalpha</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is an alphabetic character.
     */
    public static boolean isAlpha(final char ch) {
        return ch >= 65 && (ch <= 90 || (ch >= 97 && ch <= 122));
    }

    /**
     * Checks if the given character is an uppercase character. Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isupper">std::isupper</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is an uppercase character.
     */
    public static boolean isUpper(final char ch) {
        return ch >= 65 && ch <= 90;
    }

    /**
     * Checks if the given character is a lowercase character. Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/islower">std::islower</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is a lowercase character.
     */
    public static boolean isLower(final char ch) {
        return ch >= 97 && ch <= 122;
    }

    /**
     * Checks if the given character is one of the 10 decimal digits: <pre>0123456789</pre>. Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isdigit">std::isdigit</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is a digit.
     */
    public static boolean isDigit(final char ch) {
        return ch >= 48 && ch <= 57;
    }

    /**
     * Checks if the given character is a hexadecimal numeric character <pre>0123456789abcdefABCDEF</pre>. Based on: <a
     * href="https://en.cppreference.com/w/cpp/string/byte/isxdigit">std::isxdigit</a>
     *
     * @param ch Character to classify.
     * @return True if the given character is a hexadecimal numeric character.
     */
    public static boolean isXdigit(final char ch) {
        return ch >= 48 && (ch <= 57 || (ch >= 65 && (ch <= 70 || (ch >= 97 && ch <= 102))));
    }

    /**
     * Converts given hex digit character to integer value.
     *
     * @param ch Hex digit.
     * @return Number from range <code>0..15</code> (<code>0x00..0x0F</code>).
     * @throws RuntimeException When given character is illegal.
     */
    public static int toHexValue(final char ch) {
        if (ch >= 48 && ch <= 57) {
            return ch - 48;
        }
        if (ch >= 65 && ch <= 70) {
            return ch - 55;
        }
        if (ch >= 97 && ch <= 102) {
            return ch - 87;
        }
        throw new RuntimeException("Failed to convert given char to hex value: [" + ch + "].");
    }
}
