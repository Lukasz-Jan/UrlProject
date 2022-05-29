package launcher.restServices;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import launcher.Utils.UrlUtil;

@RestController
public class UrlServiceRS {

	public static final Logger log = LoggerFactory.getLogger(UrlServiceRS.class);
	private final AtomicLong getCounter = new AtomicLong();
	private final AtomicLong postCounter = new AtomicLong();

	@Autowired
	UrlUtil urlUtil;
	
	/*
	 *  POST request. If url is correct it is added to the queue. PK is created than.
	 */
	@PostMapping(path = "/postUrl", consumes = "text/plain")
	public ResponseEntity<String> postLinkByText(@RequestBody Optional<String> urlOpt) {

		log.info("POST Counter: " + postCounter.incrementAndGet());

		if(!urlOpt.isPresent()) {
			log.info("url parameter not valid");
			return new ResponseEntity<String>("url parameter not valid", HttpStatus.BAD_REQUEST);
		}
		
		if(urlOpt.get().isEmpty()) {
			
			log.info("url parameter is empty string");
			return new ResponseEntity<String>("url parameter is empty string", HttpStatus.BAD_REQUEST);			
		}		

		System.out.println(urlOpt.get());
		
		urlOpt.ifPresent(urlStr -> urlUtil.processUrl(urlStr));
		return new ResponseEntity<String>("processing", HttpStatus.ACCEPTED); 
	}

	
	
	/*
	 *  GET request. If url is correct it is added to the queue in GET. PK is created than. 
	 */	
	@GetMapping(path = "/url")
	public ResponseEntity<String> urlByGET(@RequestParam(name = "url") Optional<String> urlOpt) {

		log.debug("GET counter: " + getCounter.incrementAndGet());		
		
		if(!urlOpt.isPresent()) {
			log.info("url not valid");
			return new ResponseEntity<String>("url not valid", HttpStatus.BAD_REQUEST);
		}

		if(urlOpt.get().isEmpty()) {
			
			log.info("url is empty string");
			return new ResponseEntity<String>("query param empty", HttpStatus.BAD_REQUEST);			
		}
		
		urlOpt.ifPresent(urlStr -> urlUtil.processUrl(urlStr));
		
		return new ResponseEntity<String>("processing", HttpStatus.ACCEPTED);
	}
}
