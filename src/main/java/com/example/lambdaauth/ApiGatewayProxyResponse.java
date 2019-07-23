package com.example.lambdaauth;

import lombok.Data;

import java.util.Map;

/*
    Response class for an API Gateway proxied lambda
    Must conform to these three properties as per AWS docs
    https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-set-up-simple-proxy.html#api-gateway-simple-proxy-for-lambda-output-format
    Headers map will be merged with whichever header AWS sets, e.g. X-Amzn-Trace-Id
 */

@Data
public class ApiGatewayProxyResponse {

    private int statusCode;
    private Map<String, String> headers;
    private String body;

    public ApiGatewayProxyResponse(int statusCode, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }
}
