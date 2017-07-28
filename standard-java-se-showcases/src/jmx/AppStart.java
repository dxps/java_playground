package jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * @author vision8
 */
public class AppStart {

    /**
     * Startup of the class and JMX setup.
     * @param args Command line startup arguments.
     */
    public static void main(String... args) {

        // ------- initing the App -------

        System.out.println(">>> App is starting up ...");
        AppMBean app = new App();

        // ------- JMX setup -------

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName motd = new ObjectName("MYAPP:name=Motd");
            mbs.registerMBean(app, motd);
        } catch (Exception e) {
            System.err.println(">>> JMX init error: " + e.getMessage());
        }

        // ------- starting the App -------

        try {
            Thread appThread = new Thread((Runnable) app);
            appThread.start();
            // main thread is waiting for the app thread to stop
            appThread.join();
        } catch (InterruptedException e) {
            // nothing to do but exit
        }

    }

}
