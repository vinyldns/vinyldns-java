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
package io.vinyldns.java.model.record.set;

import io.vinyldns.java.model.record.RecordType;
import java.util.Collection;

public class SearchRecordSetsResponse
{

    private Collection<RecordSet> recordSets;
    private String startFrom;
    private String nextId;
    private Integer maxItems;
    private String recordNameFilter;
    private Collection<RecordType> recordTypeFilter;
    private String recordOwnerGroupFilter;
    private String nameSort;

    public SearchRecordSetsResponse(
            Collection<RecordSet> recordSets,
            String startFrom,
            String nextId,
            Integer maxItems,
            String recordNameFilter,
            Collection<RecordType> recordTypeFilter,
            String recordOwnerGroupFilter,
            String nameSort) {
        this.recordSets = recordSets;
        this.startFrom = startFrom;
        this.nextId = nextId;
        this.maxItems = maxItems;
        this.recordNameFilter = recordNameFilter;
        this.recordTypeFilter = recordTypeFilter;
        this.recordOwnerGroupFilter = recordOwnerGroupFilter;
        this.nameSort = nameSort;
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

    public Collection<RecordType> getRecordTypeFilter() {
        return recordTypeFilter;
    }

    public void setRecordTypeFilter(Collection<RecordType> recordTypeFilter) {
        this.recordTypeFilter = recordTypeFilter;
    }

    public String getRecordOwnerGroupFilter() {
        return recordOwnerGroupFilter;
    }

    public void setRecordOwnerGroupFilter(String recordOwnerGroupFilter) {
        this.recordOwnerGroupFilter = recordOwnerGroupFilter;
    }

    public String getNameSort() {
        return nameSort;
    }

    public void setNameSort(String nameSort) {
        this.nameSort = nameSort;
    }
}
