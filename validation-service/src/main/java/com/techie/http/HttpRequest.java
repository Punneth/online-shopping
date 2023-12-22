package com.techie.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpRequest {

    private String url;
    private String request;
    private HttpMethod httpMethod;
}
