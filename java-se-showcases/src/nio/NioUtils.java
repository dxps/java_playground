package nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Java NIO utilities.
 */
public class NioUtils {

    /**
     * Print to the standard output the files that exist on the provided location.
     * @param fileLocation The path where it looks for the files.
     */
    public static void printFileAsLines(String fileLocation) {

        Path path = Paths.get(fileLocation);
        try {
            Stream<String> lines = Files.lines(path);
            lines.forEach(s -> System.out.println(s));
        } catch (IOException e) {
            System.err.println("error: " + e.getMessage());
        }
    }

}
