package pl.mjaron.etudes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Map;

import pl.mjaron.etudes.sample.Person;

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
