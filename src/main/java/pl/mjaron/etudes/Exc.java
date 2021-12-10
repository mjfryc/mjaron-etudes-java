package pl.mjaron.etudes;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Exc {

    /**
     * Gives String containing printed message with stack trace.
     *
     * @param throwable Any Exception or Throwable object.
     * @return String containing printed message with stack trace.
     */
    public static String stackToString(final Throwable throwable) {
        final OutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        printStream.close();
        return outputStream.toString();
    }

    /**
     * @param throwable Any Exception or Throwable object.
     * @return Message or empty String but not null.
     */
    public static String messageToString(final Throwable throwable) {
        return Str.orEmpty(throwable.getMessage());
    }

}
