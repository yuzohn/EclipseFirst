package com.mail.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.mail.repository.BasExcGainRepository;
import com.mail.service.MailService;
import com.mail.service.SMSendService;
import com.mail.util.StringUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FreeMarkerConfigurer fmc;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SMSendService smSendService;

    @Autowired
    private BasExcGainRepository basExcGainRepository;


    //读取配置文件中信息，发件人邮箱
    @Value("${mail.from.info}")
    private String from;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.password}")
    private String pass;

    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("from:" + from + " to:" + to + " subject:" + subject + " content:" + content);
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
            e.printStackTrace();
        }

    }

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            // helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

    /**
     * @param map
     */
    public void sendTemplageMail(Map<String, Object> map) {
        String subject = "邮件服务";
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> mapSend = new HashMap<String, Object>();
        String sysCode = (String) map.get("sysCode");
        List<String> listMainSend = basExcGainRepository.getExeSend(sysCode);
        List<String> listAttachSend = basExcGainRepository.getAttachSend(sysCode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        mapSend.put("listInfo", map.get("data1"));
        mapSend.put("listGainExc", map.get("data2"));
        mapSend.put("date", sdf.format(new Date()));
        try {
            for (String s : listMainSend) {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setTo(s);
                mimeMessageHelper.setFrom(from);//这个必须加，不加就报错。当然和你设置的username是一样的。
                Template t = fmc.getConfiguration().getTemplate("message.ftl");//获取模板
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, mapSend);//解析模板
                mimeMessageHelper.setText(content, true);
                mailSender.send(message);
            }
            for (String ss : listAttachSend) {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setTo(ss);
                mimeMessageHelper.setFrom(from);//这个必须加，不加就报错。当然和你设置的username是一样的。
                Template t = fmc.getConfiguration().getTemplate("message.ftl");//获取模板
                String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, mapSend);//解析模板
                mimeMessageHelper.setText(content, true);
                mailSender.send(message);
            }
        } catch (Exception e) {
            logger.error("发送带模板的邮件时发生异常！", e);
        }
    }

    /**
     * 发送短信
     * @param sendString
     * @param sysCode
     */
    public void sendMsg(String sendString,String sysCode) {
        List<String> listSend = basExcGainRepository.getPhoneSend(sysCode);
        try {
            List<String> list = StringUtil.getStrList(sendString,350);
            for(String string : list){
                for (String phone : listSend) {
                    smSendService.sendSms(string,phone);
                    Thread.sleep(1000);//保证短信按照顺序发送
                }
            }
        } catch (Exception e) {
            logger.error("发送短信时发生异常！", e);
        }
    }

    public boolean sendMail(String receive, String subject, String msg, String filename)
            throws GeneralSecurityException {
        if (StringUtils.isEmpty(receive)) {
            return false;
        }
        // 发件人电子邮箱密码
        //final String pass = "123456";

        // 指定发送邮件的主机为 smtp.qq.com
        //String host = "smtp.163.com"; // 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() { // qq邮箱服务器账户、第三方登录授权码
                return new PasswordAuthentication(from, pass); // 发件人邮件用户名、密码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receive));
            // Set Subject: 主题文字
            message.setSubject(subject);
            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 消息
            messageBodyPart.setText(msg);
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);
            // 附件部分
            messageBodyPart = new MimeBodyPart();
            // 设置要发送附件的文件路径
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));

            // messageBodyPart.setFileName(filename);
            // 处理附件名称中文（附带文件路径）乱码问题
            messageBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(messageBodyPart);
            // 发送完整消息
            message.setContent(multipart);
            // 发送消息
            Transport.send(message);
            // System.out.println("Sent message successfully....");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}


