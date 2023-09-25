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
package io.vinyldns.java.model.batch;

/** Helper class for serializing review comments (when approving/rejecting batch changes) */
public final class BatchChangeReview {
  private final String reviewComment;

  public BatchChangeReview(String reviewComment) {
    this.reviewComment = reviewComment;
  }

  public String getReviewComment() {
    return reviewComment;
  }

  @Override
  public int hashCode() {
    return reviewComment.hashCode();
  }

  @Override
  public String toString() {
    return "BatchChangeReview{" + "reviewComment='" + reviewComment + '\'' + "}";
  }
}
