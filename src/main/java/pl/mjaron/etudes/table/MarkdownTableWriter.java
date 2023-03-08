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

    RenderRuntime runtime = null;

    public MarkdownTableWriter() {
    }

    @Override
    public boolean getDefaultAlignedColumnWidths() {
        return true;
    }

    @Override
    public void beginTable(RenderRuntime runtime) {
        this.runtime = runtime;
    }

    @Override
    public void endTable() {
    }

    @Override
    public void beginHeader() {
    }

    public Pair<String, String> determineColumnDividerMargins(final int columnIdx) {
        final VerticalAlign verticalAlign = runtime.getVerticalAlign(columnIdx);
        if (verticalAlign == null) {
            return new Pair<>("-", "-");
        }
        if (verticalAlign == VerticalAlign.Left) {
            return new Pair<>(":", "-");
        }
        if (verticalAlign == VerticalAlign.Right) {
            return new Pair<>("-", ":");
        }
        if (verticalAlign == VerticalAlign.Center) {
            return new Pair<>(":", ":");
        }
        throw new RuntimeException("Unsupported " + VerticalAlign.class.getSimpleName() + " value: " + verticalAlign);
    }

    @Override
    public void endHeader() {
        runtime.append("|");
        runtime.appendLine();
        if (runtime.hasColumnWidths()) {
            for (int i = 0; i < runtime.getColumnsCount(); ++i) {
                final int w = runtime.getColumnWidth(i);
                final Pair<String, String> margins = determineColumnDividerMargins(i);
                runtime.append("|");
                runtime.append(margins.getKey());
                Str.pad(runtime.getOut(), w, '-');
                runtime.append(margins.getValue());
            }
        } else {
            for (int i = 0; i < runtime.getColumnsCount(); ++i) {
                final Pair<String, String> margins = determineColumnDividerMargins(i);
                runtime.append("|");
                runtime.append(margins.getKey());
                runtime.append("----");
                runtime.append(margins.getValue());
            }
        }
        runtime.appendLine("|");
    }

    @Override
    public void beginRow() {
    }

    @Override
    public void endRow() {
        runtime.appendLine("|");
    }

    @Override
    public void writeCell(final String what) {
        runtime.append("| ");
        runtime.appendPadded(what, ' ');
        runtime.append(' ');
    }
}
