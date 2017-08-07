package tech.vision8.embjetty.app.services.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

/**
 * @author vision8
 */
public class AppResource {

    private static final Logger log = LoggerFactory.getLogger(AppResource.class);

    public static Instant startTime = Instant.now();


    // TODO: This is part of the REST exercise.
    //
    // Basically, you have to implement the App as a resource
    // and expose it to the external world through two REST operations:
    //
    // - a GET /api/app 
    //   this operation should return the uptime in a JSON format
    //
    // - a POST /api/app 
    //   this operation should reset the uptime and return the new state in JSON format
    //   (the same result as the previous operation, but this time with the new uptime)
    //
    // Get inspired from SalutationResource example included in this package.
    // A utility method named humanReadableFormat is provided to be helpful.


    public String humanReadableFormat(Duration duration) {

        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

}
