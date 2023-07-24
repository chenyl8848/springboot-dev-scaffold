package com.cyl.scaffold.util;

import com.cyl.scaffold.core.util.MD5Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author cyl
 * @date 2023-04-23 11:20
 * @description MD5Util 测试类
 */
public class MD5UtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MD5UtilTest.class);

    @Test
    public void testMD5() {
        String content = "Java 是最好的编程语言!";
        String salt = RandomStringUtils.random(5, "1234567890abcdefghijklmnopqrstuvwxyz");
        String md = MD5Util.MD5(content, StandardCharsets.UTF_8);
        LOGGER.info("md : {}", md);
        // md : d5e8a20de196599a047a1fccb79a643b
        String md5 = MD5Util.MD5(content, salt, StandardCharsets.UTF_8);
        LOGGER.info("salt : {} md5 : {}", salt, md5);
        // salt : jpnfn md5 : 4b2cc9729fd12ee0673bb4e01c555e81
    }
}
