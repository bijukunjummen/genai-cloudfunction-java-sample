package functions.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PredictionRequestMappingTest {

    @Test
    void testPredictionRequestMapping() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = """
                {
                  "instances": [
                    { "content": "Hello"}
                  ],
                  "parameters": {
                    "temperature": 0.2,
                    "maxOutputTokens": 256,
                    "topK": 40,
                    "topP": 0.95
                  }
                }
                """;
        PredictionRequest req = objectMapper.readValue(json, PredictionRequest.class);

        PredictionRequest expected =
                new PredictionRequest(
                        List.of(new PredictionRequest.Instance("Hello")),
                        new PredictionRequest.Parameters(0.2, 256, 40, 0.95));

        assertThat(req).isEqualTo(expected);
    }
}
