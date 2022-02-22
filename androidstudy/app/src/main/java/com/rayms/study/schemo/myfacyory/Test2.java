package com.rayms.study.schemo.myfacyory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.rayms.study.study.http.HttpClient.MyHttpClientUtils.converStreamToString;

/**
 * @auther RayMouns
 * @date 2022/2/9
 * @time 18:30.
 */
public class Test2 {
  public static void main(String[] args) {
    System.setProperty("http.agent", "Chrome");
    try {
      URL url = new URL("https://coderbyte.com/api/challenges/json/rest-get-simple");
      try {
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        String s = converStreamToString(inputStream);
        JSONObject jsonObject = new JSONObject(s);
        JSONArray hobbies = jsonObject.getJSONArray("hobbies");
        System.out.println(hobbies);
      } catch (IOException ioEx) {
        System.out.println(ioEx);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    } catch (MalformedURLException malEx) {
      System.out.println(malEx);
    }
  }
}
