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
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取用户详情
     *
     * @param uid     用户 id
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> userDetail(String uid, Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/v1/user/detail/" + uid + "/?csrf_token=", cookies, restTemplate);
    }

    /**
     * 收藏计数
     *
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> userSubcount(Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/subcount/?csrf_token=", cookies, restTemplate);
    }

    /**
     * 更新用户信息
     *
     * @param gender:    性别 0:保密 1:男性 2:女性
     * @param birthday:  出生日期,时间戳 unix timestamp
     * @param nickname:  用户昵称
     * @param province:  省份id
     * @param city:      城市id
     * @param signature： 用户签名
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> userUpdate(Integer gender, Long birthday, String nickname, Integer province,
            Integer city, String signature, Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        params.put("avatarImgId", "0");
        params.put("birthday", birthday);
        params.put("city", city);
        params.put("gender", gender);
        params.put("nickname", nickname);
        params.put("province", province);
        params.put("signature", signature);
        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/user/profile/update?csrf_token=", cookies, restTemplate);
    }

    /**
     * 获得相似歌单
     *
     * @param id      歌曲 id
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> userDetail1(String uid, Integer limit, Integer offset,
            Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        params.put("songid", uid);
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/discovery/simiPlaylist?csrf_token=", cookies, restTemplate);
    }

}
