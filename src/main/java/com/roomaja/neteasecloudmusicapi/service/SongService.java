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

    /**
     * 传入音乐 id(支持多个 id, 用 , 隔开), 可获得歌曲详情(注意:歌曲封面现在需要通过专辑内容接口获取)
     * 
     * @param ids     音乐 id, 如 ids=347230
     * @param cookies
     * @return
     */
    public ResponseEntity<JSONObject> songDetail(String ids, Map<String, String> cookies) {

        JSONObject params = new JSONObject();

        JSONArray c = new JSONArray();
        for (String id : ids.split("\\s*,\\s*")) {
            JSONObject curId = new JSONObject();
            curId.put("id", id);
            c.add(curId);
        }

        params.put("c", JSONArray.toJSONString(c));
        params.put("ids", "[" + ids + "]");
        params.put("csrf_token", cookies.get("__csrf"));

        String[] encrypt = CryptoUtil.weapiEncrypt(params.toJSONString());

        return RestTemplateUtil.postWeapi(encrypt[0], encrypt[1],
                Constant.NETEASE_BASE_URL + "/weapi/v3/song/detail?csrf_token=", cookies, restTemplate);
    }

}
