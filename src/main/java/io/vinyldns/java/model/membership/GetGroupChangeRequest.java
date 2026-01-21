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
package io.vinyldns.java.model.membership;

/** Request object for GET /groups/change/{groupChangeId} */
public class GetGroupChangeRequest {
  private final String groupChangeId;

  public GetGroupChangeRequest(String groupChangeId) {
    this.groupChangeId = groupChangeId;
  }

  public String getGroupChangeId() {
    return groupChangeId;
  }

  @Override
  public String toString() {
    return "GetGroupChangeRequest{" + "groupChangeId='" + groupChangeId + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetGroupChangeRequest)) return false;
    GetGroupChangeRequest that = (GetGroupChangeRequest) o;
    return groupChangeId.equals(that.groupChangeId);
  }

  @Override
  public int hashCode() {
    return groupChangeId.hashCode();
  }
}
