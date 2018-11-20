package org.spa.FireBase;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class FireBase {
    private final static String AUTH_KEY_FCM= "AAAAEwt0Efc:APA91bETTM5G45BefxHvM5fp8HYoJ_dx9HYJVQ85oPPkxnsdhh2JGH43sORo-nPSHYsC6avvrI_MYjuSoWJZmqYYc4ok_9xlAfSU7mbqfDRRPp0pr-U9fMfsVhow3M_By9FEj8cVT4WQ";//app服务密钥

    private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";//谷歌推送地址

    public static void pushFCMNotification(JSONObject json) {
        try {
            URL url = new URL(API_URL_FCM);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080));//因为谷歌国内访问不到需要本地开vpn，此代码在本地测试需要用到。发布到海外服务器可以去掉！
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","key="+AUTH_KEY_FCM);
            conn.setRequestProperty("Content-Type","application/json");//不设置默认发送文本格式。设置就是json
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }
            wr.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


/*    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("to","eq2CTOeb7eI:APA91bGloGBYsp884-K3Sq4BW5VVZwBPggSaHFsGghqyViJwOxziN8imKDnInOfOYxF4VCjgHb5bE868pq3LczFjmjaVbEuE2A5RA-RpJCloJtJVdhBxbcws16igvWMNib75P2n0Ahqw");//推送到哪台客户端机器，方法一推一个token,
        //批量推送 ，最多1000个token ，此处的tokens是一个token JSONArray数组json.put("registration_ids", tokens);
        JSONObject info = new JSONObject();
        info.put("title","Notification Tilte");
        info.put("body", "Hello Test notification");
        info.put("icon", "myicon");
        json.put("notification", info);
        //发送推送
        FireBase.pushFCMNotification(json);

    }*/

}
