package com.provedcode.paypal.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provedcode.config.PaypalConfig;
import com.provedcode.paypal.model.dto.AccessTokenResponseDTO;
import com.provedcode.paypal.model.dto.OrderDTO;
import com.provedcode.paypal.model.response.OrderResponse;

@Component
public class PayPalHttpClient {
    private final HttpClient httpClient;
    private final PaypalConfig paypalConfig;
    private final ObjectMapper objectMapper;

    @Autowired
    public PayPalHttpClient(PaypalConfig paypalConfig, ObjectMapper objectMapper) {
        this.paypalConfig = paypalConfig;
        this.objectMapper = objectMapper;
        httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    }

    public AccessTokenResponseDTO getAccessToken() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PayPalEndpoints.GET_ACCESS_TOKEN.createUrl(paypalConfig.getBaseUrl())))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodeBasicCredentials())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials")).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        return objectMapper.readValue(content, AccessTokenResponseDTO.class);
    }

    public OrderResponse createOrder(OrderDTO order) throws IOException, InterruptedException {
        AccessTokenResponseDTO accessTokenDto = getAccessToken();
        String payload = objectMapper.writeValueAsString(order);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PayPalEndpoints.ORDER_CHECKOUT.createUrl(paypalConfig.getBaseUrl())))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenDto.accessToken())
                .POST(HttpRequest.BodyPublishers.ofString(payload)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        return objectMapper.readValue(content, OrderResponse.class);
    }

    private String encodeBasicCredentials() {
        String credentials = paypalConfig.getClientId() + ":" + paypalConfig.getSecret();
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(credentials.getBytes());
    }
}
