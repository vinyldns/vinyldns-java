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

public class Zone {
  private String id;
  private String name;
  private String email;
  private ZoneStatus status;
  private DateTime created;
  private DateTime updated; // optional
  private ZoneConnection connection; // optional
  private ZoneConnection transferConnection; // optional
  private boolean shared;
  private ZoneACL acl;
  private String adminGroupId;
  private DateTime latestSync; // optional
  private Boolean isTest = false;
  private String backendId; // optional

  public Zone() {}

  public Zone(
      String id,
      String name,
      String email,
      ZoneStatus status,
      DateTime created,
      DateTime updated,
      ZoneConnection connection,
      ZoneConnection transferConnection,
      boolean shared,
      ZoneACL acl,
      String adminGroupId,
      DateTime latestSync,
      Boolean isTest,
      String backendId) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.status = status;
    this.created = created;
    this.updated = updated;
    this.connection = connection;
    this.transferConnection = transferConnection;
    this.shared = shared;
    this.acl = acl;
    this.adminGroupId = adminGroupId;
    this.latestSync = latestSync;
    this.isTest = isTest;
    this.backendId = backendId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ZoneStatus getStatus() {
    return status;
  }

  public void setStatus(ZoneStatus status) {
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

  public ZoneConnection getConnection() {
    return connection;
  }

  public void setConnection(ZoneConnection connection) {
    this.connection = connection;
  }

  public ZoneConnection getTransferConnection() {
    return transferConnection;
  }

  public void setTransferConnection(ZoneConnection transferConnection) {
    this.transferConnection = transferConnection;
  }

  public boolean isShared() {
    return shared;
  }

  public void setShared(boolean shared) {
    this.shared = shared;
  }

  public ZoneACL getAcl() {
    return acl;
  }

  public void setAcl(ZoneACL acl) {
    this.acl = acl;
  }

  public String getAdminGroupId() {
    return adminGroupId;
  }

  public void setAdminGroupId(String adminGroupId) {
    this.adminGroupId = adminGroupId;
  }

  public DateTime getLatestSync() {
    return latestSync;
  }

  public void setLatestSync(DateTime latestSync) {
    this.latestSync = latestSync;
  }

  public Boolean getTest() {
    return isTest;
  }

  public void setTest(Boolean test) {
    this.isTest = test;
  }

  public String getBackendId() {
    return backendId;
  }

  public void setBackendId(String backendId) {
    this.backendId = backendId;
  }

  @Override
  public String toString() {
    return "Zone{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + ", status="
        + status
        + ", created="
        + created
        + ", updated="
        + updated
        + ", connection="
        + connection
        + ", transferConnection="
        + transferConnection
        + ", shared="
        + shared
        + ", acl="
        + acl
        + ", adminGroupId='"
        + adminGroupId
        + '\''
        + ", latestSync="
        + latestSync
        + ", isTest="
        + isTest
        + ", backendId='"
        + backendId
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Zone zone = (Zone) o;

    if (shared != zone.shared) return false;
    if (!id.equals(zone.id)) return false;
    if (!name.equals(zone.name)) return false;
    if (!email.equals(zone.email)) return false;
    if (status != zone.status) return false;
    if (!created.equals(zone.created)) return false;
    if (updated != null ? !updated.equals(zone.updated) : zone.updated != null) return false;
    if (connection != null ? !connection.equals(zone.connection) : zone.connection != null)
      return false;
    if (transferConnection != null
        ? !transferConnection.equals(zone.transferConnection)
        : zone.transferConnection != null) return false;
    if (!acl.equals(zone.acl)) return false;
    if (!adminGroupId.equals(zone.adminGroupId)) return false;
    if (latestSync != null ? !latestSync.equals(zone.latestSync) : zone.latestSync != null)
      return false;
    if (backendId != null ? !backendId.equals(zone.backendId) : zone.backendId != null)
      return false;
    return zone.isTest == isTest;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + status.hashCode();
    result = 31 * result + created.hashCode();
    result = 31 * result + (updated != null ? updated.hashCode() : 0);
    result = 31 * result + (connection != null ? connection.hashCode() : 0);
    result = 31 * result + (transferConnection != null ? transferConnection.hashCode() : 0);
    result = 31 * result + (shared ? 1 : 0);
    result = 31 * result + acl.hashCode();
    result = 31 * result + adminGroupId.hashCode();
    result = 31 * result + (latestSync != null ? latestSync.hashCode() : 0);
    result = 31 * result + isTest.hashCode();
    result = 31 * result + backendId.hashCode();
    return result;
  }
}
