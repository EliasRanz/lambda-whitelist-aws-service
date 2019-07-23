package com.example.lambdaauth;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.String.format;

@Slf4j
public class IPAdressAuthorizer implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {
    private static HttpURLConnection con;
    public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
        try {

            URL ipranges = new URL("https://ip-ranges.amazonaws.com/ip-ranges.json");
            con = (HttpURLConnection) ipranges.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            AwsIpRange ipRange = mapper.readValue(content.toString(), AwsIpRange.class);
            String awsService = System.getenv("AWS_SERVICE");
            log.debug(format("Searching for %s", awsService));
            for(Ipv4Prefix ipv4Prefix : ipRange.getPrefixes()) {
                if(ipv4Prefix.getService().equals(awsService)) {
                    String ip = ipv4Prefix.getIpPrefix();
                    IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ip);
                    boolean matches = ipAddressMatcher.matches(request.getRequestContext().getIdentity().getSourceIp());
                    if(matches) {
                        return new ApiGatewayProxyResponse(200, null, format("IP (%s) is in the range of %s",
                                request.getRequestContext().getIdentity().getSourceIp(), ipv4Prefix.getIpPrefix()));
                    }
                }
            }

        } catch (Exception e) {
            log.error("Failed to get IP Addresses.",e);
        } finally {

            con.disconnect();
        }
        log.debug(request.getRequestContext().getIdentity().getSourceIp());
        return new ApiGatewayProxyResponse(403, null, "IP is not a valid whitelisted IP...");
    }
}
