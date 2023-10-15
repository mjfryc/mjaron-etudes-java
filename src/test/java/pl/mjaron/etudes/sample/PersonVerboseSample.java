package pl.mjaron.etudes.sample;

import pl.mjaron.etudes.Table;
import pl.mjaron.etudes.table.DateIso8601Formatter;
import pl.mjaron.etudes.table.VerticalAlign;

public class PersonVerboseSample {

    public static void main(final String[] args) {
        run();
    }

    public static void run() {
        // Verbose options demo
        Table.render(Person.getSampleData(), Person.class)

                // By default, the Markdown table format without escaper is used.
                // Calling .markdown() causes using Markdown renderer and Markdown escaper.
                .markdown() // Use Markdown renderer and escaper.
                // or
                // .csv() // Use CSV renderer and CSV escaper.
                // or
                // .html() // Use HTML renderer and HTML escaper.

                // Skip escaping the special characters.
                .withEscaper(null)

                // Optionally force align / do not align column widths.
                .withAlignedColumnWidths()
                // or
                //.withoutAlignedColumnWidths()
                // or
                //.withEqualColumnWidths()

                // Optionally use the custom cell delimiter.
                // ',' is the default cell delimiter.
                .withCellDelimiter(',')

                // How the lines will be separated.
                .withLineBreakCRLF()
                // or '\n'
                // .withLineBreakLF()
                // or '\r'
                // .withLineBreakCR()

                // How align the text (Left, Right or Center)
                .withAlign(VerticalAlign.Left)
                // or
                // .withAlign(VerticalAlign.Right)
                // or
                // .withAlign(VerticalAlign.Center)
                // or
                // .withAlign(null) // Use the default align.

                // If default toString() method is not satisfying, use custom formatter for specific class of cells.
                .withFormatter(java.util.Date.class, new DateIso8601Formatter())

                // Where to save the table.
                //.toFile("build/sample.csv")
                // By default, the System.out is used, which can be specified as:
                .to(System.out)
                // or: .to(Stream|PrintStream|Appendable|File|StringBuilder out)

                // Run the render operation.
                .run()
        // or
        // .runToString() // to create the String with whole table - instead .to() option.
        ;
    }
}
