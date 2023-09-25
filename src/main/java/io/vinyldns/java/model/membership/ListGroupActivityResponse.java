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
package io.vinyldns.java.model.membership;

import java.util.Set;

public class ListGroupActivityResponse {
  private final Set<GroupChange> changes;
  private Integer startFrom, nextId; // optional
  private final Integer maxItems;

  public ListGroupActivityResponse(Set<GroupChange> changes, Integer maxItems) {
    this.changes = changes;
    this.maxItems = maxItems;
  }

  public ListGroupActivityResponse(
      Set<GroupChange> changes, Integer startFrom, Integer nextId, Integer maxItems) {
    this.changes = changes;
    this.startFrom = startFrom;
    this.nextId = nextId;
    this.maxItems = maxItems;
  }

  public Set<GroupChange> getGroups() {
    return changes;
  }

  public Integer getStartFrom() {
    return startFrom;
  }

  public void setStartFrom(Integer startFrom) {
    this.startFrom = startFrom;
  }

  public Integer getMaxItems() {
    return maxItems;
  }

  public Set<GroupChange> getChanges() {
    return changes;
  }

  public Integer getNextId() {
    return nextId;
  }

  public void setNextId(Integer nextId) {
    this.nextId = nextId;
  }

  @Override
  public String toString() {
    return "ListGroupActivityResponse{"
        + "changes='"
        + changes
        + '\''
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

    ListGroupActivityResponse that = (ListGroupActivityResponse) o;

    if (!changes.equals(that.changes)) return false;
    if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null)
      return false;
    if (nextId != null ? !nextId.equals(that.nextId) : that.nextId != null) return false;
    return maxItems != null ? maxItems.equals(that.maxItems) : that.maxItems == null;
  }

  @Override
  public int hashCode() {
    int result = changes.hashCode();
    result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
    result = 31 * result + (nextId != null ? nextId.hashCode() : 0);
    result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
    return result;
  }
}
