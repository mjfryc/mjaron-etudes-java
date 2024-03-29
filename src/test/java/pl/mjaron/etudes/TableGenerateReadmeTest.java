package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;
import pl.mjaron.etudes.sample.*;
import pl.mjaron.etudes.text.SimpleMarkdownWriter;

public class TableGenerateReadmeTest {

    /**
     * Provides nesting level updated with initial nesting level.
     *
     * @param level Relative nesting level.
     * @return Final nesting level.
     */
    static int nest(int level) {
        return level + 1;
    }

    @Test
    void generateReadme() {
        SimpleMarkdownWriter writer = new SimpleMarkdownWriter();
        writer.header(nest(1), "Table generation utils");
        writer.header(nest(2), "Short example");
        writer.fencedCodeBlock("gradle", "implementation 'io.github.mjfryc:mjaron-etudes-java:0.2.1'");
        writer.fencedCodeBlock(null, "Table.render(persons, Person.class).run();");
        writer.table(Table.render(Person.getSampleData(), Person.class));

        writer.header(nest(2), "Verbose example");
        writer.paragraph("Let's assume following Person class:");
        writer.fencedCodeBlock("java", TestCommon.getTestClassContent(Person.class));
        writer.paragraph("Then following code will render the table:");
        writer.fencedCodeBlock("java", TestCommon.getTestClassContent(PersonVerboseSample.class));

        writer.header(nest(2), "Markdown customization");
        writer.paragraph("Following example shows the Markdown customization options.");
        writer.fencedCodeBlock("java", TestCommon.getTestClassContent(MarkdownCustomizationSample.class));

        writer.header(nest(3), "Column alignment");
        writer.paragraph("There is possibility to set the all columns alignment and particular columns alignment. Column indexes are counted from 0.");
        writer.writeRawLine(ISystemOutAcquire.acquire(MarkdownCustomizationSample::customizeAlignment));

        writer.header(nest(3), "Column names and order");
        writer.paragraph("There is possibility to filter the columns, customize column names and order.");
        writer.writeRawLine(ISystemOutAcquire.acquire(MarkdownCustomizationSample::customizeColumnsOrder));

        writer.header(nest(2), "CSV customization");
        writer.paragraph("Below sample CSV snippet. Custom value separator may be set. Columns may be aligned but it is not recommended when importing by spreadsheet programs.");
        writer.fencedCodeBlock("java", TestCommon.getTestClassContent(CsvCustomizationSample.class));
        writer.fencedCodeBlock("csv", ISystemOutAcquire.acquire(CsvCustomizationSample::run));

        writer.header(nest(2), "HTML customization");
        writer.paragraph("Basic HTML table may be rendered. When the align is specified, it is written as inline style attribute.");
        writer.paragraph("There is possibility to set the table id and class HTML attributes, so it can be customized with CSS.");
        writer.fencedCodeBlock("java", TestCommon.getTestClassContent(HtmlCustomizationSample.class));
        writer.fencedCodeBlock("html", ISystemOutAcquire.acquire(HtmlCustomizationSample::run));
        System.out.println(writer.getOutString());
    }
}
