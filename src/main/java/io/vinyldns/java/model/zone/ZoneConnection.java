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

public class ZoneConnection {
  private String name;
  private String keyName;
  private String key;
  private String primaryServer;

  public ZoneConnection() {}

  public ZoneConnection(String name, String keyName, String key, String primaryServer) {
    this.name = name;
    this.keyName = keyName;
    this.key = key;
    this.primaryServer = primaryServer;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKeyName() {
    return keyName;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getPrimaryServer() {
    return primaryServer;
  }

  public void setPrimaryServer(String primaryServer) {
    this.primaryServer = primaryServer;
  }

  @Override
  public String toString() {
    return "ZoneConnection{"
        + "name='"
        + name
        + '\''
        + ", keyName='"
        + keyName
        + '\''
        + ", key='"
        + key
        + '\''
        + ", primaryServer='"
        + primaryServer
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ZoneConnection that = (ZoneConnection) o;

    if (!name.equals(that.name)) return false;
    if (!keyName.equals(that.keyName)) return false;
    if (!key.equals(that.key)) return false;
    return primaryServer.equals(that.primaryServer);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + keyName.hashCode();
    result = 31 * result + key.hashCode();
    result = 31 * result + primaryServer.hashCode();
    return result;
  }
}
