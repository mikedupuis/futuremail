package futuremail;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

/**
 * Created by Mike on 3/11/2017.
 */
public class FuturemailMessage {
    @Id
    @JsonProperty("id")
    public String id;

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    @JsonProperty("submissionTimeMS")
    private long submissionTimeMS;

    @JsonProperty("sendTimeMS")
    private long sendTimeMS;

    @JsonProperty("status")
    private FuturemailMessageStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSubmissionTimeMS() {
        return submissionTimeMS;
    }

    public void setSubmissionTimeMS(long submissionTimeMS) {
        this.submissionTimeMS = submissionTimeMS;
    }

    public long getSendTimeMS() {
        return sendTimeMS;
    }

    public void setSendTimeMS(long sendTimeMS) {
        this.sendTimeMS = sendTimeMS;
    }

    public boolean isValid() {
        return sendTimeMS > submissionTimeMS;
    }

    public FuturemailMessageStatus getStatus() {
        return status;
    }

    public void setStatus(FuturemailMessageStatus status) {
        this.status = status;
    }

    private String buildMessageBody() {
        StringBuilder messageBodyBuilder = new StringBuilder();
        messageBodyBuilder.append("<html><body>");
        messageBodyBuilder.append("<h3>Futuremail Message!</h3>");
        messageBodyBuilder.append("<p>From: " + getSender() + "</p>");
        messageBodyBuilder.append("<p>" + getMessage() + "</p>");
        messageBodyBuilder.append("<small>sent by Futuremail.</small>");
        messageBodyBuilder.append("</body></html>");

        return messageBodyBuilder.toString();
    }

    public MimeMessagePreparator createMimeMessagePreparator() {

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(getRecipient()));
                mimeMessage.setFrom(new InternetAddress("mail@futuremail.com"));
                mimeMessage.setContent(buildMessageBody(), "text/html; charset=utf-8");
                mimeMessage.setSubject(getSubject());
            }
        };

        return mimeMessagePreparator;
    }
}
