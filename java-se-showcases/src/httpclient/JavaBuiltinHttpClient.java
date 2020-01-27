package httpclient;


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
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class JavaBuiltinHttpClient {

    static final String endpoint = "http://localhost";
    static final String keepAliveTimeout = "20";

    static final HttpClient httpClient = newHttpClient();

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("jdk.httpclient.keepalive.timeout", keepAliveTimeout);
        printResponseDetails(doGetRequest().get());
        System.out.println(">>> Sleeping for 30 sec ...");
        TimeUnit.SECONDS.sleep(30);
        printResponseDetails(doGetRequest().get());
    }

    static HttpClient newHttpClient() {

        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .sslContext(newSslContext())
                .build();
    }

    static SSLContext newSslContext() {

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            System.err.println(">>> Error creating SSL Context: " + e.getMessage());
        }
        return sslContext;
    }

    static Optional<HttpResponse<String>> doGetRequest() {

        HttpResponse<String> response = null;
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpoint))
                .setHeader("User-Agent", "curl")
                .build();
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println(">>> Error doing request: " + e.getMessage());
        }
        return Optional.ofNullable(response);
    }

    static void printResponseDetails(HttpResponse<String> response) {

        System.out.println("------------------------------------------------------");
        System.out.println(">>> Response Headers:");
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.printf("\t %20s : %s\n", k, v));
        System.out.println(">>> Response RC: " + response.statusCode());
    }

}
