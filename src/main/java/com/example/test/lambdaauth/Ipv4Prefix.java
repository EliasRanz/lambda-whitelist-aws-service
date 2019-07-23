package com.example.test.lambdaauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Ipv4Prefix {
    @JsonProperty("ip_prefix")
    private String ipPrefix;
    @JsonProperty("region")
    private String region;
    @JsonProperty("service")
    private String service;
}
