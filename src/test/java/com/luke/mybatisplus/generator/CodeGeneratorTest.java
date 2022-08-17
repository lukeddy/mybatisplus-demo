package com.luke.mybatisplus.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGeneratorTest {
    public static void main(String[] args) {
        final String SAVE_FOLDER="/tmp/mybatis_plus";
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/demo_authdb?characterEncoding=utf-8&userSSL=false", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("luke") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(SAVE_FOLDER); // 指定输出目录
                }).packageConfig(builder -> {
                    builder.parent("com.luke") // 设置父包名
                            .moduleName("mybatisplus") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, SAVE_FOLDER));// 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("account,customer,resource,role,role_resource"); // 设置需要生成的表名
                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
