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
package io.vinyldns.java.model.batch;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListBatchChangesRequest {
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
}
