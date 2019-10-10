/**
 * Copyright 2018 Comcast Cable Communications Management, LLC
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vinyldns.java;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.vinyldns.java.model.membership.GroupStatus.Active;
import static org.testng.Assert.*;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.model.Record;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import io.vinyldns.java.model.acl.ACLRule;
import io.vinyldns.java.model.acl.AccessLevel;
import io.vinyldns.java.model.batch.*;
import io.vinyldns.java.model.membership.*;
import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.AData;
import io.vinyldns.java.model.record.data.RecordData;
import io.vinyldns.java.model.record.set.*;
import io.vinyldns.java.model.zone.*;
import io.vinyldns.java.responses.ResponseMarker;
import io.vinyldns.java.responses.VinylDNSResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.joda.time.DateTime;
import org.testng.annotations.*;

public class VinylDNSClientTest {
  private WireMockServer wireMockServer;
  private VinylDNSClientImpl client;

  @BeforeSuite
  public void beforeAll() {
    wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    wireMockServer.start();

    client =
        new VinylDNSClientImpl(
            new VinylDNSClientConfig(
                "http://localhost:" + wireMockServer.port(),
                new BasicAWSCredentials("fake", "fake")));
  }

  @AfterSuite
  public void afterAll() {
    wireMockServer.shutdown();
  }

  @AfterTest
  public void beforeEach() {
    wireMockServer.resetAll();
  }

  @Test
  public void listZonesSuccessWithParams() {
    String response = client.gson.toJson(listZonesResponse);

    String nameFilter = "someFilter";
    String startFrom = "someStart";
    int maxItems = 55;

    wireMockServer.stubFor(
        get(urlMatching("/zones?(.*)"))
            .withQueryParam("nameFilter", equalTo(nameFilter))
            .withQueryParam("startFrom", equalTo(startFrom))
            .withQueryParam("maxItems", equalTo(String.valueOf(maxItems)))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListZonesResponse> vinylDNSResponse =
        client.listZones(new ListZonesRequest(nameFilter, startFrom, maxItems));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listZonesResponse);
  }

  @Test
  public void listZonesSuccess() {
    String response = client.gson.toJson(listZonesResponse);

    wireMockServer.stubFor(
        get(urlEqualTo("/zones"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListZonesResponse> vinylDNSResponse = client.listZones(new ListZonesRequest());

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listZonesResponse);
  }

  @Test
  public void listZonesFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones")).willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<ListZonesResponse> vinylDNSResponse = client.listZones(new ListZonesRequest());

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void createZoneSuccess() {
    Zone newZone =
        new Zone(
            "newid",
            "name",
            "some@company.com",
            ZoneStatus.Active,
            new DateTime(),
            new DateTime(),
            testZoneConnection1,
            null,
            true,
            testZoneAcl1,
            "group",
            null,
            true,
            "somebackId");

    ZoneResponse response =
        new ZoneResponse(
            newZone,
            "someUserId",
            ZoneChangeType.Create,
            ZoneChangeStatus.Pending,
            new DateTime(),
            "",
            "1234");

    wireMockServer.stubFor(
        post(urlEqualTo("/zones"))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withBody(client.gson.toJson(newZone))
                    .withHeader("Content-Type", "application/json")
                    .withBody(client.gson.toJson(response))));

    VinylDNSResponse<ZoneResponse> vinylDNSResponse = client.createZone(newZone);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertEquals(vinylDNSResponse.getValue().getZone(), newZone);
  }

  @Test
  public void getZoneSuccess() {
    String response = client.gson.toJson(new GetZoneResponse(testZone1));

    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + testZone1.getId()))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<GetZoneResponse> vinylDNSResponse =
        client.getZone(new ZoneRequest(testZone1.getId()));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue().getZone(), testZone1);
  }

  @Test
  public void getZoneFailure404() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<GetZoneResponse> vinylDNSResponse = client.getZone(new ZoneRequest(zoneId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void getZoneByNameSuccess() {
    String response = client.gson.toJson(new GetZoneResponse(testZone1));

    wireMockServer.stubFor(
        get(urlEqualTo("/zones/name/" + testZone1.getName()))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<GetZoneResponse> vinylDNSResponse = client.getZoneByName(testZone1.getName());

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue().getZone(), testZone1);
  }

  @Test
  public void getZoneByNameFailure404() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/name/no-existo"))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<GetZoneResponse> vinylDNSResponse = client.getZoneByName("no-existo");

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void updateZoneSuccess() {
    Zone newZone =
        new Zone(
            "newid",
            "name123",
            "some@company.com",
            ZoneStatus.Active,
            new DateTime(),
            new DateTime(),
            testZoneConnection1,
            null,
            true,
            testZoneAcl1,
            "group",
            null,
            true,
            "somebackId");

    ZoneResponse response =
        new ZoneResponse(
            newZone,
            "someUserId",
            ZoneChangeType.Update,
            ZoneChangeStatus.Pending,
            new DateTime(),
            "",
            "1234");

    wireMockServer.stubFor(
        put(urlEqualTo("/zones/" + newZone.getId()))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withBody(client.gson.toJson(newZone))
                    .withHeader("Content-Type", "application/json")
                    .withBody(client.gson.toJson(response))));

    VinylDNSResponse<ZoneResponse> vinylDNSResponse = client.updateZone(newZone);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertEquals(vinylDNSResponse.getValue().getZone(), newZone);
  }

  @Test
  public void deleteZoneSuccess() {
    ZoneResponse response =
        new ZoneResponse(
            testZone1,
            "someUserId",
            ZoneChangeType.Delete,
            ZoneChangeStatus.Pending,
            new DateTime(),
            "",
            "1234");

    wireMockServer.stubFor(
        delete(urlEqualTo("/zones/" + testZone1.getId()))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(client.gson.toJson(response))));

    VinylDNSResponse<ZoneResponse> vinylDNSResponse =
        client.deleteZone(new ZoneRequest(testZone1.getId()));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertEquals(vinylDNSResponse.getValue().getZone(), testZone1);
  }

  @Test
  public void listZoneChangesSuccess() {

    String startFrom = "someStart";
    int maxItems = 55;
    List<ZoneResponse> zoneChanges =
        Collections.singletonList(
            new ZoneResponse(
                testZone1,
                "someUserId",
                ZoneChangeType.Update,
                ZoneChangeStatus.Pending,
                new DateTime(),
                "",
                "1234"));

    ListZoneChangesResponse listZoneChangesResponse =
        new ListZoneChangesResponse(zoneChanges, startFrom, "nextId", maxItems);

    wireMockServer.stubFor(
        get(urlMatching("/zones/" + testZone1.getId() + "/changes?(.*)"))
            .withQueryParam("startFrom", equalTo(startFrom))
            .withQueryParam("maxItems", equalTo(String.valueOf(maxItems)))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(client.gson.toJson(listZoneChangesResponse))));

    VinylDNSResponse<ListZoneChangesResponse> vinylDNSResponse =
        client.listZoneChanges(new ListZoneChangesRequest(testZone1.getId(), startFrom, maxItems));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue().getZoneChanges(), zoneChanges);
  }

  @Test
  public void syncZoneSuccess() {

    ZoneResponse response =
        new ZoneResponse(
            testZone1,
            "someUserId",
            ZoneChangeType.Create,
            ZoneChangeStatus.Pending,
            new DateTime(),
            "",
            "1234");

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/" + testZone1.getId() + "/sync"))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(client.gson.toJson(response))));

    VinylDNSResponse<ZoneResponse> vinylDNSResponse =
        client.syncZone(new ZoneRequest(testZone1.getId()));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertEquals(vinylDNSResponse.getValue().getZone(), testZone1);
  }

  @Test
  public void listRecordSetsSuccessWithParams() {
    String response = client.gson.toJson(listRecordSetsResponse);

    String recordNameFilter = "someFilter";
    String startFrom = "someStart";
    int maxItems = 55;

    wireMockServer.stubFor(
        get(urlMatching("/zones/" + zoneId + "/recordsets?(.*)"))
            .withQueryParam("recordNameFilter", equalTo(recordNameFilter))
            .withQueryParam("startFrom", equalTo(startFrom))
            .withQueryParam("maxItems", equalTo(String.valueOf(maxItems)))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListRecordSetsResponse> vinylDNSResponse =
        client.listRecordSets(
            new ListRecordSetsRequest(zoneId, recordNameFilter, startFrom, maxItems));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listRecordSetsResponse);
  }

  @Test
  public void listRecordSetsSuccess() {
    String response = client.gson.toJson(listRecordSetsResponse);

    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsets"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListRecordSetsResponse> vinylDNSResponse =
        client.listRecordSets(new ListRecordSetsRequest(zoneId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listRecordSetsResponse);
  }

  @Test
  public void listRecordSetsFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsets"))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<ListRecordSetsResponse> vinylDNSResponse =
        client.listRecordSets(new ListRecordSetsRequest(zoneId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void getRecordSetSuccess() {
    GetRecordSetResponse getRecordSetResponse = new GetRecordSetResponse(recordSet);
    String response = client.gson.toJson(getRecordSetResponse);

    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSetId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<GetRecordSetResponse> vinylDNSResponse =
        client.getRecordSet(getRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), getRecordSetResponse);
  }

  @Test
  public void getRecordSetFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSetId))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<GetRecordSetResponse> vinylDNSResponse =
        client.getRecordSet(getRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void getRecordSetFailure404() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSetId))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<GetRecordSetResponse> vinylDNSResponse =
        client.getRecordSet(getRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void deleteRecordSetsSuccess() {
    String response = client.gson.toJson(recordSetChangeDelete);

    wireMockServer.stubFor(
        delete(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSetId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.deleteRecordSet(new DeleteRecordSetRequest(zoneId, recordSetId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), recordSetChangeDelete);
  }

  @Test
  public void deleteRecordSetsFailure() {
    wireMockServer.stubFor(
        delete(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSetId))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.deleteRecordSet(new DeleteRecordSetRequest(zoneId, recordSetId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void deleteRecordSetsFailure404() {
    wireMockServer.stubFor(
        delete(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSetId))
            .willReturn(aResponse().withStatus(404).withBody("server error")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.deleteRecordSet(new DeleteRecordSetRequest(zoneId, recordSetId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void createRecordSetsSuccess() {
    String request = client.gson.toJson(createRecordSetRequest);
    String response = client.gson.toJson(recordSetChangeCreate);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/" + zoneId + "/recordsets"))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.createRecordSet(createRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertTrue(vinylDNSResponse.getMessageBody().contains(ownerGroupId));
    assertEquals(vinylDNSResponse.getValue(), recordSetChangeCreate);
  }

  @Test
  public void createRecordSetsFailure() {
    String request = client.gson.toJson(createRecordSetRequest);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/" + zoneId + "/recordsets"))
            .willReturn(aResponse().withBody(request).withStatus(500).withBody("server error")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.createRecordSet(createRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void createRecordSetsFailure409() {
    String request = client.gson.toJson(createRecordSetRequest);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/" + zoneId + "/recordsets"))
            .willReturn(aResponse().withBody(request).withStatus(409).withBody("server error")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.createRecordSet(createRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 409);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void createRecordSetsNoOwner() {
    String request = client.gson.toJson(createRecordSetRequestNoOwner);
    String response = client.gson.toJson(recordSetChangeCreateNoOwner);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/" + zoneId + "/recordsets"))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.createRecordSet(createRecordSetRequestNoOwner);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertFalse(vinylDNSResponse.getMessageBody().contains("ownerGroupId"));
    assertEquals(vinylDNSResponse.getValue(), recordSetChangeCreateNoOwner);
  }

  @Test
  public void updateRecordSetsSuccess() {
    String request = client.gson.toJson(updateRecordSetRequest);
    String response = client.gson.toJson(recordSetChangeUpdate);

    wireMockServer.stubFor(
        put(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSet.getId()))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.updateRecordSet(updateRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertEquals(vinylDNSResponse.getValue(), recordSetChangeUpdate);
  }

  @Test
  public void updateRecordSetsFailure() {
    String request = client.gson.toJson(updateRecordSetRequest);

    wireMockServer.stubFor(
        put(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSet.getId()))
            .willReturn(
                aResponse()
                    .withStatus(500)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody("server error")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.updateRecordSet(updateRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void updateRecordSetsFailure404() {
    String request = client.gson.toJson(updateRecordSetRequest);

    wireMockServer.stubFor(
        put(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSet.getId()))
            .willReturn(
                aResponse()
                    .withStatus(404)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody("not found")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.updateRecordSet(updateRecordSetRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void updateRecordSetsNoOwner() {
    String request = client.gson.toJson(updateRecordSetRequestNoOwner);
    String response = client.gson.toJson(recordSetChangeUpdateNoOwner);

    wireMockServer.stubFor(
        put(urlEqualTo("/zones/" + zoneId + "/recordsets/" + recordSet.getId()))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.updateRecordSet(updateRecordSetRequestNoOwner);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 202);
    assertEquals(vinylDNSResponse.getValue(), recordSetChangeUpdateNoOwner);
    assertFalse(vinylDNSResponse.getMessageBody().contains("ownerGroupId"));
  }

  @Test
  public void getRecordSetChangeSuccess() {
    String response = client.gson.toJson(recordSetChangeCreate);

    wireMockServer.stubFor(
        get(urlEqualTo(
                "/zones/"
                    + zoneId
                    + "/recordsets/"
                    + recordSetId
                    + "/changes/"
                    + recordSetChangeId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.getRecordSetChange(getRecordSetChangeRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), recordSetChangeCreate);
  }

  @Test
  public void getRecordSetChangeFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo(
                "/zones/"
                    + zoneId
                    + "/recordsets/"
                    + recordSetId
                    + "/changes/"
                    + recordSetChangeId))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.getRecordSetChange(getRecordSetChangeRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void getRecordSetChangeFailure404() {
    wireMockServer.stubFor(
        get(urlEqualTo(
                "/zones/"
                    + zoneId
                    + "/recordsets/"
                    + recordSetId
                    + "/changes/"
                    + recordSetChangeId))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<RecordSetChange> vinylDNSResponse =
        client.getRecordSetChange(getRecordSetChangeRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void getGroupSuccess() {
    String response = client.gson.toJson(group);

    wireMockServer.stubFor(
        get(urlEqualTo("/groups/" + groupId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<Group> vinylDNSResponse = client.getGroup(getGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), group);
  }

  @Test
  public void getGroupFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/groups/" + groupId))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<Group> vinylDNSResponse = client.getGroup(getGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void getGroupFailure404() {
    wireMockServer.stubFor(
        get(urlEqualTo("/groups/" + groupId))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<Group> vinylDNSResponse = client.getGroup(getGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void createGroupSuccess() {
    String request = client.gson.toJson(createGroupRequest);
    String response = client.gson.toJson(group);

    wireMockServer.stubFor(
        post(urlEqualTo("/groups"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<Group> vinylDNSResponse = client.createGroup(createGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), group);
  }

  @Test
  public void createGroupFailure() {
    String request = client.gson.toJson(createGroupRequest);

    wireMockServer.stubFor(
        post(urlEqualTo("/groups"))
            .willReturn(aResponse().withBody(request).withStatus(500).withBody("server error")));

    VinylDNSResponse<Group> vinylDNSResponse = client.createGroup(createGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void createGroupFailure409() {
    String request = client.gson.toJson(createGroupRequest);

    wireMockServer.stubFor(
        post(urlEqualTo("/groups"))
            .willReturn(aResponse().withBody(request).withStatus(409).withBody("conflict")));

    VinylDNSResponse<Group> vinylDNSResponse = client.createGroup(createGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 409);
    assertEquals(vinylDNSResponse.getMessageBody(), "conflict");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void updateGroupSuccess() {
    String request = client.gson.toJson(updateGroupRequest);
    String response = client.gson.toJson(group);

    wireMockServer.stubFor(
        put(urlEqualTo("/groups/" + groupId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withBody(request)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<Group> vinylDNSResponse = client.updateGroup(updateGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), group);
  }

  @Test
  public void deleteGroupSuccess() {
    String response = client.gson.toJson(group);

    wireMockServer.stubFor(
        delete(urlEqualTo("/groups/" + groupId))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<Group> vinylDNSResponse = client.deleteGroup(deleteGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), group);
  }

  @Test
  public void deleteGroupFailure() {
    wireMockServer.stubFor(
        delete(urlEqualTo("/groups/" + groupId))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<Group> vinylDNSResponse = client.deleteGroup(deleteGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void deleteGroupFailure404() {
    wireMockServer.stubFor(
        delete(urlEqualTo("/groups/" + groupId))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<Group> vinylDNSResponse = client.deleteGroup(deleteGroupRequest);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void listGroupsSuccessWithParams() {
    String response = client.gson.toJson(listGroupsResponse);

    String groupNameFilter = listGroupsResponse.getGroupNameFilter();
    String startFrom = listGroupsResponse.getStartFrom();
    int maxItems = listGroupsResponse.getMaxItems();

    wireMockServer.stubFor(
        get(urlMatching("/groups?(.*)"))
            .withQueryParam("groupNameFilter", equalTo(groupNameFilter))
            .withQueryParam("startFrom", equalTo(startFrom))
            .withQueryParam("maxItems", equalTo(String.valueOf(maxItems)))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListGroupsResponse> vinylDNSResponse =
        client.listGroups(new ListGroupsRequest(groupNameFilter, startFrom, maxItems));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listGroupsResponse);
  }

  @Test
  public void listGroupsSuccess() {
    String response = client.gson.toJson(listGroupsResponse);

    wireMockServer.stubFor(
        get(urlEqualTo("/groups"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListGroupsResponse> vinylDNSResponse =
        client.listGroups(new ListGroupsRequest());

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listGroupsResponse);
  }

  @Test
  public void listGroupsFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/groups"))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<ListGroupsResponse> vinylDNSResponse =
        client.listGroups(new ListGroupsRequest());

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void listAdminsSuccess() {
    ListAdminsResponse listAdminsResponse = new ListAdminsResponse(adminUserInfo);
    String response = client.gson.toJson(listAdminsResponse);
    wireMockServer.stubFor(
        get(urlEqualTo("/groups/" + groupId + "/admins"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListAdminsResponse> vinylDNSResponse = client.listAdmins(groupId);

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listAdminsResponse);
  }

  @Test
  public void listMembersSuccessWithParams() {
    String response = client.gson.toJson(listMembersResponse);

    String startFrom = listMembersResponse.getStartFrom();
    int maxItems = listMembersResponse.getMaxItems();

    wireMockServer.stubFor(
        get(urlMatching("/groups/" + groupId + "/members?(.*)"))
            .withQueryParam("startFrom", equalTo(startFrom))
            .withQueryParam("maxItems", equalTo(String.valueOf(maxItems)))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListMembersResponse> vinylDNSResponse =
        client.listMembers(new ListMembersRequest(groupId, startFrom, maxItems));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listMembersResponse);
  }

  @Test
  public void listMembersSuccess() {
    String response = client.gson.toJson(listMembersResponse);

    wireMockServer.stubFor(
        get(urlEqualTo("/groups/" + groupId + "/members"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListMembersResponse> vinylDNSResponse =
        client.listMembers(new ListMembersRequest(groupId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listMembersResponse);
  }

  @Test
  public void listMembersFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/groups/" + groupId + "/members"))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<ListMembersResponse> vinylDNSResponse =
        client.listMembers(new ListMembersRequest(groupId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void listGroupActivitySuccessWithParams() {
    String response = client.gson.toJson(listGroupActivityResponse);

    String startFrom = listGroupActivityResponse.getStartFrom();
    int maxItems = listGroupActivityResponse.getMaxItems();

    wireMockServer.stubFor(
        get(urlMatching("/groups/" + groupId + "/activity?(.*)"))
            .withQueryParam("startFrom", equalTo(startFrom))
            .withQueryParam("maxItems", equalTo(String.valueOf(maxItems)))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListGroupActivityResponse> vinylDNSResponse =
        client.listGroupActivity(new ListGroupActivityRequest(groupId, startFrom, maxItems));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listGroupActivityResponse);
  }

  @Test
  public void createBatchChangeSuccess() {

    List<ChangeInput> changes = new ArrayList<>();
    AData adata = new AData("1.2.3.4");
    AddChangeInput addInput = new AddChangeInput("www.example.com", RecordType.A, 300L, adata);
    DeleteRecordSetChangeInput deleteInput =
        new DeleteRecordSetChangeInput("www.example.com", RecordType.A);
    DeleteRecordSetChangeInput deleteInputWithData =
        new DeleteRecordSetChangeInput("foo.example.com", RecordType.A, adata);
    changes.add(addInput);
    changes.add(deleteInput);
    changes.add(deleteInputWithData);

    CreateBatchRequest batchRequest = new CreateBatchRequest(changes);

    AddSingleChange addSingleChange = new AddSingleChange();
    addSingleChange.setChangeType(ChangeInputType.Add);
    addSingleChange.setId("1234");
    addSingleChange.setInputName("testString");
    addSingleChange.setRecord(adata);
    addSingleChange.setRecordChangeId("1111");
    addSingleChange.setRecordName("testName");
    addSingleChange.setRecordSetId("testId");
    addSingleChange.setZoneId("testZone");
    addSingleChange.setZoneName("testZoneName");
    addSingleChange.setType(RecordType.A);
    addSingleChange.setSystemMessage("testMessage");
    addSingleChange.setStatus(SingleChangeStatus.Complete);

    DeleteRecordSetSingleChange deleteSingleChange = new DeleteRecordSetSingleChange();
    deleteSingleChange.setChangeType(ChangeInputType.DeleteRecordSet);
    deleteSingleChange.setId("1234");
    deleteSingleChange.setInputName("testString");
    deleteSingleChange.setRecordChangeId("1111");
    deleteSingleChange.setRecordName("testName");
    deleteSingleChange.setRecordSetId("testId");
    deleteSingleChange.setZoneId("testZone");
    deleteSingleChange.setZoneName("testZoneName");
    deleteSingleChange.setType(RecordType.A);
    deleteSingleChange.setSystemMessage("testMessage");
    deleteSingleChange.setStatus(SingleChangeStatus.Complete);

    DeleteRecordSetSingleChange deleteSingleChangeWithData = new DeleteRecordSetSingleChange();
    deleteSingleChangeWithData.setChangeType(ChangeInputType.DeleteRecordSet);
    deleteSingleChangeWithData.setId("1234");
    deleteSingleChangeWithData.setInputName("testString");
    deleteSingleChangeWithData.setRecordChangeId("1111");
    deleteSingleChangeWithData.setRecordName("testName");
    deleteSingleChangeWithData.setRecordSetId("testId");
    deleteSingleChangeWithData.setZoneId("testZone");
    deleteSingleChangeWithData.setZoneName("testZoneName");
    deleteSingleChangeWithData.setType(RecordType.A);
    deleteSingleChangeWithData.setRecord(adata);
    deleteSingleChangeWithData.setSystemMessage("testMessage");
    deleteSingleChangeWithData.setStatus(SingleChangeStatus.Complete);

    List<SingleChange> singleChangeList = new ArrayList<>();
    singleChangeList.add(addSingleChange);
    singleChangeList.add(deleteSingleChange);
    singleChangeList.add(deleteSingleChangeWithData);

    BatchResponse batchResponse = new BatchResponse();
    batchResponse.setId("1234");
    batchResponse.setUserId("testUserId");
    batchResponse.setUserName("testUserName");
    batchResponse.setComments("testComments");
    batchResponse.setCreatedTimestamp(new Date());
    batchResponse.setChanges(singleChangeList);
    batchResponse.setStatus(BatchChangeStatus.Complete);
    batchResponse.setApprovalStatus(BatchChangeApprovalStatus.AutoApproved);
    batchResponse.setOwnerGroupId(ownerGroupId);
    batchResponse.setOwnerGroupName("testOwnerGroupName");

    String request = client.gson.toJson(batchRequest);
    String response = client.gson.toJson(batchResponse);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges"))
            .withRequestBody(new EqualToJsonPattern(request, true, true))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<BatchResponse> vinylDNSBatchResponse = client.createBatchChanges(batchRequest);

    assertEquals(vinylDNSBatchResponse.getStatusCode(), 202);
  }

  @Test
  public void createScheduledBatchChangeSuccess() {

    List<ChangeInput> changes = new ArrayList<>();
    AData adata = new AData("1.2.3.4");
    AddChangeInput addInput = new AddChangeInput("www.example.com", RecordType.A, 300L, adata);
    changes.add(addInput);

    CreateBatchRequest batchRequest = new CreateBatchRequest(changes);

    Instant scheduledTime = Instant.now().plus(5, ChronoUnit.SECONDS);
    batchRequest.setScheduledTime(scheduledTime);

    AddSingleChange addSingleChange = new AddSingleChange();
    addSingleChange.setChangeType(ChangeInputType.Add);
    addSingleChange.setId("1234");
    addSingleChange.setInputName("testString");
    addSingleChange.setRecord(adata);
    addSingleChange.setType(RecordType.A);
    addSingleChange.setStatus(SingleChangeStatus.NeedsReview);

    List<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(
        new ValidationError(SingleChangeError.ZoneDiscoveryError, "Zone does not exist"));
    addSingleChange.setValidationErrors(validationErrors);

    List<SingleChange> singleChangeList = new ArrayList<>();
    singleChangeList.add(addSingleChange);

    BatchResponse batchResponse = new BatchResponse();
    batchResponse.setId("1234");
    batchResponse.setUserId("testUserId");
    batchResponse.setUserName("testUserName");
    batchResponse.setComments("testComments");
    batchResponse.setCreatedTimestamp(new Date());
    batchResponse.setChanges(singleChangeList);
    batchResponse.setStatus(BatchChangeStatus.Scheduled);
    batchResponse.setApprovalStatus(BatchChangeApprovalStatus.PendingReview);
    batchResponse.setOwnerGroupId(ownerGroupId);
    batchResponse.setOwnerGroupName("testOwnerGroupName");

    String request = client.gson.toJson(batchRequest);
    String response = client.gson.toJson(batchResponse);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges"))
            .withRequestBody(new EqualToJsonPattern(request, true, true))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<BatchResponse> vinylDNSBatchResponse = client.createBatchChanges(batchRequest);

    assertEquals(vinylDNSBatchResponse.getStatusCode(), 202);
  }

  @Test
  public void approveBatchChangeSuccess() {
    List<SingleChange> singleChangeList = new ArrayList<>();

    AddSingleChange addSingleChange = new AddSingleChange();
    addSingleChange.setChangeType(ChangeInputType.Add);
    addSingleChange.setId("1234");
    addSingleChange.setInputName("testString");
    addSingleChange.setRecord(new AData("1.2.3.4"));
    addSingleChange.setType(RecordType.A);
    addSingleChange.setStatus(SingleChangeStatus.Pending);
    singleChangeList.add(addSingleChange);

    BatchResponse batchResponse = new BatchResponse();
    batchResponse.setId("1234");
    batchResponse.setUserId("testuser");
    batchResponse.setUserName("testuser");
    batchResponse.setCreatedTimestamp(new Date());
    batchResponse.setChanges(singleChangeList);
    batchResponse.setStatus(BatchChangeStatus.PendingProcessing);
    batchResponse.setApprovalStatus(BatchChangeApprovalStatus.ManuallyApproved);
    batchResponse.setOwnerGroupId("someOwnerGroupId");
    batchResponse.setReviewerId("reviewerId");
    String reviewComment = "some approval comment";
    batchResponse.setReviewComment(reviewComment);
    batchResponse.setReviewTimestamp(new Date());

    String request = client.gson.toJson(new BatchChangeReview(reviewComment));
    String response = client.gson.toJson(batchResponse);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges/" + batchResponse.getId() + "/approve"))
            .withRequestBody(new EqualToJsonPattern(request, true, true))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<BatchResponse> vinylDNSBatchResponse =
        client.approveBatchChanges("1234", reviewComment);

    assertEquals(vinylDNSBatchResponse.getStatusCode(), 202);
  }

  @Test
  public void approveBatchChangeFailure() {
    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges/noExisto/approve"))
            .willReturn(aResponse().withStatus(404).withBody("Not found")));

    VinylDNSResponse<BatchResponse> vinylDNSResponse = client.approveBatchChanges("noExisto");

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "Not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void rejectBatchChangeSuccess() {
    List<SingleChange> singleChangeList = new ArrayList<>();

    AddSingleChange addSingleChange = new AddSingleChange();
    addSingleChange.setChangeType(ChangeInputType.Add);
    addSingleChange.setId("1234");
    addSingleChange.setInputName("testString");
    addSingleChange.setRecord(new AData("1.2.3.4"));
    addSingleChange.setType(RecordType.A);
    addSingleChange.setStatus(SingleChangeStatus.Rejected);
    singleChangeList.add(addSingleChange);

    BatchResponse batchResponse = new BatchResponse();
    batchResponse.setId("1234");
    batchResponse.setUserId("testuser");
    batchResponse.setUserName("testuser");
    batchResponse.setCreatedTimestamp(new Date());
    batchResponse.setChanges(singleChangeList);
    batchResponse.setStatus(BatchChangeStatus.Rejected);
    batchResponse.setApprovalStatus(BatchChangeApprovalStatus.ManuallyRejected);
    batchResponse.setOwnerGroupId("someOwnerGroupId");
    batchResponse.setReviewerId("reviewerId");

    String reviewComment = "some rejection comment";
    batchResponse.setReviewComment(reviewComment);
    batchResponse.setReviewTimestamp(new Date());

    String request = client.gson.toJson(new BatchChangeReview(reviewComment));
    String response = client.gson.toJson(batchResponse);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges/" + batchResponse.getId() + "/reject"))
            .withRequestBody(new EqualToJsonPattern(request, true, true))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<BatchResponse> vinylDNSBatchResponse =
        client.rejectBatchChanges("1234", reviewComment);

    assertEquals(vinylDNSBatchResponse.getStatusCode(), 202);
  }

  @Test
  public void rejectBatchChangeFailure() {
    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges/noExisto/reject"))
            .willReturn(aResponse().withStatus(404).withBody("Not found")));

    VinylDNSResponse<BatchResponse> vinylDNSResponse = client.rejectBatchChanges("noExisto");

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "Not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void cancelBatchChangeSuccess() {
    List<SingleChange> singleChangeList = new ArrayList<>();

    AddSingleChange addSingleChange = new AddSingleChange();
    addSingleChange.setChangeType(ChangeInputType.Add);
    addSingleChange.setId("1234");
    addSingleChange.setInputName("testString");
    addSingleChange.setRecord(new AData("1.2.3.4"));
    addSingleChange.setType(RecordType.A);
    addSingleChange.setStatus(SingleChangeStatus.Rejected);
    singleChangeList.add(addSingleChange);

    BatchResponse batchResponse = new BatchResponse();
    batchResponse.setId("1234");
    batchResponse.setUserId("testuser");
    batchResponse.setUserName("testuser");
    batchResponse.setCreatedTimestamp(new Date());
    batchResponse.setChanges(singleChangeList);
    batchResponse.setStatus(BatchChangeStatus.Cancelled);
    batchResponse.setApprovalStatus(BatchChangeApprovalStatus.Cancelled);
    batchResponse.setOwnerGroupId("someOwnerGroupId");
    batchResponse.setReviewerId("reviewerId");
    batchResponse.setReviewComment("Looks good!");
    batchResponse.setReviewTimestamp(new Date());

    String response = client.gson.toJson(batchResponse);

    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges/" + batchResponse.getId() + "/cancel"))
            .willReturn(
                aResponse()
                    .withStatus(202)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<BatchResponse> vinylDNSBatchResponse = client.cancelBatchChanges("1234");

    assertEquals(vinylDNSBatchResponse.getStatusCode(), 202);
  }

  @Test
  public void cancelBatchChangeFailure() {
    wireMockServer.stubFor(
        post(urlEqualTo("/zones/batchrecordchanges/noExisto/cancel"))
            .willReturn(aResponse().withStatus(404).withBody("Not found")));

    VinylDNSResponse<BatchResponse> vinylDNSResponse = client.cancelBatchChanges("noExisto");

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "Not found");
    assertNull(vinylDNSResponse.getValue());
  }

  @Test
  public void listRecordSetChangeSuccess() {
    String response = client.gson.toJson(listRecordSetChangesResponse);

    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsetchanges"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<ListRecordSetChangesResponse> vinylDNSResponse =
        client.listRecordSetChanges(new ListRecordSetChangesRequest(zoneId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue(), listRecordSetChangesResponse);
  }

  @Test
  public void listRecordSetChangeFailure() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId + "/recordsetchanges"))
            .willReturn(aResponse().withStatus(500).withBody("server error")));

    VinylDNSResponse<ListRecordSetChangesResponse> vinylDNSResponse =
        client.listRecordSetChanges(new ListRecordSetChangesRequest(zoneId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 500);
    assertEquals(vinylDNSResponse.getMessageBody(), "server error");
    assertNull(vinylDNSResponse.getValue());
  }

  private String rsId = "recordSetId";
  private String zoneId = "zoneId";
  private ZoneConnection testZoneConnection1 =
      new ZoneConnection("name", "keyName", "key", "server");
  private ZoneACL testZoneAcl1 =
      new ZoneACL(
          Collections.singleton(
              new ACLRule(
                  AccessLevel.Read,
                  "description",
                  "userId",
                  "groupId",
                  "recordMask",
                  Collections.singleton(RecordType.A))));
  private Zone testZone1 =
      new Zone(
          "id",
          "name",
          "some@company.com",
          ZoneStatus.Active,
          new DateTime(),
          new DateTime(),
          testZoneConnection1,
          null,
          true,
          testZoneAcl1,
          "group",
          null,
          true,
          "somebackId");
  private List<Zone> zones = Collections.singletonList(testZone1);
  private ListZonesResponse listZonesResponse =
      new ListZonesResponse(zones, "startFrom", "nextId", 100, "nameFilter");

  private String recordSetId = "recordSetId";
  private String recordSetName = "recordSetName";
  private String ownerGroupId = "ownerGroupId";
  private List<RecordData> recordDataList = Collections.singletonList(new AData("192.168.1.1"));
  private RecordSet recordSet =
      new RecordSet(
          zoneId,
          recordSetName,
          RecordType.A,
          100,
          recordDataList,
          recordSetId,
          ownerGroupId,
          RecordSetStatus.Active,
          new DateTime(),
          new DateTime());
  private RecordSet recordSetNoOwner =
      new RecordSet(
          zoneId,
          recordSetName,
          RecordType.A,
          100,
          recordDataList,
          recordSetId,
          null,
          RecordSetStatus.Active,
          new DateTime(),
          new DateTime());
  private RecordSet recordSetUpdate =
      new RecordSet(
          zoneId,
          "recordSetNameUpdated",
          RecordType.A,
          38400,
          recordDataList,
          recordSetId,
          ownerGroupId,
          RecordSetStatus.Active,
          new DateTime(),
          new DateTime());
  private List<RecordSet> recordSetList = Collections.singletonList(recordSet);

  private ListRecordSetsResponse listRecordSetsResponse =
      new ListRecordSetsResponse(recordSetList, "startFrom", "nextId", 22, "nameFilter");

  private String recordSetChangeId = "recordSetChangeId";
  private RecordSetChange recordSetChangeDelete =
      new RecordSetChange(
          recordSetChangeId,
          testZone1,
          recordSet,
          "userId",
          RecordSetChangeType.Delete,
          RecordSetChangeStatus.Pending,
          new DateTime(),
          null,
          null);
  private RecordSetChange recordSetChangeCreate =
      new RecordSetChange(
          recordSetChangeId,
          testZone1,
          recordSet,
          "userId",
          RecordSetChangeType.Create,
          RecordSetChangeStatus.Pending,
          new DateTime(),
          null,
          null);
  private RecordSetChange recordSetChangeCreateNoOwner =
      new RecordSetChange(
          recordSetChangeId,
          testZone1,
          recordSetNoOwner,
          "userId",
          RecordSetChangeType.Create,
          RecordSetChangeStatus.Pending,
          new DateTime(),
          null,
          null);
  private RecordSetChange recordSetChangeUpdate =
      new RecordSetChange(
          recordSetChangeId,
          testZone1,
          recordSet,
          "userId",
          RecordSetChangeType.Update,
          RecordSetChangeStatus.Pending,
          new DateTime(),
          null,
          recordSet);
  private RecordSetChange recordSetChangeUpdateNoOwner =
      new RecordSetChange(
          recordSetChangeId,
          testZone1,
          recordSetNoOwner,
          "userId",
          RecordSetChangeType.Update,
          RecordSetChangeStatus.Pending,
          new DateTime(),
          null,
          recordSetNoOwner);
  private CreateRecordSetRequest createRecordSetRequest =
      new CreateRecordSetRequest(
          zoneId, recordSetName, RecordType.A, 100, recordDataList, ownerGroupId);
  private CreateRecordSetRequest createRecordSetRequestNoOwner =
      new CreateRecordSetRequest(zoneId, recordSetName, RecordType.A, 100, recordDataList);
  private GetRecordSetRequest getRecordSetRequest = new GetRecordSetRequest(zoneId, recordSetId);

  private String groupId = "groupId";
  private GetRecordSetChangeRequest getRecordSetChangeRequest =
      new GetRecordSetChangeRequest(zoneId, recordSetId, recordSetChangeId);
  private UpdateRecordSetRequest updateRecordSetRequest =
      new UpdateRecordSetRequest(
          rsId, zoneId, recordSetName, ownerGroupId, RecordType.A, 100, recordDataList);
  private UpdateRecordSetRequest updateRecordSetRequestNoOwner =
      new UpdateRecordSetRequest(rsId, zoneId, recordSetName, RecordType.A, 100, recordDataList);

  private String adminId = "adminId";
  private Set<UserInfo> adminUserInfo = Collections.singleton(new UserInfo(adminId));
  private Set<MemberId> adminMemberId = Collections.singleton(new MemberId(adminId));
  private Group group =
      new Group(
          "groupName",
          "email",
          adminUserInfo,
          adminUserInfo,
          "groupId",
          "description",
          new DateTime(),
          Active);

  private Group newGroup =
      new Group(
          "groupName1",
          "email1",
          adminUserInfo,
          adminUserInfo,
          "groupId",
          "description1",
          new DateTime(),
          Active);

  private Set<GroupChange> groupChanges =
      Collections.singleton(new GroupChange("ID", newGroup, group, "", "", GroupChangeType.Update));

  private CreateGroupRequest createGroupRequest =
      new CreateGroupRequest("createGroup", "create@group.com", adminMemberId, adminMemberId);
  private UpdateGroupRequest updateGroupRequest =
      new UpdateGroupRequest(
          "groupId",
          "groupId",
          "createGroup",
          "update@group.com",
          adminMemberId,
          adminMemberId,
          new DateTime(),
          GroupStatus.Active);
  private GetGroupRequest getGroupRequest = new GetGroupRequest(groupId);
  private DeleteGroupRequest deleteGroupRequest = new DeleteGroupRequest(groupId);

  private List<Group> groupList = Collections.singletonList(group);

  private ListGroupsResponse listGroupsResponse =
      new ListGroupsResponse(groupList, "groupNameFilter", "startFrom", "nextId", 100);

  private ListMembersResponse listMembersResponse =
      new ListMembersResponse(adminUserInfo, "startFrom", "nextId", 100);

  private ListGroupActivityResponse listGroupActivityResponse =
      new ListGroupActivityResponse(groupChanges, "startFrom", "nextId", 100);

  private List<RecordSetChange> recordSetChangeList =
      Collections.singletonList(recordSetChangeCreate);

  private ListRecordSetChangesResponse listRecordSetChangesResponse =
      new ListRecordSetChangesResponse(zoneId, recordSetChangeList, "", "startFrom", 1);
}
