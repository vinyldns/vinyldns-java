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

public class SSHFPData implements RecordData {
  private int algorithm;
  private int typ;
  private String fingerprint;

  public SSHFPData() {}

  public SSHFPData(int algorithm, int typ, String fingerprint) {
    this.algorithm = algorithm;
    this.typ = typ;
    this.fingerprint = fingerprint;
  }

  public int getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(int algorithm) {
    this.algorithm = algorithm;
  }

  public int getTyp() {
    return typ;
  }

  public void setTyp(int typ) {
    this.typ = typ;
  }

  public String getFingerprint() {
    return fingerprint;
  }

  public void setFingerprint(String fingerprint) {
    this.fingerprint = fingerprint;
  }

  @Override
  public String toString() {
    return "SSHFPData{"
        + "algorithm="
        + algorithm
        + ", typ="
        + typ
        + ", fingerprint='"
        + fingerprint
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SSHFPData sshfpData = (SSHFPData) o;

    if (algorithm != sshfpData.algorithm) return false;
    if (typ != sshfpData.typ) return false;
    return fingerprint.equals(sshfpData.fingerprint);
  }

  @Override
  public int hashCode() {
    int result = algorithm;
    result = 31 * result + typ;
    result = 31 * result + fingerprint.hashCode();
    return result;
  }
}
