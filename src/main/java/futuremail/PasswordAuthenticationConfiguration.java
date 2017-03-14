package futuremail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.PasswordAuthentication;

/**
 * Created by Mike on 3/12/2017.
 */
@Configuration
public class PasswordAuthenticationConfiguration {

    @Bean PasswordAuthentication getPasswordAuthentication() {
    }
}
