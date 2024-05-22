package com.codechen.scaffold;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;

/**
 * @author：Java陈序员
 * @date 2023-04-10 16:22
 * @description MyBatis-Plus Generator UI 启动类
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        GeneratorConfig generatorConfig = GeneratorConfig.builder()
                // 数据库连接地址
                .jdbcUrl("jdbc:mysql://localhost:3306/community-group-buy?serverTimezone=Asia/Shanghai&characterEncoding=utf-8")
                // 数据库用户名
                .userName("root")
                // 数据库密码
                .password("root")
                // 数据库驱动
                .driverClassName("com.mysql.cj.jdbc.Driver")
                // 数据库 schema，MySQL,PGSQL,ORACLE,DB2 类型的数据库需要指定
                .schemaName("")
                // 自定义各类文件名生成规则
                .nameConverter(new NameConverter() {
                    @Override
                    public String controllerNameConvert(String entityName) {
                        return this.entityNameConvert(entityName) + "Controller";
                    }

                    @Override
                    public String serviceNameConvert(String entityName) {
                        return this.entityNameConvert(entityName) + "Service";
                    }

                    @Override
                    public String mapperNameConvert(String entityName) {
                        return this.entityNameConvert(entityName) + "Mapper";
                    }
                })
                // 所有生成 Java 文件的父级包名，也可以在 UI 界面上配置
                .basePackage("com.codechen.scaffold")
                // UI 界面端口
                .port(9090)
                .build();
        MybatisPlusToolsApplication.run(generatorConfig);
    }
}
