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
package io.vinyldns.java.model.zone;

import java.util.List;

public class ListAbandonedZonesResponse {
  private final List<AbandonedZoneChangeResponse> zonesDeletedInfo;

  /**
   * (optional) The startFrom parameter that was sent in on the HTTP request. Will not be present if
   * the startFrom parameter was not sent
   */
  private String startFrom;

  /**
   * (optional) The identifier to be passed in as the startFrom parameter to retrieve the next page
   * of results. If there are no results left, this field will not be present.
   */
  private String nextId;

  /** The maxItems parameter that was sent in on the HTTP request. This will be 100 if not sent. */
  private final Integer maxItems;

  private Boolean ignoreAccess;

  public ListAbandonedZonesResponse(
      List<AbandonedZoneChangeResponse> zonesDeletedInfo, Integer maxItems) {
    this.zonesDeletedInfo = zonesDeletedInfo;
    this.maxItems = maxItems;
  }

  public ListAbandonedZonesResponse(
      List<AbandonedZoneChangeResponse> zonesDeletedInfo,
      String startFrom,
      String nextId,
      Integer maxItems,
      Boolean ignoreAccess) {
    this.zonesDeletedInfo = zonesDeletedInfo;
    this.startFrom = startFrom;
    this.nextId = nextId;
    this.maxItems = maxItems;
    this.ignoreAccess = ignoreAccess;
  }

  public List<AbandonedZoneChangeResponse> getAbondonedZones() {
    return zonesDeletedInfo;
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

  public Integer getMaxItems() {
    return maxItems;
  }

  public Boolean getIgnoreAccess() {
    return ignoreAccess;
  }

  @Override
  public String toString() {
    return "abandonedZoneChangeResponse{"
        + "zonesDeletedInfo="
        + zonesDeletedInfo
        + ", startFrom='"
        + startFrom
        + '\''
        + ", nextId='"
        + nextId
        + '\''
        + ", maxItems="
        + maxItems
        + '\''
        + ", ignoreAccess="
        + ignoreAccess
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ListAbandonedZonesResponse that = (ListAbandonedZonesResponse) o;

    if (!zonesDeletedInfo.equals(that.zonesDeletedInfo)) return false;
    if (startFrom != null ? !startFrom.equals(that.startFrom) : that.startFrom != null)
      return false;
    if (nextId != null ? !nextId.equals(that.nextId) : that.nextId != null) return false;
    return maxItems != null ? maxItems.equals(that.maxItems) : that.maxItems == null;
  }

  @Override
  public int hashCode() {
    int result = zonesDeletedInfo.hashCode();
    result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
    result = 31 * result + (nextId != null ? nextId.hashCode() : 0);
    result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
    result = 31 * result + (ignoreAccess != null ? ignoreAccess.hashCode() : 0);
    return result;
  }
}
