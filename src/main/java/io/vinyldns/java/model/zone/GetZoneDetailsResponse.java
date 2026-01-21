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

import io.vinyldns.java.model.acl.ZoneAccessLevel;
import java.util.Objects;

/** Response object for GET /zones/{zoneId}/details */
public class GetZoneDetailsResponse {
  private final Zone zone;
  private final ZoneAccessLevel accessLevel;

  public GetZoneDetailsResponse(Zone zone, ZoneAccessLevel accessLevel) {
    this.zone = zone;
    this.accessLevel = accessLevel;
  }

  public Zone getZone() {
    return zone;
  }

  public ZoneAccessLevel getAccessLevel() {
    return accessLevel;
  }

  @Override
  public String toString() {
    return "GetZoneDetailsResponse{" + "zone=" + zone + ", accessLevel=" + accessLevel + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetZoneDetailsResponse)) return false;
    GetZoneDetailsResponse that = (GetZoneDetailsResponse) o;
    return Objects.equals(zone, that.zone) && accessLevel == that.accessLevel;
  }

  @Override
  public int hashCode() {
    return Objects.hash(zone, accessLevel);
  }
}
