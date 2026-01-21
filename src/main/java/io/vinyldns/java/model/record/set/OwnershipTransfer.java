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

/** Ownership transfer data included in the PUT request body as "recordSetGroupChange". */
public class OwnershipTransfer {
  private final OwnershipTransferStatus ownershipTransferStatus;
  private final String requestedOwnerGroupId;

  public OwnershipTransfer(
      OwnershipTransferStatus ownershipTransferStatus, String requestedOwnerGroupId) {
    this.ownershipTransferStatus =
        Objects.requireNonNull(ownershipTransferStatus, "ownershipTransferStatus");
    this.requestedOwnerGroupId = requestedOwnerGroupId;
  }

  public OwnershipTransferStatus getOwnershipTransferStatus() {
    return ownershipTransferStatus;
  }

  public String getRequestedOwnerGroupId() {
    return requestedOwnerGroupId;
  }

  @Override
  public String toString() {
    return "OwnershipTransfer{"
        + "ownershipTransferStatus="
        + ownershipTransferStatus
        + ", requestedOwnerGroupId='"
        + requestedOwnerGroupId
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OwnershipTransfer)) return false;
    OwnershipTransfer that = (OwnershipTransfer) o;
    return ownershipTransferStatus == that.ownershipTransferStatus
        && Objects.equals(requestedOwnerGroupId, that.requestedOwnerGroupId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ownershipTransferStatus, requestedOwnerGroupId);
  }
}
