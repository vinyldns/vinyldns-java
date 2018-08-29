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
package io.vinyldns.java.model.record.set;

import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.RecordData;
import java.util.Collection;

public class CreateRecordSetRequest extends RecordSetBase {
  public CreateRecordSetRequest() {}

  public CreateRecordSetRequest(
      String zoneId, String name, RecordType type, long ttl, Collection<RecordData> records) {
    super(zoneId, name, type, ttl, records);
  }

  @Override
  public String toString() {
    return "CreateRecordSetRequest{" + super.toString() + "}";
  }
}
