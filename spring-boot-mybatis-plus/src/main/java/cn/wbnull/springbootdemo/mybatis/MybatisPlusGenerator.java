package cn.wbnull.springbootdemo.mybatis;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Scanner;

/**
 * MybatisPlusGenerator
 *
 * @author dukunbiao(null)  2024-02-19
 * https://github.com/dkbnull/SpringBootDemo
 */
@Slf4j
public class MybatisPlusGenerator {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String PACKAGE_PATH = System.getProperty("user.dir") + "/spring-boot-mybatis-plus/src/main/java";
    private static final String RESOURCES_MAPPER_PATH = System.getProperty("user.dir") + "/spring-boot-mybatis-plus/src/main/resources/mapper/";

    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(URL, USERNAME, PASSWORD)
                .build();

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.global(globalConfig());
        autoGenerator.packageInfo(packageConfig());
        autoGenerator.strategy(strategyConfig());

        autoGenerator.execute();
    }

    private static GlobalConfig globalConfig() {
        return new GlobalConfig.Builder()
                .outputDir(PACKAGE_PATH)
                .author("null")
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .disableOpenDir()
                .build();
    }

    private static PackageConfig packageConfig() {
        return new PackageConfig.Builder()
                .parent("cn.wbnull.springbootdemo")
                .pathInfo(Collections.singletonMap(OutputFile.xml, RESOURCES_MAPPER_PATH))
                .build();
    }

    private static StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder()
                .addInclude(scanner().split(","))
                .mapperBuilder()
                .enableBaseResultMap()
                .enableBaseColumnList()
                .entityBuilder()
                .enableLombok()
                .enableTableFieldAnnotation()
                .build();
    }

    public static String scanner() {
        Scanner scanner = new Scanner(System.in);
        String hint = "请输入数据库表名，多个表名使用英文逗号分隔：";
        log.info(hint);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (ipt != null && ipt.length() > 0) {
                return ipt;
            }
        }

        throw new MybatisPlusException("请输入正确的数据库表名");
    }
}
