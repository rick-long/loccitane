package org.spa.utils;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * simple xml date 读写转换器
 * <p/>
 * Created by Ivy on 2016/01/16.
 */
public class DateTimeConverter implements Converter<Date> {

    public Date read(InputNode node) throws Exception {
        String dateString = node.getValue();
        if (StringUtils.isNotBlank(dateString)) {
            System.out.println("dateString:" + dateString);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        }
        return null;
    }

    public void write(OutputNode node, Date value) throws Exception {
        if (value != null) {
            String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
            node.setValue(dateString);
        }
    }
}
