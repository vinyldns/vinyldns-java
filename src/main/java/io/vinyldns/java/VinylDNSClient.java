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

import io.vinyldns.java.model.batch.*;
import io.vinyldns.java.model.membership.*;
import io.vinyldns.java.model.record.set.*;
import io.vinyldns.java.model.zone.*;
import io.vinyldns.java.responses.VinylDNSFailureResponse;
import io.vinyldns.java.responses.VinylDNSResponse;
import io.vinyldns.java.responses.VinylDNSSuccessResponse;

public interface VinylDNSClient {
  // Zone
  /**
   * Retrieves the list of zones a user has access to. The zone name is only sorted alphabetically.
   *
   * @param request See {@link ListZonesRequest ListZonesRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListZonesResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ListZonesResponse> listZones(ListZonesRequest request);

  /**
   * Creates a zone.
   *
   * @param zone See {@link Zone Zone model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetZoneResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;GetZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ZoneResponse> createZone(Zone zone);

  /**
   * Retrieves zone by ID.
   *
   * @param request See {@link ZoneRequest ZoneRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetZoneResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;GetZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<GetZoneResponse> getZone(ZoneRequest request);

  /**
   * Updates a zone.
   *
   * @param zone See {@link Zone Zone model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetZoneResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ZoneResponse> updateZone(Zone zone);

  /**
   * Deletes zone by ID.
   *
   * @param request See {@link ZoneRequest ZoneRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetZoneResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;GetZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ZoneResponse> deleteZone(ZoneRequest request);

  /**
   * Retrieves the most recent 100 zone changes. This call will return a subset
   * of the full information in each change, as detailed in the attributes section.
   *
   * @param request See {@link ListZoneChangesRequest ListBatchChangesRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListZoneChangesResponse&gt;}
   *     in case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListZoneChangesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ListZoneChangesResponse> listZoneChanges(ListZoneChangesRequest request);

  /**
   * Sync zone info with existing zone info.
   *
   * @param request See {@link ZoneRequest ZoneRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ZoneResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ZoneResponse&gt;} in case of failure
   */
  VinylDNSResponse<ZoneResponse> syncZone(ZoneRequest request);

  // RecordSet
  /**
   * Retrieves a list of RecordSets from the zone
   *
   * @param request See {@link ListRecordSetsRequest ListRecordSetsRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListRecordSetsResponse&gt;}
   *     in case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListRecordSetsResponse&gt;} in case of failure
   */
  VinylDNSResponse<ListRecordSetsResponse> listRecordSets(ListRecordSetsRequest request);

  /**
   * Retrieves a RecordSet in a specified zone
   *
   * @param request See {@link GetRecordSetRequest GetRecordSetRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetRecordSetResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;GetRecordSetResponse&gt;} in case of failure
   */
  VinylDNSResponse<GetRecordSetResponse> getRecordSet(GetRecordSetRequest request);

  /**
   * Creates a RecordSet in a specified zone
   *
   * @param request See {@link CreateRecordSetRequest CreateRecordSetRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;RecordSetChange&gt;} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;RecordSetChange&gt;} in case of failure
   */
  VinylDNSResponse<RecordSetChange> createRecordSet(CreateRecordSetRequest request);

  /**
   * Update a RecordSet in a specified zone
   *
   * @param request See {@link UpdateRecordSetRequest UpdateRecordSetRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;RecordSetChange&gt;} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;RecordSetChange&gt;} in case of failure
   */
  VinylDNSResponse<RecordSetChange> updateRecordSet(UpdateRecordSetRequest request);

  /**
   * Delete a RecordSet in a specified zone
   *
   * @param request See {@link DeleteRecordSetRequest DeleteRecordSetRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;RecordSetChange&gt;} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;RecordSetChange&gt;} in case of failure
   */
  VinylDNSResponse<RecordSetChange> deleteRecordSet(DeleteRecordSetRequest request);

  /**
   * List record set changes in a specific zone
   * @param request See {@link ListRecordSetChangesRequest ListRecordSetChangesRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListRecordSetChangesResponse&gt;} in
   *    case of success and {@link VinylDNSFailureResponse
   *    VinylDNSFailureResponse&lt;ListRecordSetChangesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ListRecordSetChangesResponse> listRecordSetChanges(ListRecordSetChangesRequest request);

  /**
   * Retrieves a RecordSetChange in a specified zone
   *
   * @param request See {@link GetRecordSetChangeRequest GetRecordSetChangeRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;RecordSetChange&gt;} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;RecordSetChange&gt;} in case of failure
   */
  VinylDNSResponse<RecordSetChange> getRecordSetChange(GetRecordSetChangeRequest request);

  // Groups
  /**
   * Retrieves a group by ID
   *
   * @param request See {@link GetGroupRequest GetGroupRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;Group&gt;} in case of success
   *     and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;Group&gt;} in case of
   *     failure.
   */
  VinylDNSResponse<Group> getGroup(GetGroupRequest request);

  /**
   * Create a group.
   *
   * @param request See {@link CreateGroupRequest CreateGroupRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;Group&gt;} in case of success
   *     and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;Group&gt;} in case of
   *     failure.
   */
  VinylDNSResponse<Group> createGroup(CreateGroupRequest request);

  /**
   * Delete a group with a specific ID
   *
   * @param request See {@link DeleteGroupRequest DeleteGroupRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;Group&gt;} in case of success
   *     and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;Group&gt;} in case of
   *     failure.
   */
  VinylDNSResponse<Group> deleteGroup(DeleteGroupRequest request);

  /**
   * Retrieves the list of groups a user has access to.
   *
   * @param request See {@link ListGroupsRequest ListGroupsRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListGroupsResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListGroupsResponse&gt;} in case of failure.
   */
  VinylDNSResponse<ListGroupsResponse> listGroups(ListGroupsRequest request);

  // Batch
  /**
   * Retrieves the most recent 100 batch changes created by the user. This call will return a subset
   * of the full information in each change, as detailed in the attributes section.
   *
   * @param request See {@link ListBatchChangesRequest ListBatchChangesRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListBatchChangesResponse&gt;}
   *     in case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListBatchChangesResponse&gt;} in case of failure
   */
  VinylDNSResponse<ListBatchChangesResponse> listBatchChanges(ListBatchChangesRequest request);

  /**
   * Retrieves a batch change given the batch change ID. Only the user who created a batch change
   * will have access to get it.
   *
   * @param id Unique identifier assigned to each created batch change.
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> getBatchChanges(String id);

  /**
   * Creates a batch change with SingleAddChanges and/or SingleDeleteChanges across different zones.
   * A delete and add of the same record will be treated as an update on that record set. Regardless
   * of the input order in the batch change, all deletes for the same recordset will be logically
   * applied before the adds. Current supported record types for creating a batch change are: A,
   * AAAA, CNAME, and PTR. A batch must contain at least one change and no more than 20 changes.
   *
   * @param request See {@link CreateBatchRequest CreateBatchRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> createBatchChanges(CreateBatchRequest request);

  // ToDo: Membership:
  // ToDo:   List Group Activity
  // ToDo:   List Group Members
  // ToDo:   List Group Admins
  // ToDo:   List Groups
  // ToDo:   Update Group
}
