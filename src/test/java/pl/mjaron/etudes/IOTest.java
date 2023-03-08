package pl.mjaron.etudes;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IOTest {

    @Test
    void mainTestString() {
        final String str = "Fish changes everything.";
        final File targetFile = new File("./test_dir/string.txt");
        IO.write(str, targetFile);
        final String resultString = IO.readAllToString(targetFile);
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

    @Test
    void cleanDirectory() {
        final File abc = new File(IO.mkdirs("./test_dir/to_delete"), "abc.txt");
        final File def = new File(IO.mkdirs("./test_dir/to_delete"), "def.txt");
        IO.write("Abc", abc);
        IO.write("Def", def);
        IO.cleanDirectory("./test_dir/to_delete");
        assertEquals(Objects.requireNonNull(new File("./test_dir/to_delete").listFiles()).length, 0);
    }
}

