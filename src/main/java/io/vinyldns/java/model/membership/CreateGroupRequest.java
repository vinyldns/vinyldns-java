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
package io.vinyldns.java.model.membership;

import java.util.Set;

public class CreateGroupRequest {
  private final String name, email;
  private String description; // optional
  private final Set<MemberId> members, admins;

  public CreateGroupRequest(
      String name, String email, Set<MemberId> members, Set<MemberId> admins) {
    this.name = name;
    this.email = email;
    this.members = members;
    this.admins = admins;
  }

  public CreateGroupRequest(
      String name, String email, Set<MemberId> members, Set<MemberId> admins, String description) {
    this(name, email, members, admins);
    this.description = description;
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

  @Override
  public String toString() {
    return "CreateGroupRequest{"
        + "name='"
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
        + ", admins"
        + admins
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CreateGroupRequest group = (CreateGroupRequest) o;

    if (!name.equals(group.name)) return false;
    if (!email.equals(group.email)) return false;
    if (description != null ? !description.equals(group.description) : group.description != null)
      return false;
    if (!members.equals(group.members)) return false;
    return admins.equals(group.admins);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + members.hashCode();
    result = 31 * result + admins.hashCode();
    return result;
  }
}
