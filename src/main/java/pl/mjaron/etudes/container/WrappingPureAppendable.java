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

package pl.mjaron.etudes.container;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import pl.mjaron.etudes.IPureAppendable;

/**
 * Wraps the {@link Appendable} object so append operations doesn't have signatures with exceptions.
 *
 * @since 0.2.0
 */
public class WrappingPureAppendable implements IPureAppendable {

    @NotNull
    private final Appendable appendable;

    public WrappingPureAppendable(@NotNull final Appendable appendable) {
        this.appendable = appendable;
    }

    public static WrappingPureAppendable from(Appendable appendable) {
        return new WrappingPureAppendable(appendable);
    }

    @Contract("_->this")
    @NotNull
    @Override
    public IPureAppendable append(final CharSequence csq) {
        try {
            appendable.append(csq);
        } catch (IOException e) {
            throw new RuntimeException("Failed to append char sequence.", e);
        }
        return this;
    }

    @Contract("_,_,_->this")
    @NotNull
    @Override
    public IPureAppendable append(final CharSequence csq, final int start, final int end) {
        try {
            appendable.append(csq, start, end);
        } catch (IOException e) {
            throw new RuntimeException("Failed to append char sequence with given range.", e);
        }
        return this;
    }

    @Contract("_->this")
    @NotNull
    @Override
    public IPureAppendable append(final char c) {
        try {
            appendable.append(c);
        } catch (IOException e) {
            throw new RuntimeException("Failed to append char.", e);
        }
        return this;
    }

    @NotNull
    @Override
    public Appendable getUnderlyingObject() {
        return appendable;
    }
}
