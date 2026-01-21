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

/** Response object for GET /groups/change/{groupChangeId} */
public class GetGroupChangeResponse {
  private final GroupChange groupChange;

  public GetGroupChangeResponse(GroupChange groupChange) {
    this.groupChange = groupChange;
  }

  public GroupChange getGroupChange() {
    return groupChange;
  }

  @Override
  public String toString() {
    return "GetGroupChangeResponse{" + "groupChange=" + groupChange + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetGroupChangeResponse)) return false;
    GetGroupChangeResponse that = (GetGroupChangeResponse) o;
    return groupChange.equals(that.groupChange);
  }

  @Override
  public int hashCode() {
    return groupChange.hashCode();
  }
}
