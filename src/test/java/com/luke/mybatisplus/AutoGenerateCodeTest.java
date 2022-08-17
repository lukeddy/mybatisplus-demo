package com.luke.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class AutoGenerateCodeTest {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/mybatis_plus?characterEncoding=utf-8&userSSL=false", "root", "123456")
                        .globalConfig(builder -> {
                            builder.author("luke") // 设置作者
                             //.enableSwagger() // 开启 swagger 模式
                                    .fileOverride() // 覆盖已生成文件
                                    .outputDir("/tmp/mybatis_plus"); // 指定输出目录
                        }).packageConfig(builder -> {
                            builder.parent("com.luke.demo") // 设置父包名
                                    .moduleName("mybatisplus") // 设置父包模块名
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, "/tmp/mybatis_plus"));// 设置mapperXml生成路径
                        })
                        .strategyConfig(builder -> {
                            builder.addInclude("user"); // 设置需要生成的表名
                                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                        })
                        .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                        .execute();
    }
}
