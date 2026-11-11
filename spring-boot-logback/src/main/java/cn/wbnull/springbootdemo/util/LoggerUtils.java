package cn.wbnull.springbootdemo.util;

import cn.wbnull.springbootdemo.config.UtilConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Logger 工具类
 *
 * @author dukunbiao(null)  2018-12-17
 * https://github.com/dkbnull/Util
 */
@Slf4j
public class LoggerUtils {

    private static final int OFF = 0;
    private static final int ERROR = 3;
    private static final int WARN = 4;
    private static final int INFO = 6;
    private static final int DEBUG = 7;
    private static final int ALL = 8;

    private LoggerUtils() {
    }

    private static boolean offLevel() {
        return StringUtils.toInt(UtilConfig.getLogLevel(), 0) == OFF;
    }

    private static boolean errorLevel() {
        return StringUtils.toInt(UtilConfig.getLogLevel(), 0) > ERROR;
    }

    private static boolean warnLevel() {
        return StringUtils.toInt(UtilConfig.getLogLevel(), 0) > WARN;
    }

    private static boolean infoLevel() {
        return StringUtils.toInt(UtilConfig.getLogLevel(), 0) > INFO;
    }

    private static boolean debugLevel() {
        return StringUtils.toInt(UtilConfig.getLogLevel(), 0) > DEBUG;
    }

    public static void error(String message) {
        if (offLevel()) {
            return;
        }

        if (errorLevel()) {
            log.error(message);
        }
    }

    public static void error(String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (errorLevel()) {
            log.error(toMessage(position, method, content));
        }
    }

    public static void error(String mac, String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (errorLevel()) {
            log.error(toMessage(mac, position, method, content));
        }
    }

    public static void error(String message, Throwable t) {
        if (offLevel()) {
            return;
        }

        if (errorLevel()) {
            log.error(message, t);
        }
    }

    public static void error(String position, String method, Throwable t) {
        if (offLevel()) {
            return;
        }

        if (errorLevel()) {
            log.error(toMessage(position, method, ""), t);
        }
    }

    public static void error(String mac, String position, String method, Throwable t) {
        if (offLevel()) {
            return;
        }

        if (errorLevel()) {
            log.error(toMessage(mac, position, method, ""), t);
        }
    }

    public static void warn(String message) {
        if (offLevel()) {
            return;
        }

        if (warnLevel()) {
            log.warn(message);
        }
    }

    public static void warn(String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (warnLevel()) {
            log.warn(toMessage(position, method, content));
        }
    }

    public static void warn(String mac, String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (warnLevel()) {
            log.warn(toMessage(mac, position, method, content));
        }
    }

    public static void info(String message) {
        if (offLevel()) {
            return;
        }

        if (infoLevel()) {
            log.info(message);
        }
    }

    public static void info(String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (infoLevel()) {
            log.info(toMessage(position, method, content));
        }
    }

    public static void info(String mac, String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (infoLevel()) {
            log.info(toMessage(mac, position, method, content));
        }
    }

    public static void debug(String message) {
        if (offLevel()) {
            return;
        }

        if (debugLevel()) {
            log.debug(message);
        }
    }

    public static void debug(String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (debugLevel()) {
            log.debug(toMessage(position, method, content));
        }
    }

    public static void debug(String mac, String position, String method, String content) {
        if (offLevel()) {
            return;
        }

        if (debugLevel()) {
            log.debug(toMessage(mac, position, method, content));
        }
    }

    private static String toMessage(String position, String method, String content) {
        return "\n位置：" + position + "\n接口：" + method + "\n参数：" + content + "\n";
    }

    private static String toMessage(String mac, String position, String method, String content) {
        return "\n终端：" + mac + "\n位置：" + position + "\n接口：" + method + "\n参数：" + content + "\n";
    }
}
