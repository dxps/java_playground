package nio.tests;

import nio.NioUtils;

/**
 * @author vision8
 */
public class NioTests {

    public static void main(String... args) {

        String location = "/tmp/niotest1.txt";
        System.out.println(String.format(">>> Listing the lines that exist on file %s:", location));
        NioUtils.printFileAsLines(location);

    }

}
