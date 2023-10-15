package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import pl.mjaron.etudes.sample.Cat;
import pl.mjaron.etudes.table.BlankTableWriter;
import pl.mjaron.etudes.table.ITableSource;
import pl.mjaron.etudes.table.ListTableSource;
import pl.mjaron.etudes.table.StringSeriesList;

@Deprecated
public class ObjDeprecatedTest {

    @Test
    void asTable0() {
        final Cat cat = Cat.sampleCat();
        final String table = Table.render(Arrays.asList(cat, Cat.otherCat()), Cat.class).runToString();
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
}
