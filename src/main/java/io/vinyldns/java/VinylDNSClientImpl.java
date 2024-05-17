/*
 * Copyright 2018 Comcast Cable Communications Management, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vinyldns.java;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.SignerFactory;
import com.amazonaws.http.AmazonHttpClient;
import com.google.gson.Gson;
import io.vinyldns.java.handlers.ErrorResponseHandler;
import io.vinyldns.java.handlers.StringResponseHandler;
import io.vinyldns.java.model.Methods;
import io.vinyldns.java.model.batch.*;
import io.vinyldns.java.model.membership.*;
import io.vinyldns.java.model.record.set.*;
import io.vinyldns.java.model.zone.*;
import io.vinyldns.java.responses.VinylDNSFailureResponse;
import io.vinyldns.java.responses.VinylDNSResponse;
import io.vinyldns.java.responses.VinylDNSSuccessResponse;
import io.vinyldns.java.serializers.SerializationFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class VinylDNSClientImpl implements VinylDNSClient {
  private VinylDNSClientConfig config;

  private AmazonHttpClient client;

  Gson gson = SerializationFactory.createGson();

  public VinylDNSClientImpl(VinylDNSClientConfig config) {
    this.config = config;

    this.client = new AmazonHttpClient(new ClientConfiguration());
  }

  public VinylDNSClientImpl() {
    this.config =
        new VinylDNSClientConfig(
            System.getenv("VINYLDNS_API_URL"),
            new BasicAWSCredentials(
                System.getenv("VINYLDNS_ACCESS_KEY_ID"),
                System.getenv("VINYLDNS_SECRET_ACCESS_KEY")),
            SignerFactory.getSigner("VinylDNS", "us/east"));

    this.client = new AmazonHttpClient(new ClientConfiguration());
  }

  // Zone
  @Override
  public VinylDNSResponse<ListZonesResponse> listZones(ListZonesRequest request) {
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), "zones", null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    if (request.getNameFilter() != null) {
      vinylDNSRequest.addParameter("nameFilter", request.getNameFilter());
    }

    return executeRequest(vinylDNSRequest, ListZonesResponse.class);
  }

  @Override
  public VinylDNSResponse<ZoneResponse> createZone(Zone zone) {
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), "zones", zone),
        ZoneResponse.class);
  }

  @Override
  public VinylDNSResponse<GetZoneResponse> getZone(ZoneRequest request) {
    String path = "zones/" + request.getZoneId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null), GetZoneResponse.class);
  }

  @Override
  public VinylDNSResponse<GetZoneResponse> getZoneByName(String zoneName) {
    String path = "zones/name/" + zoneName;
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null), GetZoneResponse.class);
  }

  @Override
  public VinylDNSResponse<ZoneResponse> updateZone(Zone zone) {
    String path = "zones/" + zone.getId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, zone), ZoneResponse.class);
  }

  @Override
  public VinylDNSResponse<ZoneResponse> deleteZone(ZoneRequest request) {
    String path = "zones/" + request.getZoneId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.DELETE.name(), getBaseUrl(), path, null), ZoneResponse.class);
  }

  @Override
  public VinylDNSResponse<ListZoneChangesResponse> listZoneChanges(ListZoneChangesRequest request) {
    String path = "zones/" + request.getZoneId() + "/changes";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, ListZoneChangesResponse.class);
  }

  @Override
  public VinylDNSResponse<ListAbandonedZonesResponse> listAbandonedZones(
      ListAbondonedZonesRequest request) {
    String path = "zones/deleted/changes";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getNameFilter() != null) {
      vinylDNSRequest.addParameter("nameFilter", request.getNameFilter());
    }

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, ListAbandonedZonesResponse.class);
  }

  @Override
  public VinylDNSResponse<ZoneResponse> syncZone(ZoneRequest request) {
    String path = "zones/" + request.getZoneId() + "/sync";
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, null), ZoneResponse.class);
  }

  // RecordSet
  @Override
  public VinylDNSResponse<ListRecordSetsResponse> listRecordSets(ListRecordSetsRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsets";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    if (request.getRecordNameFilter() != null) {
      vinylDNSRequest.addParameter("recordNameFilter", request.getRecordNameFilter());
    }

    return executeRequest(vinylDNSRequest, ListRecordSetsResponse.class);
  }

  @Override
  public VinylDNSResponse<Group> getGroup(GetGroupRequest request) {
    String path = "groups/" + request.getId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null), Group.class);
  }

  @Override
  public VinylDNSResponse<Group> createGroup(CreateGroupRequest request) {
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), "groups", request), Group.class);
  }

  @Override
  public VinylDNSResponse<Group> updateGroup(UpdateGroupRequest request) {
    String path = "groups/" + request.getGroupId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, request), Group.class);
  }

  @Override
  public VinylDNSResponse<Group> deleteGroup(DeleteGroupRequest request) {
    String path = "/groups/" + request.getId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.DELETE.name(), getBaseUrl(), path, null), Group.class);
  }

  @Override
  public VinylDNSResponse<ListGroupsResponse> listGroups(ListGroupsRequest request) {
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), "groups", null);

    if (request.getGroupNameFilter() != null) {
      vinylDNSRequest.addParameter("groupNameFilter", request.getGroupNameFilter());
    }

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, ListGroupsResponse.class);
  }

  @Override
  public VinylDNSResponse<ListAdminsResponse> listAdmins(String groupId) {
    String path = "groups/" + groupId + "/admins";
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null),
        ListAdminsResponse.class);
  }

  @Override
  public VinylDNSResponse<ListMembersResponse> listMembers(ListMembersRequest request) {
    String path = "groups/" + request.getGroupId() + "/members";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, ListMembersResponse.class);
  }

  @Override
  public VinylDNSResponse<ListGroupActivityResponse> listGroupActivity(
      ListGroupActivityRequest request) {
    String path = "groups/" + request.getGroupId() + "/activity";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, ListGroupActivityResponse.class);
  }

  @Override
  public VinylDNSResponse<RecordSetChange> createRecordSet(CreateRecordSetRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsets";
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, request),
        RecordSetChange.class);
  }

  @Override
  public VinylDNSResponse<GetRecordSetResponse> getRecordSet(GetRecordSetRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsets/" + request.getRecordSetId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null),
        GetRecordSetResponse.class);
  }

  @Override
  public VinylDNSResponse<RecordSetChange> updateRecordSet(UpdateRecordSetRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsets/" + request.getId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, request),
        RecordSetChange.class);
  }

  @Override
  public VinylDNSResponse<RecordSetChange> deleteRecordSet(DeleteRecordSetRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsets/" + request.getRecordSetId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.DELETE.name(), getBaseUrl(), path, null),
        RecordSetChange.class);
  }

  @Override
  public VinylDNSResponse<ListRecordSetChangesResponse> listRecordSetChanges(
      ListRecordSetChangesRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsetchanges";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, ListRecordSetChangesResponse.class);
  }

  @Override
  public VinylDNSResponse<RecordSetChange> getRecordSetChange(GetRecordSetChangeRequest request) {
    String path =
        "zones/"
            + request.getZoneId()
            + "/recordsets/"
            + request.getRecordSetId()
            + "/changes/"
            + request.getRecordSetChangeId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null), RecordSetChange.class);
  }

  @Override
  public VinylDNSResponse<ListBatchChangesResponse> listBatchChanges(
      ListBatchChangesRequest request) {
    String path = "zones/batchrecordchanges";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    if (request.getIgnoreAccess() != null) {
      vinylDNSRequest.addParameter("ignoreAccess", request.getIgnoreAccess().toString());
    }

    if (request.getApprovalStatus() != null) {
      vinylDNSRequest.addParameter("approvalStatus", request.getApprovalStatus().toString());
    }

    return executeRequest(vinylDNSRequest, ListBatchChangesResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> getBatchChanges(String id) {
    String path = "zones/batchrecordchanges/" + id;
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null), BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> createBatchChanges(CreateBatchRequest request) {
    String path = "zones/batchrecordchanges";

    VinylDNSRequest<CreateBatchRequest> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, request);

    if (request.getAllowManualReview() != null) {
      vinylDNSRequest.addParameter("allowManualReview", request.getAllowManualReview().toString());
    }

    return executeRequest(vinylDNSRequest, BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> approveBatchChanges(String id) {
    String path = "zones/batchrecordchanges/" + id + "/approve";
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, null), BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> approveBatchChanges(String id, String reviewComment) {
    String path = "zones/batchrecordchanges/" + id + "/approve";
    return executeRequest(
        new VinylDNSRequest<>(
            Methods.POST.name(), getBaseUrl(), path, new BatchChangeReview(reviewComment)),
        BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> rejectBatchChanges(String id) {
    String path = "zones/batchrecordchanges/" + id + "/reject";
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, null), BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> rejectBatchChanges(String id, String reviewComment) {
    String path = "zones/batchrecordchanges/" + id + "/reject";
    return executeRequest(
        new VinylDNSRequest<>(
            Methods.POST.name(), getBaseUrl(), path, new BatchChangeReview(reviewComment)),
        BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<BatchResponse> cancelBatchChanges(String id) {
    String path = "zones/batchrecordchanges/" + id + "/cancel";
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, null), BatchResponse.class);
  }

  @Override
  public VinylDNSResponse<SearchRecordSetsResponse> searchRecordSets(
      SearchRecordSetsRequest request) {
    String path = "recordsets";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }

    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    if (request.getRecordNameFilter() != null) {
      vinylDNSRequest.addParameter("recordNameFilter", request.getRecordNameFilter());
    }

    if (request.getNameSort() != null) {
      vinylDNSRequest.addParameter("nameSort", request.getNameSort().name());
    }

    if (request.getRecordOwnerGroupFilter() != null) {
      vinylDNSRequest.addParameter("recordOwnerGroupFilter", request.getRecordOwnerGroupFilter());
    }

    return executeRequest(vinylDNSRequest, SearchRecordSetsResponse.class);
  }

  private <S, R> VinylDNSResponse<R> executeRequest(VinylDNSRequest<S> req, Class<R> responseType) {
    Request<String> request = new DefaultRequest<>("VinylDNS");
    request.setEndpoint(req.getEndpoint());
    request.setResourcePath(req.getResourcePath());
    request.setHttpMethod(req.getHttpMethod());
    request.setHeaders(req.getHeaders());
    request.setParameters(req.getParameters());

    request.addHeader("Content-Type", "application/json");

    if (req.getPayload() != null) {
      String content = gson.toJson(req.getPayload());

      try {
        request.setContent(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    config.getSigner().sign(request, config.getCredentials());

    try {
      Response<String> response =
          client
              .requestExecutionBuilder()
              .errorResponseHandler(new ErrorResponseHandler())
              .request(request)
              .execute(new StringResponseHandler());

      int statusCode = response.getHttpResponse().getStatusCode();
      String messageBody = response.getAwsResponse();

      if (statusCode / 100 * 100 == 200) {
        R responseObject = gson.fromJson(messageBody, responseType);

        return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
      } else {
        return new VinylDNSFailureResponse<>(messageBody, statusCode);
      }
    } catch (AmazonServiceException e) {
      return new VinylDNSFailureResponse<>(e.getRawResponseContent(), e.getStatusCode());
    }
  }

  private String getBaseUrl() {
    if (config.getBaseUrl().endsWith("/")) {
      return config.getBaseUrl();
    } else {
      return config.getBaseUrl() + "/";
    }
  }
}
