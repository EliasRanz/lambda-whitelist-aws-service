# introduction
Checks https://ip-ranges.amazonaws.com/ip-ranges.json for a list of AWS IP Ranges for a service and then validates the request came from that source.

Expects you to set the `AWS_SERVICE` environment variable.


endpoint: `com.example.lambdaauth.IPAdressAuthorizer::handleRequest`

to build the zip: `./gradlew buildZip`
The zip is then ready to be deployed as a lambda. `<REPO_HOME>/build/distributions/route53-health-check-apigateway-whitelist-lambda-0.0.1-SNAPSHOT.zip`