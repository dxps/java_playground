package nio;

import java.io.*;

/**
 * @author vision8
 */
public class InputStreamExamples {


    /** Read a file as bytes and print the contents to the standard output. */
    public static void printContentOfTextFileAsBytes(String filePath) throws IOException {

        InputStream input = new FileInputStream(filePath);

        // read() returns an int which contains the byte value of the byte read
        int data = input.read();

        while (data != -1) {
            System.out.println(data);
            // casting data to char will show the actual character:
            // System.out.println((char)data);
            data = input.read();
        }
        input.close();
    }

    /** Read a file as characters (for text files) and print the contents to the standard output. */
    public static void printContentOfTextFileAsChars(String filePath) throws IOException {

        Reader reader = new FileReader(filePath);

        // read() returns an int which contains the byte value of the byte read
        int data = reader.read();

        while (data != -1) {
            System.out.print((char) data);
            data = reader.read();
        }
        reader.close();
    }

    /* Just for the sake of showcase & testing. */
    public static void main(String ... args) throws IOException {

        String filePath = "/tmp/niotest1.txt";

        System.out.println(String.format("\n>>> The content of %s file as bytes (integer values):", filePath));
        printContentOfTextFileAsBytes(filePath);

        System.out.println(String.format("\n>>> The content of %s file as text (characters):", filePath));
        printContentOfTextFileAsChars(filePath);

    }

}
