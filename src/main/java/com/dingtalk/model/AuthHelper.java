package com.dingtalk.model;

import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.JsapiTicket;
import com.dingtalk.open.client.api.service.corp.CorpConnectionService;
import com.dingtalk.open.client.api.service.corp.JsapiService;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.common.ServiceException;
import com.dingtalk.open.client.common.ServiceNotExistException;
import org.springframework.stereotype.Repository;

/**
 * Created by wb-zzc249334 on 2017/3/3.
 */
@Repository("authHelper")
public class AuthHelper {

    public static String CorpID = "dingf57f3beb11fe1a4735c2f4657eb6378f";
    public static String SSOSecret = "eBYDr5qpmssPy3uQMOVHIdZJPmzQdhY2RJH6zkDU04JPy0h59Vjv8vkNN0DyLPng";

    //获取accessToken   accessToken 有效期2小时

    public String  getAccessToken()  {

        String accessToken = "";

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CorpConnectionService corpConnectionService = serviceFactory.getOpenService(CorpConnectionService.class);
            accessToken = corpConnectionService.getCorpToken(CorpID,SSOSecret);


        } catch (SdkInitException e) {
            e.printStackTrace();
        } catch (ServiceNotExistException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    //获取JS_Ticket  JS_Ticket 有效期为2小时
    public  String getJSTicket(String accessToken){
        String JSTicket = "";

        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            JsapiService jsapiService = serviceFactory.getOpenService(JsapiService.class);
            JsapiTicket jsapiTicket = jsapiService.getJsapiTicket(accessToken , "jsapi");
            JSTicket = jsapiTicket.getTicket();

        } catch (SdkInitException e) {
            e.printStackTrace();
        } catch (ServiceNotExistException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return JSTicket;
    }

    public static void main(String[] args){
        AuthHelper auth = new AuthHelper();
        String param = auth.getAccessToken();
        System.out.println("accessToken = "+param);
        System.out.println("JSTicket = "+ auth.getJSTicket(param));
    }






}
