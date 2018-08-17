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
package vinyldns.java.model.batch;

import java.util.List;
import java.util.Objects;

public class CreateBatchRequest {
  private String comments;

  private List<ChangeInput> changes;

  public CreateBatchRequest(String comments, List<ChangeInput> changes) {
    this.comments = comments;
    this.changes = changes;
  }

  public CreateBatchRequest(List<ChangeInput> changes) {
    this(null, changes);
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public List<ChangeInput> getChanges() {
    return changes;
  }

  public void setChanges(List<ChangeInput> changes) {
    this.changes = changes;
  }

  @Override
  public String toString() {
    return "CreateBatchRequest{" +
        "comments='" + comments + '\'' +
        ", changes=" + changes +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreateBatchRequest that = (CreateBatchRequest) o;
    return Objects.equals(comments, that.comments) &&
        Objects.equals(changes, that.changes);
  }

  @Override
  public int hashCode() {

    return Objects.hash(comments, changes);
  }
}
