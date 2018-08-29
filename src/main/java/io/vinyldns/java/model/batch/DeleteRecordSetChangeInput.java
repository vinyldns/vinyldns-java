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

import java.util.Objects;

public class DeleteRecordSetChangeInput implements ChangeInput {
  private String inputName;

  public DeleteRecordSetChangeInput(String inputName) {
    this.inputName = inputName;
  }

  @Override
  public ChangeInputType getChangeType() {
    return ChangeInputType.DeleteRecordSet;
  }

  @Override
  public String getInputName() {
    return inputName;
  }

  @Override
  public String toString() {
    return "DeleteRecordSetChangeInput{" + "inputName='" + inputName + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteRecordSetChangeInput that = (DeleteRecordSetChangeInput) o;
    return Objects.equals(inputName, that.inputName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(inputName);
  }
}
