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

import io.vinyldns.java.model.Order;
import java.util.Objects;

public class SearchRecordSetsRequest {
  private final String recordNameFilter;
  private String recordOwnerGroupFilter;
  private Order nameSort;
  private String startFrom;
  private Integer maxItems;

  public SearchRecordSetsRequest(final String recordNameFilter) {
    this.recordNameFilter = recordNameFilter;
  }

  public SearchRecordSetsRequest(
      String recordNameFilter,
      String recordOwnerGroupFilter,
      Order nameSort,
      String startFrom,
      Integer maxItems) {
    this.recordNameFilter = recordNameFilter;
    this.recordOwnerGroupFilter = recordOwnerGroupFilter;
    this.nameSort = nameSort;
    this.startFrom = startFrom;
    this.maxItems = maxItems;
  }

  @Override
  public String toString() {
    return "ListRecordSetGlobalRequest{"
        + "recordNameFilter='"
        + recordNameFilter
        + '\''
        + ", recordOwnerGroupFilter='"
        + recordOwnerGroupFilter
        + '\''
        + ", nameSort="
        + nameSort
        + ", startFrom='"
        + startFrom
        + '\''
        + ", maxItems="
        + maxItems
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SearchRecordSetsRequest that = (SearchRecordSetsRequest) o;
    return recordNameFilter.equals(that.recordNameFilter)
        && recordOwnerGroupFilter.equals(that.recordOwnerGroupFilter)
        && nameSort == that.nameSort
        && startFrom.equals(that.startFrom)
        && maxItems.equals(that.maxItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recordNameFilter, recordOwnerGroupFilter, nameSort, startFrom, maxItems);
  }

  public String getRecordNameFilter() {
    return recordNameFilter;
  }

  public String getRecordOwnerGroupFilter() {
    return recordOwnerGroupFilter;
  }

  public void setRecordOwnerGroupFilter(String recordOwnerGroupFilter) {
    this.recordOwnerGroupFilter = recordOwnerGroupFilter;
  }

  public Order getNameSort() {
    return nameSort;
  }

  public void setNameSort(Order nameSort) {
    this.nameSort = nameSort;
  }

  public String getStartFrom() {
    return startFrom;
  }

  public void setStartFrom(String startFrom) {
    this.startFrom = startFrom;
  }

  public Integer getMaxItems() {
    return maxItems;
  }

  public void setMaxItems(Integer maxItems) {
    this.maxItems = maxItems;
  }
}
