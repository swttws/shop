package com.su;

import com.su.service.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Random;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class test {

    @Test
    public void tests(){
        Random random = new Random();
        String ch = "0123456789qwertyuiopasdfghjklzxcvbnm";
        int length = ch.length();
        String str = "";
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(length);
            str += ch.substring(index, index + 1);
        }
        System.out.println(str);

    }

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void test3() throws MessagingException {
        int count=1;//默认发送一次
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        while(count--!=0) {
            String codeNum = "";
            int[] code = new int[3];
            Random random = new Random();
            //自动生成验证码
            for (int i = 0; i < 6; i++) {
                int num = random.nextInt(10) + 48;
                int uppercase = random.nextInt(26) + 65;
                int lowercase = random.nextInt(26) + 97;
                code[0] = num;
                code[1] = uppercase;
                code[2] = lowercase;
                codeNum += (char) code[random.nextInt(3)];
            }
            System.out.println(codeNum);
            //标题
            helper.setSubject("您的验证码为：" + codeNum);
            //内容
            helper.setText("您好！，感谢支持冰咖啡的小站。您的验证码为：" + "<h2>" + codeNum + "</h2>" + "千万不能告诉别人哦！", true);
            //邮件接收者
            helper.setTo("2178127391@qq.com");
            //邮件发送者，必须和配置文件里的一样，不然授权码匹配不上
            helper.setFrom("2178127391@qq.com");
            mailSender.send(mimeMessage);
            System.out.println("邮件发送成功！" + (count + 1));
        }
    }

}
