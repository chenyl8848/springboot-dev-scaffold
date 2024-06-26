# Spring Boot快速开发脚手架

> 封装开发中常用的 `SpringBoot` 集成。
> 
> 配套前端：https://github.com/chenyl8848/vue3-admin-template

## 目录
- [][项目环境](#项目环境)
- [][集成 `Swagger`](#集成-swagger)
- [][集成 `MyBatis Plus`](#集成-mybatisplus)
- [][多数据源](#多数据源)

## 项目环境

## 集成 `Swagger`

### 依赖

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>${swagger.version}</version>
</dependency>

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>swagger-bootstrap-ui</artifactId>
    <version>${swagger-bootstrap-ui.version}</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>${springfox-swagger-ui.version}</version>
</dependency>
```

说明：
1. `swagger-bootstrap-ui`：通过 `http://ip:port/doc.html` 访问
2. `springfox-swagger-ui`：通过 `http://ip:port/swagger-ui.html` 访问

### 配置

```java
/**
 * @author：Java陈序员
 * @date 2023-03-22 16:06
 * @description swagger 配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 文档信息
     * @return
     */
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot 开发脚手架")
                .description("SpringBoot 快速开发脚手架")
                .contact(new Contact("Java陈序员", "https://github.com/chenyl8848", "1063415880@qq.com"))
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket docket(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.codechen.scaffold.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
```

## 集成 `MyBatis Plus`
### 依赖
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>${mybaits-plus.version}</version>
</dependency>
```

### 配置
```java
@Configuration
@MapperScan(basePackages = {"com.codechen.scaffold.mapper"})
public class MyBatisPlusConfig {

    /**
     * 全局插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 禁止全表 删除/更新 插件
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * 自动填充字段
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
                this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
                this.setFieldValByName("logicDelete", CommonCodeEnum.NORMAL.getCode(), metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            }
        };
    }
}
```

## 跨域处理

## 登录鉴权

## 防护 `XSS`、`SQL` 注入攻击

## 幂等性处理

## 参数校验

## 全局异常处理

## 多数据源

## 参数加解密

## 数据脱敏

## 文件上传下载

## 日志处理

## 集成 `Redis`

## 集成 `Spring Cache`

## 分布式锁

## 集成 `POI`

## 导入导出

## 本地缓存

## 定时任务

## 代码自动生成

## 常用工具类

### `RSA` 加解密工具类
```java
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

```

### `MD5` 工具类
```java
public class MD5Util {

    private static final String ALGORITHMS = "MD5";

    /**
     * MD5 加密
     *
     * @param content 明文
     * @param charset 字符
     * @return 摘要
     */
    public static String MD5(String content, Charset charset) {
        String md5 = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHMS);

            messageDigest.update(content.getBytes(charset));

            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    /**
     * MD5 加密
     *
     * @param content 明文
     * @param salt    随机盐
     * @param charset 字符
     * @return 摘要
     */
    public static String MD5(String content, String salt, Charset charset) {
        String md5 = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHMS);
            messageDigest.update(content.getBytes(charset));
            messageDigest.update(salt.getBytes(charset));

            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    /**
     * MD5 加密
     *
     * @param content 明文
     * @return 摘要
     */
    public static String MD5(String content) {
        return MD5(content, StandardCharsets.UTF_8);
    }

    /**
     * MD5 加密
     *
     * @param content 明文
     * @param salt    随机盐
     * @return 摘要
     */
    public static String MD5(String content, String salt) {
        return MD5(content, salt, StandardCharsets.UTF_8);
    }
}

```

## 性能监控