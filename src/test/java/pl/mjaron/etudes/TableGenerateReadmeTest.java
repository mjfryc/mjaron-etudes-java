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

    void generateTableGenerationUtils(final SimpleMarkdownWriter writer) {
        writer.header("Table generation utils");
        {
            final SimpleMarkdownWriter shortExampleWriter = writer.child("Short example");
            shortExampleWriter.fencedCodeBlock("gradle", "implementation 'io.github.mjfryc:mjaron-etudes-java:" + pl.mjaron.etudes.Version.getVersion() + "'");
            shortExampleWriter.fencedCodeBlock(null, "Table.render(persons, Person.class).run();");
            shortExampleWriter.table(Table.render(Person.getSampleData(), Person.class));
        }

        {
            final SimpleMarkdownWriter verboseExampleWriter = writer.child("Verbose example");
            verboseExampleWriter.paragraph("Let's assume following Person class:");
            verboseExampleWriter.fencedCodeBlock("java", TestCommon.getTestClassContent(Person.class));
            verboseExampleWriter.paragraph("Then following code will render the table:");
            verboseExampleWriter.fencedCodeBlock("java", TestCommon.getTestClassContent(PersonVerboseSample.class));
            verboseExampleWriter.paragraph("With following result:");
            verboseExampleWriter.writeRawLine(ISystemOutAcquire.acquire(PersonVerboseSample::run));
        }

        {
            final SimpleMarkdownWriter markdownCustomizationWriter = writer.child("Markdown customization");
            markdownCustomizationWriter.paragraph("Following example shows the Markdown customization options.");
            markdownCustomizationWriter.fencedCodeBlock("java", TestCommon.getTestClassContent(MarkdownCustomizationSample.class));

            {
                final SimpleMarkdownWriter columnAlignmentWriter = markdownCustomizationWriter.child("Column alignment");
                columnAlignmentWriter.paragraph("There is possibility to set the all columns alignment and particular columns alignment. Column indexes are counted from 0.");
                columnAlignmentWriter.writeRawLine(ISystemOutAcquire.acquire(MarkdownCustomizationSample::customizeAlignment));
            }

            {
                final SimpleMarkdownWriter columnNamesAndOrderWriter = writer.child("Column names and order");
                columnNamesAndOrderWriter.paragraph("There is possibility to filter the columns, customize column names and order.");
                columnNamesAndOrderWriter.writeRawLine(ISystemOutAcquire.acquire(MarkdownCustomizationSample::customizeColumnsOrder));
            }
        }

        {
            final SimpleMarkdownWriter csvCustomizationWriter = writer.child("CSV customization");
            csvCustomizationWriter.paragraph("Below sample CSV snippet. Custom value separator may be set. Columns may be aligned but it is not recommended when importing by spreadsheet programs.");
            csvCustomizationWriter.fencedCodeBlock("java", TestCommon.getTestClassContent(CsvCustomizationSample.class));
            csvCustomizationWriter.fencedCodeBlock("csv", ISystemOutAcquire.acquire(CsvCustomizationSample::run));
        }

        {
            final SimpleMarkdownWriter htmlCustomizationWriter = writer.child("HTML customization");
            htmlCustomizationWriter.paragraph("Basic HTML table may be rendered. When the align is specified, it is written as inline style attribute.");
            htmlCustomizationWriter.paragraph("There is possibility to set the table id and class HTML attributes, so it can be customized with CSS.");
            htmlCustomizationWriter.fencedCodeBlock("java", TestCommon.getTestClassContent(HtmlCustomizationSample.class));
            htmlCustomizationWriter.fencedCodeBlock("html", ISystemOutAcquire.acquire(HtmlCustomizationSample::run));
        }
    }

    void generateObjectUtils(final SimpleMarkdownWriter writer) {
        writer.header("Object utils");
        writer.paragraph("Then following code will fetch the object fields:");
        writer.fencedCodeBlock("java", TestCommon.getTestClassContent(ObjTest.class));
        writer.paragraph("With the following result:");
        writer.fencedCodeBlock("json", ISystemOutAcquire.acquire(() -> {new ObjTest().getFieldValues();}));
    }

    @Test
    void generateReadme() {
        final SimpleMarkdownWriter writer = new SimpleMarkdownWriter();
        writer.header("mjaron-etudes-java");
        writer.paragraph("Standalone library for printing Java object lists (arrays, iterables) as a Markdown, CSV or HTML formatted table.");
        writer.paragraph("Utils compatible with Java 1.8.");
        writer.writeRaw(
                "[![Maven Central](https://img.shields.io/maven-central/v/io.github.mjfryc/mjaron-etudes-java?color=dark-green&style=flat)](https://search.maven.org/artifact/io.github.mjfryc/mjaron-etudes-java/)\n" +
                        "[![Java CI with Gradle](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle.yml)\n" +
                        "[![Publish package to GitHub Packages](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/mjfryc/mjaron-etudes-java/actions/workflows/gradle-publish.yml)\n" +
                        "[![Run on Repl.it](https://img.shields.io/badge/replit-Try%20online-orange)](https://replit.com/@mjfryc/Test-of-mjaron-etudes-java030?v=1)\n\n"
        );

        writer.writeRawLine("![EtudesLogo](other/Etudes.png)");
        writer.writeRawLine();

        writer.paragraph("This description has been generated from the unit test: `" + TableGenerateReadmeTest.class.getSimpleName() + "`");

        generateTableGenerationUtils(writer.child());
        generateObjectUtils(writer.child());
        System.out.println(writer.getOutString());
    }
}
