package com.hjb.tool;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.sql.Driver;

public class Generator {
    public final static String DB_CONNECTION = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true";
    public final static String DB_USER_NAME = "root";
    public final static String DB_PWD = "123456";
    public final static String SYS_PACKAGE_NAME = "com.hjb";
    public final static String SYS_AHURTOR = "jinmu";

    public static void main(String[] args) {
        String[] tableNames = new String[]{"goods"};
        String[] modules = new String[]{"service", "web"};//项目模块名，需自定义
        for (String module : modules) {
            moduleGenerator(module, tableNames);
        }
    }

    private static void moduleGenerator(String module, String[] tableNames) {
        GlobalConfig globalConfig = getGlobalConfig(module);// 全局配置
        DataSourceConfig dataSourceConfig = getDataSourceConfig();// 数据源配置
        PackageConfig packageConfig = getPackageConfig(module);// 包配置
        StrategyConfig strategyConfig = getStrategyConfig(tableNames);// 策略配置

        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setPackageInfo(packageConfig)
                .setStrategy(strategyConfig)
                .execute();

    }
    private static StrategyConfig getStrategyConfig(String[] tableNames) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)//驼峰命名
                .setEntityLombokModel(true)
                .setRestControllerStyle(false)
                //.setTablePrefix("nideshop_")
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        return strategyConfig;
    }

    private static PackageConfig getPackageConfig(String module) {
        PackageConfig packageConfig = new PackageConfig();
        String packageName = SYS_PACKAGE_NAME;
        packageConfig.setParent(packageName)
                .setEntity("bean")
                .setMapper("dao")
                .setService("service")
                .setController("controller");
        return packageConfig;
    }

    private static DataSourceConfig getDataSourceConfig() {
        String dbUrl = DB_CONNECTION;
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        //"com.mysql.cj.jdbc.Driver"
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName(Driver.class.getName())
                .setUsername(DB_USER_NAME)
                .setPassword(DB_PWD)
                .setUrl(dbUrl);
        return dataSourceConfig;
    }

    private static GlobalConfig getGlobalConfig(String module) {
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOpen(false)//new File(module).getAbsolutePath()得到模块根目录路径，因事Maven项目，代码指定路径自定义调整
                //.setOutputDir(new File(module).getAbsolutePath() + "/src/main/java")//生成文件的输出目录
                .setOutputDir(projectPath + "/src/main/java")//生成文件的输出目录
                .setFileOverride(true)//是否覆盖已有文件
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setActiveRecord(false).setDateType(DateType.ONLY_DATE)
                .setAuthor(SYS_AHURTOR)
                .setServiceName("%sService");
        return globalConfig;
    }

}