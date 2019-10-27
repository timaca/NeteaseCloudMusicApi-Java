package com.roomaja.neteasecloudmusicapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.service.LoginService;
import com.roomaja.neteasecloudmusicapi.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    private LoginService service;
    @Autowired
    private CookieUtil cookieUtil;

    /*无法使用，返回{"code":-460,"msg":"Cheating"}*/
    @RequestMapping(value = "/login")
    public JSONObject email(@RequestParam String email,
                            @RequestParam String password,
                            @RequestParam(defaultValue = "true") String rememberLogin,
                            @RequestParam(defaultValue = "1_jVUMqWEPke0/1/Vu56xCmJpo5vP1grjn_SOVVDzOc78w8OKLVZ2JH7IfkjSXqgfmh") String clientToken,
                            HttpServletResponse response,
                            HttpServletRequest request) {

        ResponseEntity<JSONObject> result = service.login(email, password, rememberLogin, clientToken, cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

    @RequestMapping(value = "/login/cellphone")
    public JSONObject phone(@RequestParam String phone,
                            @RequestParam String password,
                            @RequestParam(defaultValue = "true") String rememberLogin,
                            HttpServletResponse response,
                            HttpServletRequest request) {

        ResponseEntity<JSONObject> result = service.cellphoneLogin(phone, password, rememberLogin, cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

    @GetMapping(value = "/login/token/refresh")
    public JSONObject refresh(HttpServletResponse response, HttpServletRequest request) {
        ResponseEntity<JSONObject> result = service.refreshToken(cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

    @GetMapping(value = "/login/status")
    public JSONObject loginStatus(HttpServletResponse response, HttpServletRequest request) {
        ResponseEntity<JSONObject> result = service.loginStatus(cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

    @GetMapping(value = "/logout")
    public JSONObject logout(HttpServletResponse response, HttpServletRequest request) {
        ResponseEntity<JSONObject> result = service.logout(cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }
}
