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

package io.vinyldns.java.model.health;

import java.util.Objects;

/** Request object for POST /status (enable/disable processing) */
public class UpdateStatusRequest {
  private final boolean processingEnabled;
  private final String message;

  public UpdateStatusRequest(boolean processingEnabled, String message) {
    this.processingEnabled = processingEnabled;
    this.message = message;
  }

  public boolean isProcessingEnabled() {
    return processingEnabled;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "UpdateStatusRequest{"
        + "processingEnabled="
        + processingEnabled
        + ", message='"
        + message
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UpdateStatusRequest)) return false;
    UpdateStatusRequest that = (UpdateStatusRequest) o;
    return processingEnabled == that.processingEnabled && Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(processingEnabled, message);
  }
}
