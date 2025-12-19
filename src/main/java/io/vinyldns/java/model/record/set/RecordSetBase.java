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

import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.RecordData;
import java.util.Collection;

public abstract class RecordSetBase {
  /** id of the zone where the recordset belongs */
  private String zoneId;

  /** the name of the recordset being updated */
  private String name;

  /** the type of recordset */
  private RecordType type;

  /** the TTL in seconds */
  private long ttl;

  /** record data for recordset, see {@link RecordSet RecordSet Model} */
  private Collection<RecordData> records;

  private String ownerGroupId;

  public RecordSetBase() {}

  public RecordSetBase(
      String zoneId,
      String name,
      RecordType type,
      long ttl,
      Collection<RecordData> records,
      String ownerGroupId) {
    this.zoneId = zoneId;
    this.name = name;
    this.type = type;
    this.ttl = ttl;
    this.records = records;
    this.ownerGroupId = ownerGroupId;
  }

  public RecordSetBase(
      String zoneId, String name, RecordType type, long ttl, Collection<RecordData> records) {
    this.zoneId = zoneId;
    this.name = name;
    this.type = type;
    this.ttl = ttl;
    this.records = records;
  }

  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RecordType getType() {
    return type;
  }

  public void setType(RecordType type) {
    this.type = type;
  }

  public long getTtl() {
    return ttl;
  }

  public void setTtl(long ttl) {
    this.ttl = ttl;
  }

  public Collection<RecordData> getRecords() {
    return records;
  }

  public void setRecords(Collection<RecordData> records) {
    this.records = records;
  }

  public String getOwnerGroupId() {
    return ownerGroupId;
  }

  public void setOwnerGroupId(String ownerGroupId) {
    this.ownerGroupId = ownerGroupId;
  }

  @Override
  public String toString() {
    return "zoneId='"
                + zoneId
                + '\''
                + ", name='"
                + name
                + '\''
                + ", type="
                + type
                + ", ttl="
                + ttl
                + ", records="
                + records
                + ", 'ownerGroupId='"
                + ownerGroupId
            == null
        ? "NULL"
        : ownerGroupId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RecordSetBase that = (RecordSetBase) o;

    if (ttl != that.ttl) return false;
    if (!zoneId.equals(that.zoneId)) return false;
    if (!name.equals(that.name)) return false;
    if (!type.equals(that.type)) return false;
    if (ownerGroupId != null && !ownerGroupId.equals(that.ownerGroupId)) return false;
    return records.equals(that.records);
  }

  @Override
  public int hashCode() {
    int result = zoneId.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + type.hashCode();
    result = 31 * result + (int) (ttl ^ (ttl >>> 32));
    result = 31 * result + records.hashCode();
    result = 31 * result + (ownerGroupId == null ? "NULL".hashCode() : ownerGroupId.hashCode());
    return result;
  }
}
