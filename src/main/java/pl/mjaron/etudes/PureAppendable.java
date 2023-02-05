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

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * {@link Appendable} interface without exceptions in method signatures.
 */
public interface PureAppendable extends Appendable {

    Appendable append(CharSequence csq);

    Appendable append(CharSequence csq, int start, int end);

    Appendable append(char c);

    static WrappingPureAppendable from(Appendable appendable) {
        return WrappingPureAppendable.from(appendable);
    }

    static WrappingPureAppendable from(PrintStream printStream) {
        return WrappingPureAppendable.from(printStream);
    }

    static OutputStreamPureAppendable from(OutputStream outputStream) {
        return OutputStreamPureAppendable.from(outputStream);
    }

    static OutputStreamPureAppendable from(OutputStream outputStream, Charset charset) {
        return OutputStreamPureAppendable.from(outputStream, charset);
    }
}
