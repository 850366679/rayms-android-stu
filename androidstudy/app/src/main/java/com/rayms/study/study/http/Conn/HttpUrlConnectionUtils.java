package com.rayms.study.study.http.Conn;

import com.rayms.study.study.http.HttpClient.MyHttpClientUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.rayms.study.study.http.Conn.UrlConnManager.getHttpUrlConnection;
import static com.rayms.study.study.http.Conn.UrlConnManager.postParams;

public class HttpUrlConnectionUtils {

  public static void main(String[] args) {
    new Thread(() -> {
      useHttUrlConnectionPost("http://ip.taobao.com/service/getIpInfo.php");
    }).start();
  }

  /**
   * 发起请求
   */
  private static void useHttUrlConnectionPost(String url) {
    InputStream inputStream = null;
    HttpURLConnection mHttpUrlConnection = getHttpUrlConnection(url);
    try {
      Map<String, String> map = new HashMap<>();
      map.put("ip", "59.108.54.37");
      postParams(mHttpUrlConnection.getOutputStream(), map);
      mHttpUrlConnection.connect();
      inputStream = mHttpUrlConnection.getInputStream();
      int code = mHttpUrlConnection.getResponseCode();
      String respose = MyHttpClientUtils.converStreamToString(inputStream);
      System.out.println("请求状态码：" + code + "\n请求结果：\n" + respose);
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
