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

public class DeleteZoneAclRuleResponse {
  private final boolean deleted;
  private final ACLRule rule;

  /**
   * Constructs the response.
   *
   * @param deleted whether the rule was successfully deleted
   * @param rule the rule that was deleted (optional; include if server returns it)
   */
  public DeleteZoneAclRuleResponse(boolean deleted, ACLRule rule) {
    this.deleted = deleted;
    this.rule = rule;
  }

  /** Returns true if the rule was deleted. */
  public boolean isDeleted() {
    return deleted;
  }

  /** Returns the deleted rule, if the server returns it. May be null. */
  public ACLRule getRule() {
    return rule;
  }

  @Override
  public String toString() {
    return "DeleteZoneAclRuleResponse{" + "deleted=" + deleted + ", rule=" + rule + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DeleteZoneAclRuleResponse)) return false;
    DeleteZoneAclRuleResponse that = (DeleteZoneAclRuleResponse) o;
    return deleted == that.deleted && Objects.equals(rule, that.rule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deleted, rule);
  }
}
