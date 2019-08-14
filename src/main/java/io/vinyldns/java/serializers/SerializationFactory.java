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
package io.vinyldns.java.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vinyldns.java.model.batch.SingleChange;
import io.vinyldns.java.model.record.data.RecordData;
import org.joda.time.DateTime;

import java.time.Instant;

public class SerializationFactory {
  public static Gson createGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapterFactory(new RecordSetTypeAdapterFactory());
    gsonBuilder.registerTypeAdapterFactory(new AddSingleChangeAdapterFactory());
    gsonBuilder.registerTypeAdapter(RecordData.class, new RecordDataDeserializer());
    gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeSerializer());
    gsonBuilder.registerTypeAdapter(SingleChange.class, new ChangeInputDeserializer());
    gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
    return gsonBuilder.create();
  }
}
