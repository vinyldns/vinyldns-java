# vinyldns-java
Java client for working with VinylDNS


## Add to your maven project
### Dependency
To pull the latest version from Sonatype, add a dependency to the `pom.xml` file corresponding to the [Sonatype version](https://oss.sonatype.org/#nexus-search;quick~vinyldns.io) you want to use:
```xml
<dependency>
  <groupId>vinyldns</groupId>
  <artifactId>vinyldns-java</artifactId>
  <version>0.7.1</version> <!-- Replace with the latest release version -->
</dependency>
```

To pull in a local package of `vinyldns-java` as a Maven dependency, the project needs to be published to your local Maven repository by running the following command from the project root folder: 
```bash
mvn clean install
``` 

## Usage
```java
AWSCredentials credentials = new BasicAWSCredentials("key", "secret");
VinylDNSClientConfig config = new VinylDNSClientConfig("https://path.to.your.vinyldns", credentials);

VinylDNSClient client = new VinylDNSClientImpl(config);

VinylDNSResponse<ListZonesResponse> listZonesResponse = client.listZones(new ListZonesRequest());

```

See [VinylDNSClient interface](src/main/java/vinyldns/java/VinylDNSClient.java) to get more methods

## Testing

`vinyldns-java` has a suite of unit tests to run to test expected behavior. To run the suite of tests, run the following command from the project root folder:
```bash
mvn test
```
