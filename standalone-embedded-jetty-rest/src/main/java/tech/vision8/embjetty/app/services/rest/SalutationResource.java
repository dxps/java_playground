package tech.vision8.embjetty.app.services.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vision8
 */
@Path("/hello")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class SalutationResource {

    private static final Logger log = LoggerFactory.getLogger(SalutationResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/text")
    public String getHello() throws InterruptedException
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final String message = "Hello! This moment is " + sdf.format(new Date());

        log.info("[/text] getHello() response is \"{}\".", message);

        return message;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json")
    public SalutationResponse getHelloJSON() throws InterruptedException
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final String message = "Hello! This moment is " + sdf.format(new Date());



        return new SalutationResponse(message);
    }

    public static class SalutationResponse {

        private final String text;

        public SalutationResponse(String data)
        {
            this.text = data;
        }

        public String getData()
        {
            return text;
        }
    }

}
