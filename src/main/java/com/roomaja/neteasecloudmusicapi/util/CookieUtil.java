package com.roomaja.neteasecloudmusicapi.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieUtil {

    /**
     * 获取request中的Cookie对象Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> getCookies(HttpServletRequest request) {

        Map<String, String> cookieMap = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            cookieMap = new HashMap<>();
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        return cookieMap;
    }

    /**
     * 写入Cookie到response
     *
     * @param httpHeaders
     * @param response
     */
    public static void setCookie(HttpHeaders httpHeaders, HttpServletResponse response) {
        List<String> setCookie = httpHeaders.get("SET-COOKIE");

        if (setCookie != null) {
            for (String string : setCookie) {
                String[] entry = StringUtils.split(string, ";");
                if (entry.length > 0) {
                    String[] ck = StringUtils.split(entry[0].trim(), "=");
                    if (ck.length == 2) {
                        if(StringUtils.isNotBlank(ck[1].trim())){
                            Cookie cookie = new Cookie(ck[0].trim(), ck[1].trim());
                            cookie.setPath("/");
                            cookie.setMaxAge(14 * 24 * 60 * 60);
                            response.addCookie(cookie);
                        }
                    }
                }


            }
        }
    }


}
