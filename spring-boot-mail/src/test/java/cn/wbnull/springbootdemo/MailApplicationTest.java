package cn.wbnull.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;

@SpringBootTest(classes = MailApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailApplicationTest {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件主题
        simpleMailMessage.setSubject("服务故障报警");
        //邮件正文
        simpleMailMessage.setText("xxx服务故障！请及时处理！");
        //收件人
        simpleMailMessage.setTo("921xxxxxx@qq.com");
        //发件人
        simpleMailMessage.setFrom("921xxxxxx@qq.com");

        mailSender.send(simpleMailMessage);
    }

    @Test
    public void contextLoadsMime() throws MessagingException, UnsupportedEncodingException {
        //复杂邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装，支持多文件
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("复杂服务故障报警");
        //邮件正文，支持html标签
        mimeMessageHelper.setText("<p style='color: red'>xxx服务故障！请及时处理！</p>", true);

        //附件
        //MimeUtility.encodeWord()防止中文乱码
        mimeMessageHelper.addAttachment(MimeUtility.encodeWord("故障附件.txt", "UTF-8", "B"),
                new File("data/故障附件.txt"));
        mimeMessageHelper.addAttachment("error.txt", new File("data/error.txt"));

        mimeMessageHelper.setTo("921xxxxxx@qq.com");
        mimeMessageHelper.setFrom("921xxxxxx@qq.com");

        mailSender.send(mimeMessage);
    }
}
