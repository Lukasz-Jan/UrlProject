package launcher.services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.inject.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import launcher.services.utils.UrlQueue;

@Service
public class UrlDownloadManager {

	private static final int MAX_WORKERS = 4;
	private BlockingQueue<String> urlQueue;
	private static ExecutorService exeSrv = Executors.newFixedThreadPool(MAX_WORKERS);

	@Autowired
        private Provider<DownloadWorker> workerProvider;

	/*
	 * Creates workers which download pages content to the base.
	 */

	@PostConstruct
	private void initialize() {
		
		urlQueue = UrlQueue.getQueue();
		
		DownloadWorker dWorkerTab[] = new DownloadWorker[MAX_WORKERS];

		for(int i = 0; i < MAX_WORKERS; i++) {	

			dWorkerTab[i] = workerProvider.get();
			dWorkerTab[i].setUrlQueue(urlQueue);
			dWorkerTab[i].setThreadNo(i);
			
			exeSrv.submit(dWorkerTab[i]);
		}		
	}
}
