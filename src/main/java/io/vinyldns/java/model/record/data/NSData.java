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
package io.vinyldns.java.model.record.data;

public class NSData implements RecordData {
  private String nsdname;

  public NSData() {}

  public NSData(String nsdname) {
    this.nsdname = nsdname;
  }

  public String getNsdname() {
    return nsdname;
  }

  public void setNsdname(String nsdname) {
    this.nsdname = nsdname;
  }

  @Override
  public String toString() {
    return "NSData{" + "nsdname='" + nsdname + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NSData aData = (NSData) o;

    return nsdname.equals(aData.nsdname);
  }

  @Override
  public int hashCode() {
    return nsdname.hashCode();
  }
}
