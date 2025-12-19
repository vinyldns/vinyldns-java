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
/**
 * Copyright 2018 Comcast Cable Communications Management, LLC
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vinyldns.java.model.zone;

import java.util.Objects;
import java.util.StringJoiner;

public class ListZoneChangesRequest {

  private String zoneId;
  /**
   * In order to advance through pages of results, the startFrom is set to the nextId that is
   * returned on the previous response. It is up to the client to maintain previous pages if the
   * client wishes to advance forward and backward. If not specified, will return the first page of
   * results
   */
  private String startFrom;

  /**
   * The number of items to return in the page. Valid values are 1 to 100. Defaults to 100 if not
   * provided.
   */
  private Integer maxItems;

  public ListZoneChangesRequest() {}

  public ListZoneChangesRequest(String zoneId, String startFrom, Integer maxItems) {
    this.zoneId = zoneId;
    this.startFrom = startFrom;
    this.maxItems = maxItems;
  }

  public String getZoneId() {
    return zoneId;
  }

  public void setZoneId(String zoneId) {
    this.zoneId = zoneId;
  }

  public String getStartFrom() {
    return startFrom;
  }

  public void setStartFrom(String startFrom) {
    this.startFrom = startFrom;
  }

  public Integer getMaxItems() {
    return maxItems;
  }

  public void setMaxItems(Integer maxItems) {
    this.maxItems = maxItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ListZoneChangesRequest)) return false;
    ListZoneChangesRequest that = (ListZoneChangesRequest) o;
    return Objects.equals(getZoneId(), that.getZoneId())
        && Objects.equals(getStartFrom(), that.getStartFrom())
        && Objects.equals(getMaxItems(), that.getMaxItems());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ListZoneChangesRequest.class.getSimpleName() + "[", "]")
        .add("zoneId='" + zoneId + "'")
        .add("startFrom='" + startFrom + "'")
        .add("maxItems=" + maxItems)
        .toString();
  }
}
