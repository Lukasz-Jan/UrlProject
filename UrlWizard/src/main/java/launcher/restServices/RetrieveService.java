package launcher.restServices;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import entities.CiUrl;
import entities.CiUrlPK;
import launcher.Utils.Utils;
import launcher.repositories.UrlJpaRepo;

@RestController
public class RetrieveService {

	public static final Logger log = LoggerFactory.getLogger(RetrieveService.class);

	@Autowired
	private UrlJpaRepo jpaRepo;

	/*
	 * Retrieves one resource from database for url with fragment or with no
	 * fragment. Return all page as html to browser
	 * Example call
	 * http://localhost:8082/retrievePage/?url=https://datatracker.ietf.org/doc/html/rfc3986
	 */
	
	@GetMapping(path = "/retrievePage", produces = "text/html")
	public ResponseEntity<String> retrievePage(@RequestParam("url") Optional<String> urlOpt, @RequestParam(value = "fragment") Optional<String> fragOpt) {

		if (!urlOpt.isPresent()) {

			log.info("retrievePage no url");
			return new ResponseEntity<String>("wrong parameters in request", HttpStatus.BAD_REQUEST);
		}

		String url = urlOpt.get() + fragOpt.map(f -> "#" + f).orElse("");

		System.out.println(url);

		Optional<CiUrlPK> ciUrlPkOpt = Utils.createUrlPkDtoAfterUrlValidation(url);

		if (!ciUrlPkOpt.isPresent()) {

			return new ResponseEntity<String>("not found or wrong url parameter", HttpStatus.NOT_FOUND);
		} else {

			Optional<CiUrl> findById = jpaRepo.findById(ciUrlPkOpt.get());

			Optional<String> contentOpt = findById.map(ciUrl -> ciUrl.getContent());

			if (!contentOpt.isPresent()) {

				return new ResponseEntity<String>("content empty", HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<String>(contentOpt.get(), HttpStatus.OK);
		}
	}

	/* Example calls:
	 * http://localhost:8082/retrieveAll?page=1&size=4
	 * http://localhost:8082/retrieveAll?page=3&size=10
	 */

	@GetMapping(path = "/retrieveAll", produces = "text/plain")
	public ResponseEntity<String> retrieveAll(@RequestParam(value = "page", defaultValue = "0") String pageStr, @RequestParam(value = "size", defaultValue = "40") String sizeStr) {

		log.info("retrieveAll request");

		int pageNo;
		int size;
		try {
			pageNo = Integer.parseInt(pageStr); 
			size = Integer.parseInt(sizeStr);
		} catch (NumberFormatException e) {

			log.info("retrieveAll() parameters not of int type");
			return new ResponseEntity<String>("wrong parameters in request", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Page<CiUrl> page = jpaRepo.findAll(PageRequest.of(pageNo, size));

		if (!page.hasContent()) {
			
			log.info("No content");
			return new ResponseEntity<String>("No content for params", HttpStatus.NO_CONTENT);
		} else {
			
			StringBuilder allLinksStrB = new StringBuilder();
			page.forEach(el -> allLinksStrB.append(el.getId().getPath()).append(System.lineSeparator()));
			if (log.isDebugEnabled())
				log.debug(allLinksStrB.toString());
			
			return new ResponseEntity<String>(allLinksStrB.toString(), HttpStatus.OK);
		}
	}

	/*
	 * Retrieves two entries in json format by default ex:
	 * http://localhost:8082/retrieveJson, or retrieves by page and size params ex:
	 * http://localhost:8082/retrieveJson?page=0&size=2 Requires plug-in in chrome
	 * for json/ jason viewer.
	 */
	@GetMapping(path = "/retrieveJson")
	public Page<CiUrl> retrievePretty(@PageableDefault(page = 0, size = 2) Pageable pageable) {

		log.info("Json retrieve");
		return jpaRepo.findAll(pageable);
	}
}
