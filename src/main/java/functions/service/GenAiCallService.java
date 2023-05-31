package functions.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import functions.model.PredictionRequest;
import functions.model.PredictionResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GenAiCallService {

    private final HttpClient httpClient;

    private final GoogleCredentials credentials;
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public GenAiCallService() {
        try {
            this.credentials = GoogleCredentials.getApplicationDefault();
            this.credentials.refreshIfExpired();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.httpClient = HttpClient.newHttpClient();
    }

    public PredictionResponse predict(String projectId, String modelId, PredictionRequest predictionRequest) {
        try {
            String jsonBody = objectMapper.writeValueAsString(predictionRequest);
            String token = this.credentials.getAccessToken().getTokenValue();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://us-central1-aiplatform.googleapis.com/v1/projects/" + projectId + "/locations/us-central1/publishers/google/models/" + modelId + ":predict"))
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String body = httpResponse.body();
            PredictionResponse predictionResponse = objectMapper.readValue(body, PredictionResponse.class);
            return predictionResponse;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
