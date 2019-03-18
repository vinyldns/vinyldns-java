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
package io.vinyldns.java.model.batch;

public class GetRecordSetRequest {
    private final String recordSetId, zoneId;

    public GetRecordSetRequest(String zoneId, String recordSetId) {
        this.zoneId = zoneId;
        this.recordSetId = recordSetId;
    }

    public String getRecordSetId() { return recordSetId; }

    public String getZoneId() { return zoneId; }

    @Override
    public String toString() {
        return "GetRecordSetRequest{"
            + "recordSetId='"
            + recordSetId
            + "\'"
            + ", zoneId='"
            + zoneId
            + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetRecordSetRequest that = (GetRecordSetRequest) o;
        if (!zoneId.equals(that.zoneId)) return false;
        return recordSetId.equals(that.recordSetId);
    }

    @Override
    public int hashCode() {
        int result = zoneId.hashCode();
        result = 31 * result + recordSetId.hashCode();
        return result;
    }
}
