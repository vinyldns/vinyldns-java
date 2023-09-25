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

import java.util.Objects;
import java.util.Set;
import org.joda.time.DateTime;

public class UpdateGroupRequest {
  private final String id, name, email, groupId;
  private String description; // optional
  private final Set<MemberId> members, admins;
  private DateTime created;
  private GroupStatus status;

  public UpdateGroupRequest(
      String id,
      String groupId,
      String name,
      String email,
      Set<MemberId> members,
      Set<MemberId> admins,
      DateTime created,
      GroupStatus status) {
    this.id = id;
    this.groupId = groupId;
    this.name = name;
    this.email = email;
    this.members = members;
    this.admins = admins;
    this.created = created;
    this.status = status;
  }

  public UpdateGroupRequest(
      String id,
      String groupId,
      String name,
      String email,
      Set<MemberId> members,
      Set<MemberId> admins,
      DateTime created,
      GroupStatus status,
      String description) {
    this(id, groupId, name, email, members, admins, created, status);
    this.description = description;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public Set<MemberId> getMembers() {
    return members;
  }

  public Set<MemberId> getAdmins() {
    return admins;
  }

  public String getId() {
    return id;
  }

  public DateTime getCreated() {
    return created;
  }

  public void setCreated(DateTime created) {
    this.created = created;
  }

  public GroupStatus getStatus() {
    return status;
  }

  public void setStatus(GroupStatus status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "UpdateGroupRequest{"
        + "id='"
        + id
        + '\''
        + ",groupId='"
        + groupId
        + '\''
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", description='"
        + description
        + '\''
        + ", members="
        + members
        + ", admins="
        + admins
        + ", created="
        + created
        + ", status="
        + status
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UpdateGroupRequest)) return false;
    UpdateGroupRequest that = (UpdateGroupRequest) o;
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getGroupId(), that.getGroupId())
        && Objects.equals(getName(), that.getName())
        && Objects.equals(getEmail(), that.getEmail())
        && Objects.equals(getDescription(), that.getDescription())
        && Objects.equals(getMembers(), that.getMembers())
        && Objects.equals(getAdmins(), that.getAdmins())
        && Objects.equals(getCreated(), that.getCreated())
        && getStatus() == that.getStatus();
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + groupId.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + members.hashCode();
    result = 31 * result + admins.hashCode();
    result = 31 * result + created.hashCode();
    result = 31 * result + status.hashCode();
    return result;
  }
}
