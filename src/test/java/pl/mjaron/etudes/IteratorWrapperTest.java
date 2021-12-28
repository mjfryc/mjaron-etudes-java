package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IteratorWrapperTest {

    @Test
    void curr() {

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        IteratorWrapper<Integer> wrapper = new IteratorWrapper<>(list.iterator());
        assertNull(wrapper.curr());
        Integer element0 = wrapper.next();
        assertNotNull(element0);
        assertEquals(0, element0.intValue());
        assertEquals(element0.intValue(), wrapper.curr().intValue());
        Integer element1 = wrapper.next();
        assertEquals(element1.intValue(), wrapper.curr().intValue());
        assertNotEquals(element0.intValue(), element1.intValue());
    }
}