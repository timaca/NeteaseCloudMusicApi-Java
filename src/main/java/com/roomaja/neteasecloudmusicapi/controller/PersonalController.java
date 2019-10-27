package com.roomaja.neteasecloudmusicapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.service.PersonalService;
import com.roomaja.neteasecloudmusicapi.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PersonalController {
    @Autowired
    PersonalService personalService;
    @Autowired
    private CookieUtil cookieUtil;

    /*私人fm*/
    @RequestMapping(value = "/personal_fm")
    public JSONObject refresh(HttpServletResponse response, HttpServletRequest request) {
        ResponseEntity<JSONObject> result = personalService.personalFm(cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

}
