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

import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.RecordData;
import java.util.Objects;

public class AddSingleChange implements SingleChange {
  private String id;
  private ChangeInputType changeType;
  private String zoneId;
  private String zoneName;
  private RecordType type;
  private String recordName;
  private String inputName;
  private SingleChangeStatus status;
  private String systemMessage;
  private String recordChangeId;
  private String recordSetId;

  private RecordData record;
  private Long ttl;

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public ChangeInputType getChangeType() {
    return changeType;
  }

  public void setChangeType(ChangeInputType changeType) {
    this.changeType = changeType;
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

  public RecordData getRecord() {
    return record;
  }

  public void setRecord(RecordData record) {
    this.record = record;
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
  public RecordType getType() {
    return type;
  }

  public void setType(RecordType type) {
    this.type = type;
  }

  public Long getTtl() {
    return ttl;
  }

  public void setTtl(Long ttl) {
    this.ttl = ttl;
  }

  @Override
  public String toString() {
    return "AddSingleChange{"
        + "id='"
        + id
        + '\''
        + ", changeType="
        + changeType
        + ", zoneId='"
        + zoneId
        + '\''
        + ", zoneName='"
        + zoneName
        + '\''
        + ", type="
        + type
        + ", recordName='"
        + recordName
        + '\''
        + ", inputName='"
        + inputName
        + '\''
        + ", status="
        + status
        + ", systemMessage='"
        + systemMessage
        + '\''
        + ", recordChangeId='"
        + recordChangeId
        + '\''
        + ", recordSetId='"
        + recordSetId
        + '\''
        + ", record="
        + record
        + ", ttl="
        + ttl
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddSingleChange that = (AddSingleChange) o;
    return Objects.equals(id, that.id)
        && changeType == that.changeType
        && Objects.equals(zoneId, that.zoneId)
        && Objects.equals(zoneName, that.zoneName)
        && type == that.type
        && Objects.equals(recordName, that.recordName)
        && Objects.equals(inputName, that.inputName)
        && status == that.status
        && Objects.equals(systemMessage, that.systemMessage)
        && Objects.equals(recordChangeId, that.recordChangeId)
        && Objects.equals(recordSetId, that.recordSetId)
        && Objects.equals(record, that.record)
        && Objects.equals(ttl, that.ttl);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        id,
        changeType,
        zoneId,
        zoneName,
        type,
        recordName,
        inputName,
        status,
        systemMessage,
        recordChangeId,
        recordSetId,
        record,
        ttl);
  }
}
