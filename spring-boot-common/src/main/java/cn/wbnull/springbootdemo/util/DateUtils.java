package cn.wbnull.springbootdemo.util;

import cn.wbnull.springbootdemo.constant.UtilConstants;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date 工具类
 *
 * @author dukunbiao(null)  2018-08-31
 * https://github.com/dkbnull/Util
 */
public class DateUtils {

    private DateUtils() {
    }

    /**
     * 时间格式化
     *
     * @return 时间字符串
     */
    public static String dateFormat() {
        return new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).format(new Date());
    }

    /**
     * 时间格式化
     *
     * @param date 待格式化时间
     * @return 时间字符串
     */
    public static String dateFormat(Date date) {
        return new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).format(date);
    }

    /**
     * 时间格式化
     *
     * @param format 格式
     * @return 时间字符串
     */
    public static String dateFormat(String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(new Date());
    }

    /**
     * 时间格式化
     *
     * @param date   待格式化时间
     * @param format 格式
     * @return 时间字符串
     */
    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(date);
    }

    /**
     * 时间字符串格式化
     *
     * @param dateValue 待格式化时间字符串
     * @param toFormat  要格式化的格式
     * @return 时间字符串
     * @throws Exception
     */
    public static String dateFormat(String dateValue, String toFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(toFormat)) {
            return null;
        }

        Date date = new SimpleDateFormat(UtilConstants.DATE_FORMAT, Locale.CHINA).parse(dateValue);
        return dateFormat(date, toFormat);
    }

    /**
     * 时间字符串格式化
     *
     * @param dateValue  待格式化时间字符串
     * @param fromFormat 待格式化时间字符串格式
     * @param toFormat   要格式化的格式
     * @return 时间字符串
     * @throws Exception
     */
    public static String dateFormat(String dateValue, String fromFormat, String toFormat) throws Exception {
        if (StringUtils.isEmpty(dateValue) || StringUtils.isEmpty(fromFormat) || StringUtils.isEmpty(toFormat)) {
            return null;
        }

        Date date = new SimpleDateFormat(fromFormat, Locale.CHINA).parse(dateValue);
        return dateFormat(date, toFormat);
    }

    /**
     * 获取Linux时间戳，精确到秒
     *
     * @return Linux时间戳
     */
    public static String currentTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
