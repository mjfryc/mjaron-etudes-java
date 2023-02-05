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
import pl.mjaron.etudes.table.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjTest {

    private static Cat sampleCat() {
        final Cat cat = new Cat();
        cat.name = "John";
        cat.legsCount = 4;
        cat.setLazy(true);
        cat.setTopSpeed(35.24);
        return cat;
    }

    private static Cat otherCat() {
        final Cat cat = new Cat();
        cat.name = "Bob";
        cat.legsCount = 5;
        cat.setLazy(false);
        cat.setTopSpeed(75.00);
        return cat;
    }

    @Test
    void getFieldValues() {
        final Cat cat = sampleCat();
        final Map<String, Object> values = Obj.getFieldValues(cat);
        System.out.println(values);

        assertEquals("John", values.get("name"));
        assertEquals(4, values.get("legsCount"));
        assertEquals(true, values.get("lazy"));
        assertEquals(35.24, values.get("topSpeed"));
    }

    @Test
    void asTable0() {
        final Cat cat = sampleCat();
        final String table = Table.toString(Arrays.asList(cat, otherCat()), Cat.class, new MarkdownTableWriter());
        System.out.println(table);
    }

    @Test
    void asTable1() {
        final int[][] arr = {{0, 1, 2}, {3, 499, 5}, {6, 7, 8}};
        final ITableSource source = new ListTableSource<>(StringSeriesList.from(arr));
        final String arrTable = Obj.asTable(source, new BlankTableWriter());
        //final String arrTable = Obj.asTable(source, new MarkdownTableWriter());
        System.out.println("Numbers array:\n" + arrTable);
    }

    @SuppressWarnings("unused")
    static class Cat {

        public String name;

        public int legsCount = 0;

        public boolean lazy = true;

        private double topSpeed = 0;

        public double getTopSpeed() {
            return topSpeed;
        }

        public void setTopSpeed(double topSpeed) {
            this.topSpeed = topSpeed;
        }

        public boolean isLazy() {
            return lazy;
        }

        public void setLazy(boolean lazy) {
            this.lazy = lazy;
        }
    }
}
