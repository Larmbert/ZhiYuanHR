package com.light.mailserver.mailreceiver;

import com.light.ehr.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Author Light
 * @CreateTime 2020/6/22
 */
@Component
public class MailReceiver {

    public static final Logger logger= LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;
    @RabbitListener(queues = "light.mail")
    public void handler(Employee employee){
        logger.info(employee.toString());
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(msg);
        try {
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSentDate(new Date());
            helper.setSubject("入职欢迎");
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("jobName",employee.getJobLevel().getName());
            context.setVariable("depName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("邮件发送失败："+e.getMessage());
        }
    }
}
