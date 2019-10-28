package com.roomaja.neteasecloudmusicapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.service.SimiService;
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
@RequestMapping(value = "/simi")
public class SimiController {
    @Autowired
    SimiService simiService;
    @Autowired
    private CookieUtil cookieUtil;

    /* 获得相似歌手 */
    @RequestMapping(value = "/artist")
    public JSONObject simiArtist(@RequestParam String id,
                                 HttpServletResponse response,
                                 HttpServletRequest request) {
        ResponseEntity<JSONObject> result = simiService.simiArtist(id, cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

    /* 获得相似歌单 */
    @RequestMapping(value = "/playlist")
    public JSONObject simiPlaylist(@RequestParam String id,
                                   @RequestParam(defaultValue = "50") String limit,
                                   @RequestParam(defaultValue = "0") String offset,
                                   HttpServletResponse response,
                                   HttpServletRequest request) {
        ResponseEntity<JSONObject> result = simiService.simiPlaylist(
                id,
                Integer.valueOf(limit),
                Integer.valueOf(offset),
                cookieUtil.getCookies(request));
        CookieUtil.setCookie(result.getHeaders(), response);
        return result.getBody();
    }

}
