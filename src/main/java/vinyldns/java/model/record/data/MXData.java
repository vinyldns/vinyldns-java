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
package vinyldns.java.model.record.data;

public class MXData implements RecordData {
  private int preference;
  private String exchange;

  public MXData() {}

  public MXData(int preference, String exchange) {
    this.preference = preference;
    this.exchange = exchange;
  }

  public int getPreference() {
    return preference;
  }

  public void setPreference(int preference) {
    this.preference = preference;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  @Override
  public String toString() {
    return "MXData{" + "preference=" + preference + ", exchange='" + exchange + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MXData mxData = (MXData) o;

    if (preference != mxData.preference) return false;
    return exchange.equals(mxData.exchange);
  }

  @Override
  public int hashCode() {
    int result = preference;
    result = 31 * result + exchange.hashCode();
    return result;
  }
}
