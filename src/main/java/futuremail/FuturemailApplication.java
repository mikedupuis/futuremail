package futuremail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FuturemailApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuturemailApplication.class, args);
	}
}
