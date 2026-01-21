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

public class AddZoneAclRuleResponse {
  private final ACLRule rule;

  /**
   * Constructs the response with the persisted ACL rule.
   *
   * @param rule the ACL rule returned by the server
   */
  public AddZoneAclRuleResponse(ACLRule rule) {
    this.rule = rule;
  }

  /** Returns the persisted ACL rule. */
  public ACLRule getRule() {
    return rule;
  }

  @Override
  public String toString() {
    return "AddZoneAclRuleResponse{" + "rule=" + rule + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AddZoneAclRuleResponse)) return false;
    AddZoneAclRuleResponse that = (AddZoneAclRuleResponse) o;
    return Objects.equals(rule, that.rule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rule);
  }
}
