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

public class SRVData implements RecordData {
    private int priority;
    private int weight;
    private int port;
    private String target;

    public SRVData() {
    }

    public SRVData(int priority, int weight, int port, String target) {
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.target = target;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "SRVData{" +
                "priority=" + priority +
                ", weight=" + weight +
                ", port=" + port +
                ", target='" + target + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SRVData srvData = (SRVData) o;

        if (priority != srvData.priority) return false;
        if (weight != srvData.weight) return false;
        if (port != srvData.port) return false;
        return target.equals(srvData.target);
    }

    @Override
    public int hashCode() {
        int result = priority;
        result = 31 * result + weight;
        result = 31 * result + port;
        result = 31 * result + target.hashCode();
        return result;
    }
}
