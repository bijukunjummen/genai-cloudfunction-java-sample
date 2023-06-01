package functions.model;

import java.util.List;

public record PredictionResponse(List<Prediction> predictions) {
    public record Prediction(String content) {

    }
}
