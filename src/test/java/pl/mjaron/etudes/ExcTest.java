package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ExcTest {

    @Test
    void stackToString() {
        try {
            throw new RuntimeException("sample_exception");
        } catch (final Exception e) {
            String exceptionStackTrace = Exc.stackToString(e);
            System.out.println("exceptionStackTrace:\n" + exceptionStackTrace);
            assertFalse(exceptionStackTrace.isEmpty());
        }
    }
}
