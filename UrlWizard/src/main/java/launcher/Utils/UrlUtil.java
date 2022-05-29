package launcher.Utils;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import entities.CiUrlPK;
import launcher.services.utils.UrlQueue;

@Service
public class UrlUtil {

	public static final Logger log = LoggerFactory.getLogger(UrlUtil.class);

	//private ConcurrentLinkedQueue<String> urlQueue;
	private BlockingQueue<String> urlQueue;

	@PostConstruct
	private void initialize() {

		urlQueue = UrlQueue.getQueue();
	}

	/*
	 * Process url for every post/get call. If url is
	 * correct it is added to the queue.
	 */
	
	public void processUrl(String urlStr) {

		Optional<CiUrlPK> urlPkOpt = Utils.createUrlPkDtoAfterUrlValidation(urlStr);

		urlPkOpt.ifPresent(urlPk-> urlQueue.add(urlStr));
		
		if(log.isDebugEnabled()) {
			log.debug("UrlUtil:processUrl adding " + urlStr + " to queue");
		}
		return;
	}
}
