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
package io.vinyldns.java.model.zone;

import io.vinyldns.java.model.record.set.RecordSet;

public class GetRecordSetResponse {
  private final RecordSet recordSet;

  public GetRecordSetResponse(RecordSet recordSet) {
    this.recordSet = recordSet;
  }

  public RecordSet getRecordSet() {
    return recordSet;
  }

  @Override
  public String toString() {
    return "GetRecordSetResponse{recordSet=" + recordSet.toString() + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GetRecordSetResponse that = (GetRecordSetResponse) o;
    return recordSet.equals(that.recordSet);
  }

  @Override
  public int hashCode() {
    return recordSet.hashCode();
  }
}
