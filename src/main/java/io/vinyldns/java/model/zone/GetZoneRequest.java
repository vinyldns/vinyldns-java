package io.vinyldns.java.model.zone;

public class GetZoneRequest {
    private final String zoneId;

    public GetZoneRequest(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    @Override
    public String toString() {
        return "GetZoneRequest{" +
            "zoneId='" +
            zoneId +
            "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetZoneRequest that = (GetZoneRequest) o;
        return zoneId.equals(that.zoneId);
    }

    @Override
    public int hashCode() {
        return zoneId.hashCode();
    }
}
