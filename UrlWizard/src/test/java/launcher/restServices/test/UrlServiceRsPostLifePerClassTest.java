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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import entities.CiUrl;
import entities.CiUrlPK;
import launcher.repositories.UrlJpaRepo;
import launcher.testUtils.TestDataProducer;
import launcher.testUtils.Utils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class UrlServiceRsPostLifePerClassTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private UrlJpaRepo jpaRepo;

	private String postEndPoint;

	private final int MAX_ATTEMPTS = 12;

	private List<String> testUrlsList;

	@BeforeAll
	public void init() {

		postEndPoint = "http://localhost:" + this.port + "/postUrl";
	}
	
	@AfterAll
	public void tearDown() {

		jpaRepo.deleteAll();
	}	

	@Test
	@Order(1)
	public void postOneLinkOk() throws Exception {

		System.out.println("POST TEST 1 \n");
		System.out.println("postUrl: " + postEndPoint);

		String url = "https://www.komputerswiat.pl/aktualnosci/sprzet/nvidia-wprowadzi-nowe-wersje-kart-geforce-rtx-30-z-limitem-kopania-kryptowalut/pcn6t15";

		Optional<CiUrlPK> ciUrlPkOpt = Utils.parseAndValidateUrl(url);

		if(ciUrlPkOpt.isPresent()) {

			// sending:
			System.out.println(postEndPoint + "\n\n");
			ResponseEntity<String> response = restTemplate.postForEntity(postEndPoint, url, String.class);
			assertThat(response.getStatusCode().equals(HttpStatus.ACCEPTED));

			// getting pk from base
			Optional<CiUrl> findByIdOpt = jpaRepo.findById(ciUrlPkOpt.get());

			assertThat(findByIdOpt.get().getId().getPath()).isEqualTo(url);
			// size already on in base did it managed?
			assertEquals(jpaRepo.findAll().size(), 1);

			int i = 0;
			Optional<String> contentOpt = Optional.ofNullable(jpaRepo.findById(ciUrlPkOpt.get()).get().getContent());
			// did not managed to download
			assertThat(contentOpt).isEmpty();

			do {
				Thread.sleep(200);
				contentOpt = Optional.ofNullable(jpaRepo.findById(ciUrlPkOpt.get()).get().getContent());
				i++;
			} while(!contentOpt.isPresent() && i < MAX_ATTEMPTS);

			// not managed to download
			assertThat(contentOpt).isNotEmpty();
			// assertThat(contentOpt.get().contains("RTX 30"));
			assertThat(contentOpt.get().contains("procurement"));
		}
	}

	@Test
	@Order(2)
	public void postOneLinkNotConnected() throws Exception {

		System.out.println("POST TEST 2 \n");

		System.out.println("jpaRepo.findAll().size(): " + jpaRepo.findAll().size());

		String urlStr = "https://www.website.com/online-store-builder/";

		Optional<CiUrlPK> ciUrlPkOpt = Utils.parseAndValidateUrl(urlStr);

		if(ciUrlPkOpt.isPresent()) {

			ResponseEntity<String> response = restTemplate.postForEntity(postEndPoint, urlStr, String.class);

			assertThat(response.getStatusCode().equals(HttpStatus.ACCEPTED));

			assertEquals(jpaRepo.findAll().size(), 2);

			Optional<CiUrl> urlEntityOpt = jpaRepo.findById(ciUrlPkOpt.get());
			assertThat(urlEntityOpt).isNotEmpty();

			Optional<String> urlInDb = Optional.ofNullable(urlEntityOpt.get().getId().getPath());

			urlInDb.ifPresent(urlFromDb->assertThat(urlFromDb).isEqualTo(urlStr));

			Optional<String> contentInDb = Optional.ofNullable(urlEntityOpt.get().getContent());
			assertThat(contentInDb).isEmpty();

			int i = 0;
			Optional<String> contentOpt = Optional.empty();
			do {
				Thread.sleep(200);
				contentOpt = Optional.ofNullable(jpaRepo.findById(ciUrlPkOpt.get()).get().getContent());
				i++;
			} while(!contentOpt.isPresent() && i < MAX_ATTEMPTS);

			assertThat(contentInDb).isEmpty();
		}
	}

	@Test
	@Order(3)
	public void sendManyParallelWithRepeatedUrlsInRequestParam() throws Exception {

		System.out.println("POST TEST sendMany \n");

		testUrlsList = TestDataProducer.produceUrlListWithReapeats();
		int oldNoOfRows = jpaRepo.findAll().size();

		assertEquals(oldNoOfRows, 2);

		List<ResponseEntity<String>> collect = testUrlsList.stream().parallel().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}

		Set<String> urlsSet = new HashSet<String>(testUrlsList);

		int newNoOfRowsShouldBe = oldNoOfRows + urlsSet.size();
		int newNoOfRows = jpaRepo.findAll().size();
		assertEquals(newNoOfRowsShouldBe, newNoOfRows);

		// now wait and check for entities contents
		int i = 0;
		boolean contentSetInAllIds = true;

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

			System.out.println("i: " + i);
		} while(!contentSetInAllIds && i < MAX_ATTEMPTS);

		if(i >= MAX_ATTEMPTS && contentSetInAllIds == false)
			fail("Probably not enough time to download all contents");

		// check for content in each table row:

		Map<String, String> urlMap = TestDataProducer.produceUrlHashMap();
		
		for(String url: urlsSet) {

			CiUrlPK pK = Utils.parseAndValidateUrl(url).get();
			String content = jpaRepo.findById(pK).get().getContent();
			// content found in db, now check if contains test word which should be there

			String checkWord = urlMap.get(url);

			if(!content.contains(checkWord)) {
				
				System.out.println(content);
				
				fail("Content does not contain \"" + checkWord + "\" for path \n" + url);

				System.out.println();
			}
		}
	}
}
