package com.roomaja.neteasecloudmusicapi.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@CacheConfig(cacheNames = "cookies")
public class CookieUtil {
    /**
     * 获取request中的Cookie对象Map
     *
     * @param request
     * @return
     */
    @Cacheable(key = "#request.getSession().getId()")
    public Map<String, String> getCookies(HttpServletRequest request) {

        Map<String, String> cookieMap = null;

        Cookie[] cookies = request.getCookies();
        cookieMap = new HashMap<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }

        /* 特殊处理__csrf */
        cookieMap.putIfAbsent("__csrf", RandomStringUtils.randomAlphanumeric(10));

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
                        if (StringUtils.isNotBlank(ck[1].trim())) {
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
