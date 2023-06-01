# Google Cloud Function sample for Gen AI Prediction for Palm 2

## Running Locally
```shell
CURRENT_PROJECT=enter_the_name_of_the_project ./gradlew cloudFunctionRun
```

```shell
curl -v -X "http://localhost:8080?content=Hello" \
```


## Deploying the function
```shell
# Build the uber jar
./gradlew shadowJar

# Deploy the jar
gcloud beta functions deploy genai-http-function \
--gen2 \
--runtime java17 \
--trigger-http \
--entry-point functions.GenAISample \
--source ./build/libs/ \
--region us-central1 \
--set-env-vars CURRENT_PROJECT=enter_the_name_of_the_project \
--allow-unauthenticated
```
