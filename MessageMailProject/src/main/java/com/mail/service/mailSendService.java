//package com.mail.service;
//
//import freemarker.template.Template;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.InputStreamSource;
//import org.springframework.core.io.Resource;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import javax.mail.internet.MimeMessage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 邮件发送服务类
// */
//
//public class mailSendService {
//
//    @Autowired
//    JavaMailSenderImpl mailSender;
//
//    @Autowired
//    private FreeMarkerConfigurer freeMarker;// 邮件模板
//
//    /**
//     * 发送简单邮件
//     * @return
//     * @throws Exception
//     */
//    public boolean sendSimpleMail() throws Exception {
//        MimeMessage message = null;
//        boolean flag = true;
//        try {
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom("邮件发送者");
//            helper.setTo("邮件接收者");
//            helper.setSubject("邮件主题");
//            helper.setText("内容");
//            mailSender.send(message);
//        } catch (Exception e) {
//            flag = false;
////            logger.error("系统异常邮件发送异常:{}", e);
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//
//    //发送模板与带附件的邮件
//    public boolean sendDetectedMail(MailFileDto mailDto) throws Exception {
//        MimeMessage message = null;
//        boolean flag = true;
//        try {
//            String[] sendTo = {"张三","李四","王五"};//接受者可以有多个，以数组的形式传入参数
//            message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("邮件发送者");
//            helper.setTo("邮件接收者");
//            helper.setSubject("邮件主题");
//            // 邮件参数
//            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("producttype", mailDto.getProductType());
//            model.put("warrantyType", mailDto.getWarrantyType());
//
//            //获取静态文件流资源
//            Resource resource = new ClassPathResource("images/logo.png");
//            model.put("logoImg", "data:image/png;base64," + this.img2Base64(resource));//对于模板中的静态图片、需要先把图片流转换Base64，才能带入到邮件中去
//            // 读取附件html模板：运单附件格式为PDF
//            Template attachmentTempl = freeMarker.getConfiguration().getTemplate("checkReport.html");
//            final String attachmentHtml = FreeMarkerTemplateUtils.processTemplateIntoString(attachmentTempl, model);
//            // 转换PDF格式的附件
//            final byte[] contents = Html2PdfUtil.buildPdf(attachmentHtml);//将html邮件模板转换成PDF
//            helper.addAttachment("diagnosticReport.pdf", new InputStreamSource() {
//                @Override
//                public InputStream getInputStream() throws IOException {
//                    return new ByteArrayInputStream(contents);
//                }
//            });
//            // PDF附件上传文件服务器
//            String filePath = upload2FileServer(mailDto.getRefNumber() + ".pdf", contents, mailDto.getFileServiceUrl());
//            model.put("downloadFilePath", filePath);
//            // 读取邮件 html 模板
//            Template template = freeMarker.getConfiguration().getTemplate("detected.html");
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//            helper.setText(html, true);
//            // 带有静态图片资源
//            //FileSystemResource file = new FileSystemResource(new File(fileSrc));//此方法项目打包发布后获取不到静态资源
//            helper.addInline("logo", resource);
//            // 开始发送
//            mailSender.send(message);
//        } catch (Exception e) {
//            flag = false;
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    /**
//     * 把Html转换pdf文件
//     */
//    public static byte[] buildPdf(String ctx) throws DocumentException, IOException{
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
//        Document document = new Document(PageSize.A4);
//        PdfWriter writer = PdfWriter.getInstance(document, baos);
//        document.open();
//
//        CssAppliers cssAppliers = new CssAppliersImpl();
//        cssAppliers.setChunkCssAplier(new MyChunkCssApplier());
//
//        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
//        DefaultTagProcessorFactory tpf=(DefaultTagProcessorFactory)Tags.getHtmlTagProcessorFactory();
//        tpf.addProcessor(Tag.IMG, ImageProcessor.class.getName());
//        htmlContext.setTagFactory(tpf);
//
//        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
//        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,new HtmlPipeline(htmlContext, new PdfWriterPipeline(document,writer)));
//
//        XMLWorker worker = new XMLWorker(pipeline, true);
//        XMLParser xmlParser = new XMLParser(worker);
//        ByteArrayInputStream bais=new ByteArrayInputStream(ctx.getBytes());
//        xmlParser.parse(new InputStreamReader(bais));
//        xmlParser.flush();
//        document.close();
//        byte[] result=baos.toByteArray();
//        baos.flush();
//        baos.close();
//        return result;
//    }
//
//
//
//    /**
//     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//     * @return
//     */
//    public static String img2Base64(Resource resource)  throws Exception{
//        byte[] data = null;
//        // 读取图片字节数组
//        try {
//            InputStream inputStream = resource.getInputStream();
//            data = new byte[inputStream.available()];
//            inputStream.read(data);
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new String(Base64.encodeBase64(data));
//    }
//
//}
