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

public class DeleteRecordSetChangeInput extends ChangeInput {
  public DeleteRecordSetChangeInput(String inputName, RecordType type) {
    super(ChangeInputType.DeleteRecordSet, inputName, type);
  }

  public DeleteRecordSetChangeInput(String inputName, RecordType type, RecordData record) {
    super(ChangeInputType.DeleteRecordSet, inputName, type, record);
  }

  @Override
  public String toString() {
    return String.format(
        "DeleteRecordSetChangeInput{changeType='%s', inputName='%s', type='%s', record='%s'}",
        this.getChangeType().name(), this.getInputName(), this.getType().name(), this.getRecord());
  }
}
