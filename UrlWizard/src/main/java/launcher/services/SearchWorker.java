package launcher.services;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import entities.CiUrl;
import launcher.services.utils.ResultMap;
import launcher.services.utils.Sequence;
import launcher.repositories.UrlJpaRepo;
import launcher.services.utils.CompositeEl;

public class SearchWorker implements Runnable {

	public static final Logger log = LoggerFactory.getLogger(SearchWorker.class);

	private final int workerNo;
	private BlockingQueue<CompositeEl> searchQueue;
	private UrlJpaRepo jpaRepo;
	private boolean process = true;

	@Override
	public void run() {

		log.debug("Worker_" + workerNo + " start");
		
		while(process) {

				CompositeEl el = null;
				try {
					el = searchQueue.take();
				} catch(InterruptedException e) {

					log.info("SearchWorker_" + workerNo + " exception caught");
					e.printStackTrace();
				}
				String searchedWord = el.getWord();
				log.debug("SearchWorker_" + workerNo + searchedWord);
				searchForWord(searchedWord, el.getCounter());

				log.info("SearchWorker_" + workerNo + " end process " + el.getWord());
				
				Sequence.markFinished(el.getCounter());
		}

	}

	public SearchWorker(UrlJpaRepo jpaRepo, BlockingQueue<CompositeEl> searchQueue, int workerNo) {
		this.searchQueue = searchQueue;
		this.jpaRepo = jpaRepo;
		this.workerNo = workerNo;
	}

	private void searchForWord(String word, Long seq) {

		Pageable pageablePart = PageRequest.of(0, 100);
		Page<CiUrl> page = jpaRepo.findAll(pageablePart);
		processPage(page, word, seq);
		
		while(page.hasNext()) {
			
			pageablePart = page.nextPageable();
			page = jpaRepo.findAll(pageablePart);
			processPage(page, word, seq);
		}
	}
	
	private void processPage(Page<CiUrl> page, String word, Long seq) {

		page.forEach(ciUrl -> {
			
			Optional<String> contentOpt = Optional.ofNullable(ciUrl.getContent());

			contentOpt.ifPresent(contentStr-> {

				if(contentStr.contains(word)) {

					ResultMap.addResult(seq, ciUrl.getId().getPath());
				}
			});			
		});
	}
}