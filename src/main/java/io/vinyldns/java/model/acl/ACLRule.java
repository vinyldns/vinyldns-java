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
package io.vinyldns.java.model.acl;

import io.vinyldns.java.model.record.RecordType;
import java.util.Set;

public class ACLRule {
  private AccessLevel accessLevel;
  private String description; // optional
  private String userId; // optional
  private String groupId; // optional
  private String recordMask; // optional
  private Set<RecordType> recordTypes;

  public ACLRule() {}

  public ACLRule(
      AccessLevel accessLevel,
      String description,
      String userId,
      String groupId,
      String recordMask,
      Set<RecordType> recordTypes) {
    this.accessLevel = accessLevel;
    this.description = description;
    this.userId = userId;
    this.groupId = groupId;
    this.recordMask = recordMask;
    this.recordTypes = recordTypes;
  }

  public AccessLevel getAccessLevel() {
    return accessLevel;
  }

  public void setAccessLevel(AccessLevel accessLevel) {
    this.accessLevel = accessLevel;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getRecordMask() {
    return recordMask;
  }

  public void setRecordMask(String recordMask) {
    this.recordMask = recordMask;
  }

  public Set<RecordType> getRecordTypes() {
    return recordTypes;
  }

  public void setRecordTypes(Set<RecordType> recordTypes) {
    this.recordTypes = recordTypes;
  }

  @Override
  public String toString() {
    return "ACLRule{"
        + "accessLevel="
        + accessLevel
        + ", description='"
        + description
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", groupId='"
        + groupId
        + '\''
        + ", recordMask='"
        + recordMask
        + '\''
        + ", recordTypes="
        + recordTypes
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ACLRule aclRule = (ACLRule) o;

    if (accessLevel != aclRule.accessLevel) return false;
    if (description != null
        ? !description.equals(aclRule.description)
        : aclRule.description != null) return false;
    if (userId != null ? !userId.equals(aclRule.userId) : aclRule.userId != null) return false;
    if (groupId != null ? !groupId.equals(aclRule.groupId) : aclRule.groupId != null) return false;
    if (recordMask != null ? !recordMask.equals(aclRule.recordMask) : aclRule.recordMask != null)
      return false;
    return recordTypes.equals(aclRule.recordTypes);
  }

  @Override
  public int hashCode() {
    int result = accessLevel.hashCode();
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
    result = 31 * result + (recordMask != null ? recordMask.hashCode() : 0);
    result = 31 * result + recordTypes.hashCode();
    return result;
  }
}
