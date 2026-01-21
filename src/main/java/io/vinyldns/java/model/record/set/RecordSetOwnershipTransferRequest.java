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

/**
 * Request object for PUT /zones/{zoneId}/recordsets/{recordSetId} to perform ownership transfer
 * actions. The body includes ownerGroupId changes (when approving) and recordSetGroupChange
 * payload.
 */
public class RecordSetOwnershipTransferRequest {
  private final String zoneId;
  private final String recordSetId;
  private final String currentOwnerGroupId;
  private final String requestedOwnerGroupId;
  private final OwnershipTransferStatus status;
  private final boolean updateOwnerGroup;

  private final OwnershipTransfer recordSetGroupChange;

  private RecordSetOwnershipTransferRequest(
      String zoneId,
      String recordSetId,
      String currentOwnerGroupId,
      String requestedOwnerGroupId,
      OwnershipTransferStatus status,
      boolean updateOwnerGroup,
      OwnershipTransfer recordSetGroupChange) {
    this.zoneId = zoneId;
    this.recordSetId = recordSetId;
    this.currentOwnerGroupId = currentOwnerGroupId;
    this.requestedOwnerGroupId = requestedOwnerGroupId;
    this.status = status;
    this.updateOwnerGroup = updateOwnerGroup;
    this.recordSetGroupChange = recordSetGroupChange;
  }

  public static RecordSetOwnershipTransferRequest of(
      String zoneId, String recordSetId, String currentOwnerGroupId, String requestedOwnerGroupId) {
    Objects.requireNonNull(zoneId, "zoneId is required");
    Objects.requireNonNull(recordSetId, "recordSetId is required");
    return new RecordSetOwnershipTransferRequest(
        zoneId,
        recordSetId,
        currentOwnerGroupId,
        requestedOwnerGroupId,
        OwnershipTransferStatus.Requested,
        false,
        new OwnershipTransfer(OwnershipTransferStatus.Requested, requestedOwnerGroupId));
  }

  public RecordSetOwnershipTransferRequest withStatus(OwnershipTransferStatus status) {
    Objects.requireNonNull(status, "status is required");
    return new RecordSetOwnershipTransferRequest(
        zoneId,
        recordSetId,
        currentOwnerGroupId,
        requestedOwnerGroupId,
        status,
        updateOwnerGroup,
        new OwnershipTransfer(status, requestedOwnerGroupId));
  }

  public RecordSetOwnershipTransferRequest withUpdateOwnerGroup(boolean update) {
    return new RecordSetOwnershipTransferRequest(
        zoneId,
        recordSetId,
        currentOwnerGroupId,
        requestedOwnerGroupId,
        status,
        update,
        recordSetGroupChange);
  }

  // --- Getters ---

  public String getZoneId() {
    return zoneId;
  }

  public String getRecordSetId() {
    return recordSetId;
  }

  public String getCurrentOwnerGroupId() {
    return currentOwnerGroupId;
  }

  public String getRequestedOwnerGroupId() {
    return requestedOwnerGroupId;
  }

  public OwnershipTransferStatus getStatus() {
    return status;
  }

  public boolean isUpdateOwnerGroup() {
    return updateOwnerGroup;
  }

  /**
   * The API expects the record set body to include { "ownerGroupId": "...", "recordSetGroupChange":
   * {...} } If updateOwnerGroup is true (approval), the service implementation should set the
   * ownerGroupId to requestedOwnerGroupId.
   */
  public OwnershipTransfer getRecordSetGroupChange() {
    return recordSetGroupChange;
  }

  @Override
  public String toString() {
    return "RecordSetOwnershipTransferRequest{"
        + "zoneId='"
        + zoneId
        + '\''
        + ", recordSetId='"
        + recordSetId
        + '\''
        + ", currentOwnerGroupId='"
        + currentOwnerGroupId
        + '\''
        + ", requestedOwnerGroupId='"
        + requestedOwnerGroupId
        + '\''
        + ", status="
        + status
        + ", updateOwnerGroup="
        + updateOwnerGroup
        + ", recordSetGroupChange="
        + recordSetGroupChange
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RecordSetOwnershipTransferRequest)) return false;
    RecordSetOwnershipTransferRequest that = (RecordSetOwnershipTransferRequest) o;
    return updateOwnerGroup == that.updateOwnerGroup
        && Objects.equals(zoneId, that.zoneId)
        && Objects.equals(recordSetId, that.recordSetId)
        && Objects.equals(currentOwnerGroupId, that.currentOwnerGroupId)
        && Objects.equals(requestedOwnerGroupId, that.requestedOwnerGroupId)
        && status == that.status
        && Objects.equals(recordSetGroupChange, that.recordSetGroupChange);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        zoneId,
        recordSetId,
        currentOwnerGroupId,
        requestedOwnerGroupId,
        status,
        updateOwnerGroup,
        recordSetGroupChange);
  }
}
