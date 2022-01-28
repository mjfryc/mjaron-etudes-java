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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    @Test
    void extension() {
        assertEquals("txt", Path.extension("/my/path/to/file.txt"));
        assertEquals("", Path.extension("/my/path/to/file"));
        assertEquals("", Path.extension("/my/path/to/file."));
        assertEquals("txt", Path.extension("/my/path/to,file.txt", ','));
    }

    @Test
    void noExtension() {
        assertEquals("/my/path/to/file", Path.noExtension("/my/path/to/file.txt"));
        assertEquals("/my/path/to/file", Path.noExtension("/my/path/to/file"));
        assertEquals("/my/path/to/file", Path.noExtension("/my/path/to/file."));
        assertEquals(",my,path,to,file", Path.noExtension(",my,path,to,file.txt", ','));
    }

    @Test
    void filename() {
        assertEquals("abc.txt", Path.filename("/c/mixed/path/separators\\abc.txt"));
        assertEquals("c.txt", Path.filename("a/b/c.txt"));
        assertEquals("a.txt", Path.filename("a.txt"));
        assertEquals("c", Path.filename("a/b/c"));
        assertEquals("", Path.filename("a/b/c/"));
        assertEquals("c.txt", Path.filename("a/b,c.txt", ','));
    }

    @Test
    void noEndSeparator() {
        assertEquals("/a/b/c", Path.noEndSeparator("/a/b/c/"));
        assertEquals("/a/b/c", Path.noEndSeparator("/a/b/c"));
        assertEquals("", Path.noEndSeparator("/"));
        assertEquals("", Path.noEndSeparator(""));
        assertEquals("/a/b,c", Path.noEndSeparator("/a/b,c,", ','));
    }

    @Test
    void parent() {
        assertEquals("/a/b/c", Path.parent("/a/b/c/"));
        assertEquals("/a/b", Path.parent("/a/b/c"));
        assertEquals("", Path.parent("/"));
        assertEquals("", Path.parent(""));
        assertEquals("a,b", Path.parent("a,b,c", ","));
        assertEquals("a,b", Path.parent("a,b,c", ','));
    }
}