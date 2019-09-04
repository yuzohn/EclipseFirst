package com.mail.service;

/**
 * Created by zouyu on 2019-4-16.
 */
public interface SMSendService {
    public String sendSms (String content,String phoneNo) throws Exception;
}
