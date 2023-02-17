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
import org.jetbrains.annotations.Range;

/**
 * Used to determine current column {@link VerticalAlign}.
 *
 * @see VerticalAlign
 * @see VerticalAlignContext#onCurrentColumnChanged()
 * @see RenderContext#getColumnIdx()
 * @see RenderContext#nextColumn()
 * @since 0.2.1
 */
public class VerticalAlignContext {

    /**
     * Reference to {@link RenderContext} which uses this {@link VerticalAlignContext}.
     *
     * @since 0.2.1
     */
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
     *
     * @since 0.2.1
     */
    private VerticalAlign[] verticalAligns = null;

    /**
     * Vertical align used by current column pointed by {@link RenderContext#getColumnIdx()}.
     * <p>
     * Calculated by {@link #onCurrentColumnChanged()}, which is called by {@link RenderContext} when needed.
     *
     * @see RenderContext#getColumnIdx()
     * @see RenderContext#nextColumn()
     * @see RenderContext#resetColumn()
     * @since 0.2.1
     */
    private VerticalAlign currentColumnVerticalAlign = null;

    /**
     * Initializes new instance with {@link RenderContext}.
     *
     * @param context {@link RenderContext} which uses this {@link VerticalAlignContext}.
     * @since 0.2.1
     */
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

    /**
     * Sets the default {@link VerticalAlign} value for whole table. Used when there is no {@link VerticalAlign}
     * specified for particular column.
     *
     * @param generalVerticalAlign Default {@link VerticalAlign} value for whole table.
     * @since 0.2.1
     */
    public void setGeneralVerticalAlign(@Nullable VerticalAlign generalVerticalAlign) {
        this.generalVerticalAlign = generalVerticalAlign;
    }

    /**
     * Sets vertical aligns for each column.
     *
     * @param verticalAligns Array of vertical aligns. The array size usually is the same as table columns count. The
     *                       array may be <code>null</code> or particular array's values may be <code>null</code>.
     * @since 0.2.1
     */
    public void setVerticalAligns(@Nullable VerticalAlign[] verticalAligns) {
        this.verticalAligns = verticalAligns;
    }

    /**
     * Provides current column vertical align.
     *
     * @return Current column vertical align.
     * @see VerticalAlign
     * @see RenderContext#getColumnIdx()
     * @since 0.2.1
     */
    public VerticalAlign getCurrentColumnVerticalAlign() {
        return this.currentColumnVerticalAlign;
    }

    /**
     * Called by {@link RenderContext} during {@link RenderOperation} to recompute the current column vertical align.
     *
     * @see #getCurrentColumnVerticalAlign()
     * @see VerticalAlign
     * @see RenderOperation
     * @see RenderContext#nextColumn()
     * @since 0.2.1
     */
    public void onCurrentColumnChanged() {
        currentColumnVerticalAlign = getForColumn(context.getColumnIdx());
    }

    /**
     * Tells the {@link VerticalAlign} value for given <code>columnIdx</code>.
     *
     * @param columnIdx Custom column index for which the {@link VerticalAlign} will be returned.
     * @return {@link VerticalAlign} value for given <code>columnIdx</code>.
     * @since 0.2.1
     */
    @Nullable
    public VerticalAlign getForColumn(@Range(from = 0, to = Integer.MAX_VALUE) final int columnIdx) {
        if (verticalAligns != null && columnIdx < verticalAligns.length && verticalAligns[columnIdx] != null) {
            return verticalAligns[columnIdx];
        } else {
            return generalVerticalAlign;
        }
    }
}
