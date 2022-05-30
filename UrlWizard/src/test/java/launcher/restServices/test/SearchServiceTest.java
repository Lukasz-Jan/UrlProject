package launcher.restServices.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
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
import static org.hamcrest.CoreMatchers.*;

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

        private List<String> positivelyLoadedPages = new ArrayList<String>();

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
        @Test
        @Order(2)
        public void parallelSearchForOneWord() throws Exception {

                initializeDatabaseDataForOption("collectionWithDuplicates");
                final String searchedWord = "DOCTYPE";
                requestUrl = getEndPointPart + "searchForText/?word=" + searchedWord; // http://localhost:8082/searchForText/?word=DOCTYPE

                List<String> urlList = Collections.nCopies(20, requestUrl);

                // twenty parallel request are sent, all are the same, which
                // contains word DOCTYPE

                List<ResponseEntity<String>> getsResponses = urlList.stream().parallel().map(url -> restTemplate.getForEntity(url, String.class)).collect(Collectors.toList());
                
                for(ResponseEntity<String> response : getsResponses) {

                        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
                }

                for(ResponseEntity<String> response : getsResponses) {

                        for(String page : positivelyLoadedPages) {

                                String pageContent = getPageContent(page);
                                // has to check if every positevely loaded page contains searched word
                                if(pageContent.contains(searchedWord)) {
                                
                                        boolean contains = response.getBody().contains(page);
                                        assertThat(contains).isEqualTo(true);
                                } 
                        }
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
                List<ResponseEntity<String>> collect = testUrlsList.stream().map(url -> restTemplate.postForEntity(postEndPoint, url, String.class)).collect(Collectors.toList());

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
                                        String contentOfThePage = getPageContent(url);

                                        if(!contentOfThePage.isEmpty()) {

                                                successDownloadsNumber++;
                                                positivelyLoadedPages.add(url);
                                                urlsIter.remove();
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
        
        private String getPageContent(String pageUrl) {

                Optional<CiUrlPK> idOpt = Utils.parseAndValidateUrl(pageUrl);
                CiUrlPK id = idOpt.get();
                String pageContent = jpaRepo.findById(id).map(ent -> ent.getContent()).orElse("");
                return pageContent;
        }
        
}
