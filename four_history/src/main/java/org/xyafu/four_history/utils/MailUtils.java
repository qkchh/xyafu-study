package org.xyafu.four_history.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import sun.net.smtp.SmtpClient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * 发邮件工具类
 * 如果使用QQ邮箱，需要在QQ邮箱里 开启服务：POP3/SMTP服务
 * 需要通过短信验证，会发一个授权码，用第三方邮箱工具登陆时，输入授权码即可
 */
public final class MailUtils {
    private static final String USER = ""; // 发件人称号，同邮箱地址
    private static final String PASSWORD = ""; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     * @param to    收件人邮箱
     * @param text  邮件正文
     * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String to, String text, String title) {
        try {
            final Properties props = new Properties();
            // 表示SMTP发送邮件，必须进行身份验证
            props.put("mail.smtp.auth", "true");
            //此处填写SMTP服务器
            props.put("mail.smtp.host", "smtp.qq.com");
            //端口号，QQ邮箱端口587
            props.put("mail.smtp.port", "587");
            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            //1、使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);

            //2、创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);

            //设置发件人
            InternetAddress form = new InternetAddress(USER);
            message.setFrom(form);

            //设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            //设置邮件标题
            message.setSubject(title);

            //设置邮件的内容体
            message.setContent(text, "text/html;charset=UTF-8");

            //3、发送邮件
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendEmail(String email,String code){
        String str="<div><pre style=\"text-align: left;\"><font face=\"宋体, monospace\" style=\"\" size=\"4\">【学习党史】</font></pre><pre style=\"text-align: left;\"><font face=\"宋体, monospace\" style=\"\"><span style=\"font-size: 9.8pt;\">你的验证码为</span></font></pre><pre style=\"text-align: left;\"><font style=\"\" size=\"6\"><font face=\"Arial Black\" color=\"#ff6600\"><b style=\"background-color: rgb(255, 255, 255);\">"+code+"</b></font></font></pre><pre style=\"text-align: left;\"><span style=\"font-size: 9.8pt; font-family: 宋体, monospace;\">十分钟内有效，请尽快使用！切勿讲验证码泄露于他人。</span></pre></div>";
        boolean isSend  = MailUtils.sendMail(email, str, "fourHistory");
        return isSend;
    }


}
