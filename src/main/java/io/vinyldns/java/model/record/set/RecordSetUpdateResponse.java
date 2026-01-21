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

import java.util.Objects;

/** Response object for PUT /zones/{zoneId}/recordsets/{recordSetId}. */
public class RecordSetUpdateResponse {
  private final String recordSetId;
  private final String zoneId;
  private final String ownerGroupId;
  private final OwnershipTransferStatus ownershipTransferStatus;
  private final String message;

  public RecordSetUpdateResponse(
      String recordSetId,
      String zoneId,
      String ownerGroupId,
      OwnershipTransferStatus ownershipTransferStatus,
      String message) {
    this.recordSetId = recordSetId;
    this.zoneId = zoneId;
    this.ownerGroupId = ownerGroupId;
    this.ownershipTransferStatus = ownershipTransferStatus;
    this.message = message;
  }

  public String getRecordSetId() {
    return recordSetId;
  }

  public String getZoneId() {
    return zoneId;
  }

  public String getOwnerGroupId() {
    return ownerGroupId;
  }

  public OwnershipTransferStatus getOwnershipTransferStatus() {
    return ownershipTransferStatus;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "RecordSetUpdateResponse{"
        + "recordSetId='"
        + recordSetId
        + '\''
        + ", zoneId='"
        + zoneId
        + '\''
        + ", ownerGroupId='"
        + ownerGroupId
        + '\''
        + ", ownershipTransferStatus="
        + ownershipTransferStatus
        + ", message='"
        + message
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RecordSetUpdateResponse)) return false;
    RecordSetUpdateResponse that = (RecordSetUpdateResponse) o;
    return Objects.equals(recordSetId, that.recordSetId)
        && Objects.equals(zoneId, that.zoneId)
        && Objects.equals(ownerGroupId, that.ownerGroupId)
        && ownershipTransferStatus == that.ownershipTransferStatus
        && Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recordSetId, zoneId, ownerGroupId, ownershipTransferStatus, message);
  }
}
