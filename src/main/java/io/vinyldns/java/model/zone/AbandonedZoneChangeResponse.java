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

import java.util.Objects;

public class AbandonedZoneChangeResponse {
  private final ZoneResponse zoneChange;
  private final String adminGroupName;
  private final String userName;
  private final Boolean accessLevel;

  public AbandonedZoneChangeResponse(
      ZoneResponse zoneChange, String adminGroupName, String userName, Boolean accessLevel) {
    this.zoneChange = zoneChange;
    this.adminGroupName = adminGroupName;
    this.userName = userName;
    this.accessLevel = accessLevel;
  }

  public ZoneResponse getZoneChange() {
    return zoneChange;
  }

  public String getAdminGroupName() {
    return adminGroupName;
  }

  public String getUserName() {
    return userName;
  }

  public Boolean getAccessLevel() {
    return accessLevel;
  }

  @Override
  public String toString() {
    return "zoneChange{"
        + "zoneChange="
        + zoneChange
        + ", adminGroupName='"
        + adminGroupName
        + '\''
        + ", userName="
        + userName
        + ", accessLevel="
        + accessLevel
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbandonedZoneChangeResponse)) return false;
    AbandonedZoneChangeResponse that = (AbandonedZoneChangeResponse) o;
    return Objects.equals(getZoneChange(), that.getZoneChange())
        && Objects.equals(getAdminGroupName(), that.getAdminGroupName())
        && getUserName() == that.getUserName()
        && getAccessLevel() == that.getAccessLevel();
  }

  @Override
  public int hashCode() {
    int result = zoneChange.hashCode();
    result = 31 * result + (adminGroupName != null ? adminGroupName.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (accessLevel != null ? accessLevel.hashCode() : 0);
    return result;
  }
}
