
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Java11HttpClientExample1 {

    // default
    private final HttpClient httpClient = createHttpClient();

    // private final String endpoint = "https://aggregator.etoegang.nl/";
//    private final String endpoint = "https://connectis-eh01.dev.test-development.nl";
    private final String endpoint = "http://localhost:8000";

    // private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    private static HttpClient createHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .sslContext(createSslContext().get())
                .build();
    }

    private static Optional<SSLContext> createSslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            System.err.println(">>> Error: " + e.getMessage());
        }
        return Optional.ofNullable(sslContext);
    }

    private static TrustManager[] trustAllCerts = new TrustManager[]{

            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };

    private void doGetRequest() throws IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .setHeader("User-Agent", "curl/7.64.1")
                .setHeader("Keep-Alive", "timeout=10,max=50")
                .build();
        System.out.printf(">>> Sending request to %s ...\n", endpoint);
        printResponse(httpClient.send(request, HttpResponse.BodyHandlers.ofString()));

    }

    private void printResponse(HttpResponse<String> response) {

        // print response headers
        System.out.println("<<< response headers:");
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.printf("\t'%s' : '%s'\n", k, v));

        // print status code
        System.out.printf("<<< response rc: %d\n", response.statusCode());

        // print response body
        System.out.printf("<<< response body: %s\n", response.body());
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        // -Djdk.httpclient.keepalive.timeout=10
        // -Djdk.httpclient.HttpClient.debug=true
        // -Djdk.httpclient.HttpClient.log=errors,requests,headers,content,ssl,trace,channel"

//        System.getProperties().setProperty("jdk.httpclient.keepalive.timeout", String.valueOf(0));
        System.getProperties().setProperty("jdk.httpclient.HttpClient.debug", String.valueOf(true));
        System.getProperties().setProperty("jdk.httpclient.HttpClient.log", String.valueOf("errors,requests,headers,content,ssl,trace,channel"));

        var instance = new Java11HttpClientExample1();
        instance.doGetRequest();
        System.out.println(">>> Sleeping 30 seconds ...");
        TimeUnit.SECONDS.sleep(10);
        instance.doGetRequest();

    }

}
