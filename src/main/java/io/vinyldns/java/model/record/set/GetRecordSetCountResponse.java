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

/** Response object for GET /zones/{zoneId}/recordsetcount */
public class GetRecordSetCountResponse {
  private final int count;

  public GetRecordSetCountResponse(int count) {
    this.count = count;
  }

  public int getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "GetRecordSetCountResponse{" + "count=" + count + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetRecordSetCountResponse)) return false;
    GetRecordSetCountResponse that = (GetRecordSetCountResponse) o;
    return count == that.count;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(count);
  }
}
