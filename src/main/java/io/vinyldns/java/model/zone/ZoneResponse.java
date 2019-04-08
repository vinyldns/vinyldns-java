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

import org.joda.time.DateTime;

import java.util.Objects;

public class ZoneResponse {
    private Zone zone;
    private String userId;
    private ZoneChangeType changeType;
    private ZoneChangeStatus status = ZoneChangeStatus.Pending;
    private DateTime created;
    private String systemMessage; //optional
    private String id;

    public ZoneResponse() {}

    public ZoneResponse(Zone zone,
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

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ZoneChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ZoneChangeType changeType) {
        this.changeType = changeType;
    }

    public ZoneChangeStatus getStatus() {
        return status;
    }

    public void setStatus(ZoneChangeStatus status) {
        this.status = status;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ZoneResponse{" +
                "zone=" + zone +
                ", userId='" + userId + '\'' +
                ", changeType=" + changeType +
                ", status=" + status +
                ", created=" + created +
                ", systemMessage='" + systemMessage + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZoneResponse)) return false;
        ZoneResponse that = (ZoneResponse) o;
        return Objects.equals(getZone(), that.getZone()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                getChangeType() == that.getChangeType() &&
                getStatus() == that.getStatus() &&
                Objects.equals(getCreated(), that.getCreated()) &&
                Objects.equals(getSystemMessage(), that.getSystemMessage()) &&
                Objects.equals(getId(), that.getId());
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
