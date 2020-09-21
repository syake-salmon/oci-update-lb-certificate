# oci-update-lb-certificate

![Build](https://github.com/syake-salmon/oci-update-lb-certificate/workflows/CI/badge.svg) | [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=syake-salmon_oci-update-lb-certificate&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=syake-salmon_oci-update-lb-certificate) | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=syake-salmon_oci-update-lb-certificate&metric=security_rating)](https://sonarcloud.io/dashboard?id=syake-salmon_oci-update-lb-certificate) | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=syake-salmon_oci-update-lb-certificate&metric=coverage)](https://sonarcloud.io/dashboard?id=syake-salmon_oci-update-lb-certificate)

*oci-update-lb-certificate is a utility CUI application which support auto-updating SSL certificate on OCI load balancer.*

日本語は[こちら](README_ja.md)
<hr />

## Requirements
- OCI CLI: v2.12.11 or higher
- Java: 1.8.0_265 or higher

## How-to-Get
There are 2 options.

- Exec `mvn package -DskipTests=true`.
- Download binary from [here](https://github.com/syake-salmon/oci-update-lb-certificate/releases).

## How-to-Use
1. Place the JAR(-with-dependencies) anywhere you want.
2. Run the JAR with `java -jar` command.

### Command-Line Arguments
You can check them, by `-h` option.

#### Required
- --certificate-name <TEXT> : A friendly name for the certificate bundle.
- --listener-name <TEXT> : The name of the listener to update.
- --load-balancer-id <TEXT> : The OCID of load balancer on which to add the certificate bundle.

#### Optional
- --ca-certificate-file <FILENAME> : The Certificate Authority certificate, or any interim certificate, that you received from your SSL certificate provider.
- --config-file <FILENAME> : The path to the OCI-CLI config file. (default: ~/.oci/config)
- --max-wait-seconds <INTEGER> : The maximum time to wait for the work request. (default: 1200)
- --passphrase <TEXT> : A passphrase for encrypted private keys. This is needed only if you created your certificate with a passphrase.
- --private-key-file <FILENAME> : The SSL private key for your certificate, in PEM format.
- --profile <TEXT> : The profile in the config file to load. (default: DEFAULT)
- --public-certificate-file <FILENAME> : The public certificate, in PEM format, that you received from your SSL certificate provider.
- --wait-interval-seconds <INTEGER>: Check every --wait-interval-seconds to see whether the work request to see if it has done. (default: 30)
- -d or --debug : For debug level log output.
- -h or --help  : print how-to-use and exit
