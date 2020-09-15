/*
 * Copyright (c) 2019 Cisco Systems, Inc. and/or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nipun.cisco.dnas.consumer;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.nipun.cisco.dnas.service.CiscoDnasPluginService;

public class JsonEventConsumer {

    private static final Logger log = LogManager.getLogger(JsonEventConsumer.class);
    private long lastSuccessTimeStamp = -1;
    private RedisFeeder redisFeeder;
    private CiscoDnasPluginService dnasCiscoPluginService;

    public void accept(final JSONObject eventData, final Map<String, Long> activeTenantsMap, final String apiUrl, final String apiKey) {
        log.debug("Receving fist packet at consumer accept method.");
        if (redisFeeder == null) {
            log.debug("Creating new RedisFeeder object if object was null.");
            redisFeeder = new RedisFeeder();
        }
        String eventType = eventData.getString("eventType");
        log.debug("eventType : " + eventType);
        log.debug(eventData.toString());
        redisFeeder.accept(eventData, activeTenantsMap, apiUrl, apiKey);

        log.debug("[1]: Succesfully received the packet.");
        setLastSuccessTimeStamp(System.currentTimeMillis());
    }

    public long getLastSuccessTimeStamp() {
        return lastSuccessTimeStamp;
    }

    public void setLastSuccessTimeStamp(final long lastSuccessTimeStamp) {
        this.lastSuccessTimeStamp = lastSuccessTimeStamp;
    }
}
