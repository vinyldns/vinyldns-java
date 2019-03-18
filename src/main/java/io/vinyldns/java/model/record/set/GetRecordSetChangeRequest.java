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
package io.vinyldns.java.model.record.set;

public class GetRecordSetChangeRequest {
    private final String zoneId, recordSetId, recordSetChangeId;

    public GetRecordSetChangeRequest(String zoneId, String recordSetId, String recordSetChangeId) {
        this.zoneId = zoneId;
        this.recordSetId = recordSetId;
        this.recordSetChangeId = recordSetChangeId;
    }

    public String getZoneId() { return zoneId; }

    public String getRecordSetId() { return recordSetId; }

    public String getRecordSetChangeId() { return recordSetChangeId; }

    @Override
    public String toString() {
        return "GetRecordSetChangeRequest{"
            + "zoneId='"
            + zoneId
            + "\'"
            + ", recordSetId='"
            + recordSetId
            + "\'"
            + ", recordSetChangeId='"
            + recordSetChangeId
            + "\'"
            + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetRecordSetChangeRequest that = (GetRecordSetChangeRequest) o;
        if (!zoneId.equals(that.zoneId)) return false;
        if (!recordSetId.equals(that.recordSetId)) return false;
        return recordSetChangeId.equals(that.getRecordSetChangeId());
    }

    @Override
    public int hashCode() {
        int result = zoneId.hashCode();
        result = 31 * result + recordSetId.hashCode();
        result = 31 * result + recordSetChangeId.hashCode();
        return result;
    }
}
