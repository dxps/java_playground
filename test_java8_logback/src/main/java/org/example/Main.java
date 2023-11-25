package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Main main = new Main();
        System.out.println("Resource logback.xml is "
                + main.getClass().getClassLoader().getResource("/logback.xml")
        );

        logger.info("Starting up ...");

        logger.warn("Shutting down ...");
    }
}