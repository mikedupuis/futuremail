package futuremail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mike on 3/11/2017.
 */
@Configuration
public class RestTemplateConfiguration {
    @Bean public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
