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

package io.vinyldns.java.model.record.set;

import java.util.List;

public class GetRecordSetChangeHistoryResponse {
  private final List<RecordSetChange> changes;

  public GetRecordSetChangeHistoryResponse(List<RecordSetChange> changes) {
    this.changes = changes;
  }

  public List<RecordSetChange> getChanges() {
    return changes;
  }

  @Override
  public String toString() {
    return "GetRecordSetChangeHistoryResponse{" + "changes=" + changes + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetRecordSetChangeHistoryResponse)) return false;
    GetRecordSetChangeHistoryResponse that = (GetRecordSetChangeHistoryResponse) o;
    return changes.equals(that.changes);
  }

  @Override
  public int hashCode() {
    return changes.hashCode();
  }
}
