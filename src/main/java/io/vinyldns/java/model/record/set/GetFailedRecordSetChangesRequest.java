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
 * See the License for the License governing permissions and limitations under the License.
 */
package io.vinyldns.java.model.record.set;

/** Request object for GET /metrics/health/zones/{zoneId}/recordsetchangesfailure */
public class GetFailedRecordSetChangesRequest {

  private final String zoneId;

  public GetFailedRecordSetChangesRequest(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getZoneId() {
    return zoneId;
  }

  @Override
  public String toString() {
    return "GetFailedRecordSetChangesRequest{" + "zoneId='" + zoneId + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetFailedRecordSetChangesRequest)) return false;
    GetFailedRecordSetChangesRequest that = (GetFailedRecordSetChangesRequest) o;
    return zoneId.equals(that.zoneId);
  }

  @Override
  public int hashCode() {
    return zoneId.hashCode();
  }
}
