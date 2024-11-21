package com.codechen.scaffold.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author：Java陈序员
 * @date：2024-11-6 14:17
 * @description：代码生成配置
 */
@Configuration
@ConfigurationProperties(prefix = "codechen.generator")
@Data
public class GeneratorConfig {

    /** 表前缀 */
    private String tablePrefix;

    /** 是否忽略表前缀，默认不忽略 false */
    private Boolean ignoreTablePrefix = false;

    /** 作者 */
    private String author;

    /** 包名 */
    private String packageName;

    /** entity 后缀 */
    private String entitySuffix = "Entity";

    /** mapper 后缀 */
    private String mapperSuffix = "Mapper";

    /** 创建时间 */
    private String createTime;

    /** 是否开启 Swagger 默认开启 true */
    private Boolean enableSwagger = true;

    /** entity 继承基类 如配置值，则继承基类；否则不继承 示例(包 + 类名)：com.codechen.library.framework.domain.AbstractEntity */
    private String baseEntity;

    /** entity 忽略字段 多个用,(英文逗号)隔开*/
    private String ignoreColumn;

    /** controller 统一返回实体 示例(包 + 类名)：com.codechen.library.framework.domain.Result */
    private String commonResult;

}
