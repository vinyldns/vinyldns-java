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
package io.vinyldns.java.responses;

public class VinylDNSResponse<T> implements ResponseMarker {
  private T value;

  private String messageBody;

  private int statusCode;

  public VinylDNSResponse(T value, String messageBody, int statusCode) {
    this.value = value;
    this.messageBody = messageBody;
    this.statusCode = statusCode;
  }

  public T getValue() {
    return value;
  }

  public String getMessageBody() {
    return messageBody;
  }

  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String toString() {
    return "value=" + value + ", messageBody=" + messageBody + ", statusCode=" + statusCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VinylDNSResponse<?> that = (VinylDNSResponse<?>) o;

    if (statusCode != that.statusCode) return false;
    if (value != null ? !value.equals(that.value) : that.value != null) return false;
    return messageBody != null ? messageBody.equals(that.messageBody) : that.messageBody == null;
  }

  @Override
  public int hashCode() {
    int result = value != null ? value.hashCode() : 0;
    result = 31 * result + (messageBody != null ? messageBody.hashCode() : 0);
    result = 31 * result + statusCode;
    return result;
  }
}
