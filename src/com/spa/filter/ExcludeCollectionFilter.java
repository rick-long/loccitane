package com.spa.filter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 * fastjson 所有 继承Collection接口的属性 都不解析
 * 
 * @author Ivy
 */
public class ExcludeCollectionFilter implements PropertyPreFilter {

    private final Class<?> clazz;
    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();

    public ExcludeCollectionFilter(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    public ExcludeCollectionFilter(Class<?> clazz, String... properties) {
        super();
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
                this.includes.add(item);
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }

        /**
         * 不是当前的java bean 不解释
         */
        if (clazz != null && !clazz.isInstance(source)) {
            return false;
        }

        // 获取当前需要序列化的对象的类对象
        Class<?> currentClazz = source.getClass();
        Field field = null;
        try {
            field = currentClazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(name + " NoSuchFieldException");
            return false;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (field != null) {
            Class<?>[] currentClazzArray = field.getType().getInterfaces();
            for (Class<?> cz : currentClazzArray) {
                // 实现了Collection 接口的属性都不序列化
                if (cz.equals(Collection.class)) {
                    return false;
                }
            }
        }

        if (this.excludes.contains(name)) {
            return false;
        }

        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {

        /*  ExcludeCollectionFilter filter = new ExcludeCollectionFilter(Device.class);
        
        Device device = new Device();
        System.out.println(JSON.toJSONString(device, filter));*/

    }

}
