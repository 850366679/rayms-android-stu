package com.rayms.study.studyhttp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUrlConnectionUtils {

  public static void main(String[] args) {
    new Thread(() -> {
      useHttUrlConnectionPost("http://ip.taobao.com/service/getIpInfo.php");
    }).start();
  }

  /**
   * 配置默认参数
   */
  public static HttpURLConnection getHttpUrlConnection(String url) {
    HttpURLConnection mHttpUrlConnection = null;
    try {
      URL mUrl = new URL(url);
      mHttpUrlConnection = (HttpURLConnection) mUrl.openConnection();
      mHttpUrlConnection.setConnectTimeout(15000);
      mHttpUrlConnection.setReadTimeout(15000);
      mHttpUrlConnection.setRequestMethod("POST");
      mHttpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
      mHttpUrlConnection.setDoInput(true);
      mHttpUrlConnection.setDoOutput(true);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return mHttpUrlConnection;
  }

  public static void postParams(OutputStream output, Map<String, String> map) {
    try {
      StringBuilder mStringBuilder = new StringBuilder();
      for (String key : map.keySet()) {
        if (!mStringBuilder.toString().isEmpty()) {
          mStringBuilder.append("&");
        }
        mStringBuilder.append(URLEncoder.encode(key, "UTF-8"));
        mStringBuilder.append("=");
        mStringBuilder.append(URLEncoder.encode(map.get(key), "UTF-8"));
      }
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output,
          StandardCharsets.UTF_8));
      writer.write(mStringBuilder.toString());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
