package com.esfak47.common.extend;

import com.esfak47.common.extension.ExtensionFactory;
import com.esfak47.common.lang.Tuple;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tony
 * @date 2018/5/16
 */
public class RegisterExtendFactory implements ExtensionFactory {

    private final static ConcurrentHashMap<Tuple<Class, String>, Object> classConcurrentHashMap = new ConcurrentHashMap<>();

    public static void register(Tuple<Class, String> tuple, Object impl) {
        classConcurrentHashMap.put(tuple, impl);
    }

    public static void remove(Tuple<Class, String> tuple) {
        classConcurrentHashMap.remove(tuple);
    }

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        Object o = classConcurrentHashMap.get(Tuple.tuple(type, name));
        if (o != null){
            return (T) o;
        }
        return null;
    }
}
