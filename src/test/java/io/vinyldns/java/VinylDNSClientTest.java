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
import io.vinyldns.java.model.acl.ACLRule;
import io.vinyldns.java.model.acl.AccessLevel;
import io.vinyldns.java.model.membership.Group;
import io.vinyldns.java.model.membership.ListGroupsRequest;
import io.vinyldns.java.model.membership.ListGroupsResponse;
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
  public void getZoneSuccess() {
    String response = client.gson.toJson(new GetZoneResponse(testZone1));

    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + testZone1.getId()))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(response)));

    VinylDNSResponse<GetZoneResponse> vinylDNSResponse = client.getZone(new GetZoneRequest(testZone1.getId()));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Success);
    assertEquals(vinylDNSResponse.getStatusCode(), 200);
    assertEquals(vinylDNSResponse.getValue().getZone(), testZone1);
  }

  @Test
  public void getZoneFailure404() {
    wireMockServer.stubFor(
        get(urlEqualTo("/zones/" + zoneId))
            .willReturn(aResponse().withStatus(404).withBody("not found")));

    VinylDNSResponse<GetZoneResponse> vinylDNSResponse = client.getZone(new GetZoneRequest(zoneId));

    assertTrue(vinylDNSResponse instanceof ResponseMarker.Failure);
    assertEquals(vinylDNSResponse.getStatusCode(), 404);
    assertEquals(vinylDNSResponse.getMessageBody(), "not found");
    assertNull(vinylDNSResponse.getValue());
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
  private List<RecordData> recordDataList = Collections.singletonList(new AData("192.168.1.1"));
  private RecordSet recordSet =
      new RecordSet(
          zoneId,
          recordSetName,
          RecordType.A,
          100,
          recordDataList,
          recordSetId,
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
  private CreateRecordSetRequest createRecordSetRequest =
      new CreateRecordSetRequest(zoneId, recordSetName, RecordType.A, 100, recordDataList);

  private String adminId = "adminId";
  private Set<String> adminUserIds = Collections.singleton(adminId);
  private Group group =
      new Group("groupName",
          "email",
          adminUserIds,
          adminUserIds,
          "groupId",
          "description",
          new DateTime(),
          Active);

  private List<Group> groupList = Collections.singletonList(group);

  private ListGroupsResponse listGroupsResponse =
      new ListGroupsResponse(groupList, "groupNameFilter", "startFrom", "nextId", 100);
}
