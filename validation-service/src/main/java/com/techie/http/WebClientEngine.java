package com.techie.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Component
public class WebClientEngine {

    @Autowired
    private WebClient weBuilder;

    public static final String POST="POST";
    public static final String GET="GET";
    public static final String DELETE="DELETE";

    public ResponseEntity<String> execute(HttpRequest httpRequest) {
        try {

            ResponseEntity<String> response = clientHttpCall(httpRequest);

            HttpStatusCode statusCode = response.getStatusCode();
            if (statusCode.is2xxSuccessful()) {
                return response;
            } else {
                String errorBody = response.getBody();
                return createCustomErrorResponse(statusCode, errorBody, response.getHeaders());
            }
        } catch (HttpClientErrorException | HttpServerErrorException exception) {
            return createCustomErrorResponse(exception.getStatusCode(),
                    exception.getResponseBodyAsString(), exception.getResponseHeaders());
        } catch (Exception exception) {
            // In Generic Exception class(Exception class) we didn't get any statusCode, headers and responseBody
            // that's why we are returning a null and printing a stacktrace.
            exception.printStackTrace();
            return null;
        }
    }

    private ResponseEntity<String> clientHttpCall(HttpRequest httpRequest) {

        switch (httpRequest.getHttpMethod().toString()) {
            case GET:
                return null;
            case DELETE:
                return null;
            default:
                return weBuilder.post().uri(httpRequest.getUrl())
                        .bodyValue(httpRequest.getRequest())
                        .headers(httpHeaders -> {
                                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                                }
                        )
                        .retrieve().toEntity(String.class).block();
        }
    }

    private ResponseEntity<String> createCustomErrorResponse(HttpStatusCode statusCode, String errorBody, HttpHeaders headers) {
        if (null == headers) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return new ResponseEntity<String>(errorBody, headers, statusCode);
    }
}
