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

public class ChangeInput {
  private final ChangeInputType changeType;
  private final String inputName;
  private final RecordType type;
  private final RecordData record;

  public ChangeInput(ChangeInputType changeType, String inputName, RecordType type) {
    this.changeType = changeType;
    this.inputName = inputName;
    this.type = type;
    this.record = null;
  }

  public ChangeInput(ChangeInputType changeType, String inputName, RecordType type, RecordData record) {
    this.changeType = changeType;
    this.inputName = inputName;
    this.type = type;
    this.record = record;
  }

  public ChangeInputType getChangeType() {
    return changeType;
  }

  public String getInputName() {
    return inputName;
  }

  public RecordType getType() {
    return type;
  }

  public RecordData getRecord() { return record; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChangeInput that = (ChangeInput) o;
    return Objects.equals(inputName, that.inputName)
        && this.changeType == that.changeType
        && this.type == that.type
        && Objects.equals(record, that.record);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputName, changeType, type, record);
  }
}
