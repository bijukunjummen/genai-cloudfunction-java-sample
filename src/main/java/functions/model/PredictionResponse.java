package functions.model;

import java.util.List;

public record PredictionResponse(List<Prediction> predictions) {
    record Prediction(String content) {

    }
}
