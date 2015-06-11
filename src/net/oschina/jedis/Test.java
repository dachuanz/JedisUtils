
package net.oschina.jedis;

import org.apache.log4j.PropertyConfigurator;

import redis.clients.jedis.Jedis;


/**
 * @author zdc
 * @since 2015年6月11日
 */
public class Test {
    static {
        PropertyConfigurator.configure("config/log4j.properties");
    }
public static void main(String[] args) {
    JedisUtils jedisUtils=   JedisUtils.getInstance();
       Jedis test=   jedisUtils.getJedis("XXXXXXXXXXXXX", 6379);
       String key ="ddd";
       String value="ddddd2w";
       System.out.println("xxxx");
       test.set(key.getBytes(), value.getBytes());
      byte [] bs=test.get(key.getBytes());
      System.out.println(new String(bs));
}
}
