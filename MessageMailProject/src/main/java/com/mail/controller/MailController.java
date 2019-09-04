package com.mail.controller;


import com.mail.repository.BasExcGainRepository;
import com.mail.service.MailService;
import com.mail.service.SMSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("/mail")
public class MailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;

    @Autowired
    private BasExcGainRepository basExcGainRepository;

    @Autowired
    private SMSendService smSendService;

    //需要发送的系统
    public static String[] sysCode = {"03001"};

    /**
     * 发送带有模板的邮件模式
     *
     * @return
     */
    //@Scheduled(cron="0 10 8 * * ?")//每天早上八点十分发送
    @RequestMapping("/simpMail_Templage")
    @ResponseBody
    public void simpTemplageMail() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
//            for(String s : sysCode){
//                List<BasSysException> list1 = basExcGainRepository.getExcMsg();//详细的异常统计
//                List<BasExcGain> list2 = basExcGainRepository.getGainMsg();//总统计数
//                Map<String, Object> map = new HashMap<String, Object>();
//                List<MailBean> list3 = new ArrayList<MailBean>();//用于封装给模板
//                List<BainBean> list4 = new ArrayList<BainBean>();//用于封装给模板
//                StringBuffer sb = new StringBuffer();//封装给手机短信
//                for (BasSysException basSysException : list1) {
//                    MailBean mailBean1 = new MailBean();
//                    mailBean1.setExName(basSysException.getExeTyp());
//                    mailBean1.setExNum(String.valueOf(basSysException.getTotal()));
//                    mailBean1.setSysName(basSysException.getSysCnm());
//                    mailBean1.setTime(sdf.format(date));
//                    mailBean1.setDataSrc(basSysException.getDataSrc());
//                    list3.add(mailBean1);
//                    sb.append(basSysException.getExeTyp()).append(basSysException.getTotal()).append(",");
//                    map.put("data1", list3);
//                }
//                for (BasExcGain basExcGain : list2) {
//                    BainBean bainBean = new BainBean();
//                    bainBean.setDataSrc(basExcGain.getDataSrc());
//                    bainBean.setTime(sdf.format(basExcGain.getReceiveTm()));
//                    bainBean.setSysName(basExcGain.getSysNme());
//                    bainBean.setTotalNum(String.valueOf(basExcGain.getTatalNum()));
//                    bainBean.setErrorNum(String.valueOf(basExcGain.getErrorNum()));
//                    list4.add(bainBean);
//                    map.put("data2", list4);
//                }
//                map.put("sysCode", s);
//                //mailService.sendTemplageMail(map);//发邮件服务
//                //mailService.sendMsg(sb.toString().trim(),s);//发短信服务
//
//                String filename ="F:\\ppolicy.txt";
//                mailService.sendMail("zouyu@zsins.com","发送附件","大眼睛",filename);
//            }
            String filename ="F:\\ppolicy.txt";
            boolean s = mailService.sendMail("zouyu@zsins.com","发送附件","大眼睛",filename);
            System.out.println(s);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送简单邮件模式
     *
     * @return
     */
    @RequestMapping("/simpMail")
    public String simpMail() {
        logger.info("进入 邮件发送服务");
        String toMail = "zouyu@zsins.com";
        String subject = "邮件服务测试 simple";
        String content = "今晚 7：00 浙商时代大厦618开会";
        mailService.sendSimpleMail(toMail, subject, content);
        return "Hello world spring boot";
    }

    /**
     * 发送html邮件模式
     *
     * @return
     */
    @RequestMapping("/simpMail_html")
    public String simpHtmlMail() {
        logger.info("进入邮件发送服务");
        String toMail = "zouyu@zsins.com";
        String subject = "邮件服务测试 simple";
        String content = "<html><head><style>td{border:solid #add9c0; border-width:0px 1px 1px 0px; padding:10px 0px;} table{border:solid #add9c0; border-width:1px 0px 0px 1px;}</style></head><body style=''><h3>xx系统中错误领导项目，项目信息如下：</h3>" +
                "<table><tr><th>合同号</th><th>项目名称</th><th>客户名称</th><th>合同总金额(元)</th><th>合同实收款(元)</th><th>签订日期</th><th>项目所属领导</th><th>提交人</th></tr>" +
                "<tr><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td><td>1</td></tr>" +
                "</table></body></html>";
        mailService.sendHtmlMail(toMail, subject, content);
        return "Hello world spring boot";
    }
}
