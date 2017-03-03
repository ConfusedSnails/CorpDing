/**
 * Created by wb-zzc249334 on 2017/3/3.
 */
package com.dingtalk.controller;

import com.dingtalk.model.AuthHelper;
import com.dingtalk.model.UserHelper;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.dingtalk.oapi.lib.aes.Utils;
import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CorpController {

    private static final Logger logger = LoggerFactory.getLogger(CorpController.class);

    @Autowired
    AuthHelper authHelper;

    @Autowired
    UserHelper userHelper;

    @RequestMapping("/get_js_config")
    @ResponseBody
    public Map<String,Object> getJsConfig(@RequestParam(value = "url" ,required = false) String url
            ,@RequestParam(value = "corpId",required = false) String corpId){
        String accessToken = authHelper.getAccessToken();
        String JSTicket = authHelper.getJSTicket(accessToken);

        String nonceStr = Utils.getRandomStr(8);
        Long  timeStamp = System.currentTimeMillis();

        try {
            String signature = DingTalkJsApiSingnature.getJsApiSingnature(url ,nonceStr ,timeStamp ,JSTicket);
            Map<String,Object> JsApiConfig = new HashMap<String,Object>();

            JsApiConfig.put("signature",signature);
            JsApiConfig.put("nonceStr",nonceStr);
            JsApiConfig.put("timeStamp",timeStamp);
            JsApiConfig.put("corpId",corpId);

            return JsApiConfig;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" message ", e.getMessage());

        }

        return null;
    }


    @RequestMapping("/get_userInfo")
    @ResponseBody
    public Map<String,Object> getUserInfo(@RequestParam(value = "code",required = false) String code){
        String accessToken = authHelper.getAccessToken();
        System.out.println(code+" === "+accessToken);
        CorpUserBaseInfo corpUserBaseInfo = userHelper.getUserInfoByCode(accessToken , code);
        Map<String ,Object> userInfo = new HashMap<String,Object>();
        if(userInfo != null){
            userInfo.put("userId" , corpUserBaseInfo.getUserid());
            userInfo.put("isSys", corpUserBaseInfo.getIs_sys());
            userInfo.put("deviceId", corpUserBaseInfo.getDeviceId());
            userInfo.put("sysLevel", corpUserBaseInfo.getSys_level());
        }

        return userInfo;
    }

    @RequestMapping("/check")
    @ResponseBody
    public String test(){
        return "Success";
    }

}

