package pl.mjaron.etudes.sample;

import pl.mjaron.etudes.Table;

import static pl.mjaron.etudes.table.RenderContext.col;

public class CsvCustomizationSample {
    public static void run() {
        Table.render(Person.getSampleData(), Person.class).csv()
                .withCellDelimiter(" , ")    // Allows changing the cell separator; ',' is by default.

                // Column alignment used here only to make it more human-readable.
                // Column alignment is not recommended for CSV due to importing it later by spreadsheet applications.
                // Additional spaces for alignment may break the cell content.
                // E.g. Use "Trim spaces" option when importing with LibreOffice Calc.
                .withAlignedColumnWidths()

                // Let's select the rendered columns.
                .withColumns(
                // @formatter:off Preferences > Editor > Code Style > Formatter Control
                col("contact", "CONTACT")
                        .col("address")
                        .col("surname").as("SURNAME")
                // @formatter:on
        ).run();
    }
}
