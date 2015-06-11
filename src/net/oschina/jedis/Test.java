// Copyright (c) 2000-2009 GloryScience. All Rights Reserved.
// This software is the confidential and proprietary information of GloryScience
// Original author: zdc
//-------------------------------------------------------------------------
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
//-------------------------------------------------------------------------
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
       Jedis test=   jedisUtils.getJedis("192.168.10.7", 6379);
       String key ="ddd";
       String value="ddddd2w";
       System.out.println("xxxx");
       test.set(key.getBytes(), value.getBytes());
      byte [] bs=test.get(key.getBytes());
      System.out.println(new String(bs));
}
}
