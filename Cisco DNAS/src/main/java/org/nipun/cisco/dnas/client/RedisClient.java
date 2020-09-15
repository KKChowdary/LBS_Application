
package org.nipun.cisco.dnas.client;

import org.nipun.cisco.dnas.utils.ConfigUtil;

import redis.clients.jedis.Jedis;

public class RedisClient {

    private static Jedis jedis;

    public static Jedis getJedis() {
        if (jedis == null) {
            init();
        }
        return jedis;
    }

    private static void init() {
        String host = ConfigUtil.getConfig().getProperty("redis.host");
        jedis = new Jedis(host);
    }
}
