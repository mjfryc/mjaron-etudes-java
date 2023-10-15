package pl.mjaron.etudes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ISystemOutAcquireTest {

    static void printingCode() {
        System.out.print("Sample output.");
    }

    @Test
    void acquireCall() {
        final String contentFromCall = ISystemOutAcquire.acquire(ISystemOutAcquireTest::printingCode);
        assertEquals("Sample output.", contentFromCall);
    }

    @Test
    void acquireScope() {

        final String contentFromScope;
        try (ISystemOutAcquire acquire = ISystemOutAcquire.acquire()) {
            printingCode();
            contentFromScope = acquire.toString();
        }
        assertEquals("Sample output.", contentFromScope);
    }
}