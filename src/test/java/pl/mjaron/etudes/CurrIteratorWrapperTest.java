package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrIteratorWrapperTest {

    @Test
    void curr() {

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        ICurrIterator<Integer> wrapper = new CurrIteratorWrapper<>(list.iterator());
        assertNull(wrapper.getCurrent());
        Integer element0 = wrapper.next();
        assertNotNull(element0);
        assertEquals(0, element0.intValue());
        assertEquals(element0.intValue(), wrapper.getCurrent().intValue());
        Integer element1 = wrapper.next();
        assertEquals(element1.intValue(), wrapper.getCurrent().intValue());
        assertNotEquals(element0.intValue(), element1.intValue());
    }
}