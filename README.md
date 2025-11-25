[![Travis build](https://api.travis-ci.org/vinyldns/vinyldns-java.svg?branch=master)](https://travis-ci.org/vinyldns/vinyldns-java)
![GitHub](https://img.shields.io/github/license/vinyldns/vinyldns-java)

# vinyldns-java

Java client for working with VinylDNS

## Add to your maven project

### Dependency

To pull the latest version from Sonatype, add a dependency to the `pom.xml` file corresponding to the [Sonatype version](https://oss.sonatype.org/#nexus-search;quick~vinyldns.io) you want to use:

```xml
<dependency>
  <groupId>io.vinyldns</groupId>
  <artifactId>vinyldns-java</artifactId>
  <version>0.9.5</version> <!-- Replace with the latest release version -->
</dependency>
```

To pull in a local package of `vinyldns-java` as a Maven dependency, the project needs to be published to your local Maven repository by running the following command from the project root folder: 

```bash
mvn clean install
```

## Usage

```java
AWSCredentials credentials = new BasicAWSCredentials("my-key", "my-secret");
VinylDNSClientConfig config = new VinylDNSClientConfig("https://my-vinyldns-api.com", credentials);

VinylDNSClient client = new VinylDNSClientImpl(config);

VinylDNSResponse<ListZonesResponse> listZonesResponse = client.listZones(new ListZonesRequest());
```

### Use with `VINYLDNS_*` environment variables

Alternatively, a `VinylDNSClient` can be instantiated via `VINYLDNS_API_URL`, `VINYLDNS_ACCESS_KEY_ID`, and `VINYLDNS_SECRET_ACCESS_KEY` environment variables. For example:

```bash
export VINYLDNS_API_URL=https://my-vinyldns-api.com
export VINYLDNS_ACCESS_KEY_ID="my-key"
export VINYLDNS_SECRET_ACCESS_KEY="my-secret"
```

Instantiate a client using the `VINYLDNS_*` environment variables:

```java
VinylDNSClient client = new VinylDNSClientImpl();

VinylDNSResponse<ListZonesResponse> listZonesResponse = client.listZones(new ListZonesRequest());
```

See [VinylDNSClient interface](src/main/java/vinyldns/java/VinylDNSClient.java) to get more methods

## Testing

`vinyldns-java` has a suite of unit tests to run to test expected behavior. To run the suite of tests, run the following command from the project root folder:
```bash
mvn test
```
