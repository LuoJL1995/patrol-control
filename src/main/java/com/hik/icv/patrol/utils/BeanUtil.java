package com.hik.icv.patrol.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 转换工具
 * @Author LuoJiaLei
 * @Date 2020/4/26
 * @Time 15:12
 */
public class BeanUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 构造新的destinationClass实例对象，通过source对象中的字段内容
     * 映射到destinationClass实例对象中，并返回新的destinationClass实例对象。
     *
     * @param source           源数据对象
     * @param destinationClass 要构造新的实例对象Class
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapListToObjectList(List<Map> mapList, Class<T> destinationClass) {
        List<T> objectList = new ArrayList();
        if (mapList == null || mapList.isEmpty()) {
            return objectList;
        }
        for (Map t : mapList) {
            T object = dozer.map(t, destinationClass);
            objectList.add(object);
        }
        return objectList;
    }


    /**
     * 将对象list转化成Map，property必须满足唯一性，同时是字符串
     *
     * @param list
     * @param property
     * @return
     * @throws Exception
     */
    public static <T> Map listToMap(List<T> list, String property) {
        Map map = new HashMap(128);
        if (list == null || list.isEmpty()) {
            return map;
        }
        try {
            for (T t : list) {
                String value = BeanUtils.getProperty(t, property);
                if (StringUtils.isNotEmpty(value)) {
                    map.put(value, t);
                }
            }
        } catch (Exception e) {
            logger.error("出现异常" + e);
        }
        return map;
    }

    /**
     * 将对象list转化成Map，property必须满足唯一性，同时是字符串
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static Map<String, String> listToMap(List<String> list) {
        Map<String, String> map = new HashMap(128);
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (String t : list) {
            if (StringUtils.isNotEmpty(t)) {
                map.put(t, t);
            }
        }
        return map;
    }

}
