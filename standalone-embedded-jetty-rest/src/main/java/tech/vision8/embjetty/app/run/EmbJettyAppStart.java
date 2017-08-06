package tech.vision8.embjetty.app.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vision8
 */
public class EmbJettyAppStart {

    private static final Logger log = LoggerFactory.getLogger(EmbJettyAppStart.class);

    public static void main(String... args) {

        log.info("----------------------------------------------------");
        log.info("-                 Starting up                      -");
        log.info("----------------------------------------------------");

        try {
            WebAppServer.instance().start();
        } catch (Exception e) {
            log.error("Webapp server startup failed: " + e.getMessage());
        }
    }

}
