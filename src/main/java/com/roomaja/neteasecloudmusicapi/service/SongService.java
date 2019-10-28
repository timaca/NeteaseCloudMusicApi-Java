package com.roomaja.neteasecloudmusicapi.service;

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
public class SongService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 歌曲链接
     * 
     * @param id      音乐 id
     * @param br      码率,默认设置了 999000 即最大码率,如果要 320k 则可设置为 320000,其他类推
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> songUrl(String id, String br, Map<String, String> cookies) {

        JSONObject params = new JSONObject();
        params.put("ids", "[" + id + "]");
        params.put("br", br);

        JSONObject object = new JSONObject();
        object.put("params", params);
        object.put("url", Constant.NETEASE_BASE_URL + "/api/song/enhance/player/url");
        object.put("method", "POST");

        String[] encrypt = CryptoUtil.linuxapiEncrypt(object.toJSONString());

        return RestTemplateUtil.postLinuxapi(encrypt[0], Constant.NETEASE_LINUXAPI_BASE_URL, cookies, restTemplate);
    }

}
