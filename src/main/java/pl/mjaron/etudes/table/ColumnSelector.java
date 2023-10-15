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

import java.util.ArrayList;
import java.util.List;

import pl.mjaron.etudes.Pair;

/**
 * Used to select the columns which will be rendered.
 * <p>
 * The order of the declared columns matters.
 *
 * @since 0.3.0
 */
public class ColumnSelector {

    Pair<String, String> last = null;

    List<Pair<String, String>> entries = new ArrayList<>();

    public ColumnSelector col(final String id) {
        last = new Pair<>(id, null);
        entries.add(last);
        return this;
    }

    public ColumnSelector as(final String alias) {
        last.setValue(alias);
        return this;
    }

    public ColumnSelector col(final String id, final String alias) {
        last = new Pair<>(id, alias);
        entries.add(last);
        return this;
    }

    public List<Pair<String, String>> getEntries() {
        return entries;
    }

    public String getColumnAlias(final String id) {
        for (final Pair<String, String> entry : entries) {
            if (entry.getKey().equals(id)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
