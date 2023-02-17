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

package pl.mjaron.etudes.table;

import pl.mjaron.etudes.Pair;
import pl.mjaron.etudes.Str;

/**
 * Writes table in Markdown style.
 */
public class MarkdownTableWriter implements ITableWriter {

    RenderContext context = null;

    public MarkdownTableWriter() {
    }

    @Override
    public boolean getDefaultAlignedColumnWidths() {
        return true;
    }

    @Override
    public void beginTable(RenderContext options) {
        this.context = options;
    }

    @Override
    public void endTable() {
    }

    @Override
    public void beginHeader() {
    }

    public Pair<String, String> determineColumnDividerMargins(final int columnIdx) {
        final VerticalAlign verticalAlign = context.getVerticalAlignContext().getForColumn(columnIdx);
        if (verticalAlign == null) {
            return new Pair<>("-", "-");
        }
        if (verticalAlign == VerticalAlign.Left) {
            return new Pair<>(":", " ");
        }
        if (verticalAlign == VerticalAlign.Right) {
            return new Pair<>(" ", ":");
        }
        if (verticalAlign == VerticalAlign.Center) {
            return new Pair<>(":", ":");
        }
        throw new RuntimeException("Unsupported " + VerticalAlign.class.getSimpleName() + " value: " + verticalAlign);
    }

    @Override
    public void endHeader() {
        context.append("|");
        context.appendLine();
        if (context.hasColumnWidths()) {
            for (int i = 0; i < context.getColumnWidths().length; ++i) {
                final int w = context.getColumnWidths()[i];
                //for (final int w : context.getColumnWidths()) {
                final Pair<String, String> margins = determineColumnDividerMargins(i);
                context.append("|");
                context.append(margins.getKey());
                Str.pad(context.out(), w, '-');
                context.append(margins.getValue());
            }
        } else {
            for (int i = 0; i < context.getColumnsCount(); ++i) {
                final Pair<String, String> margins = determineColumnDividerMargins(i);
                context.append("|");
                context.append(margins.getKey());
                context.append("----");
                context.append(margins.getValue());
            }
        }
        context.append("|");
        context.appendLine();
    }

    @Override
    public void beginRow() {
    }

    @Override
    public void endRow() {
        context.append("|");
        context.appendLine();
    }

    @Override
    public void writeCell(final String what) {
        context.append("| ");
        context.appendPadded(what, ' ');
        context.append(' ');
    }
}
