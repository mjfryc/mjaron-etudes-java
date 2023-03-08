package pl.mjaron.etudes;

import java.io.*;
import java.nio.charset.Charset;

@SuppressWarnings("unused")
public class IO {

    /**
     * Default buffer size used by {@link #copyStream(InputStream, OutputStream)}
     */
    public static final int DEFAULT_BUFFER_SIZE = 4 * 1024;

    /**
     * Write all <code>data</code> to given <code>outputStream</code>.
     *
     * @param data         Byte array.
     * @param outputStream Any {@link java.io.OutputStream}.
     * @throws RuntimeException on any IO operation error.
     */
    public static void write(final byte[] data, final OutputStream outputStream) {
        try {
            outputStream.write(data);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to write all bytes.", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write given <code>data</code> to <code>file</code>
     *
     * @param data Byte array.
     * @param file Any {@link java.io.File}.
     */
    public static void write(final byte[] data, final File file) {
        try {
            write(data, new FileOutputStream(file));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException("Failed to write data to file: File not found: [" + file.getPath() + "].", e);
        }
    }

    /**
     * Write given <code>string</code> to given <code>file</code> with given <code>charset</code>
     *
     * @param string  String used to write to the file.
     * @param file    Written file.
     * @param charset Charset.
     */
    public static void write(final String string, final File file, final Charset charset) {
        write(string.getBytes(charset), file);
    }

    /**
     * Write given <code>string</code> to given <code>file</code> with default {@link java.nio.charset.Charset}.
     *
     * @param string String used to write to the file.
     * @param file   Written file.
     */
    public static void write(final String string, final File file) {
        write(string.getBytes(), file);
    }

    /**
     * Read whole content of file to byte array.
     *
     * @param file Any {@link java.io.File}.
     * @return Byte array with file content.
     * @throws RuntimeException when file is not found or file read operation fails.
     */
    public static byte[] readAllBytes(final File file) {
        try {
            return readAllBytes(new FileInputStream(file));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException("Failed to read from file: File is not found: [" + file.getPath() + "].", e);
        }
    }

    /**
     * Copy any {@link java.io.InputStream} to {@link java.io.OutputStream}.
     *
     * @param inputStream  Any {@link java.io.InputStream}. User is responsible to close this stream when no longer
     *                     used.
     * @param outputStream Any {@link java.io.OutputStream}. User is responsible to close this stream when no longer
     *                     used.
     * @param bufferSize   Size of buffer used while reading / writing.
     * @throws IOException On any read / write error.
     */
    public static void copyStream(final InputStream inputStream, final OutputStream outputStream, final int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        while (true) {
            final int bytesReadCount = inputStream.read(buffer, 0, buffer.length);
            if (bytesReadCount == -1) {
                break;
            }
            outputStream.write(buffer, 0, bytesReadCount);
        }
    }

    /**
     * Copy any {@link java.io.InputStream} to {@link java.io.OutputStream} with default buffer size.
     *
     * @param inputStream  Any {@link java.io.InputStream} used to read data.
     * @param outputStream Any {@link java.io.OutputStream} used to write data.
     * @throws IOException on any IO operation error.
     */
    public static void copyStream(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        copyStream(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Reads all content from any {@link java.io.InputStream}.
     *
     * @param inputStream {@link java.io.InputStream} used to read data. Finally, it is closed even an exception is
     *                    thrown.
     * @return All bytes of given <code>inputStream</code>.
     * @throws RuntimeException on any IO operation error.
     */
    public static byte[] readAllBytes(final InputStream inputStream) {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            copyStream(inputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (final IOException e) {
            throw new RuntimeException("Failed to read all bytes from input stream.", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readAllToString(final File targetFile) {
        return new String(readAllBytes(targetFile), Charset.defaultCharset());
    }

    /**
     * Cleans content of a directory but doesn't delete itself.
     *
     * @param directory Any file which is a directory. Other files will be ignored.
     * @throws RuntimeException when cannot delete a file or directory.
     */
    public static void cleanDirectory(final File directory) {
        final File[] children = directory.listFiles();
        if (children == null) {
            return;
        }
        for (final File entry : children) {
            if (entry.isDirectory()) {
                cleanDirectory(entry);
            }
            if (!entry.delete()) {
                throw new RuntimeException("Failed to delete file: [" + entry.getAbsolutePath() + "].");
            }
        }
    }

    /**
     * Cleans content of a directory but doesn't delete itself.
     *
     * @param directory Any file path which is a directory. Other files will be ignored.
     * @throws RuntimeException when cannot delete a file or directory.
     */
    public static void cleanDirectory(final String directory) {
        cleanDirectory(new File(directory));
    }

    /**
     * Delete file or directory, even if directory is not empty.
     *
     * @param file File which will be deleted.
     */
    public static void delete(final File file) {
        if (file.isDirectory()) {
            cleanDirectory(file);
        }
        if (!file.delete()) {
            throw new RuntimeException("Failed to delete file: [" + file.getAbsolutePath() + "].");
        }
    }

    /**
     * Creates a directories pointed by path.
     *
     * @param destination destination directory.
     * @return destination directory.
     * @throws RuntimeException when failed to create path.
     */
    public static File mkdirs(final File destination) {
        if (!destination.mkdirs()) {
            if (!destination.exists()) {
                throw new RuntimeException("Failed to create directories: [" + destination.getAbsolutePath() + "].");
            }
        }
        return destination;
    }

    public static File mkdirs(final String destination) {
        return mkdirs(new File(destination));
    }
}
