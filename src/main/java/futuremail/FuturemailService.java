package futuremail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Created by Mike on 3/11/2017.
 */
@Service("futuremailService")
public class FuturemailService {
    @Autowired private MongoTemplate mongoTemplate;

    @Autowired private JavaMailSender javaMailSender;

    private List<FuturemailMessage> getScheduledMessages() {
        return mongoTemplate.find(
            new Query()
                .addCriteria(Criteria.where("status").is("NEW"))
                .addCriteria(Criteria.where("sendTimeMS").lt(System.currentTimeMillis()))
            , FuturemailMessage.class
        );
    }

    @Scheduled(cron = "* * * * *") public void sendFuturemailMessages() {
        for (FuturemailMessage futuremailMessage : getScheduledMessages()) {
            try {
                this.javaMailSender.send(futuremailMessage.createMimeMessagePreparator());
                futuremailMessage.setStatus(FuturemailMessageStatus.SENT);
            } catch (MailException ex){
                futuremailMessage.setStatus(FuturemailMessageStatus.FAILED);
            } finally {
                mongoTemplate.updateFirst(
                        new Query(Criteria.where("id").is(futuremailMessage.getId())),
                        new Update().set("status", futuremailMessage.getStatus()),
                        FuturemailMessage.class);
            }
        }
    }
}
