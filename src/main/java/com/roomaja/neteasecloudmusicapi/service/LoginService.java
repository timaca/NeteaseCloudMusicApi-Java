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
public class LoginService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 账号登陆
     * @param username
     * @param password
     * @param rememberLogin
     * @param clientToken
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> login(String username, String password, String rememberLogin, String clientToken, Map<String, String> cookies) {

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", CryptoUtil.getMd5(password));
        object.put("rememberLogin", rememberLogin);
        object.put("clientToken", clientToken);
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());

        return RestTemplateUtil.post(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login?csrf_token=", cookies, restTemplate);
    }

    /**
     * 手机登陆
     * @param phone
     * @param password
     * @param rememberLogin
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> cellphoneLogin(String phone, String password, String rememberLogin, Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("phone", phone);
        object.put("password", CryptoUtil.getMd5(password));
        object.put("rememberLogin", rememberLogin);
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());

        return RestTemplateUtil.post(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login/cellphone", cookies, restTemplate);

    }

    /**
     * 刷新登陆
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> refreshToken(Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));
        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());
        return RestTemplateUtil.post(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login/token/refresh", cookies, restTemplate);
    }

    /**
     * 登陆状态
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> loginStatus(Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));
        String[] encrypt = CryptoUtil.Encrypt(object.toJSONString());
        return RestTemplateUtil.get(Constant.NETEASE_BASE_URL,cookies,restTemplate);
    }

}
