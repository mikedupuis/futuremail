package futuremail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;
import java.net.PasswordAuthentication;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Mike on 3/11/2017.
 */
@Configuration
public class JavaMailSenderConfiguration {
    @Autowired private PasswordAuthentication passwordAuthentication;

    @Bean public JavaMailSender getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setUsername(passwordAuthentication.getUserName());
        javaMailSender.setPassword(new String(passwordAuthentication.getPassword()));

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailProperties.put("mail.smtp.debug", true);
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        javaMailSender.setJavaMailProperties(mailProperties);

        return javaMailSender;
    }
}
