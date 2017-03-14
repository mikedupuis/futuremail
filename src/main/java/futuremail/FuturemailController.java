package futuremail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mike on 3/11/2017.
 */
@RestController
public class FuturemailController {
    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody String requestFuturemail(@RequestBody MultiValueMap<String, String> params) throws Exception {
        if (params == null)
            return "error";

        FuturemailMessage futuremailMessage = new FuturemailMessage();
        futuremailMessage.setRecipient(params.getFirst("recipient"));
        futuremailMessage.setSender(params.getFirst("sender"));
        futuremailMessage.setMessage(params.getFirst("message"));
        futuremailMessage.setSendTimeMS(new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z").parse(params.getFirst("sendTime")).getTime());
        futuremailMessage.setSubject(params.getFirst("subject"));
        futuremailMessage.setSent(false);
        futuremailMessage.setSubmissionTimeMS(System.currentTimeMillis());

        if (!futuremailMessage.isValid())
            return "invalid";

        mongoTemplate.insert(futuremailMessage);

        return "success";
    }
}
