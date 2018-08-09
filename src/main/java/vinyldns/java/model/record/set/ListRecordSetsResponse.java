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
package vinyldns.java.model.record.set;

import java.util.Collection;

public class ListRecordSetsResponse {
    /**
     * refer to recordset model, the RecordSet data will also include the accessLevel the requesting user
     * has based off acl rules and membership in Zone Admin Group
     */
    private Collection<RecordSet> recordSets;

    /**
     * startFrom sent in request, will not be returned if not provided
     */
    private String startFrom;

    /**
     * nextId, used as startFrom parameter of next page request, will not be returned if record sets are exhausted
     */
    private String nextId;

    /**
     * maxItems sent in request, default is 100
     */
    private Integer maxItems;

    /**
     * name filter sent in request
     */
    private String recordNameFilter;

    public ListRecordSetsResponse() {
    }

    public ListRecordSetsResponse(Collection<RecordSet> recordSets, String startFrom, String nextId, Integer maxItems,
                                  String recordNameFilter) {
        this.recordSets = recordSets;
        this.startFrom = startFrom;
        this.nextId = nextId;
        this.recordNameFilter = recordNameFilter;
    }

    public Collection<RecordSet> getRecordSets() {
        return recordSets;
    }

    public void setRecordSets(Collection<RecordSet> recordSets) {
        this.recordSets = recordSets;
    }

    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(Integer maxItems) {
        this.maxItems = maxItems;
    }

    public String getRecordNameFilter() {
        return recordNameFilter;
    }

    public void setRecordNameFilter(String recordNameFilter) {
        this.recordNameFilter = recordNameFilter;
    }

    @Override
    public String toString() {
        return "ListRecordSetsResponse{" +
                "recordSets=" + recordSets +
                ", startFrom='" + startFrom + '\'' +
                ", nextId='" + nextId + '\'' +
                ", maxItems=" + maxItems +
                ", recordNameFilter='" + recordNameFilter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListRecordSetsResponse that = (ListRecordSetsResponse) o;

        if (!recordSets.equals(that.recordSets)) return false;
        if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null) return false;
        if (nextId != null ? !nextId.equals(that.nextId) : that.nextId != null) return false;
        if (maxItems != null ? !maxItems.equals(that.maxItems) : that.maxItems != null) return false;
        return recordNameFilter != null ? recordNameFilter.equals(that.recordNameFilter) : that.recordNameFilter == null;
    }

    @Override
    public int hashCode() {
        int result = recordSets.hashCode();
        result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
        result = 31 * result + (nextId != null ? nextId.hashCode() : 0);
        result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
        result = 31 * result + (recordNameFilter != null ? recordNameFilter.hashCode() : 0);
        return result;
    }
}
