package launcher.restServices;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import launcher.services.interfaces.Search;

@RestController
public class SearchService {

	public static final Logger log = LoggerFactory.getLogger(SearchService.class);
	private final AtomicLong searchCounter = new AtomicLong();

	@Autowired
	private Search searcher;

	/*
	 * Example requests: http://localhost:8082/search/?word=disclosures
	 * http://localhost:8082/search/?word=DOCTYPE
	 * http://localhost:8082/search/?word
	 */

	//@GetMapping(path = "/searchForText", produces = "text/plain")
	@GetMapping(path = "/searchForText", produces = "text/plain")
	public ResponseEntity<String> search(@RequestParam(name = "word") Optional<String> wordParOpt) {

		if(!wordParOpt.isPresent()) {

			log.info("BAD_REQUEST");
			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}

		if(wordParOpt.get().isEmpty()) {

			log.info("EMPTY UNPROCESSABLE_ENTITY");
			return new ResponseEntity<String>("", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Long counter = searchCounter.getAndIncrement();

		log.info("counter         : " + counter);
		log.info("searched word   : " + wordParOpt.get());

		Optional<Set<String>> searchOpt = searcher.search(wordParOpt.get(), counter);

		if(!searchOpt.isPresent())
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);

		log.info("After search for pages with word " + wordParOpt.get());

		String returnContent = searchOpt.get().stream().collect(Collectors.joining(System.lineSeparator()));

		return new ResponseEntity<String>(returnContent, HttpStatus.OK);
	}
}