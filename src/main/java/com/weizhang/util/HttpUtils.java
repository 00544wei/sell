package com.weizhang.util;

import com.google.gson.Gson;
import com.weizhang.wxconfig.AccessToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.HttpRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtils {
    private static String APPID = "wxfef232c784236ebc";

    private static String APPSECRET = "aacdfcfc7b5fcc2fa52d2f0e609d9ae8";

    public static String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
    public static final String RESPONSE_RESP_CONDE = "respCode";
    public static <T extends Map<String, Object>> T sendPostRetMap(String url, Map<String, String> authorizationMap, Map<String, Object> paramMap, Class<T> resultType) {
        return reqPostRetMap(url, authorizationMap, paramMap, resultType);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Map<String, Object>> T reqPostRetMap(String url, Map<String, String> authorizationMap, Object paramMap, Class<T> resultType) {

        return null;
    }

    public static AccessToken getAccessToken() throws Exception{
        AccessToken accessToken = new AccessToken();
        JSONObject jsonObject =  doGetStr(URL);
        if (jsonObject!= null){
            accessToken.setAccess_token(jsonObject.getString("access_token"));
            accessToken.setExpires_in(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }

    public static JSONObject doGetStr(String url) throws Exception{
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse response = defaultHttpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity == null){
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        return jsonObject;
    }

    public static JSONObject doPostStr(String url, String outStr) throws Exception{
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
        HttpResponse response = defaultHttpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }
}
