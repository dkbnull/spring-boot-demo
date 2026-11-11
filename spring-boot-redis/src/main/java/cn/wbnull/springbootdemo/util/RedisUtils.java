package cn.wbnull.springbootdemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtils
 *
 * @author dukunbiao(null)  2024-03-16
 * https://github.com/dkbnull/SpringBootDemo
 */
@Component
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key     键
     * @param timeout 时间(秒)
     * @return
     */
    public boolean expire(String key, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 0 永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false 不存在
     */
    public boolean hasKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }

        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true 成功 false 失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒) time要大于0，如果time小于等于0，将设置无限期
     * @return true 成功 false 失败
     */
    public boolean set(String key, Object value, long timeout) {
        try {
            if (timeout <= 0) {
                return set(key, value);
            }

            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个值或多个
     */
    public void delete(String... keys) {
        if (keys == null || keys.length <= 0) {
            return;
        }

        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        } else {
            redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
        }
    }

    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     * @throws Exception
     */
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须 > 0");
        }

        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta
     * @return
     * @throws Exception
     */
    public long decrement(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须 > 0");
        }

        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     * @return true 存在 false 不存在
     */
    public boolean hasKeyHash(String key, String hashKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(key, hashKey));
    }

    /**
     * 获取缓存
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     * @return 值
     */
    public Object getHash(String key, String hashKey) {
        return key == null ? null : redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 向一张hash表中放入数据，如果不存在将创建
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     * @return true 成功 false失败
     */
    public boolean setHash(String key, String hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据，如果不存在将创建
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     * @param timeout 时间(秒) 注意：如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false 失败
     */
    public boolean setHash(String key, String hashKey, Object value, long timeout) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);

            if (timeout > 0) {
                expire(key, timeout);
            }

            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key      键 不能为null
     * @param hashKeys 项 可以是多个 不能为null
     */
    public void deleteHash(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 递增
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public long incrementHash(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 递增
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public double incrementHash(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public long decrementHash(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public double decrementHash(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * 获取key对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> getHash(String key) {
        return key == null ? null : redisTemplate.opsForHash().entries(key);
    }

    /**
     * 缓存
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean setHash(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 缓存并设置超时时间
     *
     * @param key     键
     * @param map     对应多个键值
     * @param timeout 时间(秒)
     * @return true 成功 false 失败
     */
    public boolean setHash(String key, Map<String, Object> map, long timeout) {
        try {
            redisTemplate.opsForHash().putAll(key, map);

            if (timeout > 0) {
                expire(key, timeout);
            }

            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> membersSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询，是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false 不存在
     */
    public boolean isMemberSet(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long addSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key     键
     * @param timeout 时间(秒)
     * @param values  值 可以是多个
     * @return 成功个数
     */
    public long addSet(String key, long timeout, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (timeout > 0) {
                expire(key, timeout);
            }
            return count;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sizeSet(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long removeSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return 0;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0到-1代表所有值
     * @return
     */
    public List<Object> rangeList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sizeList(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时，0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object indexList(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean rightPushList(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     * @return
     */
    public boolean rightPushList(String key, Object value, long timeout) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (timeout > 0) {
                expire(key, timeout);
            }

            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean rightPushList(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 时间(秒)
     * @return
     */
    public boolean rightPushList(String key, List<Object> value, long timeout) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (timeout > 0) {
                expire(key, timeout);
            }

            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean setList(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return false;
        }
    }

    /**
     * 移除N个值为value的数据
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long removeList(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error("Redis操作异常", e);
            return 0;
        }
    }
}
