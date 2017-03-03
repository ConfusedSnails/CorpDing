package com.dingtalk.model;

import com.dingtalk.open.client.ServiceFactory;

import com.dingtalk.open.client.api.model.corp.CorpUserBaseInfo;
import com.dingtalk.open.client.api.service.corp.CorpUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * Created by wb-zzc249334 on 2017/3/3.
 */
@Repository("userHelper")
public class UserHelper {

    public static final Logger logger = LoggerFactory.getLogger(UserHelper.class);


    public CorpUserBaseInfo getUserInfoByCode(String accessToken , String code){
        CorpUserBaseInfo corpUserBaseInfo = null;
        try{
           CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
           corpUserBaseInfo = corpUserService.getUserinfo(accessToken ,code);
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        return corpUserBaseInfo;
    }

}
