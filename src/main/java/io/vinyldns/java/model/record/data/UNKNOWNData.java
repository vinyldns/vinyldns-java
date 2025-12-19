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
package io.vinyldns.java.model.record.data;

public class UNKNOWNData implements RecordData {
  private String rawData;

  public UNKNOWNData() {}

  public UNKNOWNData(String rawData) {
    this.rawData = rawData;
  }

  public String getRawData() {
    return rawData;
  }

  public void setRawData(String rawData) {
    this.rawData = rawData;
  }

  @Override
  public String toString() {
    return "UNKNOWNData{" + "rawData='" + rawData + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UNKNOWNData that = (UNKNOWNData) o;

    return rawData != null ? rawData.equals(that.rawData) : that.rawData == null;
  }

  @Override
  public int hashCode() {
    return rawData != null ? rawData.hashCode() : 0;
  }
}
