package com.rayms.study.study.http.Conn;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UrlConnManager {

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

  /**
   * 组装post请求参数
   */
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
}
