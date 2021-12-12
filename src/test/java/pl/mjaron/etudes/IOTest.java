package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IOTest {

    @Test
    void mainTestString() {
        final String str = "Fish changes everything.";
        final File targetFile = new File("./test_dir/string.txt");
        IO.write(str, targetFile);
        final String resultString = IO.readAllString(targetFile);
        assertEquals(str, resultString);
    }

    @Test
    void mainTestBinary() {
        final byte[] data = new byte[]{0, 1, 2};
        final File targetFile = new File("./test_dir/binary.bin");
        IO.write(data, targetFile);
        final byte[] resultBytes = IO.readAllBytes(targetFile);
        assertArrayEquals(data, resultBytes);
    }
}

