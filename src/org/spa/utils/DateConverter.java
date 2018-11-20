package org.spa.utils;

import org.apache.commons.lang3.StringUtils;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ivy on 2016/01/16.
 */
public class DateConverter implements Converter<Date> {

    public Date read(InputNode node) throws Exception {
        String dateString = node.getValue();
        if (StringUtils.isNotBlank(dateString)) {
            System.out.println("dateString:" + dateString);
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        }
        return null;
    }

    public void write(OutputNode node, Date value) throws Exception {
        if (value != null) {
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(value);
            node.setValue(dateString);
        }
    }
}
