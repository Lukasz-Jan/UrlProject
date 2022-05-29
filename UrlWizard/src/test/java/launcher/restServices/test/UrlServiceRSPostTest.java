package launcher.restServices.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestMethodOrder(OrderAnnotation.class)
public class UrlServiceRSPostTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UrlJpaRepo jpaRepo;

	private String postEndPoint;

	private final int MAX_ATTEMPTS = 12;

	private List<String> testUrlsList;

	@BeforeEach
	public void init() {
		postEndPoint = "http://localhost:" + this.port + "/postUrl";
	}
	
	@AfterEach
	public void tear() {	
		jpaRepo.deleteAll();
		jpaRepo.flush();
	}	

	//@Test
	@Order(1)
	public void sendRequestsInSequenceWithRepeatedUrlsCheckAnswearStatus() throws Exception {

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();
		int noOfRows = jpaRepo.findAll().size();

		assertEquals(0, noOfRows);

		List<ResponseEntity<String>> collect = testUrlsList.stream().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}
		
		Set<String> urlsSet = new HashSet<String>(testUrlsList);
		System.out.println("No of url: " + urlsSet.size());
		int i = 0;
		boolean contentSetInAllIds = true;
		Thread.sleep(15000);
		do {
			Thread.sleep(500);
			i++;

			//System.out.println("i :" + i);
			contentSetInAllIds = true;
			for(String url : urlsSet) {

				Optional<CiUrlPK> urlPk = Utils.parseAndValidateUrl(url);
				//System.out.println("url: " + url);
				Optional<String> contentOpt = urlPk.map(pK->jpaRepo.findById(pK)).map(urlEntity->urlEntity.get().getContent());

				if(!contentOpt.isPresent()) {

					contentSetInAllIds = false;
					break;
				}
			}

		} while(!contentSetInAllIds && i < MAX_ATTEMPTS);
	}
	
	//@Test	
	@Order(2)
	public void sendRequestsInSequenceWithRepeatedUrlsAndNoOfPrimaryKeysCheck() throws Exception {

		System.out.println("UrlServiceRSPostTest POST TEST sendMany \n");

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();

		List<ResponseEntity<String>> collect = testUrlsList.stream().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}
		
		Set<String> urlsSet = new HashSet<String>(testUrlsList);



		int i = 0;
		boolean contentSetInAllIds = true;
		Thread.sleep(15000);
		do {
			Thread.sleep(500);
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
	}	
	
	//@Test
	@Order(3)
	public void sendManyRequestsParallelWithRepeatedUrlsAndCheckAnswearStatus() throws Exception {

		System.out.println("UrlServiceRSPostTest POST TEST sendMany \n");

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();
		int noOfRows = jpaRepo.findAll().size();

		assertEquals(0, noOfRows);

		List<ResponseEntity<String>> collect = testUrlsList.stream().parallel().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}
		
		Set<String> urlsSet = new HashSet<String>(testUrlsList);
		
		int i = 0;
		boolean contentSetInAllIds = true;
		Thread.sleep(17000);
		do {
			Thread.sleep(500);
			i++;

			System.out.println("i :" + i);
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
	}	

	//@Test
	@Order(4)
	public void sendManyRequestsParallelWithRepeatedUrlsAndNoOfPrimaryKeysCheck() throws Exception {

		System.out.println("UrlServiceRSPostTest POST TEST sendMany \n");

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();
		

		assertEquals(0, jpaRepo.findAll().size());

		List<ResponseEntity<String>> collect = testUrlsList.stream().parallel().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}
		
		Set<String> urlsSet = new HashSet<String>(testUrlsList);

		int i = 0;
		boolean contentSetInAllIds = true;
		Thread.sleep(17000);
		do {
			Thread.sleep(500);
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
	}
	
	
	@Test
	@Order(5)
	
	// may happen also optimistic locking failed in test
	// when two the same url are downloaded in parallel
	// test then is not passed but it is ok
	public void sendManyRequestsParallelWithRepeatedUrlsAndContentColumnCheck() throws Exception {

		System.out.println("UrlServiceRSPostTest POST TEST sendMany \n");

		testUrlsList = TestDataProducer.produceUrlListWithReapeats_V1();
		int oldNoOfRows = jpaRepo.findAll().size();

		assertEquals(0, oldNoOfRows);

		List<ResponseEntity<String>> collect = testUrlsList.stream().parallel().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}

		Set<String> urlsSet = new HashSet<String>(testUrlsList);


		int i = 0;
		boolean contentSetInAllIds = true;
		Thread.sleep(17000);
		do {
			Thread.sleep(500);
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

		if(i >= MAX_ATTEMPTS && contentSetInAllIds == false)
			fail("Probably not enough time to download all contents or maybe optimistic lock failure");

		// check for content in each table row:

		Map<String, String> urlMap = TestDataProducer.produceUrlHashMap();
		
		for(String url: urlsSet) {

			CiUrlPK pK = Utils.parseAndValidateUrl(url).get();
			String content = jpaRepo.findById(pK).get().getContent();
			// content found in db

			System.out.println(url);
			String checkWord = urlMap.get(url);

			if(!content.contains(checkWord)) {
				
				System.out.println(content);
				
				fail("Content does not contain \"" + checkWord + "\" for path \n" + url);

				System.out.println();
			}
		}
	}		
}
