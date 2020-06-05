package com.hik.icv.patrol.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description 集合工具
 * @Author LuoJiaLei
 * @Date 2020/4/26
 * @Time 15:13  
 */
public class CollectionUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CollectionUtil.class);

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     */
    public static List extractToList(final Collection collection, final String propertyName) {

        if (collection == null) {
            return new ArrayList();
        }

        List list = new ArrayList(collection.size());

        try {
            for (Object obj : collection) {
                Object property = PropertyUtils.getProperty(obj, propertyName);
                if (property != null) {
                    list.add(property);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Checked Exception.", e);
        }

        return list;
    }

    /**
     * see extractToList(final Collection collection, final String propertyName) 元素不重复
     *
     * @param collection
     * @param propertyName
     * @return
     */
    public static List extractToListUnRepeat(final Collection collection, final String propertyName) {

        List list = new ArrayList();

        if (collection == null) {
            return list;
        }

        try {
            for (Object obj : collection) {
                Object property = PropertyUtils.getProperty(obj, propertyName);
                if (null != property && !list.contains(property)) {
                    list.add(property);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Checked Exception.", e);
        }

        return list;
    }

    public static List extractToListWithSeparator(final Collection<String> collection, final String separator) {
        List list = new ArrayList();

        try {
            for (String obj : collection) {
                if (obj.contains(separator)) {
                    list.add(obj.substring(0, obj.indexOf(separator)));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Checked Exception.", e);
        }

        return list;
    }

    /**
     * 搜索集合中的对象的一个属性propertyName == value 只返回第一个匹配的对象
     *
     * @param collection
     * @param propertyName
     * @param value
     * @return
     */
    public static Object findFirst(final Collection collection, final String propertyName, final Object value) {
        if (collection == null) {
            return null;
        }
        if (value == null) {
            return null;
        }
        for (Object obj : collection) {
            try {
                if (value.equals(PropertyUtils.getProperty(obj, propertyName))) {
                    return obj;
                }
            } catch (Exception e) {
                throw new RuntimeException("Unexpected Checked Exception.", e);
            }
        }
        return null;
    }

    public static Object findTheOne(final Collection collection, final String propertyName, final Object value) {
        return findFirst(collection, propertyName, value);
    }

    /**
     * 搜索集合中的对象的一个属性propertyName == value 返回全部匹配的对象
     *
     * @param collection
     * @param propertyName
     * @param value
     * @return
     */
    public static List findAll(final Collection collection, final String propertyName, final Object value) {
        List list = new ArrayList();

        for (Object obj : collection) {
            try {
                if (value.equals(PropertyUtils.getProperty(obj, propertyName))) {
                    list.add(obj);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unexpected Checked Exception.", e);
            }
        }
        return list;
    }

    public static List findTheOnes(final Collection collection, final String propertyName, final Object value) {
        return findAll(collection, propertyName, value);
    }

    /**
     * 搜索集合中的对象的一个属性propertyName == value 返回全部匹配的对象
     *
     * @param collection
     * @param propertyName
     * @param values
     * @return
     */
    public static List findAll(final Collection collection, final String propertyName, final Collection values) {
        List list = new ArrayList();

        for (Object obj : collection) {
            try {
                if (values.contains(PropertyUtils.getProperty(obj, propertyName))) {
                    list.add(obj);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unexpected Checked Exception.", e);
            }
        }
        return list;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static Boolean isEmpty(final Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 移除重复
     *
     * @param collection
     * @return
     */
    public static List<String> removeDuplicate(final Collection<String> collection) {

        List<String> unrepeatedList = new ArrayList();
        collection.forEach(string -> {
            if (!unrepeatedList.contains(string)) {
                unrepeatedList.add(string);
            }
        });

        return unrepeatedList;
    }

    /**
     * 删除集合中, 某个属性重复的所有对象, 只保留第一个
     *
     * @param collection
     * @param propertyName
     * @return
     */
    public static List removeDuplicateBy(final Collection collection, final String propertyName) {

        List<Object> propertyList = new ArrayList<>();
        List unrepeatedList = new ArrayList();

        collection.forEach(item -> {
            try {
                Object property = PropertyUtils.getProperty(item, propertyName);
                if (!propertyList.contains(property)) {
                    propertyList.add(property);
                    unrepeatedList.add(item);
                }
            } catch (Exception e) {
                throw new RuntimeException("Unexpected Checked Exception.", e);
            }
        });

        return unrepeatedList;
    }

    /**
     * 集合中是否包含重复对象
     *
     * @param collection
     * @param propertyName
     * @return
     */
    public static Boolean hasDuplicate(final Collection collection, final String propertyName) {

        if (collection == null || collection.size() == 0) {
            return false;
        }

        List unrepeatedList = removeDuplicateBy(collection, propertyName);
        return collection.size() != unrepeatedList.size();
    }

}
