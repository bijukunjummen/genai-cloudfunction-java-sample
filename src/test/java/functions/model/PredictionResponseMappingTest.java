package functions.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PredictionResponseMappingTest {

    @Test
    void testPredictionResponseMapping() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = """
                {
                   "predictions": [
                     {
                       "safetyAttributes": {
                         "categories": [],
                         "scores": [],
                         "blocked": false
                       },
                       "recitationResult": {
                         "recitations": [],
                         "recitationAction": "NO_ACTION"
                       },
                       "content": "Sample content"
                     }
                   ]
                 }
                """;
        PredictionResponse response = objectMapper.readValue(json, PredictionResponse.class);

        assertThat(response.predictions().get(0).content()).isEqualTo("Sample content");
    }
}
