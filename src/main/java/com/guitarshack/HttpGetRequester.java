package com.guitarshack;

import com.google.gson.Gson;
import com.guitarshack.domain.GetRequestParameter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HttpGetRequester {

    public Map<String, Object> request(GetRequestParameter getRequestParameter) {

        String paramString2 = "?" + getRequestParameter.getParameters().stream()
                .map(v -> "%s=%s&".formatted(v.getKey(), v.getValue().toString()))
                .collect(Collectors.joining());

        URI uri = URI.create(getRequestParameter.getBaseUri() + paramString2);
        HttpRequest request = HttpRequest.newBuilder(uri).build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            return new Gson().fromJson(result, HashMap.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not execute http get request for %s".formatted(getRequestParameter), e);
        }
    }
}