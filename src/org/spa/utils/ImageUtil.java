package org.spa.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

    // 或得扩展名
    public static String getExtendName(String fileName)
    {
        int index = fileName.lastIndexOf(".");
        if (index != -1)
        {
            return fileName.substring(index + 1);
        }
        return null;
    }

    // 获得文件列表
    public static List getFileList(String path, List filterName , String Url)
    {
        File file = new File(path+Url);
        List list = new ArrayList();

        String[] fileList = file.list();
        if (fileList==null){
            return null;
        }
        for (String s : fileList)
        {
            String extendName = getExtendName(s);
            if (filterName.contains(extendName))
            {
                list.add(WebThreadLocal.getUrlRoot()+Url+s);
            }
        }
        return list;
    }
}
