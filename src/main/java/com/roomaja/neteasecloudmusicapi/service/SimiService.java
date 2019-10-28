package com.roomaja.neteasecloudmusicapi.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.config.Constant;
import com.roomaja.neteasecloudmusicapi.util.CryptoUtil;
import com.roomaja.neteasecloudmusicapi.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SimiService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获得相似歌手
     *
     * @param id      歌手 id
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> simiArtist(String id, Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        params.put("artistid", id);
        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/discovery/simiArtist?csrf_token=", cookies, restTemplate);
    }

    /**
     * 获得相似歌单
     *
     * @param id      歌曲 id
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> simiPlaylist(String id, Integer limit, Integer offset, Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        JSONArray c = new JSONArray();

        params.put("songid", id);
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/discovery/simiPlaylist?csrf_token=", cookies, restTemplate);
    }

}
