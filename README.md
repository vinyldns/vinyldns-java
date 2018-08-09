# vinyldns-java
Java client for working with VinylDNS


## Add to your maven project
### Dependency
```xml
<dependency>
  <groupId>vinyldns</groupId>
  <artifactId>vinyldns-java</artifactId>
  <version>8.0.0</version> <!-- Replace by the latest release version -->
</dependency>
```
## Usage
```java
AWSCredentials credentials = new BasicAWSCredentials("key", "secret");
VinylDNSClientConfig config = new VinylDNSClientConfig("https://path.to.your.vinyldns", credentials);

VinylDNSClient client = new VinylDNSClientImpl(config);

VinylDNSResponse<ListZonesResponse> listZonesResponse = client.listZones(new ListZonesRequest());

```

See [VinylDNSClient interface](src/main/java/vinyldns/java/VinylDNSClient.java) to get more methods
