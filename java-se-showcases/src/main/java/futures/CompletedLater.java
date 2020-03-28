package futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class CompletedLater {

    public static void main(String[] args) {

        String message = "hello";
        System.out.println(String.format(">>> Sending the message '%s' now.", message));
        String echo = echoAsync(message).toCompletableFuture().join();
        System.out.println(String.format(">>> Got the reply '%s'.", echo));
    }

    static CompletableFuture<String> echoAsync(String message) {

        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                cf.complete(message);
                System.out.println(">>> runAsync > Completed the future");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return cf;
    }

}
