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
     * Tells whether String is empty.
     *
     * @param what Given String or null.
     * @return True when <code>what</code> String is null or empty (length is 0).
     */
    public static boolean isEmpty(final String what) {
        return (what == null || what.isEmpty());
    }

    /**
     * Tells whether String contains any characters.
     *
     * @param what Given String or null.
     * @return True when String is not null and contains any characters.
     */
    public static boolean notEmpty(final String what) {
        return !isEmpty(what);
    }

    /**
     * @param what        Given String.
     * @param alternative Alternative String, used when <code>what</code> is null or empty.
     * @return <code>alternative</code> String when <code>what</code> String is null or empty.
     */
    public static String ifEmpty(final String what, final String alternative) {
        return (isEmpty(what)) ? alternative : what;
    }

    /**
     * @param what Any nullable String.
     * @return String object given in parameter or empty String it
     */
    public static String orEmpty(final String what) {
        if (what == null) {
            return "";
        }
        return what;
    }

    /**
     * @param what Any object.
     * @return toString() result or empty String if given object was null.
     */
    public static String orEmpty(final Object what) {
        if (what == null) {
            return "";
        }
        return what.toString();
    }

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

    /**
     * E.g: change "capitalize" to "Capitalize".
     *
     * @param what   Given String.
     * @param locale Locale settings.
     * @return Capitalized String.
     */
    public static String capitalize(final String what, final java.util.Locale locale) {
        if (isEmpty(what)) {
            return what;
        }
        if (!Character.isLowerCase(what.charAt(0))) {
            return what;
        }
        return what.substring(0, 1).toUpperCase(locale) + what.substring(1);
    }

    /**
     * E.g: change "capitalize" to "Capitalize" with default Locale settings.
     *
     * @param what Given String.
     * @return Capitalized String.
     */
    public static String capitalize(final String what) {
        return capitalize(what, Locale.ROOT);
    }

    /**
     * Fills String with given character from left side.
     *
     * @param what Given String.
     * @param size Width of desired String.
     * @param ch   Character used to fill padding gap.
     * @param out  Output where result String will be written.
     */
    public static void padLeft(final String what, final int size, final char ch, final StringBuilder out) {
        int missing = size - what.length();
        while (missing > 0) {
            out.append(ch);
            --missing;
        }
        out.append(what);
    }

    /**
     * Fills String with given character from left side.
     *
     * @param what Given String.
     * @param size Width of desired String.
     * @param ch   Character used to fill padding gap.
     * @return Padded String.
     */
    public static String padLeft(final String what, final int size, final char ch) {
        final StringBuilder out = new StringBuilder();
        padLeft(what, size, ch, out);
        return out.toString();
    }

    /**
     * Fills String with spaces from left side.
     *
     * @param what Given String.
     * @param size Width of desired String.
     * @return Padded String.
     */
    public static String padLeft(final String what, final int size) {
        return padLeft(what, size, ' ');
    }

    /**
     * Fills String with given character from right side.
     *
     * @param what Given String.
     * @param size Width of desired String.
     * @param ch   Character used to fill padding gap.
     * @param out  Output where result String will be written.
     */
    public static void padRight(final String what, final int size, final char ch, final StringBuilder out) {
        out.append(what);
        int missing = size - what.length();
        while (missing > 0) {
            out.append(ch);
            --missing;
        }
    }

    /**
     * Fills String with given character from right side.
     *
     * @param what Given String.
     * @param size Width of desired String.
     * @param ch   Character used to fill padding gap.
     * @return Padded String.
     */
    public static String padRight(final String what, final int size, final char ch) {
        final StringBuilder out = new StringBuilder();
        padRight(what, size, ch, out);
        return out.toString();
    }

    /**
     * Fills String with spaces from right side.
     *
     * @param what Given String.
     * @param size Width of desired String.
     * @return Padded String.
     */
    public static String padRight(final String what, final int size) {
        return padRight(what, size, ' ');
    }

    /**
     * Append given count of characters to StringBuilder.
     *
     * @param out  StringBuilder instance.
     * @param size Characters count.
     * @param ch   Character used to append.
     */
    public static void pad(final StringBuilder out, int size, final char ch) {
        for (; size > 0; --size) {
            out.append(ch);
        }
    }

    /**
     * Find last index of given characters.
     *
     * @param what  String where looking for last occurrence.
     * @param chars Set of characters.
     * @return Last occurrence of any character from <code>chars</code> set.
     */
    public static int lastIndexAnyOf(final String what, final String chars) {
        for (int idx = what.length() - 1; idx >= 0; --idx) {
            for (int ch = 0; ch < chars.length(); ++ch) {
                if (what.charAt(idx) == chars.charAt(ch)) {
                    return idx;
                }
            }
        }
        return -1;
    }

    public static int lastIndexOf(final String what, final char ch) {
        return what.lastIndexOf(ch);
    }
}
