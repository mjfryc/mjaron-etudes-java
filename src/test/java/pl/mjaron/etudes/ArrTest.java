package pl.mjaron.etudes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ArrTest {

    @Test
    void ensureSize() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Arr.ensureSize(arrayList, 3);
        assertEquals(3, arrayList.size());
        assertEquals(new ArrayList<Integer>() {{
            add(null);
            add(null);
            add(null);
        }}, arrayList);
    }

    @Test
    void trimToSize() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>() {{
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        Arr.trimToSize(arrayList, 2);
        assertEquals(new ArrayList<Integer>() {{
            add(0);
            add(1);
        }}, arrayList);
        Arr.trimToSize(arrayList, 2);
        assertEquals(new ArrayList<Integer>() {{
            add(0);
            add(1);
        }}, arrayList);
        Arr.trimToSize(arrayList, 3);
        assertEquals(new ArrayList<Integer>() {{
            add(0);
            add(1);
        }}, arrayList);
    }

    @Test
    void resizeArrayList() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>() {{
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        Arr.resize(arrayList, 7);
        assertEquals(new ArrayList<Integer>() {{
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
            add(null);
            add(null);
        }}, arrayList);
        Arr.resize(arrayList, 1);
        assertEquals(new ArrayList<Integer>() {{
            add(0);
        }}, arrayList);
        Arr.resize(arrayList, 2);
        assertEquals(new ArrayList<Integer>() {{
            add(0);
            add(null);
        }}, arrayList);
        Arr.resize(arrayList, 0);
        assertTrue(arrayList.isEmpty());
    }

    @Test
    void add() {

        int[] myArray = new int[10];
        int[] extendedArray = Arr.add(myArray, 1);
        assertEquals(1, extendedArray[10]);
    }

    @Test
    void add1() {
        int[] a = new int[]{1, 2, 3};
        int[] b = new int[]{6, 7, 5};
        int[] c = Arr.add(a, b);
        assertArrayEquals(c, new int[]{1, 2, 3, 6, 7, 5});
    }

    @Test
    void add2() {
        char[] a = new char[]{};
        char[] b = new char[]{};
        char[] c = Arr.add(a, b);
        assertArrayEquals(c, new char[]{});
    }

    @Test
    void add3() {
        boolean[] a = new boolean[]{false};
        boolean[] b = new boolean[]{};
        boolean[] c = Arr.add(a, b);
        assertArrayEquals(c, new boolean[]{false});
    }

    @SuppressWarnings("WrapperTypeMayBePrimitive")
    @Test
    void containsReference() {
        final Integer i0 = 0;
        final Integer i1 = 1;
        final Integer i2 = 2;
        final Integer i3 = 3;
        final Integer i4 = 4;
        final Integer[] arr = new Integer[]{i1, i2, i3};
        assertTrue(Arr.containsReference(arr, i1));
        assertFalse(Arr.containsReference(arr, i4));

        final Integer[] emptyArr = new Integer[0];
        assertFalse(Arr.containsReference(arr, i0));
    }
}
