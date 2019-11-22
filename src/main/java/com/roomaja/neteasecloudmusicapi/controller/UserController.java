package com.roomaja.neteasecloudmusicapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.service.UserService;
import com.roomaja.neteasecloudmusicapi.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/user")
public class UserController {
        @Autowired
        UserService userService;
        @Autowired
        private CookieUtil cookieUtil;

        /* 登陆后调用此接口 , 传入用户 id, 可以获取用户详情 */
        @RequestMapping(value = "/detail")
        public JSONObject userDetail(@RequestParam String uid, HttpServletResponse response,
                        HttpServletRequest request) {
                ResponseEntity<JSONObject> result = userService.userDetail(uid, cookieUtil.getCookies(request));
                CookieUtil.setCookie(result.getHeaders(), response);
                return result.getBody();
        }

        /* 获取用户信息 , 歌单，收藏，mv, dj 数量 */
        @RequestMapping(value = "/subcount")
        public JSONObject userSubcount(HttpServletResponse response, HttpServletRequest request) {
                ResponseEntity<JSONObject> result = userService.userSubcount(cookieUtil.getCookies(request));
                CookieUtil.setCookie(result.getHeaders(), response);
                return result.getBody();
        }

        /* 更新用户信息 */
        @RequestMapping(value = "/update")
        public JSONObject userUpdate(@RequestParam Integer gender, @RequestParam Long birthday,
                        @RequestParam String nickname, @RequestParam Integer province, @RequestParam Integer city,
                        @RequestParam String signature, HttpServletResponse response, HttpServletRequest request) {
                ResponseEntity<JSONObject> result = userService.userUpdate(gender, birthday, nickname, province, city,
                                signature, cookieUtil.getCookies(request));
                CookieUtil.setCookie(result.getHeaders(), response);
                return result.getBody();
        }
}
