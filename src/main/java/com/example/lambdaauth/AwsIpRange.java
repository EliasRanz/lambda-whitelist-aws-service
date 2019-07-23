package com.example.lambdaauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AwsIpRange {
    @JsonProperty("syncToken")
    private String syncToken;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("prefixes")
    private List<Ipv4Prefix> prefixes;
    @JsonProperty("ipv6_prefixes")
    private List<Ipv6Prefix> ipv6Prefixes;
}
