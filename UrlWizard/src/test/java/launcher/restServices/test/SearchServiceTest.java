package launcher.restServices.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import org.springframework.http.ResponseEntity;
import entities.CiUrl;
import entities.CiUrlPK;
import launcher.repositories.UrlJpaRepo;
import launcher.testUtils.TestDataProducer;
import launcher.testUtils.Utils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class SearchServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private UrlJpaRepo jpaRepo;

	private String getEndPointPart;

	private String postEndPoint;

	private int MAX_ATTEMPTS = 40;

	private List<String> testUrlsList;

	private String requestUrl;

	private static final int INTERVAL_MS = 1500;

	@BeforeAll
	public void init() {

		getEndPointPart = "http://localhost:" + this.port + "/";
		postEndPoint = "http://localhost:" + this.port + "/postUrl";
	}

	@AfterAll
	public void tearDown() {

		// jpaRepo.deleteAll();
	}

	// data to database should be loaded from script urlLoad.sql and now is not
	// searched word doctype should be hidden in https://www.google.com/
	// Sample command which is tested:
	// http://localhost:8082/searchForText/?word=doctype
	// test -Dtest=SearchServiceTest#singleSearchForOneWordOnManyPages
	@Test
	@Order(1)
	public void singleSearchForOneWordOnManyPages() throws Exception {

		initializeDatabaseDataForOption("smallCollectionWithDuplicates");
		MAX_ATTEMPTS = 20;

		final String expectedResponseStr = "https://www.google.com/";
		final String searchedWord = "doctype";

		requestUrl = getEndPointPart + "searchForText/?word=" + searchedWord; // SHOULD
		                                                                      // BE
		                                                                      // http://localhost:xy/search/?word=doctype
		//

		System.out.println("Search in database for : " + requestUrl);

		ResponseEntity<String> respEntity = restTemplate.getForEntity(requestUrl, String.class);

		assertThat(respEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

		System.out.println("\nrespEntity.getBody():\n" + respEntity.getBody());

		assertThat(respEntity.getBody()).isEqualTo(expectedResponseStr);
	}

	/*
	 * parallel: http://localhost:8082/search/?word=DOCTYPE
	 */
	//@Test
	@Order(2)
	public void parallelSearchForOneWord() throws Exception {

		initializeDatabaseDataForOption("collectionWithDuplicates");
		final String searchedWord = "DOCTYPE";
		requestUrl = getEndPointPart + "searchForText/?word=" + searchedWord; // http://localhost:8082/searchForText/?word=DOCTYPE

		List<String> urlList = Collections.nCopies(20, requestUrl);

		// twenty parallel request are sent, all are the same, which
		// contains word DOCTYPE

		List<ResponseEntity<String>> getsResponses = urlList.stream().parallel().map(url->restTemplate.getForEntity(url, String.class)).collect(Collectors.toList());

		final String expectedResponseStr = "https://openjdk.java.net/jeps/361\r\n" + "https://www.nvidia.com/pl-pl/geforce/rtx/\r\n"
		                + "https://openjdk.java.net/projects/panama/\r\n" + "https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1\r\n"
		                + "https://openjdk.java.net/jeps/366\r\n"
		                + "https://www.nvidia.com/pl-pl/geforce/gaming-laptops/?nvid=nv-int-csfg-19993#cid=gf40_nv-int-csfg_en-us\r\n"
		                + "https://openjdk.java.net/jeps/323\r\n" + "https://www.nvidia.com/en-us/\r\n"
		                + "https://www.defence24.com/ibcs-in-production-phase-more-tests-soon\r\n" + "https://www.energa.pl/dom/oferty.html\r\n"
		                + "https://mkyong.com/java/what-is-new-in-java-11/\r\n"
		                + "https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList\r\n" + "https://www.energa.pl/dom\r\n"
		                + "https://www.nvidia.com/pl-pl/geforce/graphics-cards/30-series/\r\n"
		                + "https://www.energa.pl/dom/aktualnosci/news/zmiana-taryfy-Energa-Operator-2021\r\n"
		                + "https://www.defence24.com/polish-modernization-of-bwp-1-analysis\r\n" + "https://datatracker.ietf.org/doc/html/rfc3986\r\n"
		                + "https://www.nvidia.com/en-gb/design-visualization/desktop-graphics/\r\n" + "https://www.energa.pl/dom/oferty/prosument.html";


	
		for(ResponseEntity<String> response : getsResponses) {

			assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
			
			assertThat(response.getBody()).isEqualTo(expectedResponseStr);
		}
	}

	/*
	 * Requests to download pages are sent firstly. Then pages are
	 * downloaded to database.
	 */
	private void initializeDatabaseDataForOption(String opt) throws InterruptedException {

		MAX_ATTEMPTS = 15;

		testUrlsList = TestDataProducer.pageCollection(opt);

		int noOfLinks = testUrlsList.size();
		System.out.println("noOfLinks to be downloaded: " + noOfLinks);

		// sending links to be downloaded to database
		List<ResponseEntity<String>> collect = testUrlsList.stream().map(url->restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

		for(ResponseEntity<String> resp : collect) {

			assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.ACCEPTED);
		}

		Set<String> urlPageLinksSet = new HashSet<String>(testUrlsList);
		int distinctLinksSize = urlPageLinksSet.size();

		int successDownloadsNumber = 0;
		int i = 0;
		do {
			Thread.sleep(INTERVAL_MS);

			Iterator<String> urlsIter = urlPageLinksSet.iterator();

			while(urlsIter.hasNext()) {

				String url = urlsIter.next();

				Optional<CiUrlPK> urlPkOpt = Utils.parseAndValidateUrl(url);

				Optional<CiUrl> entityOpt = urlPkOpt.flatMap(pk->jpaRepo.findById(pk));

				if(entityOpt.isPresent()) {
					String contentOfThePage = entityOpt.map(ent->ent.getContent()).orElse("");

					if(!contentOfThePage.isEmpty()) {

						successDownloadsNumber++;
						urlsIter.remove();
					}
				}
			}

			i++;
		} while((i < MAX_ATTEMPTS) && (successDownloadsNumber < distinctLinksSize - 1));

		System.out.println("Number of links to download: " + distinctLinksSize);
		System.out.println("Number of downloaded pages:  " + successDownloadsNumber);

		if(successDownloadsNumber < distinctLinksSize) {

			System.out.println("WARNING not all links were retrieved, test may need redesign or update");
		}

		double inintervalMs = i * INTERVAL_MS / 1000;
		System.out.println("Download time: " + inintervalMs);
	}
}
