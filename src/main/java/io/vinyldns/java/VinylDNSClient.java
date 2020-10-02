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
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ZoneResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;ZoneResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<ZoneResponse> createZone(Zone zone);

  /**
   * Retrieves zone by ID.
   *
   * @param request See {@link ZoneRequest ZoneRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetZoneResponse&gt;} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;GetZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<GetZoneResponse> getZone(ZoneRequest request);

  /**
   * Retrieves zone by name.
   *
   * @param zoneName Name to use for zone lookup
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;GetZoneResponse&gt;} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;GetZonesResponse&gt;} in case of failure
   */
  VinylDNSResponse<GetZoneResponse> getZoneByName(String zoneName);

  /**
   * Updates a zone.
   *
   * @param zone See {@link Zone Zone model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ZoneResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;ZoneResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<ZoneResponse> updateZone(Zone zone);

  /**
   * Deletes zone by ID.
   *
   * @param request See {@link ZoneRequest ZoneRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ZoneResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;ZoneResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<ZoneResponse> deleteZone(ZoneRequest request);

  /**
   * Retrieves up to 100 (default) zone changes sorted by most recent. This call will return a
   * subset of the full information in each change, as detailed in the attributes section.
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
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ZoneResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;ZoneResponse&gt;} in
   *     case of failure
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
   *
   * @param request See {@link ListRecordSetChangesRequest ListRecordSetChangesRequest Model}
   * @return {@link VinylDNSSuccessResponse
   *     VinylDNSSuccessResponse&lt;ListRecordSetChangesResponse&gt;} in case of success and {@link
   *     VinylDNSFailureResponse VinylDNSFailureResponse&lt;ListRecordSetChangesResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<ListRecordSetChangesResponse> listRecordSetChanges(
      ListRecordSetChangesRequest request);

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
   * Update a group.
   *
   * @param request See {@link UpdateGroupRequest CreateGroupRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;Group&gt;} in case of success
   *     and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;Group&gt;} in case of
   *     failure.
   */
  VinylDNSResponse<Group> updateGroup(UpdateGroupRequest request);

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

  /**
   * Retrieves the list of admins for a group.
   *
   * @param groupId groupId
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListAdminsResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListAdminsResponse&gt;} in case of failure.
   */
  VinylDNSResponse<ListAdminsResponse> listAdmins(String groupId);

  /**
   * Retrieves the atmost 100 members for a group.
   *
   * @param request See {@link ListMembersRequest ListMembersRequest}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;ListMembersResponse&gt;} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse&lt;ListMembersResponse&gt;} in case of failure.
   */
  VinylDNSResponse<ListMembersResponse> listMembers(ListMembersRequest request);

  /**
   * Retrieves the list of group activity for a groupId.
   *
   * @param request See {@link ListGroupActivityRequest ListGroupActivityRequest}
   * @return {@link VinylDNSSuccessResponse
   *     VinylDNSSuccessResponse&lt;ListGroupActivityResponse&gt;} in case of success and {@link
   *     VinylDNSFailureResponse VinylDNSFailureResponse&lt;ListGroupActivityResponse&gt;} in case
   *     of failure.
   */
  VinylDNSResponse<ListGroupActivityResponse> listGroupActivity(ListGroupActivityRequest request);

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
   * AAAA, CNAME, MX, PTR and TXT. A batch must contain at least one change and no more than the
   * configured number of changes.
   *
   * @param request See {@link CreateBatchRequest CreateBatchRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> createBatchChanges(CreateBatchRequest request);

  /**
   * Approve a batch change that is currently in PendingReview status. Approving user must be a
   * system administrator (i.e. support or super user). Successful approval will cause the batch to
   * enter the queue for auto-processing.
   *
   * @param id Identifier for batch change requiring review.
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> approveBatchChanges(String id);

  /**
   * Approve a batch change that is currently in PendingReview status. Approving user must be a
   * system administrator (i.e. support or super user). Successful approval will cause the batch to
   * enter the queue for auto-processing.
   *
   * @param id Identifier for batch change requiring review.
   * @param reviewComment Comment for the review.
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> approveBatchChanges(String id, String reviewComment);

  /**
   * Reject a batch change that is currently in PendingReview status. Rejecting user must be a
   * system administrator (i.e. support or super user). Successful rejection will cause the batch to
   * enter a Rejected state where none of the changes will be implemented.
   *
   * @param id Identifier for batch change requiring review.
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> rejectBatchChanges(String id);

  /**
   * Reject a batch change that is currently in PendingReview status. Rejecting user must be a
   * system administrator (i.e. support or super user). Successful rejection will cause the batch to
   * enter a Rejected state where none of the changes will be implemented.
   *
   * @param id Identifier for batch change requiring review.
   * @param reviewComment Comment for the review.
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> rejectBatchChanges(String id, String reviewComment);

  /**
   * Cancel a batch change that is currently in PendingReview status. Cancelling user must be the
   * submitter of the batch change; system administrators (i.e. support or super users) will have to
   * reject the batch change instead of cancelling. Successful cancellation will cause the batch to
   * enter a Cancelled state where none of the changes will be implemented.
   *
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse&lt;BatchResponse&gt;} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse&lt;BatchResponse&gt;} in
   *     case of failure
   */
  VinylDNSResponse<BatchResponse> cancelBatchChanges(String id);

  /**
   * Retrieves a list of RecordSets globally in the VinylDNS database based on search criteria. A
   * minimum of two alpha-numeric characters is required.
   *
   * @param request See {@link SearchRecordSetsRequest SearchRecordSetsRequest Model}
   * @return {@link VinylDNSSuccessResponse
   *     VinylDNSSuccessResponse&lt;SearchRecordSetsResponse&gt;} in case of success and {@link
   *     VinylDNSFailureResponse VinylDNSFailureResponse&lt;SearchRecordSetsResponse&gt;} in case
   *     of failure
   */
  VinylDNSResponse<SearchRecordSetsResponse> searchRecordSets(
          SearchRecordSetsRequest request);
}
