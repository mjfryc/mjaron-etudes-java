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

import org.jetbrains.annotations.NotNull;
import pl.mjaron.etudes.Arr;

import java.util.Arrays;

/**
 * Used to determine the final column widths.
 *
 * @since 0.3.0
 */
public class ColumnWidthResolver {

    /**
     * The column width policy.
     *
     * @since 0.3.0
     */
    private AlignmentMode mode = AlignmentMode.DEFAULT;

    /**
     * Requested widths of all columns used during the table rendering.
     *
     * @since 0.3.0
     */
    private int[] widths = null;

    /**
     * Configures the mode and widths before the resolving stage.
     *
     * @param mode   Requested mode.
     * @param widths Column widths. It must be set only when mode is {@link AlignmentMode#ARBITRARY}.
     * @throws IllegalArgumentException When widths value is unexpected.
     * @since 0.3.0
     */
    public void configure(final AlignmentMode mode, final int[] widths) {
        if (mode == AlignmentMode.ARBITRARY) {
            if (widths == null) {
                throw new IllegalArgumentException("When setting " + AlignmentMode.class.getSimpleName() + " to " + mode + ", column widths must be specified.");
            }
        } else if (mode == AlignmentMode.ALIGNED || mode == AlignmentMode.EQUAL) {
            if (widths != null) {
                throw new IllegalArgumentException("When setting " + AlignmentMode.class.getSimpleName() + " to " + mode + ", column widths mustn't be specified. It will be computed during resolving stage.");
            }
        }
        this.mode = mode;
        this.widths = widths;
    }

    public void configure(final AlignmentMode mode) {
        configure(mode, null);
    }

    public void resolve(@NotNull final RenderRuntime runtime) {
        if (mode == AlignmentMode.DEFAULT) {
            if (runtime.getWriter().getDefaultAlignedColumnWidths()) {
                mode = AlignmentMode.ALIGNED;
            } else {
                mode = AlignmentMode.NOT_ALIGNED;
            }
        }

        if (mode == AlignmentMode.ALIGNED) {
            this.widths = TableColumnsWidthDetector.compute(runtime);
        } else if (mode == AlignmentMode.EQUAL) {
            this.widths = TableColumnsWidthDetector.compute(runtime);
            final int maxWidth = Arr.max(this.widths);
            Arrays.fill(widths, maxWidth);
        }
    }

    /**
     * Tells whether column widths are specified or not.
     *
     * @return <code>true</code> when column widths are specified, <code>false</code> otherwise.
     * @since 0.3.0
     */
    public boolean hasWidths() {
        return this.widths != null;
    }

    /**
     * Provides the resolved column widths or null if not specified.
     *
     * @return Requested column widths or <code>null</code>.
     * @since 0.3.0
     */
    public int[] getWidths() {
        return widths;
    }

    public int getWidth(final int index) {
        return widths[index];
    }
}
