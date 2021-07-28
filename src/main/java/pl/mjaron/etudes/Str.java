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

import java.util.Locale;

/**
 * String utilities class.
 */
public abstract class Str {
    /**
     * @param what String where looking for char occurrences.
     * @param ch   Searched character.
     * @return Count of character ch in string what.
     */
    public static int charsCount(final String what, final char ch) {
        int count = 0;
        final int len = what.length();
        for (int i = 0; i < len; ++i) {
            if (what.charAt(i) == ch) {
                ++count;
            }
        }
        return count;
    }

    public static String capitalize(final String what, final java.util.Locale locale) {
        if (what == null || what.isEmpty()) {
            return what;
        }
        if (!Character.isLowerCase(what.charAt(0))) {
            return what;
        }
        return what.substring(0, 1).toUpperCase(locale) + what.substring(1);
    }

    public static String capitalize(final String what) {
        return capitalize(what, Locale.ROOT);
    }

    /**
     * Add fill string with given character from left side.
     */
    public static void padLeft(final String what, final int size, final char ch, final StringBuilder out) {
        int missing = size - what.length();
        while(missing > 0) {
            out.append(ch);
            --missing;
        }
        out.append(what);
    }

    public static String padLeft(final String what, final int size, final char ch) {
        final StringBuilder out = new StringBuilder();
        padLeft(what, size, ch, out);
        return out.toString();
    }

    public static String padLeft(final String what, final int size) {
        return padLeft(what, size, ' ');
    }

    /**
     * Add fill string with given character from right side.
     */
    public static void padRight(final String what, final int size, final char ch, final StringBuilder out) {
        out.append(what);
        int missing = size - what.length();
        while(missing > 0) {
            out.append(ch);
            --missing;
        }
    }

    public static String padRight(final String what, final int size, final char ch) {
        final StringBuilder out = new StringBuilder();
        padRight(what, size, ch, out);
        return out.toString();
    }

    public static String padRight(final String what, final int size) {
        return padRight(what, size, ' ');
    }

    public static void pad(final StringBuilder out, int size, final char ch) {
        for (;size > 0; --size) {
            out.append(ch);
        }
    }
}
