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

package pl.mjaron.etudes;

import java.io.File;

public abstract class Path {

    public static String extension(final String path) {
        final int dot = path.lastIndexOf('.');
        if (dot < 0) {
            return "";
        }
        return path.substring(dot + 1);
    }

    public static String filename(final String path, final char separator) {
        final int separatorIdx = path.lastIndexOf(separator);
        if (separatorIdx <= 0) {
            return path;
        }
        if (separatorIdx + 1 == path.length()) {
            return "";
        }
        return path.substring(separatorIdx + 1);
    }

    public static String filename(final String path, final String separators) {
        final int separatorIdx = Str.lastIndexAnyOf(path, separators);
        if (separatorIdx <= 0) {
            return path;
        }
        if (separatorIdx + 1 == path.length()) {
            return "";
        }
        return path.substring(separatorIdx + 1);
    }

    public static final String COMMON_SEPARATORS = "/\\";

    public static String filename(final String path) {
        return filename(path, COMMON_SEPARATORS);
    }
}
