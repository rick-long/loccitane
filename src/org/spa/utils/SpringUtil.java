package org.spa.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * Created by Ivy on 2016/01/16.
 */
final public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    /**
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ctx == null) {
            ctx = applicationContext;
        }
    }

    /**
     * 推荐使用getBean(clazz)方法，不需要类型转换
     *
     * @param name
     * @return
     */
    static public Object getBean(String name) {
        return ctx.getBean(name);
    }

    /**
     * 使用泛型获取bean， 该bean对象必须在spring中注册
     *
     * @param <T>   泛型类型定义
     * @param clazz 需要获取bean的类
     * @return
     * @author huanghaiping
     */
    public static <T> T getBean(Class<T> clazz) {
        return (T) getBean(StringUtils.uncapitalize(clazz.getSimpleName()));
    }


}
