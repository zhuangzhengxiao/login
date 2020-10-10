package com.zzx.login.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class CollectionUtil {
    static Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    static public <T> T getFirst(List<T> list) {
        return isEmpty(list) ? null : list.get(0);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static void print(List list) {
        if (isEmpty(list)) {
            logger.debug("list is empty or null");
            return;
        }
        for (Object o : list) {
            logger.debug(o + "");
        }
    }

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length <= 0;
    }

}
