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
package vinyldns.java;

import vinyldns.java.model.batch.BatchResponse;
import vinyldns.java.model.batch.CreateBatchRequest;
import vinyldns.java.model.batch.ListBatchChangesRequest;
import vinyldns.java.model.batch.ListBatchChangesResponse;
import vinyldns.java.model.record.set.*;
import vinyldns.java.model.zone.ListZonesRequest;
import vinyldns.java.model.zone.ListZonesResponse;
import vinyldns.java.responses.VinylDNSFailureResponse;
import vinyldns.java.responses.VinylDNSResponse;
import vinyldns.java.responses.VinylDNSSuccessResponse;

public interface VinylDNSClient {
  // Zone
  /**
   * Retrieves the list of zones a user has access to. The zone name is only sorted alphabetically.
   *
   * @param request See {@link ListZonesRequest ListZonesRequest Model}
   * @return {@link VinylDNSSuccessResponse
   *     VinylDNSSuccessResponse<vinyldns.java.model.zone.ListZonesResponse>} in case of success and
   *     {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse<vinyldns.java.model.zone.ListZonesResponse>} in case of failure
   */
  VinylDNSResponse<ListZonesResponse> listZones(ListZonesRequest request);
  // ToDo: Create Zone
  // ToDo: Get Zone
  // ToDo: Delete Zone
  // ToDo: Update Zone
  // ToDo: List Zone Changes
  // ToDo: Sync Zone

  // RecordSet
  /**
   * Retrieves a list of RecordSets from the zone
   *
   * @param request See {@link ListRecordSetsRequest ListRecordSetsRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<ListRecordSetsResponse>} in case
   *     of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse<ListRecordSetsResponse>} in case of failure
   */
  VinylDNSResponse<ListRecordSetsResponse> listRecordSets(ListRecordSetsRequest request);

  /**
   * Creates a RecordSet in a specified zone
   *
   * @param request See {@link CreateRecordSetRequest CreateRecordSetRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<RecordSetChange>} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<RecordSetChange>} in
   *     case of failure
   */
  VinylDNSResponse<RecordSetChange> createRecordSet(CreateRecordSetRequest request);

  /**
   * Delete a RecordSet in a specified zone
   *
   * @param request See {@link DeleteRecordSetRequest DeleteRecordSetRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<RecordSetChange>} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<RecordSetChange>} in
   *     case of failure
   */
  VinylDNSResponse<RecordSetChange> deleteRecordSet(DeleteRecordSetRequest request);
  // ToDo: List RecordSet Changes
  // ToDo: Get RecordSet
  // ToDo: Update RecordSet
  // ToDo: Get RecordSet Change

  // Batch
  /**
   * Retrieves the most recent 100 batch changes created by the user. This call will return a subset
   * of the full information in each change, as detailed in the attributes section.
   *
   * @param request See {@link ListBatchChangesRequest ListBatchChangesRequest Model}
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<ListBatchChangesResponse>} in
   *     case of success and {@link VinylDNSFailureResponse
   *     VinylDNSFailureResponse<ListBatchChangesResponse>} in case of failure
   */
  VinylDNSResponse<ListBatchChangesResponse> listBatchChanges(ListBatchChangesRequest request);

  /**
   * Retrieves a batch change given the batch change ID. Only the user who created a batch change
   * will have access to get it.
   *
   * @param id Unique identifier assigned to each created batch change.
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<BatchResponse>} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<BatchResponse>} in case
   *     of failure
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
   * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<BatchResponse>} in case of
   *     success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<BatchResponse>} in case
   *     of failure
   */
  VinylDNSResponse<BatchResponse> createBatchChanges(CreateBatchRequest request);

  // ToDo: Membership:
  // ToDo:   List Group Activity
  // ToDo:   List Group Members
  // ToDo:   List Group Admins
  // ToDo:   List Groups
  // ToDo:   Get Group
  // ToDo:   Delete Group
  // ToDo:   Update Group
  // ToDo:   Create Group
}
