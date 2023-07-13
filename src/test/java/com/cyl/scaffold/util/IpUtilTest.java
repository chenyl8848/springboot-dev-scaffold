package com.cyl.scaffold.util;

import com.cyl.scaffold.core.util.IpUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author cyl
 * @date 2023-07-10 11:20
 * @description ip 工具类测试类
 */
@SpringBootTest
public class IpUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtilTest.class);

    /**
     * 测试 ip 所属地址
     */
    @Test
    public void testGetIpRegion() {
        String ip = "220.248.12.158"; // IpRegion:上海
//        String ip = "47.52.236.180"; // IpRegion:香港
//        String ip = "172.22.12.123"; // IpRegion:内网IP
//        String ip = "164.114.53.60"; // IpRegion:美国
        String ipRegion = IpUtil.getIpRegion(ip);
        LOGGER.info("IpRegion:{}", ipRegion);
    }
}
