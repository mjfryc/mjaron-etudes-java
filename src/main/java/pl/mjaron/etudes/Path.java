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

public abstract class Path {

    public static final String COMMON_SEPARATORS = "/\\";
    public static final char EXTENSION_CHAR = '.';

    /**
     * Gives position of last separator character in path.
     *
     * @param path       Given path.
     * @param separators Allowed separators.
     * @return Position of last separator or -1 if separator is not found.
     */
    public static int lastSeparatorPos(final String path, final String separators) {
        return Str.lastIndexAnyOf(path, separators);
    }

    public static int lastSeparatorPos(final String path, final char separator) {
        return Str.lastIndexOf(path, separator);
    }

    /**
     * Gives position of last extension character in path.
     *
     * @param path Given path.
     * @return Position of last extension character or -1 if dot is not found.
     */
    public static int lastExtensionPos(final String path) {
        return path.lastIndexOf(EXTENSION_CHAR);
    }

    public static int extensionPos(final String path, final String separators) {
        final int lastExtension = lastExtensionPos(path);
        if (lastExtension == -1) { // There is no extension.
            return -1;
        }
        final int lastSeparator = lastSeparatorPos(path, separators);
        if (lastSeparator > lastExtension) {
            return -1;
        }
        return lastExtension;
    }

    public static int extensionPos(final String path, final char separator) {
        final int lastExtension = lastExtensionPos(path);
        if (lastExtension == -1) { // There is no extension.
            return -1;
        }
        final int lastSeparator = lastSeparatorPos(path, separator);
        if (lastSeparator > lastExtension) {
            return -1;
        }
        return lastExtension;
    }

    /**
     * Provides filename extension without a dot, e.g:
     * <pre>
     *     a.txt -> txt
     *     a.    -> ""
     *     a     ->
     *     ""    -> ""
     *     a.b/c -> ""
     * </pre>
     *
     * @param path Given path.
     * @return Extension without a dot.
     */
    public static String extension(final String path, final String separators) {
        final int dot = extensionPos(path, separators);
        if (dot < 0) {
            return ""; // There is no extension.
        }
        return path.substring(dot + 1);
    }

    public static String extension(final String path, final char separator) {
        final int dot = extensionPos(path, separator);
        if (dot < 0) {
            return ""; // There is no extension.
        }
        return path.substring(dot + 1);
    }

    public static String extension(final String path) {
        return extension(path, COMMON_SEPARATORS);
    }

    public static String noExtension(final String path, final String separators) {
        final int dot = extensionPos(path, separators);
        if (dot < 0) {
            return path; // There is no extension.
        }
        return path.substring(0, dot);
    }

    public static String noExtension(final String path, final char separator) {
        final int dot = extensionPos(path, separator);
        if (dot < 0) {
            return path; // There is no extension.
        }
        return path.substring(0, dot);
    }

    public static String noExtension(final String path) {
        return noExtension(path, COMMON_SEPARATORS);
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

    /**
     * Similar to https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/FilenameUtils.html#getName-java.lang.String-
     *
     * @param path String stored as path. Cannot be null.
     * @return Filename without parent path.
     */
    public static String filename(final String path) {
        return filename(path, COMMON_SEPARATORS);
    }

    public static String noEndSeparator(final String path, final String separators) {
        if (path.isEmpty()) {
            return path;
        }
        if (separators.indexOf(path.charAt(path.length() - 1)) < 0) {
            return path;
        }
        return path.substring(0, path.length() - 1);
    }

    public static String noEndSeparator(final String path, final char separator) {
        if (path.isEmpty()) {
            return path;
        }
        if ((path.charAt(path.length() - 1)) != separator) {
            return path;
        }
        return path.substring(0, path.length() - 1);
    }

    public static String noEndSeparator(final String path) {
        return noEndSeparator(path, COMMON_SEPARATORS);
    }

    /**
     * Provides parent path, e.g:
     * <pre>
     *     /a/b  -> /a
     *     /a/b/ -> /a/b
     *     /     -> "" (empty)
     *     ""    -> ""
     * </pre>
     *
     * @param path       Initial path.
     * @param separators Allowed separator characters.
     * @return Path without filename and without filename separator.
     */
    public static String parent(final String path, final String separators) {
        final int separatorIdx = Str.lastIndexAnyOf(path, separators);
        if (separatorIdx <= 0) {
            return "";
        }
        return path.substring(0, separatorIdx);
    }

    public static String parent(final String path, final char separator) {
        final int separatorIdx = path.lastIndexOf(separator);
        if (separatorIdx <= 0) {
            return "";
        }
        return path.substring(0, separatorIdx);
    }

    public static String parent(final String path) {
        return parent(path, COMMON_SEPARATORS);
    }
}
