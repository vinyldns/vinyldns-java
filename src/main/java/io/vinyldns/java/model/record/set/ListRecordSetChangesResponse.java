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

import java.util.List;
import java.util.Objects;

public class ListRecordSetChangesResponse {
  private String zoneId;
  private List<RecordSetChange> recordSetChanges;
  private Integer nextId; // optional
  private Integer startFrom; // optional
  private Integer maxItems;

  public ListRecordSetChangesResponse(
      String zoneId,
      List<RecordSetChange> recordSetChanges,
      Integer nextId,
      Integer startFrom,
      Integer maxItems) {
    this.zoneId = zoneId;
    this.recordSetChanges = recordSetChanges;
    this.nextId = nextId;
    this.startFrom = startFrom;
    this.maxItems = maxItems;
  }

  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public List<RecordSetChange> getRecordSetChanges() {
    return recordSetChanges;
  }

  public void setRecordSetChanges(List<RecordSetChange> recordSetChanges) {
    this.recordSetChanges = recordSetChanges;
  }

  public Integer getNextId() {
    return nextId;
  }

  public void setNextId(Integer nextId) {
    this.nextId = nextId;
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

  public void setMaxItems(Integer maxItems) {
    this.maxItems = maxItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ListRecordSetChangesResponse)) return false;
    ListRecordSetChangesResponse that = (ListRecordSetChangesResponse) o;
    return Objects.equals(getZoneId(), that.getZoneId())
        && Objects.equals(getRecordSetChanges(), that.getRecordSetChanges())
        && Objects.equals(getNextId(), that.getNextId())
        && Objects.equals(getStartFrom(), that.getStartFrom())
        && Objects.equals(getMaxItems(), that.getMaxItems());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getZoneId(), getRecordSetChanges(), getNextId(), getStartFrom(), getMaxItems());
  }

  @Override
  public String toString() {
    return "ListRecordSetChangesResponse{"
        + "zoneId='"
        + zoneId
        + '\''
        + ", recordSetChanges="
        + recordSetChanges
        + ", nextId='"
        + nextId
        + '\''
        + ", startFrom='"
        + startFrom
        + '\''
        + ", maxItems="
        + maxItems
        + '}';
  }
}
