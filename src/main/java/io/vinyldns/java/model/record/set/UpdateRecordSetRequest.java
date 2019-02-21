package io.vinyldns.java.model.record.set;

import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.RecordData;

import java.util.Collection;

public class UpdateRecordSetRequest extends RecordSet {
    public UpdateRecordSetRequest() {}

    public UpdateRecordSetRequest(String id, String zoneId, String name, RecordType type, long ttl, Collection<RecordData> records) {
        super();
        this.setId(id);
        this.setZoneId(zoneId);
        this.setName(name);
        this.setType(type);
        this.setTtl(ttl);
        this.setRecords(records);
    }

    @Override
    public String toString() {
        return "UpdateRecordSetRequest{" + super.toString() + "}";
    }
}
