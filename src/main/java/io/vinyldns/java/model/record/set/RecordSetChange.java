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
package io.vinyldns.java.model.record.set;

import io.vinyldns.java.model.zone.Zone;
import org.joda.time.DateTime;

public class RecordSetChange {
  /** the id of the change. This is not the id of the recordset */
  private String id;

  /** contains information about the zone when the change was made */
  private Zone zone;

  /** contains the recordset model */
  private RecordSet recordSet;

  /** the user id that initiated the change */
  private String userId;

  /** type of change requested (Create, Update, Delete) */
  private RecordSetChangeType changeType;

  /**
   * status of change requested (Pending, Submitted, Validated, Applied, Verified, Complete, Failed)
   */
  private RecordSetChangeStatus status;

  /** the time (GMT) the change was initiated */
  private DateTime created;

  private String systemMessage; // optional

  private RecordSet updates; // optional

  public RecordSetChange() {}

  public RecordSetChange(
      String id,
      Zone zone,
      RecordSet recordSet,
      String userId,
      RecordSetChangeType changeType,
      RecordSetChangeStatus status,
      DateTime created,
      String systemMessage,
      RecordSet updates) {
    this.id = id;
    this.zone = zone;
    this.recordSet = recordSet;
    this.userId = userId;
    this.changeType = changeType;
    this.status = status;
    this.created = created;
    this.systemMessage = systemMessage;
    this.updates = updates;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Zone getZone() {
    return zone;
  }

  public void setZone(Zone zone) {
    this.zone = zone;
  }

  public RecordSet getRecordSet() {
    return recordSet;
  }

  public void setRecordSet(RecordSet recordSet) {
    this.recordSet = recordSet;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public RecordSetChangeType getChangeType() {
    return changeType;
  }

  public void setChangeType(RecordSetChangeType changeType) {
    this.changeType = changeType;
  }

  public RecordSetChangeStatus getStatus() {
    return status;
  }

  public void setStatus(RecordSetChangeStatus status) {
    this.status = status;
  }

  public DateTime getCreated() {
    return created;
  }

  public void setCreated(DateTime created) {
    this.created = created;
  }

  public String getSystemMessage() {
    return systemMessage;
  }

  public void setSystemMessage(String systemMessage) {
    this.systemMessage = systemMessage;
  }

  public RecordSet getUpdates() {
    return updates;
  }

  public void setUpdates(RecordSet updates) {
    this.updates = updates;
  }

  @Override
  public String toString() {
    return "RecordSetChange{"
        + "id='"
        + id
        + '\''
        + ", zone="
        + zone
        + ", recordSet="
        + recordSet
        + ", userId='"
        + userId
        + '\''
        + ", changeType="
        + changeType
        + ", status="
        + status
        + ", created="
        + created
        + ", systemMessage='"
        + systemMessage
        + '\''
        + ", updates="
        + updates
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RecordSetChange that = (RecordSetChange) o;

    if (!id.equals(that.id)) return false;
    if (!zone.equals(that.zone)) return false;
    if (!recordSet.equals(that.recordSet)) return false;
    if (!userId.equals(that.userId)) return false;
    if (changeType != that.changeType) return false;
    if (status != that.status) return false;
    if (!created.equals(that.created)) return false;
    if (systemMessage != null
        ? !systemMessage.equals(that.systemMessage)
        : that.systemMessage != null) return false;
    return updates != null ? updates.equals(that.updates) : that.updates == null;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + zone.hashCode();
    result = 31 * result + recordSet.hashCode();
    result = 31 * result + userId.hashCode();
    result = 31 * result + changeType.hashCode();
    result = 31 * result + status.hashCode();
    result = 31 * result + created.hashCode();
    result = 31 * result + (systemMessage != null ? systemMessage.hashCode() : 0);
    result = 31 * result + (updates != null ? updates.hashCode() : 0);
    return result;
  }
}
