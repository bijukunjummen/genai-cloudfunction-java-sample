package functions.model;

import java.util.List;

public record PredictionRequest(List<Instance> instances, Parameters parameters) {
    public record Instance(String content){

    }

    public record Parameters(double temperature, int maxOutputTokens, int topK, double topP) {

    }
}



