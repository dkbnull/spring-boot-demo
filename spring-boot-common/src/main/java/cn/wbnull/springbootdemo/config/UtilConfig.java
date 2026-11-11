package cn.wbnull.springbootdemo.config;


import cn.wbnull.springbootdemo.util.StringUtils;

/**
 * Logger 工具类
 *
 * @author dukunbiao(null)  2018-12-17
 * https://github.com/dkbnull/Util
 */
public class UtilConfig {

    private static String logLevel;

    private static void init() {
        logLevel = "8";
    }

    public static String getLogLevel() {
        if (StringUtils.isEmpty(logLevel)) {
            init();
        }

        return logLevel;
    }
}
