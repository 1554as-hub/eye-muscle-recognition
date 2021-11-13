package com.eyecloud;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class GeneratorMain {
    public static final String USER_DIR = "D:\\IDEA-WorkeSpace\\eye-muscle-recognition\\eye-muscle-reverse";
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/eyescloud?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai"
                , "saber", "123456")
                .globalConfig(builder -> {
                    builder.author("balabala") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(USER_DIR+"\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com") // 设置父包名
                            .moduleName("eyescloud") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.serviceImpl")
                            .controller("controller")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, USER_DIR + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user_history_file") // 设置需要生成的表名
                            .addTablePrefix("sys_")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper")
                    ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
