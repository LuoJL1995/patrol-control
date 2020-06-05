package com.hik.icv.patrol.utils;

import java.util.*;

/**
 * @Description map排序工具类
 * @Author LuoJiaLei
 * @Date 2020/4/26
 * @Time 15:15
 */
public class MapSortUtil {

    public static final String DESC = "desc";

    public static final String ASC = "asc";



    /**
     * 根据key排序
     *
     * @param map
     * @param rule : asc(升序),desc(降序)
     * @return
     */
    public static Map<String, String> sortByKey(Map<String, String> map, String rule) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = null;
        if (rule.equalsIgnoreCase(ASC)) {
            sortMap = new TreeMap<>((str1, str2) -> str1.compareTo(str2));
        } else {
            sortMap = new TreeMap<>((str1, str2) -> str2.compareTo(str1));
        }
        sortMap.putAll(map);
        return sortMap;
    }


    /**
     * 根据key排序(忽略大小写)
     * @param rule : asc(升序),desc(降序)
     * @param map
     * @return
     */
    public static Map<String, String> sortByKeyIgnoreCase(Map<String, String> map, String rule) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = null;
        if (rule.equalsIgnoreCase(ASC)) {
            sortMap = new TreeMap<>((str1, str2) -> str1.toLowerCase().compareTo(str2.toLowerCase()));
        } else {
            sortMap = new TreeMap<>((str1, str2) -> str2.toLowerCase().compareTo(str1.toLowerCase()));
        }
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 根据value排序
     *
     * @param oriMap
     * @param rule   : asc(升序),desc(降序)
     * @return
     */
    public static Map<String, String> sortByValue(Map<String, String> oriMap, String rule) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }

        Map<String, String> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(oriMap.entrySet());
        if (rule.equalsIgnoreCase(ASC)) {
            Collections.sort(entryList, (me1, me2) -> me1.getValue().compareTo(me2.getValue()));
        } else {
            Collections.sort(entryList, (me1, me2) -> me2.getValue().compareTo(me1.getValue()));
        }

        Iterator<Map.Entry<String, String>> iterator = entryList.iterator();
        Map.Entry<String, String> tmpEntry;
        while (iterator.hasNext()) {
            tmpEntry = iterator.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    /**
     * 根据value排序  Integer类型
     *
     * @param oriMap
     * @param rule   : asc(升序),desc(降序)
     * @return
     */
    public static Map<String, Integer> sortByValueInteger(Map<String, Integer> oriMap, String rule) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(oriMap.entrySet());
        if (rule.equalsIgnoreCase(ASC)) {
            Collections.sort(entryList, (me1, me2) -> me1.getValue().compareTo(me2.getValue()));
        } else {
            Collections.sort(entryList, (me1, me2) -> me2.getValue().compareTo(me1.getValue()));
        }

        Iterator<Map.Entry<String, Integer>> iterator = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry;
        while (iterator.hasNext()) {
            tmpEntry = iterator.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    /**
     * 根据value排序(忽略大小写)
     * @param oriMap
     * @param rule   : asc(升序),desc(降序)
     * @return
     */
    public static Map<String, String> sortByValueIgnoreCase(Map<String, String> oriMap, String rule) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }

        Map<String, String> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(oriMap.entrySet());
        if (rule.equalsIgnoreCase(ASC)) {
            Collections.sort(entryList, (me1, me2) -> me1.getValue().toLowerCase().compareTo(me2.getValue().toLowerCase()));
        } else {
            Collections.sort(entryList, (me1, me2) -> me2.getValue().toLowerCase().compareTo(me1.getValue().toLowerCase()));
        }

        Iterator<Map.Entry<String, String>> iterator = entryList.iterator();
        Map.Entry<String, String> tmpEntry;
        while (iterator.hasNext()) {
            tmpEntry = iterator.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
}
