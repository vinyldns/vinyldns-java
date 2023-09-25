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
package io.vinyldns.java.serializers;

import static org.testng.Assert.*;

import com.google.gson.Gson;
import io.vinyldns.java.model.record.data.*;
import org.testng.annotations.Test;

public class SerializationFactoryTest {
  private Gson gson = SerializationFactory.createGson();

  @Test
  public void testRecordData() {
    testSerialization(new AData("192.168.1.1"), AData.class);
    testSerialization(new AAAAData("2001:0db8:85a3:0000:0000:8a2e:0370:7334"), AAAAData.class);
    testSerialization(new CNAMEData("name"), CNAMEData.class);
    testSerialization(new MXData(1, "exchange"), MXData.class);
    testSerialization(new NSData("ns"), NSData.class);
    testSerialization(new PTRData("ptrdname"), PTRData.class);
    testSerialization(new SOAData("mname", "rname", 11L, 22L, 33L, 44L, 55L), SOAData.class);
    testSerialization(new SPFData("SPFData"), SPFData.class);
    testSerialization(new SRVData(0, 100, 80, "SPFData"), SRVData.class);
    testSerialization(new SSHFPData(0, 1, "SPFData"), SSHFPData.class);
    testSerialization(new TXTData("text"), TXTData.class);
    testSerialization(new UNKNOWNData("someRawUnknownData"), UNKNOWNData.class);
  }

  private <T> void testSerialization(T original, Class<T> clazz) {
    assertEquals(gson.fromJson(gson.toJson(original), clazz), original);
  }
}
