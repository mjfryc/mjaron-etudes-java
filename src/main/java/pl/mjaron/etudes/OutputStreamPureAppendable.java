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

package pl.mjaron.etudes;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class OutputStreamPureAppendable implements PureAppendable {

    private final OutputStream out;

    private final Charset charset;

    public OutputStreamPureAppendable(OutputStream out, Charset charset) {
        this.out = out;
        this.charset = charset;
    }

    public OutputStreamPureAppendable(OutputStream out) {
        this(out, Charset.defaultCharset());
    }

    public static OutputStreamPureAppendable from(OutputStream out, Charset charset) {
        return new OutputStreamPureAppendable(out, charset);
    }

    public static OutputStreamPureAppendable from(OutputStream out) {
        return new OutputStreamPureAppendable(out);
    }

    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public OutputStreamPureAppendable append(CharSequence csq) {
        try {
            out.write(csq.toString().getBytes(charset));
        } catch (IOException e) {
            throw new RuntimeException("Failed to append char sequence.", e);
        }
        return this;
    }

    @Override
    public OutputStreamPureAppendable append(CharSequence csq, int start, int end) {
        return this.append(csq.subSequence(start, end));
    }

    @Override
    public OutputStreamPureAppendable append(char c) {
        return this.append(String.valueOf(c));
    }
}
