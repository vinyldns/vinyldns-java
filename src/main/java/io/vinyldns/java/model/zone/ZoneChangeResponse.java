package io.vinyldns.java.model.zone;

import org.joda.time.DateTime;

import java.util.Objects;

public class ZoneChangeResponse {
    private Zone zone;
    private String userId;
    private ZoneChangeType changeType;
    private ZoneChangeStatus status = ZoneChangeStatus.Pending;
    private DateTime created;
    private String systemMessage; //optional
    private String id;

    public ZoneChangeResponse() {}

    public ZoneChangeResponse(Zone zone,
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
        return "ZoneChangeResponse{" +
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
        if (!(o instanceof ZoneChangeResponse)) return false;
        ZoneChangeResponse that = (ZoneChangeResponse) o;
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
