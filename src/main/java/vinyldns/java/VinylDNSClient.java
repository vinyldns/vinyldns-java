/**
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
package vinyldns.java;

import vinyldns.java.model.record.set.*;
import vinyldns.java.model.record.set.*;
import vinyldns.java.model.zone.ListZonesRequest;
import vinyldns.java.model.zone.ListZonesResponse;
import vinyldns.java.responses.VinylDNSResponse;
import vinyldns.java.responses.VinylDNSFailureResponse;
import vinyldns.java.responses.VinylDNSSuccessResponse;

public interface VinylDNSClient {
    //Zone
    /**
     * Retrieves the list of zones a user has access to. The zone name is only sorted alphabetically.
     * @param request See {@link ListZonesRequest ListZonesRequest Model}
     * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<ListZonesResponse>} in case
     * of success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<ListZonesResponse>} in case
     * of failure
     */
    VinylDNSResponse<ListZonesResponse> listZones(ListZonesRequest request);

    //RecordSet
    /**
     * Retrieves a list of RecordSets from the zone
     * @param request See {@link ListRecordSetsRequest ListRecordSetsRequest Model}
     * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<ListRecordSetsResponse>} in case
     * of success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<ListRecordSetsResponse>} in case
     * of failure
     */
    VinylDNSResponse<ListRecordSetsResponse> listRecordSets(ListRecordSetsRequest request);

    /**
     * Creates a RecordSet in a specified zone
     * @param request See {@link CreateRecordSetRequest CreateRecordSetRequest Model}
     * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<RecordSetChange>} in case
     * of success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<RecordSetChange>} in case
     * of failure
     */
    VinylDNSResponse<RecordSetChange> createRecordSet(CreateRecordSetRequest request);

    /**
     * Delete a RecordSet in a specified zone
     * @param request See {@link DeleteRecordSetRequest DeleteRecordSetRequest Model}
     * @return {@link VinylDNSSuccessResponse VinylDNSSuccessResponse<RecordSetChange>} in case
     * of success and {@link VinylDNSFailureResponse VinylDNSFailureResponse<RecordSetChange>} in case
     * of failure
     */
    VinylDNSResponse<RecordSetChange> deleteRecordSet(DeleteRecordSetRequest request);
}
