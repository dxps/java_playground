package jmx;

/**
 * The JMX exposure contract.
 *
 * @author vision8
 */
public interface AppMBean {

    /** Set the message of the day (motd). */
    public void setMotd(String message);

    /** Get the message of the day (motd). */
    public String getMotd();

    /** Print to standard output the message of the day (motd). */
    public void printMotd();

    /** Tell the application to stop. */
    public void stop();

}
