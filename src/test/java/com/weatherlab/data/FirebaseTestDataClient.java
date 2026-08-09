package com.weatherlab.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherlab.config.TestConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public final class FirebaseTestDataClient {
    private static final String ENDPOINT = "https://identitytoolkit.googleapis.com/v1/";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TestUser createUser(String caseId) {
        TestUser user = TestDataFactory.uniqueUser(caseId);
        post("accounts:signUp", Map.of(
                "email", user.email(),
                "password", user.password(),
                "returnSecureToken", true));
        return user;
    }

    public void deleteUser(TestUser user) {
        try {
            JsonNode session = post("accounts:signInWithPassword", Map.of(
                    "email", user.email(),
                    "password", user.password(),
                    "returnSecureToken", true));
            post("accounts:delete", Map.of("idToken", session.path("idToken").asText()));
        } catch (IllegalStateException exception) {
            if (!exception.getMessage().contains("EMAIL_NOT_FOUND")
                    && !exception.getMessage().contains("INVALID_LOGIN_CREDENTIALS")) {
                throw exception;
            }
        }
    }

    private JsonNode post(String operation, Map<String, Object> body) {
        try {
            String requestBody = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT + operation + "?key=" + TestConfig.firebaseApiKey()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode json = objectMapper.readTree(response.body());
            if (response.statusCode() >= 400) {
                String message = json.path("error").path("message").asText("Firebase request failed");
                throw new IllegalStateException(operation + " failed: " + message);
            }
            return json;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to process Firebase response", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Firebase request was interrupted", exception);
        }
    }
}

