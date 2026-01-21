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
import com.amazonaws.http.HttpMethodName;
import com.google.gson.Gson;
import io.vinyldns.java.handlers.ErrorResponseHandler;
import io.vinyldns.java.handlers.StringResponseHandler;
import io.vinyldns.java.model.Methods;
import io.vinyldns.java.model.acl.*;
import io.vinyldns.java.model.batch.*;
import io.vinyldns.java.model.health.*;
import io.vinyldns.java.model.membership.*;
import io.vinyldns.java.model.record.set.*;
import io.vinyldns.java.model.zone.*;
import io.vinyldns.java.responses.VinylDNSFailureResponse;
import io.vinyldns.java.responses.VinylDNSResponse;
import io.vinyldns.java.responses.VinylDNSSuccessResponse;
import io.vinyldns.java.serializers.SerializationFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class VinylDNSClientImpl implements VinylDNSClient {
  private VinylDNSClientConfig config;

  private AmazonHttpClient client;

  private static final class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    @Override
    public String getMethod() {
      return METHOD_NAME;
    }

    public HttpDeleteWithBody(final String uri) {
      setURI(URI.create(uri));
    }

    public HttpDeleteWithBody(final URI uri) {
      setURI(uri);
    }

    public HttpDeleteWithBody() {}
  }

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

  @Override
  public VinylDNSResponse<GetUserResponse> getUser(GetUserRequest request) {
    String path = "users/" + request.getUserId();

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, GetUserResponse.class);
  }

  @Override
  public VinylDNSResponse<LockUnlockUserResponse> lockUser(LockUserRequest request) {
    String path = "users/" + request.getUserId() + "/lock";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, LockUnlockUserResponse.class);
  }

  @Override
  public VinylDNSResponse<LockUnlockUserResponse> unlockUser(UnlockUserRequest request) {
    String path = "users/" + request.getUserId() + "/unlock";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, LockUnlockUserResponse.class);
  }

  @Override
  public VinylDNSResponse<GetGroupChangeResponse> getGroupChange(GetGroupChangeRequest request) {
    String path = "groups/change/" + request.getGroupChangeId();

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, GetGroupChangeResponse.class);
  }

  @Override
  public VinylDNSResponse<List<String>> listValidDomains() {
    String path = "groups/valid/domains";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    Type listType = new com.google.gson.reflect.TypeToken<List<String>>() {}.getType();
    return executeRequest(vinylDNSRequest, listType);
  }

  @Override
  public VinylDNSResponse<GetRecordSetCountResponse> getRecordSetCount(
      GetRecordSetCountRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsetcount";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);
    return executeRequest(vinylDNSRequest, GetRecordSetCountResponse.class);
  }

  @Override
  public VinylDNSResponse<GetRecordSetChangeHistoryResponse> getRecordSetChangeHistory(
      GetRecordSetChangeHistoryRequest request) {
    String path = "recordsetchange/history";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    if (request.getZoneId() != null) {
      vinylDNSRequest.addParameter("zoneId", request.getZoneId());
    }
    if (request.getFqdn() != null) {
      vinylDNSRequest.addParameter("fqdn", request.getFqdn());
    }
    if (request.getRecordType() != null) {
      vinylDNSRequest.addParameter("recordType", request.getRecordType());
    }
    if (request.getStartFrom() != null) {
      vinylDNSRequest.addParameter("startFrom", request.getStartFrom());
    }
    if (request.getMaxItems() != null) {
      vinylDNSRequest.addParameter("maxItems", request.getMaxItems().toString());
    }

    return executeRequest(vinylDNSRequest, GetRecordSetChangeHistoryResponse.class);
  }

  @Override
  public VinylDNSResponse<GetFailedRecordSetChangesResponse> getFailedRecordSetChanges(
      GetFailedRecordSetChangesRequest request) {
    String path = "metrics/health/zones/" + request.getZoneId() + "/recordsetchangesfailure";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);
    return executeRequest(vinylDNSRequest, GetFailedRecordSetChangesResponse.class);
  }

  @Override
  public VinylDNSResponse<GetZoneDetailsResponse> getZoneDetails(GetZoneDetailsRequest request) {
    String path = "zones/" + request.getZoneId() + "/details";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);
    return executeRequest(vinylDNSRequest, GetZoneDetailsResponse.class);
  }

  @Override
  public VinylDNSResponse<List<String>> getZonesBackendIds() {
    String path = "zones/backendids";

    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    Type listType = new com.google.gson.reflect.TypeToken<List<String>>() {}.getType();
    return executeRequest(vinylDNSRequest, listType);
  }

  @Override
  public VinylDNSResponse<GetFailedZoneChangesResponse> getFailedZoneChanges(
      GetFailedZoneChangesRequest request) {
    String path = "metrics/health/zonechangesfailure";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);
    return executeRequest(vinylDNSRequest, GetFailedZoneChangesResponse.class);
  }

  @Override
  public VinylDNSResponse<AddZoneAclRuleResponse> addZoneAclRule(AddZoneAclRuleRequest request) {
    String path = "zones/" + request.getZoneId() + "/acl/rules";

    ACLRule rule = request.getRule();
    VinylDNSRequest<ACLRule> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, rule);
    return executeRequest(vinylDNSRequest, AddZoneAclRuleResponse.class);
  }

  @Override
  public VinylDNSResponse<DeleteZoneAclRuleResponse> deleteZoneAclRule(
      DeleteZoneAclRuleRequest request) {
    String path = "zones/" + request.getZoneId() + "/acl/rules";
    ACLRule rule = request.getRule();
    VinylDNSRequest<ACLRule> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.DELETE.name(), getBaseUrl(), path, rule);
    return executeRequest(vinylDNSRequest, DeleteZoneAclRuleResponse.class);
  }

  @Override
  public VinylDNSResponse<String> getPing() {
    String path = "ping";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, String.class);
  }

  @Override
  public VinylDNSResponse<String> getHealth() {
    String path = "health";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, String.class);
  }

  @Override
  public VinylDNSResponse<String> getColor() {
    String path = "color";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, String.class);
  }

  @Override
  public VinylDNSResponse<String> getPrometheusMetrics() {
    String path = "metrics/prometheus";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);

    return executeRequest(vinylDNSRequest, String.class);
  }

  @Override
  public VinylDNSResponse<StatusResponse> getStatus(GetStatusRequest request) {
    String path = "status";
    VinylDNSRequest<Void> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null);
    return executeRequest(vinylDNSRequest, StatusResponse.class);
  }

  @Override
  public VinylDNSResponse<StatusResponse> updateStatus(UpdateStatusRequest requestObj) {
    VinylDNSRequest<UpdateStatusRequest> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), "status", requestObj);
    vinylDNSRequest.addParameter(
        "processingDisabled", String.valueOf(!requestObj.isProcessingEnabled()));
    return executeRequest(vinylDNSRequest, StatusResponse.class);
  }

  @Override
  public VinylDNSResponse<RecordSetUpdateResponse> requestTransfer(
      RecordSetOwnershipTransferRequest requestObj) {
    RecordSetOwnershipTransferRequest payload =
        requestObj.withStatus(OwnershipTransferStatus.Requested).withUpdateOwnerGroup(false);
    return putRecordSet(payload);
  }

  @Override
  public VinylDNSResponse<RecordSetUpdateResponse> approveTransfer(
      RecordSetOwnershipTransferRequest requestObj) {
    RecordSetOwnershipTransferRequest payload =
        requestObj.withStatus(OwnershipTransferStatus.ManuallyApproved).withUpdateOwnerGroup(true);
    return putRecordSet(payload);
  }

  @Override
  public VinylDNSResponse<RecordSetUpdateResponse> rejectTransfer(
      RecordSetOwnershipTransferRequest requestObj) {
    RecordSetOwnershipTransferRequest payload =
        requestObj.withStatus(OwnershipTransferStatus.ManuallyRejected).withUpdateOwnerGroup(false);
    return putRecordSet(payload);
  }

  @Override
  public VinylDNSResponse<RecordSetUpdateResponse> cancelTransfer(
      RecordSetOwnershipTransferRequest requestObj) {
    RecordSetOwnershipTransferRequest payload =
        requestObj.withStatus(OwnershipTransferStatus.Cancelled).withUpdateOwnerGroup(false);
    return putRecordSet(payload);
  }

  private VinylDNSResponse<RecordSetUpdateResponse> putRecordSet(
      RecordSetOwnershipTransferRequest payload) {

    VinylDNSResponse<GetRecordSetResponse> rsResponse =
        getRecordSet(new GetRecordSetRequest(payload.getZoneId(), payload.getRecordSetId()));
    if (!(rsResponse instanceof VinylDNSSuccessResponse)) {
      throw new RuntimeException("Failed to fetch RecordSet before transfer");
    }
    RecordSet rs = rsResponse.getValue().getRecordSet();
    RecordSetTransferPayload body =
        new RecordSetTransferPayload(
            rs,
            payload.getRecordSetGroupChange(),
            payload.isUpdateOwnerGroup(),
            payload.getRequestedOwnerGroupId());

    String path =
        String.format("zones/%s/recordsets/%s", payload.getZoneId(), payload.getRecordSetId());

    VinylDNSRequest<RecordSetTransferPayload> vinylDNSRequest =
        new VinylDNSRequest<>(Methods.PUT.name(), getBaseUrl(), path, body);

    return executeRequest(vinylDNSRequest, RecordSetUpdateResponse.class);
  }

  private <S, R> VinylDNSResponse<R> executeRequest(VinylDNSRequest<S> req, Type responseType) {
    Request<String> request = new DefaultRequest<>("VinylDNS");
    request.setEndpoint(req.getEndpoint());
    request.setResourcePath(req.getResourcePath());
    request.setHttpMethod(req.getHttpMethod());
    request.setHeaders(req.getHeaders());
    request.setParameters(req.getParameters());

    request.addHeader("Content-Type", "application/json");

    if (req.getPayload() != null) {
      String content = gson.toJson(req.getPayload());
      request.setContent(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
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

  private <S, R> VinylDNSResponse<R> executeRequest(VinylDNSRequest<S> req, Class<R> responseType) {
    Request<String> request = new DefaultRequest<>("VinylDNS");
    request.setEndpoint(req.getEndpoint());
    request.setResourcePath(req.getResourcePath());
    request.setHttpMethod(req.getHttpMethod());
    request.setHeaders(new HashMap<>(req.getHeaders()));
    request.setParameters(req.getParameters());
    request.addHeader("Content-Type", "application/json");

    byte[] payloadBytes = null;
    if (req.getPayload() != null) {
      String json = gson.toJson(req.getPayload());
      payloadBytes = json.getBytes(StandardCharsets.UTF_8);
      request.setContent(new ByteArrayInputStream(payloadBytes));
      request.addHeader("x-amz-content-sha256", sha256Hex(payloadBytes));
      request.addHeader("Content-Type", "application/json");
    } else {
      request.addHeader(
          "x-amz-content-sha256",
          "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
    }

    int port = request.getEndpoint().getPort();
    String hostHeader =
        (port == -1 || port == 443 || port == 80)
            ? request.getEndpoint().getHost()
            : request.getEndpoint().getHost() + ":" + port;
    request.addHeader("Host", hostHeader);

    config.getSigner().sign(request, config.getCredentials());

    boolean deleteWithBody =
        request.getHttpMethod() == HttpMethodName.DELETE && payloadBytes != null;

    if (deleteWithBody) {
      String base = request.getEndpoint().toString().replaceAll("/$", "");
      String path = request.getResourcePath();
      if (path == null) path = "";
      if (!path.startsWith("/")) path = "/" + path;

      String url = base + path;
      String qs = buildQueryString(request.getParameters());
      if (!qs.isEmpty()) url += "?" + qs;

      HttpDeleteWithBody http = new HttpDeleteWithBody(url);

      for (Map.Entry<String, String> h : request.getHeaders().entrySet()) {
        String name = h.getKey();
        if ("Content-Length".equalsIgnoreCase(name) || "Transfer-Encoding".equalsIgnoreCase(name)) {
          continue;
        }
        http.addHeader(name, h.getValue());
      }

      http.setEntity(new ByteArrayEntity(payloadBytes, ContentType.APPLICATION_JSON));

      try (CloseableHttpClient httpClient =
              HttpClients.custom().disableContentCompression().build();
          CloseableHttpResponse resp = httpClient.execute(http)) {

        int statusCode = resp.getStatusLine().getStatusCode();
        String messageBody =
            resp.getEntity() != null
                ? EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8)
                : "";

        if (statusCode >= 200 && statusCode < 300) {
          if (responseType == String.class) {
            @SuppressWarnings("unchecked")
            R responseObject = (R) messageBody;
            return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
          }
          R responseObject = gson.fromJson(messageBody, responseType);
          return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
        } else {
          return new VinylDNSFailureResponse<>(messageBody, statusCode);
        }
      } catch (IOException e) {
        StringBuilder sb = new StringBuilder();
        Throwable cur = e;
        int depth = 0;
        while (cur != null && depth < 5) {
          sb.append(cur.getClass().getName()).append(": ").append(cur.getMessage()).append("\n");
          cur = cur.getCause();
          depth++;
        }
        for (int i = 0; i < Math.min(6, e.getStackTrace().length); i++) {
          sb.append("  at ").append(e.getStackTrace()[i].toString()).append("\n");
        }
        return new VinylDNSFailureResponse<>(sb.toString(), 599);
      }
    }

    try {
      Response<String> response =
          client
              .requestExecutionBuilder()
              .errorResponseHandler(new ErrorResponseHandler())
              .request(request)
              .execute(new StringResponseHandler());

      int statusCode = response.getHttpResponse().getStatusCode();
      String messageBody = response.getAwsResponse();

      if (statusCode >= 200 && statusCode < 300) {
        if (responseType == String.class) {
          @SuppressWarnings("unchecked")
          R responseObject = (R) messageBody;
          return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
        }
        R responseObject = gson.fromJson(messageBody, responseType);
        return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
      } else {
        return new VinylDNSFailureResponse<>(messageBody, statusCode);
      }
    } catch (AmazonServiceException e) {
      return new VinylDNSFailureResponse<>(e.getRawResponseContent(), e.getStatusCode());
    }
  }

  private static String buildQueryString(Map<String, List<String>> params) {
    if (params == null || params.isEmpty()) return "";
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (Map.Entry<String, List<String>> e : params.entrySet()) {
      String k = urlEncode(e.getKey());
      List<String> values = e.getValue();
      if (values == null || values.isEmpty()) {
        if (!first) sb.append('&');
        sb.append(k).append('=');
        first = false;
        continue;
      }
      for (String v : values) {
        if (!first) sb.append('&');
        sb.append(k).append('=').append(urlEncode(v));
        first = false;
      }
    }
    return sb.toString();
  }

  private static String urlEncode(String s) {
    try {
      return URLEncoder.encode(s, StandardCharsets.UTF_8.name())
          .replace("+", "%20")
          .replace("*", "%2A")
          .replace("%7E", "~");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String sha256Hex(byte[] bytes) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(bytes);
      StringBuilder sb = new StringBuilder(digest.length * 2);
      for (byte b : digest) sb.append(String.format("%02x", b));
      return sb.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  //   private <S, R> VinylDNSResponse<R> executeRequest(VinylDNSRequest<S> req, Class<R>
  //   responseType) {
  //     Request<String> request = new DefaultRequest<>("VinylDNS");
  //     request.setEndpoint(req.getEndpoint());
  //     request.setResourcePath(req.getResourcePath());
  //     request.setHttpMethod(req.getHttpMethod());
  //     request.setHeaders(req.getHeaders());
  //     request.setParameters(req.getParameters());
  //     request.addHeader("Content-Type", "application/json");

  //     if (req.getPayload() != null) {
  //       // String content = gson.toJson(req.getPayload());
  //       // request.setContent(new
  // ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
  //       byte[] payload = gson.toJson(request.getRule()).getBytes(StandardCharsets.UTF_8);
  // request.setContent(new ByteArrayInputStream(payload));
  // request.addHeader("Content-Length", String.valueOf(payload.length));
  // request.addHeader("Content-Type", "application/json");

  //     }

  //     config.getSigner().sign(request, config.getCredentials());
  //     try {
  //       Response<String> response =
  //           client
  //               .requestExecutionBuilder()
  //               .errorResponseHandler(new ErrorResponseHandler())
  //               .request(request)
  //               .execute(new StringResponseHandler());
  //       int statusCode = response.getHttpResponse().getStatusCode();
  //       String messageBody = response.getAwsResponse();
  //       if (statusCode / 100 * 100 == 200) {
  //         if (responseType == String.class) {
  //           @SuppressWarnings("unchecked")
  //           R responseObject = (R) messageBody;
  //           return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
  //         }
  //         R responseObject = gson.fromJson(messageBody, responseType);
  //         return new VinylDNSSuccessResponse<>(responseObject, messageBody, statusCode);
  //       } else {
  //         return new VinylDNSFailureResponse<>(messageBody, statusCode);
  //       }
  //     } catch (AmazonServiceException e) {
  //       return new VinylDNSFailureResponse<>(e.getRawResponseContent(), e.getStatusCode());
  //     }
  //   }

  private String getBaseUrl() {
    if (config.getBaseUrl().endsWith("/")) {
      return config.getBaseUrl();
    } else {
      return config.getBaseUrl() + "/";
    }
  }
}
