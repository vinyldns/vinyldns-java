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
package io.vinyldns.java.model.batch;

import java.util.Objects;
import java.util.StringJoiner;

public class ListBatchChangesRequest {
  /**
   * In order to advance through pages of results, the startFrom is set to the nextId that is
   * returned on the previous response. It is up to the client to maintain previous pages if the
   * client wishes to advance forward and backward. If not specified, will return the first page of
   * results
   */
  private String startFrom; // optional

  /**
   * The number of items to return in the page. Valid values are 1 to 100. Defaults to 100 if not
   * provided.
   */
  private Integer maxItems; // optional

  /** Filter for batch approval status */
  private BatchChangeApprovalStatus approvalStatus; // optional

  /**
   * Flag on whether to ignore whether requesting user has access to the batches that are being
   * returned in the response.
   */
  private Boolean ignoreAccess; // optional

  public ListBatchChangesRequest() {}

  public ListBatchChangesRequest(String startFrom, Integer maxItems) {
    this.startFrom = startFrom;
    this.maxItems = maxItems;
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

  public Boolean getIgnoreAccess() {
    return ignoreAccess;
  }

  public void setIgnoreAccess(Boolean ignoreAccess) {
    this.ignoreAccess = ignoreAccess;
  }

  public BatchChangeApprovalStatus getApprovalStatus() {
    return approvalStatus;
  }

  public void setApprovalStatus(BatchChangeApprovalStatus approvalStatus) {
    this.approvalStatus = approvalStatus;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ListBatchChangesRequest.class.getSimpleName() + "[", "]")
        .add("startFrom='" + startFrom + "'")
        .add("maxItems=" + maxItems)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListBatchChangesRequest that = (ListBatchChangesRequest) o;
    return Objects.equals(startFrom, that.startFrom) && Objects.equals(maxItems, that.maxItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startFrom, maxItems);
  }
}
