package futuremail;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.net.PasswordAuthentication;
import java.util.Properties;

/**
 * @author Mike
 *         Created on 3/17/2017
 */
@Configuration
public class FuturemailConfiguration {

    @Bean
    public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(new MongoClient("localhost"), "futuremail");
    }

    @Bean
    PasswordAuthentication getPasswordAuthentication(@Value("username") String username, @Value("password") String password) {
        return new PasswordAuthentication(username, password.toCharArray());
    }

    @Bean
    public JavaMailSender getMailSender(@Autowired PasswordAuthentication passwordAuthentication) {
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
