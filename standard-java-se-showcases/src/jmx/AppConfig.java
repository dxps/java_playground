package jmx;

/**
 * A simple config component.
 *
 * @author vision8
 */
public class AppConfig {

    /** message of the day */
    private String motd;

    /** running flag */
    private boolean isRunning;

    public AppConfig() {
        this.motd = "n/a";
        isRunning = true;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
