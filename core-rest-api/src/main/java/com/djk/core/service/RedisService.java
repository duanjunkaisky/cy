package com.djk.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作Service
 *
 * @author duanjunkai
 * @date 2024/05/02
 */
public interface RedisService
{

    /**
     * 保存属性
     *
     * @param key
     * @param value
     * @param time
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 获取属性
     *
     * @param key
     * @return {@link Object}
     */
    Object get(String key);

    /**
     * 删除属性
     *
     * @param key
     * @return {@link Boolean}
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     *
     * @param keys
     * @return {@link Long}
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @return {@link Boolean}
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key
     * @return {@link Long}
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     *
     * @param key
     * @return {@link Boolean}
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     *
     * @param key
     * @param delta
     * @return {@link Long}
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     *
     * @param key
     * @param delta
     * @return {@link Long}
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     *
     * @param key
     * @param hashKey
     * @return {@link Object}
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return {@link Boolean}
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     *
     * @param key
     * @return {@link Map<Object, Object>}
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     *
     * @param key
     * @param map
     * @param time
     * @return {@link Boolean}
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     *
     * @param key
     * @param map
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 删除Hash结构中的属性
     *
     * @param key
     * @param hashKey
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key
     * @param hashKey
     * @return {@link Boolean}
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return {@link Long}
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     *
     * @param key
     * @param hashKey
     * @param delta
     * @return {@link Long}
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     *
     * @param key
     * @return {@link Set<Object>}
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     *
     * @param key
     * @param values
     * @return {@link Long}
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     *
     * @param key
     * @param time
     * @param values
     * @return {@link Long}
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     *
     * @param key
     * @param value
     * @return {@link Boolean}
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     *
     * @param key
     * @return {@link Long}
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     *
     * @param key
     * @param values
     * @return {@link Long}
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     *
     * @param key
     * @param start
     * @param end
     * @return {@link List<Object>}
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     *
     * @param key
     * @return {@link Long}
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     *
     * @param key
     * @param index
     * @return {@link Object}
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     *
     * @param key
     * @param value
     * @return {@link Long}
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     *
     * @param key
     * @param value
     * @param time
     * @return {@link Long}
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     *
     * @param key
     * @param values
     * @return {@link Long}
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     *
     * @param key
     * @param time
     * @param values
     * @return {@link Long}
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     *
     * @param key
     * @param count
     * @param value
     * @return {@link Long}
     */
    Long lRemove(String key, long count, Object value);

    /**
     * 获取id
     * @param table
     * @return {@link Long}
     */
    Long generateIdCommon(String table);

    /**
     * 获取id
     * @param key
     * @return {@link Long}
     */
    Long generateId(String key);

    /**
     * 获取id
     * @param key
     * @param sec
     * @return {@link Long}
     */
    Long generateId(String key, long sec);
}