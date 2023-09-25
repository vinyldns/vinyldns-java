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

import java.util.Objects;
import java.util.Set;

public class ListAdminsResponse {
  private final Set<UserInfo> admins;

  public Set<UserInfo> getAdmins() {
    return admins;
  }

  public ListAdminsResponse(Set<UserInfo> admins) {
    this.admins = admins;
  }

  @Override
  public String toString() {
    return "ListAdminsResponse{" + "admins=" + admins + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ListAdminsResponse)) return false;
    ListAdminsResponse that = (ListAdminsResponse) o;
    return Objects.equals(getAdmins(), that.getAdmins());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAdmins());
  }
}
