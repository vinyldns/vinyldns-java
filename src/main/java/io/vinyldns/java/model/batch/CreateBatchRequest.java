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

import java.util.List;
import java.util.Objects;
import java.time.Instant;

public class CreateBatchRequest {
  private String comments;

  private List<ChangeInput> changes;

  private String ownerGroupId;

  private Instant scheduledTime; // optional

  private Boolean allowManualReview; // optional

  public CreateBatchRequest(String comments, List<ChangeInput> changes, String ownerGroupId) {
    this.comments = comments;
    this.changes = changes;
    this.setOwnerGroupId(ownerGroupId);
  }

  public CreateBatchRequest(List<ChangeInput> changes) {
    this(null, changes, null);
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public List<ChangeInput> getChanges() {
    return changes;
  }

  public void setChanges(List<ChangeInput> changes) {
    this.changes = changes;
  }

  public String getOwnerGroupId() {
    return ownerGroupId;
  }

  public void setOwnerGroupId(String ownerGroupId) {
    this.ownerGroupId = ownerGroupId;
  }

  public Boolean getAllowManualReview() {
    return allowManualReview;
  }

  public void setAllowManualReview(Boolean allowManualReview) {
    this.allowManualReview = allowManualReview;
  }

  public Instant getScheduledTime() {
    return scheduledTime;
  }

  /**
   * Sets the scheduled time (UTC). Note: Since VinylDNS truncates at SECONDS, the request will truncate anything
   * more precise than the SECONDS value.
   */
  public void setScheduledTime(Instant scheduledTime) {
    this.scheduledTime = scheduledTime;
  }

  @Override
  public String toString() {
    return "CreateBatchRequest{"
        + "comments='"
        + comments
        + '\''
        + ", changes="
        + changes
        + '\''
        + ", 'ownerGroupId='"
        + ownerGroupId
        + '\''
        + ", scheduledTime="
        + scheduledTime
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateBatchRequest that = (CreateBatchRequest) o;
    return Objects.equals(comments, that.comments)
        && Objects.equals(changes, that.changes)
        && Objects.equals(ownerGroupId, that.ownerGroupId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(comments, changes, ownerGroupId);
  }
}
