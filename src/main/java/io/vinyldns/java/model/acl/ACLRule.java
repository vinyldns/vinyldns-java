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

package io.vinyldns.java.model.acl;

import io.vinyldns.java.model.record.RecordType;
import java.util.Objects;
import java.util.Set;

public class ACLRule {
  private ZoneAccessLevel accessLevel;
  private String description;
  private String displayName;
  private String userId; // optional
  private String groupId; // optional
  private String recordMask;
  private Set<RecordType> recordTypes;

  public ACLRule() {}

  public ACLRule(
      ZoneAccessLevel accessLevel,
      String description,
      String displayName,
      String userId,
      String groupId,
      String recordMask,
      Set<RecordType> recordTypes) {
    this.accessLevel = accessLevel;
    this.description = description;
    this.displayName = displayName;
    this.userId = userId;
    this.groupId = groupId;
    this.recordMask = recordMask;
    this.recordTypes = recordTypes;
  }

  public ZoneAccessLevel getAccessLevel() {
    return accessLevel;
  }

  public void setAccessLevel(ZoneAccessLevel accessLevel) {
    this.accessLevel = accessLevel;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
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
        + ", displayName='"
        + displayName
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
    if (!(o instanceof ACLRule)) return false;
    ACLRule aclRule = (ACLRule) o;
    return accessLevel == aclRule.accessLevel
        && Objects.equals(description, aclRule.description)
        && Objects.equals(displayName, aclRule.displayName)
        && Objects.equals(userId, aclRule.userId)
        && Objects.equals(groupId, aclRule.groupId)
        && Objects.equals(recordMask, aclRule.recordMask)
        && Objects.equals(recordTypes, aclRule.recordTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        accessLevel, description, displayName, userId, groupId, recordMask, recordTypes);
  }
}
