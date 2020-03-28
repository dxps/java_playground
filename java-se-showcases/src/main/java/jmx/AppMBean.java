package jmx;

/**
 * The JMX exposure contract.
 *
 * @author vision8
 */
public interface AppMBean {

    /** Get the message of the day (motd). */
    String getMotd();

    /** Update the message of the day (motd). */
    void updateMotd();

    /** Print to standard output the message of the day (motd). */
    void printMotd();

    /** Tell the application to stop. */
    void stop();

}
