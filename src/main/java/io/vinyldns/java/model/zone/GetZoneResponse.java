package io.vinyldns.java.model.zone;

public class GetZoneResponse {
    private final Zone zone;

    public GetZoneResponse(Zone zone) {
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

        GetZoneResponse that = (GetZoneResponse) o;
        return zone.equals(that.zone);
    }

    @Override
    public int hashCode() {
        return zone.hashCode();
    }
}
