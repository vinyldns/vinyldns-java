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
package io.vinyldns.java.model.membership;

public class ListGroupsRequest {
  private String groupNameFilter, startFrom;
  private Integer maxItems;

  public ListGroupsRequest() {}

  public ListGroupsRequest(String groupNameFilter) {
    this.groupNameFilter = groupNameFilter;
  }

  public ListGroupsRequest(String groupNameFilter, String startFrom, int maxItems) {
    this.groupNameFilter = groupNameFilter;
    this.startFrom = startFrom;
    this.maxItems = maxItems;
  }

  public String getGroupNameFilter() {
    return groupNameFilter;
  }

  public void setGroupNameFilter(String groupNameFilter) {
    this.groupNameFilter = groupNameFilter;
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

  public void setMaxItems(int maxItems) {
    this.maxItems = maxItems;
  }

  @Override
  public String toString() {
    return "ListGroupsRequest{"
        + "groupNameFilter='"
        + groupNameFilter
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

    ListGroupsRequest that = (ListGroupsRequest) o;

    if (groupNameFilter != null
        ? !groupNameFilter.equals(that.groupNameFilter)
        : that.groupNameFilter != null) return false;
    if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null)
      return false;
    return maxItems != null ? maxItems.equals(that.maxItems) : that.maxItems == null;
  }

  @Override
  public int hashCode() {
    int result = groupNameFilter != null ? groupNameFilter.hashCode() : 0;
    result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
    result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
    return result;
  }
}
