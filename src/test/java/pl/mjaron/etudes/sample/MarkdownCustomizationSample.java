package pl.mjaron.etudes.sample;

import static pl.mjaron.etudes.table.RenderContext.col;

import pl.mjaron.etudes.Table;
import pl.mjaron.etudes.table.VerticalAlign;

public class MarkdownCustomizationSample {
    public static void customizeAlignment() {
        Table.render(Person.getSampleData(), Person.class).markdown().withAlign(VerticalAlign.Right).withAlign(1, VerticalAlign.Left).run();
    }

    public static void customizeColumnsOrder() {
        Table.render(Person.getSampleData(), Person.class).markdown().withColumns(
                // @formatter:off Preferences > Editor > Code Style > Formatter Control
                col("contact", "CONTACT")
                        .col("address")
                        .col("surname").as("SURNAME")
                // @formatter:on
        ).run();
    }
}
