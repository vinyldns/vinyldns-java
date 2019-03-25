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
package io.vinyldns.java.model.record.set;

import io.vinyldns.java.model.record.RecordType;
import io.vinyldns.java.model.record.data.RecordData;
import java.util.Collection;
import org.joda.time.DateTime;

public class RecordSet extends RecordSetBase {
  /**
   * the id of the recordset. This is important as you will use it for other recordset operations
   */
  private String id;

  /**
   * Active - RecordSet is added is created and ready for use, Inactive - RecordSet effects are not
   * applied, Pending - RecordSet is queued for creation, PendingUpdate - RecordSet is queued for
   * update, PendingDelete - RecordSet is queued for delete
   */
  private RecordSetStatus status;

  /** the timestamp GMT when the recordset was created */
  private DateTime created;

  /** the timestamp GMT when the recordset was last updated */
  private DateTime updated;

  public RecordSet() {}

  public RecordSet(
      String zoneId,
      String name,
      RecordType type,
      long ttl,
      Collection<RecordData> records,
      String id,
      String ownerGroupId,
      RecordSetStatus status,
      DateTime created,
      DateTime updated) {
    super(zoneId, name, type, ttl, records, ownerGroupId);
    this.id = id;
    this.status = status;
    this.created = created;
    this.updated = updated;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RecordSetStatus getStatus() {
    return status;
  }

  public void setStatus(RecordSetStatus status) {
    this.status = status;
  }

  public DateTime getCreated() {
    return created;
  }

  public void setCreated(DateTime created) {
    this.created = created;
  }

  public DateTime getUpdated() {
    return updated;
  }

  public void setUpdated(DateTime updated) {
    this.updated = updated;
  }

  @Override
  public String toString() {
    return "RecordSet{"
        + "id='"
        + id
        + '\''
        + super.toString()
        + ", status="
        + status
        + ", created="
        + created
        + ", updated="
        + updated
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    RecordSet recordSet = (RecordSet) o;

    if (!id.equals(recordSet.id)) return false;
    if (status != recordSet.status) return false;
    if (!created.equals(recordSet.created)) return false;
    return updated != null ? updated.equals(recordSet.updated) : recordSet.updated == null;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + id.hashCode();
    result = 31 * result + status.hashCode();
    result = 31 * result + created.hashCode();
    result = 31 * result + (updated != null ? updated.hashCode() : 0);
    return result;
  }
}
