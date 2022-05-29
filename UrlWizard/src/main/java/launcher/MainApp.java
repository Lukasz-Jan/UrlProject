
/*
 * All program is written and owned by Lukasz Janowski
 * email: lukasz_jan@vp.pl
 */


package launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"launcher", "entities"})
@EntityScan("entities")
@EnableJpaRepositories(basePackages = {"launcher.repositories"})
public class MainApp {

	private static final Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {
	
		ConfigurableApplicationContext appContext = SpringApplication.run(MainApp.class,  args);
		log.info("MainApp");
	}
}
