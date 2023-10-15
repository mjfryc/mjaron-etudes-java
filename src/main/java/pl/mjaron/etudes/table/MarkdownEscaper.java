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

import pl.mjaron.etudes.Arr;

public class MarkdownEscaper implements IEscaper {

    /**
     * Chars used to escape Markdown text. Source: <a href="https://github.com/telegraf/telegraf/issues/1242">...</a>
     */
    public static final char[] DEFAULT_ESCAPED_CHARS = {
            '_', '*', '[', ']', '(', ')', '~', '`', '>',
            '#', '+', '-', '=', '|', '{', '}', '.', '!'
    };
    private final static MarkdownEscaper DEFAULT_INSTANCE = new MarkdownEscaper();
    private char[] escaped = DEFAULT_ESCAPED_CHARS;

    public MarkdownEscaper() {
    }

    public MarkdownEscaper(final char[] escaped) {
        this.escaped = escaped;
        if (escaped == null) {
            throw new NullPointerException("Escaped chars must be initialized, got null.");
        }
    }

    public static MarkdownEscaper getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    @Override
    public String escape(String what) {
        final StringBuilder out = new StringBuilder(what.length());
        for (int i = 0; i < what.length(); ++i) {
            final char ch = what.charAt(i);
            if (Arr.contains(escaped, ch)) {
                out.append("&#");
                out.append((int) what.charAt(i));
                out.append(';');
            } else {
                out.append(what.charAt(i));
            }
        }
        return out.toString();
    }
}
