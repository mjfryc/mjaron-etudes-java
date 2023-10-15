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

package pl.mjaron.etudes.text;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import pl.mjaron.etudes.IPureAppendable;
import pl.mjaron.etudes.Str;
import pl.mjaron.etudes.table.RenderContext;

/**
 * Used to simplify the markdown data rendering.
 * <p>
 * Partial implementation of Markdown standard.
 * <p>
 * This API is experimental and may be changed in future release.
 * <p>
 * Markdown specification reference: <a
 * href="https://www.markdownguide.org/basic-syntax">https://www.markdownguide.org/basic-syntax</a>
 *
 * @since 0.3.0
 */
@ApiStatus.Experimental
public class SimpleMarkdownWriter {

    /**
     * Where to write the markdown output.
     */
    @NotNull
    private final IPureAppendable out;

    /**
     * Initial nest level of markdown headers.
     * Value {@code 0} means the top header. Bigger values refer to sub-headers.
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    private final int headerNestLevelBase;

    /**
     * Initializes all fields of the writer.
     *
     * @param out                 Where to write the markdown output.
     * @param headerNestLevelBase Base level of headers.
     */
    public SimpleMarkdownWriter(@NotNull final IPureAppendable out, @Range(from = 0, to = Integer.MAX_VALUE) final int headerNestLevelBase) {
        this.out = out;
        this.headerNestLevelBase = headerNestLevelBase;
    }

    /**
     * Initializes new writer with custom initial header nest level.
     *
     * @param headerNestLevelBase Base level of headers. Value {@code 0} means the top header. Bigger values refer to sub-headers.
     */
    public SimpleMarkdownWriter(@Range(from = 0, to = Integer.MAX_VALUE) final int headerNestLevelBase) {
        this(IPureAppendable.from(new StringBuilder()), headerNestLevelBase);
    }

    /**
     * Initializes the default writer with new empty pure appendable and top level of the header.
     */
    public SimpleMarkdownWriter() {
        this(IPureAppendable.from(new StringBuilder()), 0);
    }

    /**
     * Creates child writer with sub-header nested relatively to parent header nest level.
     *
     * @param headerNestLevelRelative Nesting relative to parent base header level.
     * @return New writer instance.
     */
    @NotNull
    @Contract("_-> new")
    public SimpleMarkdownWriter child(@Range(from = 0, to = Integer.MAX_VALUE) final int headerNestLevelRelative) {
        return new SimpleMarkdownWriter(getOut(), headerNestLevelBase + headerNestLevelRelative);
    }

    @NotNull
    @Contract("-> new")
    public SimpleMarkdownWriter child() {
        return child(1);
    }

    @NotNull
    @Contract("_-> new")
    public SimpleMarkdownWriter child(final String header) {
        final SimpleMarkdownWriter childWriter = this.child(1);
        childWriter.header(header);
        return childWriter;
    }

    @NotNull
    public IPureAppendable getOut() {
        return out;
    }

    @NotNull
    public String getOutString() {
        if (out.getUnderlyingObject() instanceof StringBuilder) {
            return ((StringBuilder) out.getUnderlyingObject()).toString();
        }
        throw new RuntimeException("Only " + StringBuilder.class.getSimpleName() + " class can be used with getOutputString().");
    }

    public void header(@Range(from = 0, to = Integer.MAX_VALUE) final int nestLevel, final String what) {
        Str.pad(out, headerNestLevelBase + nestLevel + 1, '#');
        out.append(' ');
        out.append(what);
        out.append("\n\n");
    }

    public void header(final String what) {
        header(0, what);
    }

    public void writeRaw(final String what) {
        out.append(what);
    }

    public void nextParagraph() {
        out.append("\n\n");
    }

    public void lineBreak() {
        out.append("  \n");
    }

    public void codeBlock(final String what) {
        out.append("    ");
        out.append(what.replace("\n", "    \n"));
        out.append("\n\n");
    }

    public void fencedCodeBlock(final String lang, final String code) {
        out.append("```");
        if (lang != null) {
            out.append(lang);
        }
        out.append("\n");
        out.append(code);
        if (!code.endsWith("\n")) {
            out.append("\n");
        }
        out.append("```\n\n");
    }

    public void table(@NotNull final RenderContext render) {
        render.to(out);
        render.run();
        out.append("\n");
    }

    public void paragraph(final String what) {
        out.append(what);
        out.append("\n\n");
    }

    public void writeRawLine(final String what) {
        out.append(what);
        out.append("\n");
    }

    public void writeRawLine() {
        out.append("\n");
    }
}
