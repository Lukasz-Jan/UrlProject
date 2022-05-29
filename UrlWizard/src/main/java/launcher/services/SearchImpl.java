package launcher.services;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import launcher.services.utils.ResultMap;
import launcher.services.utils.Sequence;
import launcher.repositories.UrlJpaRepo;
import launcher.services.interfaces.Search;
import launcher.services.utils.CompositeEl;

@Service
public class SearchImpl implements Search {

	public static final Logger log = LoggerFactory.getLogger(SearchImpl.class);

	private static final int MAX_WORKERS = 2;
	private ExecutorService exeSrv = Executors.newFixedThreadPool(MAX_WORKERS);
	volatile private BlockingQueue<CompositeEl> searchQueue = new LinkedBlockingQueue<CompositeEl>();	

	@Autowired
	private UrlJpaRepo jpaRepo;

	@PostConstruct
	private void construct() {

		SearchWorker sWorkerTab[] = new SearchWorker[MAX_WORKERS];

		for(int i = 0; i < MAX_WORKERS; i++) {
			sWorkerTab[i] = new SearchWorker(jpaRepo, searchQueue, i);
			exeSrv.submit(sWorkerTab[i]);
		}
	}

	@Override
	public Optional<Set<String>> search(String word, Long counter) {

		Sequence.addSearch(counter);
		searchQueue.add(new CompositeEl(counter, word));
		
		Optional<Set<String>> linksSetOpt = Optional.empty();
		boolean found = false;

		while(!found) {

			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			if(Sequence.isFinished(counter)) {
				
				log.info("Finished");
				linksSetOpt = Optional.ofNullable(ResultMap.getResultList(counter));
				found = true;
			}
		}		
				
		ResultMap.removeSearchForSequence(counter);
		return linksSetOpt;
	}

}
