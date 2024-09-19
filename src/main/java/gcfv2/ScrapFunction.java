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
        StringBuilder sb = new StringBuilder();

        if (url.isPresent()) {

            RestClient restClient = RestClient.create();
            ResponseEntity<String> urlResponse = restClient.get()
                    .uri(url.get())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntity(String.class);
            if (urlResponse == null) {
                sb.append("{ response: \"No Response\", statusCode:").append("\"").append("404").append("\"}");
            } else {
                sb.append("{ response: \"").append(urlResponse.getBody()).append("\",").append("statusCode: \"")
                        .append(urlResponse.getStatusCode().value()).append("\"}");
            }
            writer.write(sb.toString());
        } else {
            writer.write("URL not found in params");
        }
    }
}