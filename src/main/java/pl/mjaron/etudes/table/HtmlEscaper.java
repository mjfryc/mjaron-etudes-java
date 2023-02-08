/*
 * Copyright  2023  Michał Jaroń <m.jaron@protonmail.com>
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

import pl.mjaron.etudes.Str;

public class HtmlEscaper implements IEscaper {

    private static final char[] ESCAPED_CHARS = new char[]{'&', '<', '>', '\"', '\'', Str.CR_H, Str.LF_H};

    public boolean needsEscape(String what) {
        return Str.contains(what, ESCAPED_CHARS);
    }

    @Override
    public String escape(String what) {
        if (!needsEscape(what)) {
            return what;
        }

        final StringBuilder str = new StringBuilder(what.length() + 16);
        char prev = '\0';
        for (int i = 0; i < what.length(); ++i) {
            final char ch = what.charAt(i);
            if (ch == '&') {
                str.append("&amp");
            } else if (ch == '<') {
                str.append("&lt");
            } else if (ch == '>') {
                str.append("&gt");
            } else if (ch == '\"') {
                str.append("&quot");
            } else if (ch == '\'') {
                str.append("&#39");
            } else if (ch == Str.CR_H) {
                str.append("<br/>");
            } else if (ch == Str.LF_H) {
                if (prev != Str.CR_H) { // CR-LF causes single new line only.
                    str.append("<br/>");
                }
            } else {
                str.append(ch);
            }
            prev = ch;
        }
        return str.toString();
    }
}
