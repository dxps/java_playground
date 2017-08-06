package tech.vision8.embjetty.app.services.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vision8
 */
@ApplicationPath("/rest")
public class RestApplication extends Application {

    private Set<Object> singletons = new HashSet<>();

    public RestApplication() {
        singletons.add(new SalutationResource());
        singletons.add(new AppResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}

