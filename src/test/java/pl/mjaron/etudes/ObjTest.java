package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;
import pl.mjaron.etudes.sample.Person;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjTest {

    @Test
    public void getFieldValues() {
        final Person person = Person.getSampleData()[0];
        final Map<String, Object> values = Obj.getFieldValues(person);
        System.out.println(values);
        assertEquals("Sally", values.get("name"));
        assertEquals("Fox", values.get("surname"));
    }
}
