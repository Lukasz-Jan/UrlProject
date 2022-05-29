package launcher.test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import launcher.MainApp;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

//	https://stackoverflow.com/questions/39084491/unable-to-find-a-springbootconfiguration-when-doing-a-jpatest

@SpringBootTest
public class ControllerTest {

	@Autowired
	private MainApp controller;

	@Test
	public void contextLoads() throws Exception {

		assertThat(controller).isNotNull();
	}
}
