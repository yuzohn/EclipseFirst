package com.mail.service.impl;

import com.mail.service.SMSendService;
import com.zsins.sendSMS.SendSMS;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zouyu on 2019-4-16.
 */
@Service
public class SMSendServiceImpl implements SMSendService {
    public static Map<String, String> smsMapMsg = new HashMap<String, String>();
    static {
        smsMapMsg.put("-1","参数为空。信息、电话号码等有空指针，登陆失败");
        smsMapMsg.put("-2","电话号码个数超过1000");
        smsMapMsg.put("-10","申请缓存空间失败");
        smsMapMsg.put("-11","电话号码中有非数字字符");
        smsMapMsg.put("-12","有异常电话号码");
        smsMapMsg.put("-13","电话号码个数与实际个数不相等");
        smsMapMsg.put("-14","实际号码个数超过1000");
        smsMapMsg.put("-101","发送消息等待超时");
        smsMapMsg.put("-102","发送或接收消息失败");
        smsMapMsg.put("-103","接收消息超时");
        smsMapMsg.put("-200","其他错误");
        smsMapMsg.put("-999","web服务器内部错误");
    }
    public String sendSms(String content,String phoneNo) throws Exception{
        String sendMsg = "发送成功";
        try {
                //发送手机号、发送内容、发送手机号个数、子端口号码默认填* 发送类型
                String resultMsg = SendSMS.mongateCsSpSendSmsNew(phoneNo, content, 1, "*", "M00005");
                if(smsMapMsg.get(resultMsg) != null)
                    sendMsg = smsMapMsg.get(resultMsg);
        } catch(Exception e) {
            e.printStackTrace();
            sendMsg = "发送失败";
        }
        return sendMsg;
    }
}
