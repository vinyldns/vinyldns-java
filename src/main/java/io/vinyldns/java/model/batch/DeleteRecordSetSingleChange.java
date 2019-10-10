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
import java.util.List;
import java.util.Objects;

public class DeleteRecordSetSingleChange implements SingleChange {
  private String id;
  private ChangeInputType changeType;
  private String zoneId;
  private String zoneName;
  private String recordName;
  private String inputName;
  private RecordType type;
  private SingleChangeStatus status;
  private String systemMessage;
  private String recordChangeId;
  private String recordSetId;

  private List<ValidationError> validationErrors;

  private RecordData record;

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

  @Override
  public RecordType getType() {
    return type;
  }

  public void setType(RecordType type) {
    this.type = type;
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

  public RecordData getRecord() {
    return record;
  }

  public void setRecord(RecordData record) {
    this.record = record;
  }

  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(List<ValidationError> validationErrors) {
    this.validationErrors = validationErrors;
  }

  @Override
  public String toString() {
    return "DeleteRecordSetSingleChange{"
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
        + ", recordName='"
        + recordName
        + '\''
        + ", inputName='"
        + inputName
        + '\''
        + ", type="
        + type
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
        + ", validationErrors="
        + validationErrors
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteRecordSetSingleChange that = (DeleteRecordSetSingleChange) o;
    return Objects.equals(id, that.id)
        && changeType == that.changeType
        && Objects.equals(zoneId, that.zoneId)
        && Objects.equals(zoneName, that.zoneName)
        && Objects.equals(recordName, that.recordName)
        && Objects.equals(inputName, that.inputName)
        && type == that.type
        && status == that.status
        && Objects.equals(systemMessage, that.systemMessage)
        && Objects.equals(recordChangeId, that.recordChangeId)
        && Objects.equals(recordSetId, that.recordSetId)
        && Objects.equals(record, that.record)
        && Objects.equals(validationErrors, that.validationErrors);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        id,
        changeType,
        zoneId,
        zoneName,
        recordName,
        inputName,
        type,
        record,
        status,
        systemMessage,
        recordChangeId,
        recordSetId,
        validationErrors);
  }
}
