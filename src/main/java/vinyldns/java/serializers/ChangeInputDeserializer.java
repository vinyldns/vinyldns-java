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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import vinyldns.java.model.batch.SingleChange;

public class ChangeInputDeserializer
    implements JsonDeserializer<SingleChange>, JsonSerializer<SingleChange> {

  @Override
  public JsonElement serialize(SingleChange src, Type typeOfSrc, JsonSerializationContext context) {
    return context.serialize(src, src.getClass()).getAsJsonObject();
  }

  @Override
  public SingleChange deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    JsonElement typeElement = jsonObject.get("changeType");

    if (typeElement.isJsonNull()) {
      throw new JsonParseException("Unknown element changeType: null");
    }

    String type = typeElement.getAsString();

    try {
      String thepackage = SingleChange.class.getPackage().getName() + ".";

      return context.deserialize(
          jsonObject, Class.forName(thepackage + type + SingleChange.class.getSimpleName()));

    } catch (ClassNotFoundException cnfe) {
      throw new JsonParseException("Unknown element changeType: " + type, cnfe);
    }
  }
}
