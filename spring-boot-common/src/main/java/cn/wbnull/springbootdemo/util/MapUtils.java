package cn.wbnull.springbootdemo.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Map 工具类
 *
 * @author dukunbiao(null) 2018-07-26
 * https://github.com/dkbnull/Util
 */
public class MapUtils {

    private MapUtils() {
    }

    /**
     * 检查Map是否为空
     *
     * @param map 待检查Map
     * @return true/false
     */
    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * String型获取Map value值
     *
     * @param map Map
     * @param key key值
     * @return value值
     */
    public static String getMapString(Map map, String key) {
        if (isEmpty(map) || StringUtils.isEmpty(key)) {
            return "";
        }

        if (map.containsKey(key) && map.get(key) != null) {
            return String.valueOf(map.get(key));
        }

        return "";
    }

    /**
     * int型获取Map value值
     *
     * @param map Map
     * @param key key值
     * @return value值
     */
    public static int getMapInt(Map map, String key) {
        if (isEmpty(map) || StringUtils.isEmpty(key)) {
            return 0;
        }

        if (map.containsKey(key) && map.get(key) != null) {
            return Integer.valueOf(map.get(key).toString());
        }

        return 0;
    }

    /**
     * int型获取Map value值，无法转化则返回默认值
     *
     * @param map          Map
     * @param key          key值
     * @param defaultValue 默认值
     * @return value值
     */
    public static int getMapInt(Map map, String key, int defaultValue) {
        if (isEmpty(map) || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            if (map.containsKey(key) && map.get(key) != null) {
                return Integer.valueOf(map.get(key).toString());
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }

        return defaultValue;
    }

    /**
     * double型获取Map value值
     *
     * @param map Map
     * @param key key值
     * @return value值
     */
    public static double getMapDouble(Map map, String key) {
        if (isEmpty(map) || StringUtils.isEmpty(key)) {
            return 0d;
        }

        if (map.containsKey(key) && map.get(key) != null) {
            return Double.valueOf(map.get(key).toString());
        }

        return 0d;
    }

    /**
     * double型获取Map value值，无法转化则返回默认值
     *
     * @param map          Map
     * @param key          key值
     * @param defaultValue 默认值
     * @return value值
     */
    public static double getMapDouble(Map map, String key, double defaultValue) {
        if (isEmpty(map) || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            if (map.containsKey(key) && map.get(key) != null) {
                return Double.valueOf(map.get(key).toString());
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }

        return defaultValue;
    }

    /**
     * Java Bean 转Map
     *
     * @param object 待转化Java Bean
     * @return Map
     */
    public static Map<String, String> javaBeanToMap(Object object) {
        return JSONUtils.JSONToMap(JSONObject.parseObject(JSONObject.toJSONString(object)));
    }

    /**
     * Map转 Java Bean
     *
     * @param map Map
     * @return Java Bean
     */
    public static <T> T mapToJavaBean(Map map, Class<T> clazz) {
        return JSONObject.parseObject(new JSONObject(map).toJSONString(), clazz);
    }
}
