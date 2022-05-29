package launcher.restServices.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import entities.CiUrl;
import entities.CiUrlPK;
import launcher.repositories.UrlJpaRepo;
import launcher.testUtils.TestDataProducer;
import launcher.testUtils.Utils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RetrieveServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private UrlJpaRepo jpaRepo;

	private String getEndPointPart;

	private String postEndPoint;

	private final int MAX_ATTEMPTS = 12;

	private List<String> testUrlsList;

	@BeforeAll
	public void init() {

		getEndPointPart = "http://localhost:" + this.port + "/";
		postEndPoint = "http://localhost:" + this.port + "/postUrl";
		initializeDatabaseData();
	}

	@AfterAll
	public void tearDown() {

		jpaRepo.deleteAll();
	}

	@Test
	@Order(1)
	public void retrieveAllPageSizeCorrectTest() throws Exception {

		String url = getEndPointPart + "retrieveAll?page=1&size=4";
		
		String responseStr = restTemplate.getForObject(url, String.class);
		
		long count = responseStr.chars().filter(ch -> ch == System.lineSeparator().charAt(0)).count();
		
		assertThat(count).isEqualTo(4);

	}
	
	@Test
	@Order(2)
	public void retrieveByPageOverrunReturnsNoContent() throws Exception {

		String page = "10";
		String size = "10";
		
		int lastPageBegin = Integer.parseInt(page) * Integer.parseInt(size) - Integer.parseInt(size) - 1;
		
		List<CiUrl> noOfRows = jpaRepo.findAll();
		assertThat(noOfRows).hasSizeGreaterThan(Integer.parseInt(size));
		assertThat(noOfRows).hasSizeLessThan(lastPageBegin);
		
		String url = getEndPointPart + "retrieveAll?page=" + page + "&size=" + size;

		ResponseEntity<String> respEntity = restTemplate.getForEntity(url, String.class);

		assertThat(respEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);		
	}	
	
	@Test
	@Order(3)
	public void wrongSizeFormatReturnsUNPROCESSABLE_ENTITY() throws Exception {

		String page = "0";
		String size = "x";
		
		String url = getEndPointPart + "retrieveAll?page=" + page + "&size=" + size;

		ResponseEntity<String> respEntity = restTemplate.getForEntity(url, String.class);

		assertThat(respEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.UNPROCESSABLE_ENTITY);
		
		assertThat(respEntity.getBody()).isEqualTo("wrong parameters in request");
	}
	
	@Test
	@Order(4)
	public void wrongPageFormatReturnsUNPROCESSABLE_ENTITY() throws Exception {

		String page = "x";
		String size = "3";
		
		String url = getEndPointPart + "retrieveAll?page=" + page + "&size=" + size;

		ResponseEntity<String> respEntity = restTemplate.getForEntity(url, String.class);

		assertThat(respEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.UNPROCESSABLE_ENTITY);
		
		assertThat(respEntity.getBody()).isEqualTo("wrong parameters in request");
	}
	
	@Test
	@Order(5)
	public void retrieveListOfAllResources() throws Exception {

		String url = getEndPointPart + "retrieveAll";

		ResponseEntity<String> respEntity = restTemplate.getForEntity(url, String.class);

		assertThat(respEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		String body = respEntity.getBody();
		
		String[] urlTab = body.split(System.lineSeparator());
		
		List<Object> retrievedList = Arrays.asList(urlTab);
		
		int retrievedListSize = retrievedList.size();  
		int noOfRowsInBase = jpaRepo.findAll().size();
		
		assertThat(noOfRowsInBase).isEqualTo(retrievedListSize);
		
		for(CiUrl urlEntity: jpaRepo.findAll()) {
			
			assertThat(body).contains(urlEntity.getId().getPath());
		}
	}

	@Test
	@Order(5)
	public void retrieveOneResource() throws Exception {

		// http://localhost:8082/retrievePage/?url=https://datatracker.ietf.org/doc/html/rfc3986
		
		String url = getEndPointPart + "retrievePage/?url=https://datatracker.ietf.org/doc/html/rfc3986";

		ResponseEntity<String> respEntity = restTemplate.getForEntity(url, String.class);
		
		
		System.out.println(respEntity.getHeaders());
		System.out.println(respEntity.getHeaders().getContentType().getType());
		System.out.println(respEntity.getHeaders().getContentType());

		System.out.println("getCharset(): ");
		System.out.println(respEntity.getHeaders().getContentType().getCharset());
		System.out.println(respEntity.getHeaders().getAcceptCharset());
		System.out.println(MediaType.TEXT_HTML);
		
		
		assertThat(respEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		assertThat(respEntity.getHeaders().toString()).contains("text/html");
		
		Optional<CiUrlPK> pk = Utils.parseAndValidateUrl(url);
		
		if(pk.isPresent()) {
			
			Optional<CiUrl> findById = jpaRepo.findById(pk.get());
			
			if(findById.isPresent()) {
			
				String content = findById.get().getContent();
				
				assertThat(content).isEqualTo(respEntity.getBody());
			}
		}

	}	

	private void initializeDatabaseData() {

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();
		int noOfRows = jpaRepo.findAll().size();
		//assertEquals(0, noOfRows);

		List<ResponseEntity<String>> collect = testUrlsList.stream().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}

		Set<String> urlsSet = new HashSet<String>(testUrlsList);
		int i = 0;
		boolean contentSetInAllIds = true;
		do {

			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			i++;
			//System.out.println("i :" + i);
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
}
