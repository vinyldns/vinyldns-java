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

/**
 * Request filter for GET /recordsetchange/history.
 *
 * <p>Encapsulates query parameters for retrieving record set change history.
 *
 * @since 1.0
 */
public class GetRecordSetChangeHistoryRequest {
  private final String zoneId;
  private final String fqdn;
  private final String recordType;
  private final String startFrom; // nullable
  private final Integer maxItems; // nullable

  /**
   * Creates a request to query record set change history.
   *
   * @param zoneId the zone identifier
   * @param fqdn the fully qualified domain name
   * @param recordType the record type (e.g., A, AAAA, CNAME)
   * @param startFrom an opaque pagination token to start from (nullable)
   * @param maxItems the maximum number of items to return (nullable)
   */
  public GetRecordSetChangeHistoryRequest(
      String zoneId, String fqdn, String recordType, String startFrom, Integer maxItems) {
    this.zoneId = zoneId;
    this.fqdn = fqdn;
    this.recordType = recordType;
    this.startFrom = startFrom;
    this.maxItems = maxItems;
  }

  /** @return the zone identifier */
  public String getZoneId() {
    return zoneId;
  }

  /** @return the fully qualified domain name */
  public String getFqdn() {
    return fqdn;
  }

  /** @return the record type */
  public String getRecordType() {
    return recordType;
  }

  /** @return the pagination token to start from (nullable) */
  public String getStartFrom() {
    return startFrom;
  }

  /** @return the maximum number of items to return (nullable) */
  public Integer getMaxItems() {
    return maxItems;
  }

  @Override
  public String toString() {
    return "GetRecordSetChangeHistoryRequest{"
        + "zoneId='"
        + zoneId
        + '\''
        + ", fqdn='"
        + fqdn
        + '\''
        + ", recordType='"
        + recordType
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
    if (!(o instanceof GetRecordSetChangeHistoryRequest)) return false;
    GetRecordSetChangeHistoryRequest that = (GetRecordSetChangeHistoryRequest) o;
    return zoneId.equals(that.zoneId)
        && fqdn.equals(that.fqdn)
        && recordType.equals(that.recordType)
        && ((startFrom == null && that.startFrom == null)
            || (startFrom != null && startFrom.equals(that.startFrom)))
        && ((maxItems == null && that.maxItems == null)
            || (maxItems != null && maxItems.equals(that.maxItems)));
  }

  @Override
  public int hashCode() {
    int result = zoneId.hashCode();
    result = 31 * result + fqdn.hashCode();
    result = 31 * result + recordType.hashCode();
    result = 31 * result + (startFrom != null ? startFrom.hashCode() : 0);
    result = 31 * result + (maxItems != null ? maxItems.hashCode() : 0);
    return result;
  }
}
