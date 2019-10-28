package com.roomaja.neteasecloudmusicapi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roomaja.neteasecloudmusicapi.config.Constant;
import com.roomaja.neteasecloudmusicapi.util.CryptoUtil;
import com.roomaja.neteasecloudmusicapi.util.RestTemplateUtil;
import com.roomaja.neteasecloudmusicapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 账号登陆
     * 
     * @param username
     * @param password
     * @param rememberLogin
     * @param clientToken
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> login(String username, String password, String rememberLogin, String clientToken,
            Map<String, String> cookies) {

        JSONObject object = new JSONObject();
        object.put("username", username);
        object.put("password", CryptoUtil.getMd5(password));
        object.put("rememberLogin", rememberLogin);
        object.put("clientToken", clientToken);
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(object.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/login?csrf_token=", cookies, restTemplate);
    }

    /**
     * 手机登陆
     * 
     * @param phone
     * @param password
     * @param rememberLogin
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> cellphoneLogin(String phone, String password, String rememberLogin,
            Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("phone", phone);
        object.put("password", CryptoUtil.getMd5(password));
        object.put("rememberLogin", rememberLogin);
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(object.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/login/cellphone",
                cookies, restTemplate);

    }

    /**
     * 刷新登陆
     * 
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> refreshToken(Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));
        String[] encrypt = CryptoUtil.weapiEncrypt(object.toJSONString());
        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/login/token/refresh", cookies, restTemplate);
    }

    /**
     * 登陆状态
     * 
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> loginStatus(Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));
        // String[] encrypt = CryptoUtil.weapiEncrypt(object.toJSONString());

        ResponseEntity<String> resp = RestTemplateUtil.get(Constant.NETEASE_BASE_URL, cookies, restTemplate);

        List<String> profile = StringUtil.findall("GUser\\s*=\\s*([^;]+);", resp.getBody());
        List<String> bindings = StringUtil.findall("GBinds\\s*=\\s*([^;]+);", resp.getBody());

        String profileJsonStr = StringUtil
                .evalJS("JSON.stringify(" + profile.get(0).replace("GUser=", "").replace(";", "") + ")");
        String bindingsJsonStr = StringUtil
                .evalJS("JSON.stringify(" + bindings.get(0).replace("GBinds=", "").replace(";", "") + ")");

        JSONObject j = new JSONObject();
        j.put("code", 200);
        j.put("profile", JSON.parseObject(profileJsonStr));
        j.put("bindings", JSON.parseArray(bindingsJsonStr));

        return new ResponseEntity<JSONObject>(j, resp.getHeaders(), resp.getStatusCode());
    }

    /**
     * 退出登录
     * 
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> logout(Map<String, String> cookies) {
        JSONObject object = new JSONObject();
        object.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(object.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1], Constant.NETEASE_BASE_URL + "/weapi/logout", cookies,
                restTemplate);

    }

}
