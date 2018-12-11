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

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.http.AmazonHttpClient;
import com.google.gson.Gson;
import io.vinyldns.java.handlers.ErrorResponseHandler;
import io.vinyldns.java.handlers.StringResponseHandler;
import io.vinyldns.java.model.Methods;
import io.vinyldns.java.model.batch.BatchResponse;
import io.vinyldns.java.model.batch.CreateBatchRequest;
import io.vinyldns.java.model.batch.ListBatchChangesRequest;
import io.vinyldns.java.model.batch.ListBatchChangesResponse;
import io.vinyldns.java.model.membership.ListGroupsRequest;
import io.vinyldns.java.model.membership.ListGroupsResponse;
import io.vinyldns.java.model.record.set.CreateRecordSetRequest;
import io.vinyldns.java.model.record.set.DeleteRecordSetRequest;
import io.vinyldns.java.model.record.set.ListRecordSetsRequest;
import io.vinyldns.java.model.record.set.ListRecordSetsResponse;
import io.vinyldns.java.model.record.set.RecordSetChange;
import io.vinyldns.java.model.zone.*;
import io.vinyldns.java.responses.VinylDNSFailureResponse;
import io.vinyldns.java.responses.VinylDNSResponse;
import io.vinyldns.java.responses.VinylDNSSuccessResponse;
import io.vinyldns.java.serializers.SerializationFactory;
import java.io.ByteArrayInputStream;

public class VinylDNSClientImpl implements VinylDNSClient {
  private VinylDNSClientConfig config;

  private AmazonHttpClient client;

  Gson gson = SerializationFactory.createGson();

  public VinylDNSClientImpl(VinylDNSClientConfig config) {
    this.config = config;

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
  public VinylDNSResponse<GetZoneResponse> getZone(GetZoneRequest request) {
    String path = "zones/" + request.getZoneId();
    return executeRequest(
        new VinylDNSRequest<>(Methods.GET.name(), getBaseUrl(), path, null), GetZoneResponse.class);
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
  public VinylDNSResponse<RecordSetChange> createRecordSet(CreateRecordSetRequest request) {
    String path = "zones/" + request.getZoneId() + "/recordsets";
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, request),
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
    return executeRequest(
        new VinylDNSRequest<>(Methods.POST.name(), getBaseUrl(), path, request),
        BatchResponse.class);
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
        request.setContent(new ByteArrayInputStream(content.getBytes("UTF-8")));
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
