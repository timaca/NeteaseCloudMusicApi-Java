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
public class PersonalService {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 私人FM
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> personalFm(Map<String, String> cookies) {

        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(object.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/v1/radio/get?csrf_token=", cookies, restTemplate);
    }

}
