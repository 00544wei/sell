package com.weizhang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WxController {

    @RequestMapping("/auth")
    public void auth(){
        log.info("进入auth方法");
    }
}
