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

public class AddChangeInput extends ChangeInput {
  private final Long ttl;
  private final RecordData record;

  public AddChangeInput(String inputName, RecordType type, Long ttl, RecordData record) {
    super(ChangeInputType.Add, inputName, type);
    this.ttl = ttl;
    this.record = record;
  }

  public Long getTtl() {
    return ttl;
  }

  public RecordData getRecord() {
    return record;
  }

  @Override
  public String toString() {
    return String.format(
        "AddChangeInput{changeType='%s', inputName='%s', recordType='%s', ttl='%s', record='%s'}",
        this.getChangeType(), this.getInputName(), this.getType(), this.ttl, this.record);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddChangeInput that = (AddChangeInput) o;
    return super.equals(o)
        && Objects.equals(this.ttl, that.ttl)
        && Objects.equals(this.record, that.record);
  }

  @Override
  public int hashCode() {
    return super.hashCode() + Objects.hash(ttl, record);
  }
}
