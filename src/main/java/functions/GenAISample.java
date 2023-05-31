package functions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import functions.model.PredictionRequest;
import functions.model.PredictionResponse;
import functions.service.GenAiCallService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class GenAISample implements HttpFunction {
    private static final Logger LOGGER = Logger.getLogger(GenAISample.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String CONTENT_FIELD = "content";

    private final String projectId;
    private static final String DEFAULT_CONTENT_VALUE = "why was there a traffic congestion in bellevue this weekend ?";

    private final GenAiCallService genAiCallService = new GenAiCallService();

    public GenAISample() {
        this.projectId = System.getenv("CURRENT_PROJECT");
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String content = request.getFirstQueryParameter(CONTENT_FIELD).orElse(DEFAULT_CONTENT_VALUE);

        PredictionResponse predictionResponse = genAiCallService.predict(projectId, "text-bison",
                new PredictionRequest(
                        List.of(new PredictionRequest.Instance(content)),
                        new PredictionRequest.Parameters(0.2, 256, 40, 0.95)));
        final BufferedWriter writer = response.getWriter();
        response.setContentType("application/json");
        writer.write(objectMapper.writeValueAsString(predictionResponse));
    }
}