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

package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;
import pl.mjaron.etudes.table.VerticalAlign;

import java.nio.charset.Charset;

class Cat {
    String name = "Tom";
    int age = 2;

    Cat() {
    }

    Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class TableTest {

    @Test
    void render() {

        System.out.println("Default charset is: " + Charset.defaultCharset().name());

        final Cat[] cats = new Cat[]{new Cat(), new Cat("_Michael_", 5)};//, new Cat("My nickname is \"ABC\"", 10), new Cat("Next\r\nline", 11)};

        System.out.println("MarkdownEscaper:");
        Table.render(cats, Cat.class).withMarkdownEscaper().withAlign(VerticalAlign.Center).withAlign(1, VerticalAlign.Right).withAlignedColumnWidths().run();

        System.out.println("withoutAlignedColumnWidths():");
        Table.render(cats, Cat.class).withoutAlignedColumnWidths().withoutEscaper().run();

        System.out.println("withAlignedColumnWidths():");
        Table.render(cats, Cat.class).withAlignedColumnWidths().run();

        System.out.println("withEqualColumnWidths():");
        Table.render(cats, Cat.class).withEqualColumnWidths().run();

        System.out.println("BlankTableWriter:");
        Table.render(cats, Cat.class).withBlankTableWriter().withAlignedColumnWidths().withAlign(VerticalAlign.Left).run();

        System.out.println("CsvTableWriter:");
        Table.render(cats, Cat.class).withCsvWriter().run();

        String rendered = Table.render(cats, Cat.class).withAlignedColumnWidths().withCsvWriter().withLineBreakCRLF().runToString();
        System.out.println(rendered);

        //System.out.println("All options demo.");
        //Table.render(cats, Cat.class).withCsvWriter().withCsvEscaper().withAlignedColumnWidths(false).withCellDelimiter(';').withLineBreakCRLF().toFile("build/sample.csv").run();

        Table.render(cats, Cat.class).html().withAlign(VerticalAlign.Right).withAlign(0, VerticalAlign.Left).run();
    }
}

