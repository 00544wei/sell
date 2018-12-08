package com.weizhang.controller;

import com.weizhang.enu.ResultEnum;
import com.weizhang.util.HttpUtils;
import com.weizhang.util.ResultVOUtils;
import com.weizhang.util.SignUtils;
import com.weizhang.vo.ResultVO;
import com.weizhang.wxconfig.AccessToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WxController {
//    private static String APPID = "wxc6ba170071d09ff1";
//
//    private static String APPSECRET = "03edf6db250d4fa91eea239dd51aec11";

    private static String APPID = "wxfef232c784236ebc";

    private static String APPSECRET = "aacdfcfc7b5fcc2fa52d2f0e609d9ae8";

    @RequestMapping("/auth")
    public void auth(@RequestParam("code") String code,
                     @RequestParam("state") String state){
        log.info("进入auth方法");
        log.info("获取到的参数是：code={},state={}", code, state);
    }
    @RequestMapping("/scanCode")
    public void scanCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("signature:{},timestamp:{},nonce:{}",signature,timestamp,nonce);
        PrintWriter out = response.getWriter();
        if (SignUtils.checkSignature(signature, timestamp, nonce)) {
            Map<String, String> result = new HashMap<String, String>();
            result.put("signature", signature);
            result.put("timestamp", timestamp);
            result.put("nonce", nonce);
            out.print(echostr);
        }
    }



}
