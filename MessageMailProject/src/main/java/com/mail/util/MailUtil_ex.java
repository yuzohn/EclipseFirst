package com.mail.util;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailUtil_ex {

    private JavaMailSenderImpl javaMailSender;
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private FreeMarkerConfigurer fmc;


    public void sendTemplateEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String template, Context context)   {
        long startTimestamp = System.currentTimeMillis();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            String content = templateEngine.process(template, context);
            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMail(String email, String header, String name){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(header);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom("example@example.com");//这个必须加，不加就报错。当然和你设置的username是一样的。


            Template t =  fmc.getConfiguration().getTemplate("hello_world.ftl");//获取模板

            Map<String, Object> map = new HashMap<>();
            map.put("name", name);

            String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);//解析模板

            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
