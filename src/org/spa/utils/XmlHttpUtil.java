package org.spa.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Ivy on 2016/01/16.
 */
public class XmlHttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlHttpUtil.class);
    public static final int HTTP_STATUS_OK = 200;

    public static <T extends Serializable> T post(Object request, String url, Class<? extends T> type) {
        String parameter = SimpleXmlUtil.beanToString(request);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(parameter, ContentType.create("text/xml", Consts.UTF_8));
        httpPost.setEntity(stringEntity);
        logger.debug(httpPost.getRequestLine().toString());
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (HTTP_STATUS_OK == statusCode && entity != null) {
                if(type.isAssignableFrom(String.class)) {
                   return  (T) EntityUtils.toString(entity);
                }
                return SimpleXmlUtil.stringToBean(EntityUtils.toString(entity), type);
            } else {
                logger.error("call error with status:{}", statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close(); // 关闭流并释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String sendHttpPost(String httpUrl, String param) {
        BufferedReader in = null;
        String result = "";
        PrintWriter out = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(httpUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json; charset="+"UTF-8");

            conn.setConnectTimeout(10*1000); //连接超时
            conn.setReadTimeout(30*1000);   //读取超时

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                conn.disconnect();
            }
        }

        return result;
    }
}
