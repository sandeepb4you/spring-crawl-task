package gcfv2;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.io.BufferedWriter;
import java.util.Optional;

public class ScrapFunction implements HttpFunction {
    public void service(final HttpRequest request, final HttpResponse response) throws Exception {
        final BufferedWriter writer = response.getWriter();

        Optional<String> url = request.getFirstQueryParameter("url");

        if (url.isPresent()) {

            RestClient restClient = RestClient.create();
            ResponseEntity<String> urlResponse = restClient.get()
                    .uri(url.get())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
            writer.write(urlResponse == null ? "": urlResponse.getBody());
        } else {
            writer.write("URL not found in params");
        }
    }
}