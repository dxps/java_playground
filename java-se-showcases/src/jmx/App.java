package jmx;

/**
 * @author vision8
 */
public class App implements AppMBean, Runnable {

    private AppConfig config;

    private String baseMessage = "Hello ";

    private int counter;

    public App() {
        config = new AppConfig();
    }

    @Override
    public String getMotd() {
        return config.getMotd();
    }

    @Override
    public void updateMotd() {
        counter++;
        config.setMotd(baseMessage + counter);
        System.out.println(">>> Motd was updated.");
    }

    @Override
    public void printMotd() {
        System.out.println(config.getMotd());
    }

    @Override
    public void stop() {
        config.setRunning(false);
        System.out.println(">> App was informed to stop.");
    }

    @Override
    public void run() {

        System.out.println(">>> App started.");

        while (config.isRunning()) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                // ignoring the interruption
            }
        }

        System.out.println(">> App is stopping ...");
    }

}
