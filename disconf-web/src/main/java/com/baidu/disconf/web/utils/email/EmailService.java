package com.baidu.disconf.web.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.disconf.web.common.email.MailSenderInfo;
import com.baidu.disconf.web.common.email.SimpleMailSender;
import com.baidu.disconf.web.config.ApplicationPropertyConfig;

/**
 * @author liaoqiqi
 * @version 2014-3-2
 */
@Service
public class EmailService {

    @Autowired
    private ApplicationPropertyConfig emailProperties;

    /**
     * 发送HTML邮箱
     *
     * @return
     */
    public boolean sendHtmlEmail(String toEmail, String subject, String content) {

        //
        // 这个类主要是设置邮件
        //
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(emailProperties.getEmailHost());
        mailInfo.setMailServerPort(emailProperties.getEmailPort());
        mailInfo.setValidate(true);
        mailInfo.setUserName(emailProperties.getEmailUser());
        mailInfo.setPassword(emailProperties.getEmailPassword());// 您的邮箱密码

        mailInfo.setFromAddress(emailProperties.getFromEmail());
        mailInfo.setToAddress(toEmail);

        mailInfo.setSubject(subject);
        mailInfo.setContent(content);

        // 这个类主要来发送邮件
        return SimpleMailSender.sendHtmlMail(mailInfo);// 发送文体格式
    }
    
    public static void main(String[] args) {
    	MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("10.205.91.22");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("lemall_fecru");
        mailInfo.setPassword("@!m09182016");// 您的邮箱密码

        mailInfo.setFromAddress("lemall_fecru@le.com");
        mailInfo.setToAddress("dimmacro@163.com");

        mailInfo.setSubject("aaa");
        mailInfo.setContent("1111");

        // 这个类主要来发送邮件
        SimpleMailSender.sendHtmlMail(mailInfo);// 发送文体格式
	}
}
