package com.example.test.lambdaauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ipv6Prefix {
    @JsonProperty("ipv6_prefix")
    private String ipv6Prefix;
    @JsonProperty("region")
    private String region;
    @JsonProperty("service")
    private String service;
}
