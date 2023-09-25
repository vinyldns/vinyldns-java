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
import org.joda.time.DateTime;

public class ZoneResponse {
  private final Zone zone;
  private final String userId;
  private final ZoneChangeType changeType;
  private final ZoneChangeStatus status;
  private final DateTime created;
  private String systemMessage; // optional
  private final String id;

  public ZoneResponse(
      Zone zone,
      String userId,
      ZoneChangeType changeType,
      ZoneChangeStatus status,
      DateTime created,
      String id) {
    this.zone = zone;
    this.userId = userId;
    this.changeType = changeType;
    this.status = status;
    this.created = created;
    this.id = id;
  }

  public ZoneResponse(
      Zone zone,
      String userId,
      ZoneChangeType changeType,
      ZoneChangeStatus status,
      DateTime created,
      String systemMessage,
      String id) {
    this.zone = zone;
    this.userId = userId;
    this.changeType = changeType;
    this.status = status;
    this.created = created;
    this.systemMessage = systemMessage;
    this.id = id;
  }

  public Zone getZone() {
    return zone;
  }

  public String getUserId() {
    return userId;
  }

  public ZoneChangeType getChangeType() {
    return changeType;
  }

  public ZoneChangeStatus getStatus() {
    return status;
  }

  public DateTime getCreated() {
    return created;
  }

  public String getSystemMessage() {
    return systemMessage;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return "ZoneResponse{"
        + "zone="
        + zone
        + ", userId='"
        + userId
        + '\''
        + ", changeType="
        + changeType
        + ", status="
        + status
        + ", created="
        + created
        + ", systemMessage='"
        + systemMessage
        + '\''
        + ", id='"
        + id
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ZoneResponse)) return false;
    ZoneResponse that = (ZoneResponse) o;
    return Objects.equals(getZone(), that.getZone())
        && Objects.equals(getUserId(), that.getUserId())
        && getChangeType() == that.getChangeType()
        && getStatus() == that.getStatus()
        && Objects.equals(getCreated(), that.getCreated())
        && Objects.equals(getSystemMessage(), that.getSystemMessage())
        && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    int result = zone.hashCode();
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (changeType != null ? changeType.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (created != null ? created.hashCode() : 0);
    result = 31 * result + (systemMessage != null ? systemMessage.hashCode() : 0);
    result = 31 * result + (id != null ? id.hashCode() : 0);
    return result;
  }
}
