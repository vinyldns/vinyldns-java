/**
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
package vinyldns.java.model.batch;

import vinyldns.java.model.record.RecordType;
import vinyldns.java.model.record.data.RecordData;

import java.util.Objects;

public class AddChangeInput implements ChangeInput {
  private String inputName;

  private RecordType type;

  private Long ttl;

  private RecordData record;

  private ChangeInputType changeType = ChangeInputType.Add;

  public AddChangeInput(String inputName, RecordType type, Long ttl, RecordData record) {
    this.inputName = inputName;
    this.type = type;
    this.ttl = ttl;
    this.record = record;
  }

  @Override
  public ChangeInputType getChangeType() {
    return changeType;
  }

  @Override
  public String getInputName() {
    return inputName;
  }

  public RecordType getType() {
    return type;
  }

  public Long getTtl() {
    return ttl;
  }

  public RecordData getRecord() {
    return record;
  }

  @Override
  public String toString() {
    return "AddChangeInput{" +
        "inputName='" + inputName + '\'' +
        ", type=" + type +
        ", ttl=" + ttl +
        ", record=" + record +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddChangeInput that = (AddChangeInput) o;
    return Objects.equals(inputName, that.inputName) &&
        type == that.type &&
        Objects.equals(ttl, that.ttl) &&
        Objects.equals(record, that.record);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputName, type, ttl, record);
  }
}
