package org.spa.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
/**
 * Created by Ivy on 2016/01/16.
 */
public class SimpleXmlUtil {
    /**
     * 指定annotation策略使自定义转换器生效
     */
    public static final Serializer serializer = new Persister(new AnnotationStrategy());

    public static String beanToString(Object bean) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            serializer.write(bean, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res = out.toString();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static <T extends Serializable> T stringToBean(String source, Class<? extends T> type) {
        try {
            return serializer.read(type, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
