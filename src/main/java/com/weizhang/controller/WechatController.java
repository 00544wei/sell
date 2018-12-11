package com.weizhang.controller;

import com.weizhang.config.WechatMpConfig;
import com.weizhang.enu.ResultEnum;
import com.weizhang.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WechatMpConfig wechatMpConfig;
    @Autowired
    private WxMpService wxMpService;

    /**
     * returnUrl为指向业务逻辑的地址
     * @param returnUrl
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        WxMpService wxMpService = wechatMpConfig.wxMpService();
        //1.配置
        //2。调用方法
        String redirect_Url = "http://8tyhqi.natappfree.cc/sell/wechat/getUserInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(redirect_Url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/getUserInfo")
    public String getUserInfo(@RequestParam("code") String code,
                              @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e.getError().getErrorMsg());
            throw new SellException(ResultEnum.WECHAT_AUTHORIZE_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openid;
    }
}
