// Copyright (c) 2000-2009 GloryScience. All Rights Reserved.
// This software is the confidential and proprietary information of GloryScience
// Original author: zdc
// -------------------------------------------------------------------------
// GloryScience MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
// THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
// TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Glory Science SHALL NOT BE
// LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
// MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
//
// THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
// CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
// PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
// NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
// SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
// SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
// PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES"). GloryScience
// SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
// HIGH RISK ACTIVITIES.
// -------------------------------------------------------------------------
package net.oschina.jedis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zdc
 * @since 2015年6月9日
 */
public class JedisUtils {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 私有构造器.
     * 
     * @return
     */
    @SuppressWarnings("unused")
    private void JedisUtil() {

    }

    private static Map<String, JedisPool> maps = new HashMap<String, JedisPool>();

    /**
     * 获取连接池.
     * 
     * @return 连接池实例
     */
    private static JedisPool getPool(String ip, int port) {
        String key = ip + ":" + port;
        JedisPool pool = null;
        // int i=2;
        if (!maps.containsKey(key)) {

            try {
                /**
                 * 如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息 请尝试在构造JedisPool的时候设置自己的超时值.
                 * JedisPool默认的超时时间是2秒(单位毫秒) JedisPoolConfig
                 */
                JedisPoolConfig name = new JedisPoolConfig();
                // GenericObjectPoolConfig name = new GenericObjectPoolConfig();
                pool = new JedisPool(name, ip, port, 2000);
                maps.put(key, pool);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            pool = maps.get(key);
        }
        return pool;
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class RedisUtilHolder {

        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static JedisUtils instance = new JedisUtils();
    }

    /**
     * 当getInstance方法第一次被调用的时候，它第一次读取 RedisUtilHolder.instance，导致RedisUtilHolder类得到初始化；而这个类在装载并被初始化的时候，会初始化它的静
     * 态域，从而创建RedisUtil的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。
     * 这个模式的优势在于，getInstance方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。
     */
    public static JedisUtils getInstance() {
        return RedisUtilHolder.instance;
    }

    /**
     * 获取Redis实例.
     * 
     * @return Redis工具类实例
     */
    public Jedis getJedis(String ip, int port) {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                jedis = getPool(ip, port).getResource();
                // log.info("get redis master1!");
            } catch (Exception e) {
                log.error("get redis master1 failed!", e);
                // 销毁对象
                getPool(ip, port).returnBrokenResource(jedis);
            }
            count++;
        } while (jedis == null && count < 5);
        return jedis;
    }

    /**
     * 释放redis实例到连接池.
     * 
     * @param jedis
     *            redis实例
     */
    public void closeJedis(Jedis jedis, String ip, int port) {
        if (jedis != null) {
            getPool(ip, port).returnResourceObject(jedis);
        }
    }
}
