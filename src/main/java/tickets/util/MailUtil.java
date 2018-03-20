package tickets.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil implements Runnable {
    private String email;// 收件人邮箱
    private String code;// 激活码

    public MailUtil(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String from = "2474809464@qq.com";// 发件人电子邮箱
        String host = "smtp.qq.com"; // 指定发送邮件的主机

        Properties properties = System.getProperties();// 获取系统属性

        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证
        properties.setProperty("mail.transport.protocol", "smtp");//协议
        properties.setProperty("mail.smtp.port", "465");//端口

        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("2474809464@qq.com", "xmxsojjylzxgechg"); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            String name = javax.mail.internet.MimeUtility.encodeText("Tickets小组");
            message.setFrom(new InternetAddress(name + " <" + from + ">"));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 2.3设置邮件主题
            message.setSubject("验证你的电子邮件地址");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h1>验证你的电子邮件地址</h1><h3><a href='http://localhost:8080/user/verify?code="
                    + code + "'>http://localhost:8080/user/verify?code=" + code
                    + "</href></h3><h5>若浏览器不能正常跳转，请复制链接到地址栏</h5></body></html>";
            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
