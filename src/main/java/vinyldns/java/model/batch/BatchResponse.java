/**
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
package vinyldns.java.model.batch;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class BatchResponse {
  private String id;
  private String userId;
  private String userName;
  private String comments;
  private Date createdTimestamp;
  private List<SingleChange> changes;

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

  @Override
  public String toString() {
    return new StringJoiner(", ", BatchResponse.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("userId='" + userId + "'")
        .add("userName='" + userName + "'")
        .add("comments='" + comments + "'")
        .add("createdTimestamp=" + createdTimestamp)
        .add("changes=" + changes)
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
    BatchResponse that = (BatchResponse) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(userId, that.userId) &&
        Objects.equals(userName, that.userName) &&
        Objects.equals(comments, that.comments) &&
        Objects.equals(createdTimestamp, that.createdTimestamp) &&
        Objects.equals(changes, that.changes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, userName, comments, createdTimestamp, changes);
  }
}
