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
package vinyldns.java.model.batch;

import vinyldns.java.model.record.RecordType;

public interface SingleChange {

  String getId();

  ChangeInputType getChangeType();

  SingleChangeStatus getStatus();

  String getSystemMessage();

  String getRecordChangeId();

  String getZoneId();

  String getRecordName();

  RecordType getType();

  String getInputName();

  String getZoneName();
}
