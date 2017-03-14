package futuremail;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by Mike on 3/11/2017.
 */
@Configuration
public class MongoTemplateConfiguration {
    @Bean public MongoTemplate getMongoTemplate() {
        return new MongoTemplate(new MongoClient("localhost"), "futuremail");
    }
}
