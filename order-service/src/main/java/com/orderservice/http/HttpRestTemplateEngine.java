package com.orderservice.http;


import com.orderservice.constansts.HttpMethodEnum;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class HttpRestTemplateEngine {

    public ResponseEntity<String> execute(HttpRequest httpRequest) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(httpRequest.getRequest(), httpHeaders);

            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setOutputStreaming(false);
            restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));

            HttpMethod httpMethod = prepareHttpMethod(httpRequest.getHttpMethod());

            ResponseEntity<String> response = restTemplate.exchange(httpRequest.getUrl(), httpMethod, entity, String.class);

            HttpStatusCode statusCode = response.getStatusCode();

            if (statusCode.is2xxSuccessful()) {
                return response;
            } else {
                String errorResponse = response.getBody();
                return createCustomErrorResponse(statusCode, errorResponse, response.getHeaders());
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return createCustomErrorResponse(e.getStatusCode(), e.getResponseBodyAsString(), e.getResponseHeaders());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResponseEntity<String> createCustomErrorResponse(HttpStatusCode statusCode,
                                                             String errorResponse, HttpHeaders headers) {
        if (null != headers) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        } else {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        ResponseEntity<String> response = new ResponseEntity<>(errorResponse, headers, statusCode);
        return response;
    }

    private HttpMethod prepareHttpMethod(HttpMethod httpMethod) {

        switch (httpMethod.toString()) {
            case HttpMethodEnum.GET:
                return HttpMethod.GET;
            case HttpMethodEnum.PUT:
                return HttpMethod.PUT;
            case HttpMethodEnum.DELETE:
                return HttpMethod.DELETE;
            default:
                return HttpMethod.POST;
        }
    }

}
