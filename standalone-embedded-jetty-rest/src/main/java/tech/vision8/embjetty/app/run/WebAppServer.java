package tech.vision8.embjetty.app.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author vision8
 */
public class WebAppServer {

    private static WebAppServer instance;

    private static Logger log = LoggerFactory.getLogger(WebAppServer.class);

    static {
        instance = new WebAppServer();
    }

    public static WebAppServer instance() {
        return instance;
    }

    private WebAppServer() {
        // as being a singleton
    }

    public void start() throws Exception {

        String webappDirLocation = "webapp";

        String webappDescriptor = "/WEB-INF/web.xml";

        String webPort = "8787";

        Server server = new Server(Integer.valueOf(webPort));

        // enable parsing of jndi-related parts of web.xml and jetty-env.xml
        org.eclipse.jetty.webapp.Configuration.ClassList classlist = org.eclipse.jetty.webapp.Configuration.ClassList.setServerDefault(server);
        classlist.addAfter("org.eclipse.jetty.webapp.FragmentConfiguration", "org.eclipse.jetty.plus.webapp.EnvConfiguration", "org.eclipse.jetty.plus.webapp.PlusConfiguration");
        classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration");

        Path webappDescriptorPath = Paths.get(webappDirLocation + webappDescriptor)
                .toAbsolutePath();
        if (!Files.exists(webappDescriptorPath)) {
            throw new Exception("Path " + webappDescriptorPath.toString() + " could not be found.");
        }

        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setResourceBase(webappDirLocation);
        root.setDescriptor(webappDirLocation + webappDescriptor);

        String operSys = System.getProperty("os.name");
        if (operSys.toLowerCase().contains("win")) {
            root.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
            log.debug(String.format("For development purposes, since running on Windows (os.name='%s'), no file mapped buffer will be used.", operSys));
        }

        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default, Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part of the
        // container. Setting parent loader priority to true changes this behavior.
        // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        server.setHandler(root);

        server.start();
        log.info(String.format("Web UI started on port %s.", webPort));

        server.join();
    }

}
