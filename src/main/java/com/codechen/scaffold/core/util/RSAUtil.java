package com.codechen.scaffold.core.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：Java陈序员
 * @date 2023-04-06 17:29
 * @description RSA 加解密工具类
 */
public class RSAUtil {

    /**
     * 算法类型
     */
    public static final String ALGORITHMS = "RSA";

    /**
     * 公钥键名
     */
    public static final String PUBLIC_KEY = "publicKey";

    /**
     * 私钥键名
     */
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * 随机生成密钥对
     *
     * @return
     */
    public static Map<String, Object> generateKeyPairs() {
        Map<String, Object> keyPairs = new HashMap<>();

        try {
            // KeyPairGenerator 用于生成公钥和私钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHMS);
            // 初始化密钥对生成器，密钥大小为 96-1024 位
            keyPairGenerator.initialize(1024, new SecureRandom());

            // 生成一个密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // 获取私钥
            PrivateKey privateKey = keyPair.getPrivate();
            // 获取公钥
            PublicKey publicKey = keyPair.getPublic();

            // 对私钥进行 base64 编码
            String privateKeyString = new String(Base64.getEncoder().encode(privateKey.getEncoded()));
            // 对公钥进行 base64 编码
            String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));

            keyPairs.put(PRIVATE_KEY, privateKeyString);
            keyPairs.put(PUBLIC_KEY, publicKeyString);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return keyPairs;
    }

    /**
     * 公钥加密
     *
     * @param content   明文
     * @param publicKey 公钥
     * @param charset   字符编码
     * @return 密文
     */
    public static String encrypt(String content, String publicKey, Charset charset) {
        String encryptContent = "";
        try {
            byte[] decode = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = KeyFactory.getInstance(ALGORITHMS).generatePublic(new X509EncodedKeySpec(decode));
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            final byte[] bytes = content.getBytes(charset);
            //字符串长度
            final int len = bytes.length;
            int offset = 0;//偏移量
            int i = 0;//所分的段数
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();

            while (len > offset) {
                byte[] cache;
                if (len - offset > 117) {
                    cache = cipher.doFinal(bytes, offset, 117);
                } else {
                    cache = cipher.doFinal(bytes, offset, len - offset);
                }
                bos.write(cache);
                i++;
                offset = 117 * i;
            }
            bos.close();

            encryptContent = new String(Base64.getEncoder().encode(bos.toByteArray())).replaceAll("[\r\n]", "");

        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | IOException e) {
            e.printStackTrace();
        }

        return encryptContent;
    }


    /**
     * 公钥加密
     *
     * @param content   明文
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encrypt(String content, String publicKey) {
        return encrypt(content, publicKey, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密
     *
     * @param encryptContent 密文
     * @param privateKey     私钥
     * @param charset        字符
     * @return 明文
     */
    public static String decrypt(String encryptContent, String privateKey, Charset charset) {
        String content = "";
        try {
            final byte[] decoded = Base64.getDecoder().decode(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHMS).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            final byte[] inputByte = Base64.getDecoder().decode(encryptContent.getBytes(charset));

            final int len = inputByte.length;
            int offset = 0;
            int i = 0;
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while (len - offset > 0) {
                byte[] cache;
                if (len - offset > 128) {
                    cache = cipher.doFinal(inputByte, offset, 128);
                } else {
                    cache = cipher.doFinal(inputByte, offset, len - offset);
                }
                bos.write(cache);
                i++;
                offset = 128 * i;
            }
            bos.close();

            content = new String(bos.toByteArray(), charset);
        } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException
                | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }

        return content;

    }

    /**
     * 私钥解密
     *
     * @param encryptContent 密文
     * @param privateKey     私钥
     * @return 明文
     */
    public static String decrypt(String encryptContent, String privateKey) {
        return decrypt(encryptContent, privateKey, StandardCharsets.UTF_8);
    }
}
