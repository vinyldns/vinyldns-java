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
package vinyldns.java.model.record.set;

public class ListRecordSetsRequest {
  /** List a RecordSet in a specified zone */
  private String zoneId;

  /**
   * One or more characters contained in the name of the record set to search for. For example
   * comcast. This is a contains search only, no wildcards or regular expressions are supported
   */
  private String recordNameFilter;

  /**
   * In order to advance through pages of results, the startFrom is set to the nextId that is
   * returned on the previous response. It is up to the client to maintain previous pages if the
   * client wishes to advance forward and backward. If not specified, will return the first page of
   * results
   */
  private String startFrom;

  /**
   * The number of items to return in the page. Valid values are 1 to 100. Defaults to 100 if not
   * provided.
   */
  private Integer maxItems;

  public ListRecordSetsRequest(String zoneId) {
    this.zoneId = zoneId;
  }

  public ListRecordSetsRequest(String zoneId, String recordNameFilter) {
    this.zoneId = zoneId;
    this.recordNameFilter = recordNameFilter;
  }

  public ListRecordSetsRequest(
      String zoneId, String recordNameFilter, String startFrom, Integer maxItems) {
    this.zoneId = zoneId;
    this.recordNameFilter = recordNameFilter;
    this.startFrom = startFrom;
    this.maxItems = maxItems;
  }

  public String getRecordNameFilter() {
    return recordNameFilter;
  }

  public void setRecordNameFilter(String recordNameFilter) {
    this.recordNameFilter = recordNameFilter;
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

  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  @Override
  public String toString() {
    return "ListRecordSetsRequest{"
        + "zoneId='"
        + zoneId
        + '\''
        + ", recordNameFilter='"
        + recordNameFilter
        + '\''
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

    ListRecordSetsRequest that = (ListRecordSetsRequest) o;

    if (!zoneId.equals(that.zoneId)) return false;
    if (recordNameFilter != null
        ? !recordNameFilter.equals(that.recordNameFilter)
        : that.recordNameFilter != null) return false;
    if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null)
      return false;
    return maxItems != null ? maxItems.equals(that.maxItems) : that.maxItems == null;
  }

  @Override
  public int hashCode() {
    int result = zoneId.hashCode();
    result = 31 * result + (recordNameFilter != null ? recordNameFilter.hashCode() : 0);
    result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
    result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
    return result;
  }
}
