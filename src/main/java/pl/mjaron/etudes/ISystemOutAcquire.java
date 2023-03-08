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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public interface ISystemOutAcquire extends AutoCloseable {

    PrintStream getOriginalOut();

    PrintStream getNewOut();

    /**
     * Restores the original stream.
     */
    @Override
    void close();

    /**
     * Replaces the original {@link PrintStream} with another one.
     */
    class ToPrintStream implements ISystemOutAcquire {
        private final PrintStream originalOut;
        private final PrintStream newOut;

        public ToPrintStream(PrintStream newOut) {
            this.originalOut = System.out;
            this.newOut = newOut;
            originalOut.flush();
            newOut.flush();
            System.setOut(newOut);
        }

        @Override
        public void close() {
            originalOut.flush();
            newOut.flush();
            System.setOut(originalOut);
        }

        @Override
        public PrintStream getOriginalOut() {
            return originalOut;
        }

        @Override
        public PrintStream getNewOut() {
            return newOut;
        }
    }

    public static ToPrintStream acquire(final PrintStream newOut) {
        return new ToPrintStream(newOut);
    }

    /**
     * Captures the {@link System#out} to {@link ByteArrayOutputStream}.
     * <p>
     * Overrides the {@link #toString()} method, so all captured text is accessible.
     */
    class ToByteArray extends ToPrintStream {

        private final ByteArrayOutputStream byteArrayOutputStream;

        public ToByteArray(final ByteArrayOutputStream byteArrayOutputStream) {
            super(new PrintStream(byteArrayOutputStream));
            this.byteArrayOutputStream = byteArrayOutputStream;
        }

        @Override
        public String toString() {
            return byteArrayOutputStream.toString();
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return byteArrayOutputStream;
        }
    }

    static ToByteArray acquire(final ByteArrayOutputStream byteArrayOutputStream) {
        return new ToByteArray(byteArrayOutputStream);
    }

    static ToByteArray acquire() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        return acquire(byteArrayOutputStream);
    }

    static String acquire(final Runnable runnable) {
        try (ISystemOutAcquire acquire = ISystemOutAcquire.acquire()) {
            runnable.run();
            return acquire.toString();
        }
    }
}
