package pl.mjaron.etudes.sample;

import pl.mjaron.etudes.Table;
import pl.mjaron.etudes.table.VerticalAlign;

import static pl.mjaron.etudes.table.Html.tableId;
import static pl.mjaron.etudes.table.RenderContext.col;

public class HtmlCustomizationSample {
    public static void run() {
        // @formatter:off Preferences > Editor > Code Style > Formatter Control
        Table.render(Person.getSampleData(), Person.class)
                .html(tableId("my-table-id").tableClass("my-table-class"))
                .withAlign(VerticalAlign.Center)
                .withAlign(1, VerticalAlign.Right)

                // Let's select the rendered columns.
                .withColumns(
                        col("contact", "CONTACT")
                        .col("address")
                        .col("surname").as("SURNAME")
        ).run();
        // @formatter:on
    }
}
