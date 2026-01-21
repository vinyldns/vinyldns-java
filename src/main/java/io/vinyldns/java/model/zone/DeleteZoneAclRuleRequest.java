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

package io.vinyldns.java.model.zone;

import io.vinyldns.java.model.acl.ACLRule;
import java.util.Objects;

public class DeleteZoneAclRuleRequest {
  private final String zoneId;
  private final ACLRule rule;

  /**
   * Constructs a request to delete an ACL rule from a zone.
   *
   * @param zoneId the target zone identifier
   * @param rule the ACL rule to delete (must match the server's stored rule fields)
   */
  public DeleteZoneAclRuleRequest(String zoneId, ACLRule rule) {
    this.zoneId = zoneId;
    this.rule = rule;
  }

  /** Returns the zone identifier. */
  public String getZoneId() {
    return zoneId;
  }

  /** Returns the ACL rule to delete. */
  public ACLRule getRule() {
    return rule;
  }

  @Override
  public String toString() {
    return "DeleteZoneAclRuleRequest{" + "zoneId='" + zoneId + '\'' + ", rule=" + rule + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DeleteZoneAclRuleRequest)) return false;
    DeleteZoneAclRuleRequest that = (DeleteZoneAclRuleRequest) o;
    return Objects.equals(zoneId, that.zoneId) && Objects.equals(rule, that.rule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zoneId, rule);
  }
}
