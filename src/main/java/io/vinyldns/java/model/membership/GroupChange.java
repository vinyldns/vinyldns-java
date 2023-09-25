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
package io.vinyldns.java.model.membership;

public class GroupChange {
  private final String id;
  private final Group newGroup, oldGroup;
  private final String created, userId;
  private final GroupChangeType changeType;

  public GroupChange(
      String id,
      Group newGroup,
      Group oldGroup,
      String created,
      String userId,
      GroupChangeType changeType) {
    this.id = id;
    this.newGroup = newGroup;
    this.oldGroup = oldGroup;
    this.created = created;
    this.userId = userId;
    this.changeType = changeType;
  }

  public String getId() {
    return id;
  }

  public Group getNewGroup() {
    return newGroup;
  }

  public Group getOldGroup() {
    return oldGroup;
  }

  public String getCreated() {
    return created;
  }

  public String getUserId() {
    return userId;
  }

  public GroupChangeType getChangeType() {
    return changeType;
  }

  @Override
  public String toString() {
    return "GroupChange{"
        + "id='"
        + id
        + '\''
        + ", newGroup="
        + newGroup
        + ", oldGroup="
        + oldGroup
        + ", created='"
        + created
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", changeType="
        + changeType
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GroupChange)) return false;
    GroupChange that = (GroupChange) o;

    if (!id.equals(that.id)) return false;
    if (!newGroup.equals(that.newGroup)) return false;
    if (!oldGroup.equals(that.oldGroup)) return false;
    if (!created.equals(that.created)) return false;
    if (!userId.equals(that.userId)) return false;
    return changeType.equals(that.changeType);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + newGroup.hashCode();
    result = 31 * result + oldGroup.hashCode();
    result = 31 * result + created.hashCode();
    result = 31 * result + userId.hashCode();
    result = 31 * result + changeType.hashCode();
    return result;
  }
}
