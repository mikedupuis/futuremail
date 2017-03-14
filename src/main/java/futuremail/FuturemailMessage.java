package futuremail;

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
    @Id public String id;
    private String recipient;
    private String sender;
    private String subject;
    private String message;
    private long submissionTimeMS;
    private long sendTimeMS;
    private boolean sent;

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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isValid() {
        return sendTimeMS > submissionTimeMS;
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
