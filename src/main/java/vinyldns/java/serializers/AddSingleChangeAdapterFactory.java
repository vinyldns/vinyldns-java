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
package vinyldns.java.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import vinyldns.java.model.batch.AddSingleChange;

public class AddSingleChangeAdapterFactory implements TypeAdapterFactory {

  public <T> TypeAdapter<T> create(final Gson gson, TypeToken<T> type) {
    if (!type.getType().equals(AddSingleChange.class)) {
      return null;
    }

    final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
    return new TypeAdapter<T>() {
      public void write(JsonWriter out, T value) throws IOException {
        delegate.write(out, value);
      }

      public T read(JsonReader in) throws IOException {
        JsonElement tree = gson.fromJson(in, JsonElement.class);
        JsonObject jsonObject = tree.getAsJsonObject();

        JsonElement record = jsonObject.get("record");

        if (record.isJsonNull()) {
          return delegate.fromJsonTree(jsonObject);
        }

        JsonElement type = jsonObject.get("type");

        record.getAsJsonObject().add("type", type);

        return delegate.fromJsonTree(jsonObject);
      }
    };
  }
}
