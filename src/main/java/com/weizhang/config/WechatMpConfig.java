package com.weizhang.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class WechatMpConfig {

    @Autowired
    private WeChatAccountConfig weChatAccountConfig;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        ((WxMpInMemoryConfigStorage) wxMpConfigStorage).setAppId(weChatAccountConfig.getAppId());
        ((WxMpInMemoryConfigStorage) wxMpConfigStorage).setSecret(weChatAccountConfig.getAppSecret());
        return wxMpConfigStorage;
    }
}
