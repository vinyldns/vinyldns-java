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

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BatchResponse {
  private String id;
  private String userId;
  private String userName;
  private String comments; // optional
  private Date createdTimestamp;
  private List<SingleChange> changes;
  private BatchChangeStatus status;
  private String ownerGroupId; // optional
  private String ownerGroupName; // optional

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Date getCreatedTimestamp() {
    return createdTimestamp;
  }

  public void setCreatedTimestamp(Date createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  public List<SingleChange> getChanges() {
    return changes;
  }

  public void setChanges(List<SingleChange> changes) {
    this.changes = changes;
  }

  public BatchChangeStatus getStatus() {
    return status;
  }

  public void setStatus(BatchChangeStatus status) {
    this.status = status;
  }

  public String getOwnerGroupId() {
    return ownerGroupId;
  }

  public void setOwnerGroupId(String ownerGroupId) {
    this.ownerGroupId = ownerGroupId;
  }

  public String getOwnerGroupName() {
    return ownerGroupName;
  }

  public void setOwnerGroupName(String ownerGroupName) {
    this.ownerGroupName = ownerGroupName;
  }

  @Override
  public String toString() {
    return "BatchResponse{"
        + "id='"
        + id
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", userName='"
        + userName
        + '\''
        + ", comments='"
        + comments
        + '\''
        + ", createdTimestamp="
        + createdTimestamp
        + ", changes="
        + changes
        + ", status="
        + status
        + ", ownerGroupId='"
        + ownerGroupId
        + '\''
        + ", ownerGroupName='"
        + ownerGroupName
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BatchResponse that = (BatchResponse) o;
    return Objects.equals(id, that.id)
        && Objects.equals(userId, that.userId)
        && Objects.equals(userName, that.userName)
        && Objects.equals(comments, that.comments)
        && Objects.equals(createdTimestamp, that.createdTimestamp)
        && Objects.equals(changes, that.changes)
        && status == that.status
        && Objects.equals(ownerGroupId, that.ownerGroupId)
        && Objects.equals(ownerGroupName, that.ownerGroupName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        id,
        userId,
        userName,
        comments,
        createdTimestamp,
        changes,
        status,
        ownerGroupId,
        ownerGroupName);
  }
}
