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

/**
 * Reference: <a
 * href="https://www.rfc-editor.org/rfc/rfc4180#section-2">https://www.rfc-editor.org/rfc/rfc4180#section-2</a>
 * <p>
 * 6. Fields containing line breaks (CRLF), double quotes, and commas should be enclosed in double-quotes.  For
 * example:
 * <pre>
 * "aaa","b CRLF
 * bb","ccc" CRLF
 * zzz,yyy,xxx
 * </pre>
 * <p>
 * 7.  If double-quotes are used to enclose fields, then a double-quote appearing inside a field must be escaped by
 * preceding it with another double quote.  For example:
 * <pre>
 * "aaa","b""bb","ccc"
 * </pre>
 */
public class CsvEscaper implements IEscaper {

    private String delimiter = CsvTableWriter.DEFAULT_DELIMITER;

    public boolean needsEscape(String what) {
        return what.contains(Str.CRLF) || what.contains("\"") || what.contains(delimiter);
    }

    public String doEscape(String what) {
        return "\"" + what.replace("\"", "\"\"") + "\"";
    }

    @Override
    public void beginTable(RenderContext renderContext) {
        if (renderContext.getCellDelimiter() != null) {
            delimiter = renderContext.getCellDelimiter();
        }
    }

    @Override
    public String escape(String what) {
        if (needsEscape(what)) {
            doEscape(what);
        }
        return what;
    }
}
