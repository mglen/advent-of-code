import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Utils {

    public static Stream<String> readLines(String filename) {
        Path path = Path.of(filename);
        try {
            return Files.lines(path);
        } catch (NoSuchFileException e) {
            throw fail("File not found: " + path.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void println(String pattern, Object... args) {
        System.out.printf(pattern + "%n", args);
    }

    public static RuntimeException fail(String pattern, Object... args) {
        FailException failException = new FailException(pattern, args);
        // Better stack trace, because why not
        StackTraceElement[] stackTrace = failException.getStackTrace();
        failException.setStackTrace(Arrays.copyOfRange(stackTrace, 1, stackTrace.length));
        return failException;
    }

    private static class FailException extends RuntimeException {
        public FailException(String pattern, Object... args) {
            super(String.format(pattern, args));
        }
    }
}
