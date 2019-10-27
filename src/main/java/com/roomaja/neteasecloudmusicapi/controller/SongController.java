package com.roomaja.neteasecloudmusicapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.service.SongService;
import com.roomaja.neteasecloudmusicapi.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SongController {
    @Autowired
    SongService songService;
    @Autowired
    private CookieUtil cookieUtil;

    /*歌曲链接*/
    @RequestMapping(value = "/song/url")
    public JSONObject refresh(@RequestParam String id,
                              @RequestParam(defaultValue = "999000") String br,
                              HttpServletResponse response,
                              HttpServletRequest request) {
        ResponseEntity<JSONObject> result = songService.songUrl(id,br,cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

}
