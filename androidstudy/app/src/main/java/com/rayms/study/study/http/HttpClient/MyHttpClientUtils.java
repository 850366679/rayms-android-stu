package com.rayms.study.study.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  private ArrayList<String> test;
  public static void main(String[] args) {
    ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(0, 5, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
    // 已经禁用了
    threadPoolExecutor.execute(() -> useHttpClientGet("www.baidu.com"));

    int[] arr = { 1, 2, 3, 4, 5, 6 };

    List newAssetList = new ArrayList<>(Collections.singletonList(arr));
    newAssetList.remove(arr[0]);
    newAssetList.contains(1);
  }

  public static int SeatingStudents(int[] arr) {
    // code goes here
    if (null == arr || arr.length == 0) return 0;
    int k = 0;
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i < arr.length; i++) {
      list.add(arr[i]);
    }
    for (int i = 1; i <= arr[0] - 1; i++) {
      if (list.contains(i)) continue;
      if (i % 2 == 1) {
        if (!list.contains(i + 1)) k++;
        if (i + 2 >= arr[0]) continue;
      }
      if (!list.contains(i + 2)) k++;
    }
    return k;
  }

  public static String MinWindowSubstring(String[] strArr) {
    Map<Character, List<Integer>> map = new HashMap<>();
    String a = strArr[0];
    String b = strArr[1];
    char firstChar = 'a';
    for (int i = 0; i < b.length(); i++) {
      char c = b.charAt(i);
      if (i == 0) firstChar = c;
      if (map.containsKey(c)) {
        List<Integer> integers = map.get(c);
        integers.set(0, integers.get(0) + 1);
        continue;
      }
      List<Integer> integers = new ArrayList<>();
      integers.add(0, 1);
      for (int j = 0; j < a.length(); j++) {
        if (a.charAt(j) != c) continue;
        integers.add(j);
      }
      map.put(c, integers);
    }
    int tempMinIndex = 0;
    int tempMaxIndex = 0;
    int minIndex = 0;
    int maxIndex = 0;
    int minNum = 200;// 最小差值
    List<Integer> firstInts = Objects.requireNonNull(map.get(firstChar));
    final Integer firstCharNum = firstInts.get(0);
    for (int i = 1; i < firstInts.size(); i++) {
      if (firstCharNum + i - 1 >= firstInts.size()) continue;
      tempMinIndex = firstInts.get(i);
      tempMaxIndex = firstInts.get(firstCharNum + i - 1);
      for (Character character : map.keySet()) {
        if (character == firstChar) continue;
        Map<Integer, Integer> maxIndexandMinIndex =
            getMaxIndexandMinIndex(tempMinIndex, tempMaxIndex, map.get(character));
        for (Integer integer : maxIndexandMinIndex.keySet()) {
          tempMinIndex = integer;
          tempMaxIndex = maxIndexandMinIndex.get(integer);
        }
      }
      if (tempMaxIndex - tempMinIndex >= minNum) continue;
      minNum = tempMaxIndex - tempMinIndex;
      minIndex = tempMinIndex;
      maxIndex = tempMaxIndex;
    }
    return a.substring(minIndex, maxIndex + 1);
  }

  public static Map<Integer, Integer> getMaxIndexandMinIndex(int minIndex, int maxIndex,
      List<Integer> integers) {
    Integer charNum = Objects.requireNonNull(integers).get(0);
    Map<Integer, Integer> map = new HashMap<>();
    int minKey = 100;
    for (int j = 1; j < Objects.requireNonNull(integers).size(); j++) {
      if (charNum + j - 1 >= integers.size()) continue;
      Integer min = integers.get(j);
      Integer max = integers.get(charNum + j - 1);
      int key = min < minIndex ? min : minIndex;
      int value = max > maxIndex ? max : maxIndex;
      int i = value - key;
      if (i > minKey) continue;
      minKey = i;
      map.clear();
      map.put(key, value);
    }
    return map;
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
      stringBuffer.append(line);
    }
    return stringBuffer.toString();
  }
}
