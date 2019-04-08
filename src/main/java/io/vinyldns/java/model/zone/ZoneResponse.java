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
package io.vinyldns.java.model.zone;

public class ZoneResponse {
  private final Zone zone;

  public ZoneResponse(Zone zone) {
    this.zone = zone;
  }

  public Zone getZone() {
    return zone;
  }

  @Override
  public String toString() {
    return zone.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ZoneResponse that = (ZoneResponse) o;
    return zone.equals(that.zone);
  }

  @Override
  public int hashCode() {
    return zone.hashCode();
  }
}
