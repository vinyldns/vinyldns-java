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

import java.util.Set;

public class ListMembersResponse {
  private final Set<UserInfo> members;
  private String startFrom, nextId; // optional
  private final Integer maxItems;

  public ListMembersResponse(
      Set<UserInfo> members, String startFrom, String nextId, Integer maxItems) {
    this.members = members;
    this.startFrom = startFrom;
    this.nextId = nextId;
    this.maxItems = maxItems;
  }

  public Set<UserInfo> getMembers() {
    return members;
  }

  public Integer getMaxItems() {
    return maxItems;
  }

  public String getStartFrom() {
    return startFrom;
  }

  public String getNextId() {
    return nextId;
  }

  @Override
  public String toString() {
    return "ListMembersResponse{"
        + "members="
        + members
        + ", startFrom='"
        + startFrom
        + '\''
        + ", nextId='"
        + nextId
        + '\''
        + ", maxItems="
        + maxItems
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ListMembersResponse that = (ListMembersResponse) o;

    if (!members.equals(that.members)) return false;
    if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null)
      return false;
    if (nextId != null ? !nextId.equals(that.nextId) : that.nextId != null) return false;
    return maxItems != null ? maxItems.equals(that.maxItems) : that.maxItems == null;
  }

  @Override
  public int hashCode() {
    int result = members.hashCode();
    result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
    return result;
  }
}
