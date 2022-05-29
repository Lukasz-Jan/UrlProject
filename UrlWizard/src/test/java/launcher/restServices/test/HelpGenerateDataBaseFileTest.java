package launcher.restServices.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import entities.CiUrlPK;
import launcher.repositories.UrlJpaRepo;
import launcher.testUtils.TestDataProducer;
import launcher.testUtils.Utils;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class HelpGenerateDataBaseFileTest {

	@Autowired
	private UrlJpaRepo jpaRepo;

	@Autowired
	private TestRestTemplate restTemplate;

	private List<String> testUrlsList;

	private static final int INTERVAL_MS = 500;

	private String postEndPoint;

	private final int MAX_ATTEMPTS = 40;

	@LocalServerPort
	private int port;

	@BeforeAll
	public void init() {

		postEndPoint = "http://localhost:" + this.port + "/postUrl";
		generateDatabaseTableWithData();
	}
	
	
	
	
	
	
	/*
	 * In configuration:
	 * 	spring.jpa.hibernate.ddl-auto=update
	 * 	CI_URL table does not exist
	 * 	oraPropertiesHome.properties
	 * 	Table CI_URL is created and loaded with data from links
	 *      Then it can be used to generate initial script *.sql 
	 *      from which data is loaded in tests startup.
	 *      insert script should be generated from database for example in sqlDeveloper.
	 *      command:
	 *      mvn test -Dtest=HelpGenerateDataBaseFileTest
	 */
	

	

	private void generateDatabaseTableWithData() {

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();

		int noOfRows = jpaRepo.findAll().size();
		System.out.println("No of rows before load" + noOfRows);


		List<ResponseEntity<String>> collect = testUrlsList.stream().map(url -> restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());
		


		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}

		Set<String> urlsSet = new HashSet<String>(testUrlsList);
		int i = 0;
		boolean contentSetInAllIds = true;
		do {

			try {
				Thread.sleep(INTERVAL_MS);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			i++;

			contentSetInAllIds = true;
			for(String url : urlsSet) {

				Optional<CiUrlPK> urlPk = Utils.parseAndValidateUrl(url);
				Optional<String> contentOpt = urlPk.map(pK->jpaRepo.findById(pK)).map(urlEntity->urlEntity.get().getContent());

				if(!contentOpt.isPresent()) {

					contentSetInAllIds = false;
					break;
				}
			}

		} while(!contentSetInAllIds && i < MAX_ATTEMPTS);
		
		double inintervalMs = i * INTERVAL_MS/1000;
		System.out.println("Download time: " + inintervalMs);
	}
	
	@Test
	@Order(1)
	public void doNothing() throws Exception {

		System.out.println("doNothing()");
	}
}
