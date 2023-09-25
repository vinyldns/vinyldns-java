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

import org.joda.time.DateTime;

public class UserInfo {
  private final String id;

  // optional
  private String userName, firstName, lastName, email;
  private DateTime created;
  private LockStatus lockStatus;

  public UserInfo(String id) {
    this.id = id;
    this.lockStatus = LockStatus.Unlocked;
  }

  public UserInfo(
      String id,
      String userName,
      String firstName,
      String lastName,
      String email,
      DateTime created,
      LockStatus lockStatus) {
    this.id = id;
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.created = created;
    this.lockStatus = lockStatus;
  }

  public String getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public DateTime getCreated() {
    return created;
  }

  public void setCreated(DateTime created) {
    this.created = created;
  }

  public LockStatus getLockStatus() {
    return lockStatus;
  }

  public void setLockStatus(LockStatus lockStatus) {
    this.lockStatus = lockStatus;
  }

  @Override
  public String toString() {
    return "UserInfo{"
        + "id='"
        + id
        + '\''
        + ", userName='"
        + userName
        + '\''
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", created="
        + created
        + ", lockStatus="
        + lockStatus
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserInfo userInfo = (UserInfo) o;
    if (!id.equals(userInfo.id)) return false;
    if (userName != null ? !userName.equals(userInfo.userName) : userInfo.userName != null)
      return false;
    if (firstName != null ? !firstName.equals(userInfo.firstName) : userInfo.firstName != null)
      return false;
    if (lastName != null ? !lastName.equals(userInfo.lastName) : userInfo.lastName != null)
      return false;
    if (email != null ? !email.equals(userInfo.email) : userInfo.email != null) return false;
    if (created != null ? !created.equals(userInfo.created) : userInfo.created != null)
      return false;
    return lockStatus.equals(userInfo.lockStatus);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (created != null ? created.hashCode() : 0);
    result = 31 * result + lockStatus.hashCode();
    return result;
  }
}
