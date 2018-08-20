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
package vinyldns.java.model.batch;

import java.util.Objects;
import java.util.StringJoiner;
import vinyldns.java.model.record.RecordType;

public class DeleteRecordSetSingleChange implements SingleChange {
  private String id;
  private String zoneId;
  private String zoneName;
  private String recordName;
  private String inputName;
  private RecordType typ;
  private SingleChangeStatus status;
  private String systemMessage;
  private String recordChangeId;
  private String recordSetId;

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  @Override
  public String getZoneName() {
    return zoneName;
  }

  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  @Override
  public String getRecordName() {
    return recordName;
  }

  public void setRecordName(String recordName) {
    this.recordName = recordName;
  }

  @Override
  public String getInputName() {
    return inputName;
  }

  public void setInputName(String inputName) {
    this.inputName = inputName;
  }

  @Override
  public RecordType getType() {
    return typ;
  }

  public void setTyp(RecordType typ) {
    this.typ = typ;
  }

  @Override
  public SingleChangeStatus getStatus() {
    return status;
  }

  public void setStatus(SingleChangeStatus status) {
    this.status = status;
  }

  @Override
  public String getSystemMessage() {
    return systemMessage;
  }

  public void setSystemMessage(String systemMessage) {
    this.systemMessage = systemMessage;
  }

  @Override
  public String getRecordChangeId() {
    return recordChangeId;
  }

  public void setRecordChangeId(String recordChangeId) {
    this.recordChangeId = recordChangeId;
  }

  public String getRecordSetId() {
    return recordSetId;
  }

  public void setRecordSetId(String recordSetId) {
    this.recordSetId = recordSetId;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DeleteRecordSetSingleChange.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("zoneId='" + zoneId + "'")
        .add("zoneName='" + zoneName + "'")
        .add("recordName='" + recordName + "'")
        .add("inputName='" + inputName + "'")
        .add("typ=" + typ)
        .add("status=" + status)
        .add("systemMessage='" + systemMessage + "'")
        .add("recordChangeId='" + recordChangeId + "'")
        .add("recordSetId='" + recordSetId + "'")
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
    DeleteRecordSetSingleChange that = (DeleteRecordSetSingleChange) o;
    return Objects.equals(id, that.id)
        && Objects.equals(zoneId, that.zoneId)
        && Objects.equals(zoneName, that.zoneName)
        && Objects.equals(recordName, that.recordName)
        && Objects.equals(inputName, that.inputName)
        && typ == that.typ
        && status == that.status
        && Objects.equals(systemMessage, that.systemMessage)
        && Objects.equals(recordChangeId, that.recordChangeId)
        && Objects.equals(recordSetId, that.recordSetId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        zoneId,
        zoneName,
        recordName,
        inputName,
        typ,
        status,
        systemMessage,
        recordChangeId,
        recordSetId);
  }
}
