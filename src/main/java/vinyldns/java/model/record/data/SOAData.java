/**
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
package vinyldns.java.model.record.data;

public class SOAData implements RecordData {
    private String mname;
    private String rname;
    private Long serial;
    private Long refresh;
    private Long retry;
    private Long expire;
    private Long minimum;

    public SOAData() {
    }

    public SOAData(String mname, String rname, Long serial, Long refresh, Long retry, Long expire, Long minimum) {
        this.mname = mname;
        this.rname = rname;
        this.serial = serial;
        this.refresh = refresh;
        this.retry = retry;
        this.expire = expire;
        this.minimum = minimum;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public Long getRefresh() {
        return refresh;
    }

    public void setRefresh(Long refresh) {
        this.refresh = refresh;
    }

    public Long getRetry() {
        return retry;
    }

    public void setRetry(Long retry) {
        this.retry = retry;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Long getMinimum() {
        return minimum;
    }

    public void setMinimum(Long minimum) {
        this.minimum = minimum;
    }

    @Override
    public String toString() {
        return "SOAData{" +
                "mname='" + mname + '\'' +
                ", rname='" + rname + '\'' +
                ", serial=" + serial +
                ", refresh=" + refresh +
                ", retry=" + retry +
                ", expire=" + expire +
                ", minimum=" + minimum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SOAData soaData = (SOAData) o;

        if (!mname.equals(soaData.mname)) return false;
        if (!rname.equals(soaData.rname)) return false;
        if (!serial.equals(soaData.serial)) return false;
        if (!refresh.equals(soaData.refresh)) return false;
        if (!retry.equals(soaData.retry)) return false;
        if (!expire.equals(soaData.expire)) return false;
        return minimum.equals(soaData.minimum);
    }

    @Override
    public int hashCode() {
        int result = mname.hashCode();
        result = 31 * result + rname.hashCode();
        result = 31 * result + serial.hashCode();
        result = 31 * result + refresh.hashCode();
        result = 31 * result + retry.hashCode();
        result = 31 * result + expire.hashCode();
        result = 31 * result + minimum.hashCode();
        return result;
    }
}
