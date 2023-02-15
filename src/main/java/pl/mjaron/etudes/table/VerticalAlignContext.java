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

import org.jetbrains.annotations.Nullable;

/**
 * Used to determine current column vertical align.
 *
 * @since 0.2.1
 */
public class VerticalAlignContext {

    private final RenderContext context;

    /**
     * Defines how the content of whole table is aligned.
     * <p>
     * If <code>null</code>, the default value should be used.
     *
     * @since 0.2.1
     */
    @Nullable
    private VerticalAlign generalVerticalAlign = null;

    /**
     * Aligns used for all columns.
     */
    private VerticalAlign[] verticalAligns = null;

    private VerticalAlign currentColumnVerticalAlign = null;

    public VerticalAlignContext(RenderContext context) {
        this.context = context;
    }

    /**
     * Get the current value of {@link VerticalAlign} or <code>null</code> when value is not set.
     *
     * @return {@link VerticalAlign} or <code>null</code> when value is not set.
     * @since 0.2.1
     */
    @Nullable
    public VerticalAlign getGeneralVerticalAlign() {
        return this.generalVerticalAlign;
    }

    public void setGeneralVerticalAlign(@Nullable VerticalAlign generalVerticalAlign) {
        this.generalVerticalAlign = generalVerticalAlign;
    }

    public void setVerticalAligns(VerticalAlign[] verticalAligns) {
        this.verticalAligns = verticalAligns;
    }

    public VerticalAlign getCurrentColumnVerticalAlign() {
        return this.currentColumnVerticalAlign;
    }

    public void onCurrentColumnChanged() {
        currentColumnVerticalAlign = getForColumn(context.getColumnIdx());
    }

    public VerticalAlign getForColumn(final int columnIdx) {
        if (verticalAligns != null && columnIdx < verticalAligns.length) {
            return verticalAligns[columnIdx];
        } else {
            return generalVerticalAlign;
        }
    }
}
