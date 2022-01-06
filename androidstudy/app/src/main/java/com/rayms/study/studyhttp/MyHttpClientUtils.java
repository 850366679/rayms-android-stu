package com.rayms.study.studyhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class MyHttpClientUtils {
  public static void main(String[] args) {
    ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(0, 5, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
    // 已经禁用了
    threadPoolExecutor.execute(() -> useHttpClientGet("www.baidu.com"));
  }

  private static HttpClient createHttpClient() {
    BasicHttpParams mDefaltHttpParams = new BasicHttpParams();
    // 超时时间
    HttpConnectionParams.setConnectionTimeout(mDefaltHttpParams, 15000);
    // 设置请求超时
    HttpConnectionParams.setSoTimeout(mDefaltHttpParams, 15000);
    HttpConnectionParams.setTcpNoDelay(mDefaltHttpParams, true);
    HttpProtocolParams.setVersion(mDefaltHttpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(mDefaltHttpParams, HTTP.UTF_8);
    // 持续握手
    HttpProtocolParams.setUseExpectContinue(mDefaltHttpParams, true);
    return new DefaultHttpClient(mDefaltHttpParams);
  }

  public static void useHttpClientGet(String url) {
    HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader("Connection", "Keep-Alive");
    try {
      HttpClient httpClient = createHttpClient();
      HttpResponse response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      int code = response.getStatusLine().getStatusCode();
      if (null != entity) {
        InputStream content = entity.getContent();
        String str = converStreamToString(content);
        System.out.println("请求状态码：" + code + "\n请求结果：\n" + str);
        content.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String converStreamToString(InputStream is) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
    StringBuilder stringBuffer = new StringBuilder();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuffer.append(line).append("\n");
    }
    return stringBuffer.toString();
  }
}
