package com.cyl.scaffold.util;

import com.cyl.scaffold.core.util.RSAUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author cyl
 * @date 2023-04-23 10:45
 * @description RSAUtil 测试类
 */
public class RSAUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtilTest.class);

    /**
     * 测试生成密钥对
     */
    @Test
    public void testGenerateKeyPair() {
        Map<String, Object> keyPairs = RSAUtil.generateKeyPairs();
        LOGGER.info("publicKey : {}", keyPairs.get(RSAUtil.PUBLIC_KEY));
        // publicKey : MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHOn7KzB9Fi/Y6dAUYmhuBDEggUKfuTXtVxQA2rY1AnvCVnQQOA1qVy6T9kv7mUMnSE1B1/k89XCFSD6H7YctNz8F0ekFe8IUY+cvQG3W2kDvsxlHuHTWQ0AE2liJti3EialtPRkSLuusbmbDMYWnoMy2J73smRSyJOITCp0VZUwIDAQAB
        LOGGER.info("privateKey : {}", keyPairs.get(RSAUtil.PRIVATE_KEY));
        // privateKey : MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMc6fsrMH0WL9jp0BRiaG4EMSCBQp+5Ne1XFADatjUCe8JWdBA4DWpXLpP2S/uZQydITUHX+Tz1cIVIPofthy03PwXR6QV7whRj5y9AbdbaQO+zGUe4dNZDQATaWIm2LcSJqW09GRIu66xuZsMxhaegzLYnveyZFLIk4hMKnRVlTAgMBAAECgYEArnr4WDt0l8AAISlC3Uz6EYbkFAkPSZDqFy+1BC3yq0mHgkBFMNCXIOF82edG/gZ6+lSX/v59/0sUk33Faxc2qJ6ST/4QM/4UKHKIHzx1r4VBM67Or9IjRq10lkhkBbdBJraQMu8dVkapt1c8KtKQMyAvtZBh0ABV5t5PcTObygECQQDwjA5KUoDmchlaBuAKQjwN5HXSSSzCgL46fKNkH8JbTSRnGE2VLLeVBu3SkjFibEsLcFo0PoG+B6ptmFrQPwCTAkEA1AbuC2TXKRA0PjHLoiMQoLQpgW5dnqGD2T7V+D+6mxorwhyEWB51l0VLzMb/RJe3c3qmUeOGffKLYp1oc2V8QQJADpFc2DFw7wPf9yzNarGiM3EvlYGq/UiQvLgJqSmnOqJMXxYAtNUVdSgLRSJnbT+Dt/ig7eMPNnP+ZU7ByTTXkwJAL98krgPlpIrZENXTYVdDcNVcrbGKh7918n4Cd5uD+XMOQDSKGQ8/g5kMxT6E8irDK9qcZM7XVeaGXAyoQa2/QQJBAKnIx8uIw4Us6SYTbG1DrBVJQVgyIdA2p7S9RNeHfdFTMpbYpFHO535GXbY7u2u3vKmjQLccq7SP2BX5nopQ4Ls=
    }

    /**
     * 测试明文加密
     */
    @Test
    public void testEncrypt() {
        // 明文
        String content = "拳打南山猛虎，脚踢北海苍龙";
        // 公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHOn7KzB9Fi/Y6dAUYmhuBDEggUKfuTXtVxQA2rY1AnvCVnQQOA1qVy6T9kv7mUMnSE1B1/k89XCFSD6H7YctNz8F0ekFe8IUY+cvQG3W2kDvsxlHuHTWQ0AE2liJti3EialtPRkSLuusbmbDMYWnoMy2J73smRSyJOITCp0VZUwIDAQAB";

        String encryptContent = RSAUtil.encrypt(content, publicKey, StandardCharsets.UTF_8);
        LOGGER.info("encryptContent : {}", encryptContent);
        // encryptContent : q/J7TBHb7CvmaIp+ql3BRm+qf8PdTaRSX61+lPS6+tJMT4wjqV+iEz41zXiH+Gkjo4fpYmUyBowB8IZ0nOd+gtS35P/vCVdr1GI/RdJSH6WcCwHujmRtIzdThOez7t65DtDp2nAB+uK3j02iAN7nRQ/0MFaZHAhfsR4p9NJHsgg=
    }

    /**
     * 测试密文解密
     */
    @Test
    public void testDecrypt() {
        // 密文
        String encryptContent = "q/J7TBHb7CvmaIp+ql3BRm+qf8PdTaRSX61+lPS6+tJMT4wjqV+iEz41zXiH+Gkjo4fpYmUyBowB8IZ0nOd+gtS35P/vCVdr1GI/RdJSH6WcCwHujmRtIzdThOez7t65DtDp2nAB+uK3j02iAN7nRQ/0MFaZHAhfsR4p9NJHsgg=";
        // 私钥
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMc6fsrMH0WL9jp0BRiaG4EMSCBQp+5Ne1XFADatjUCe8JWdBA4DWpXLpP2S/uZQydITUHX+Tz1cIVIPofthy03PwXR6QV7whRj5y9AbdbaQO+zGUe4dNZDQATaWIm2LcSJqW09GRIu66xuZsMxhaegzLYnveyZFLIk4hMKnRVlTAgMBAAECgYEArnr4WDt0l8AAISlC3Uz6EYbkFAkPSZDqFy+1BC3yq0mHgkBFMNCXIOF82edG/gZ6+lSX/v59/0sUk33Faxc2qJ6ST/4QM/4UKHKIHzx1r4VBM67Or9IjRq10lkhkBbdBJraQMu8dVkapt1c8KtKQMyAvtZBh0ABV5t5PcTObygECQQDwjA5KUoDmchlaBuAKQjwN5HXSSSzCgL46fKNkH8JbTSRnGE2VLLeVBu3SkjFibEsLcFo0PoG+B6ptmFrQPwCTAkEA1AbuC2TXKRA0PjHLoiMQoLQpgW5dnqGD2T7V+D+6mxorwhyEWB51l0VLzMb/RJe3c3qmUeOGffKLYp1oc2V8QQJADpFc2DFw7wPf9yzNarGiM3EvlYGq/UiQvLgJqSmnOqJMXxYAtNUVdSgLRSJnbT+Dt/ig7eMPNnP+ZU7ByTTXkwJAL98krgPlpIrZENXTYVdDcNVcrbGKh7918n4Cd5uD+XMOQDSKGQ8/g5kMxT6E8irDK9qcZM7XVeaGXAyoQa2/QQJBAKnIx8uIw4Us6SYTbG1DrBVJQVgyIdA2p7S9RNeHfdFTMpbYpFHO535GXbY7u2u3vKmjQLccq7SP2BX5nopQ4Ls=";

        String content = RSAUtil.decrypt(encryptContent, privateKey, StandardCharsets.UTF_8);
        LOGGER.info("content : {}", content);
        // content : 拳打南山猛虎，脚踢北海苍龙
    }
}
