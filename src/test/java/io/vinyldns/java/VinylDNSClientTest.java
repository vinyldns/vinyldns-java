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
            null);

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
            null);

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
    AddChangeInput input = new AddChangeInput("www.example.com", RecordType.A, 300L, adata);
    changes.add(input);

    CreateBatchRequest batchRequest = new CreateBatchRequest(changes);

    AddSingleChange singleChange = new AddSingleChange();
    singleChange.setChangeType(ChangeInputType.Add);
    singleChange.setId("1234");
    singleChange.setInputName("testString");
    singleChange.setRecord(adata);
    singleChange.setRecordChangeId("1111");
    singleChange.setRecordName("testName");
    singleChange.setRecordSetId("testId");
    singleChange.setZoneId("testZone");
    singleChange.setZoneName("testZoneName");
    singleChange.setType(RecordType.A);
    singleChange.setSystemMessage("testMessage");
    singleChange.setStatus(SingleChangeStatus.Complete);

    List<SingleChange> singleChangeList = new ArrayList<>();
    singleChangeList.add(singleChange);

    BatchResponse batchResponse = new BatchResponse();
    batchResponse.setId("1234");
    batchResponse.setUserId("testUserId");
    batchResponse.setUserName("testUserName");
    batchResponse.setComments("testComments");
    batchResponse.setCreatedTimestamp(new Date());
    batchResponse.setChanges(singleChangeList);
    batchResponse.setStatus(BatchChangeStatus.Complete);
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
          null);
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
  private CreateRecordSetRequest createRecordSetRequest =
      new CreateRecordSetRequest(
          zoneId, recordSetName, RecordType.A, 100, recordDataList, ownerGroupId);
  private GetRecordSetRequest getRecordSetRequest = new GetRecordSetRequest(zoneId, recordSetId);

  private String groupId = "groupId";
  private GetRecordSetChangeRequest getRecordSetChangeRequest =
      new GetRecordSetChangeRequest(zoneId, recordSetId, recordSetChangeId);
  private UpdateRecordSetRequest updateRecordSetRequest =
      new UpdateRecordSetRequest(
          rsId, zoneId, recordSetName, ownerGroupId, RecordType.A, 100, recordDataList);

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
