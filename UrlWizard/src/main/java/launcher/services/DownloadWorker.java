package launcher.services;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import entities.CiUrl;
import entities.UrlWrapper;
import launcher.services.interfaces.Downloader;

@Component
@Scope("prototype")
public class DownloadWorker implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(DownloadWorker.class);
	// private ConcurrentLinkedQueue<String> urlQueue;
	private BlockingQueue<String> urlQueue;
	private int threadNo;

	@Autowired
	private Downloader downLoader;

	@Autowired
	private Provider<UrlWrapper> urlProvider;

	private boolean stopProcessing = false;

	@Override
	public void run() {

		log.info("DownloadWorker_" + threadNo);

		while(!stopProcessing) {

			String logUrl = null;
			try {

				String urlStr = urlQueue.take();
				logUrl = urlStr;
				log.info("Worker_" + threadNo + " processing     : " + urlStr);
				
				UrlWrapper urlWrapper = urlProvider.get();
				Optional<CiUrl> urlEntityOpt = Optional.ofNullable(urlWrapper.createOrFetchEntity(urlStr));
				urlEntityOpt.map(ent->downLoader.downloadContent(ent, urlStr)).orElseGet(()->printNoSuccessLog());
				
			} catch(InterruptedException | RuntimeException e) {

				log.info("Exception in download worker " + threadNo);
				e.printStackTrace();
			}
			finally {
				log.info("Worker_" + threadNo + " END processing : " + logUrl);
			}
		}
	}

	public void setUrlQueue(BlockingQueue<String> urlQueue) {

		this.urlQueue = urlQueue;
	}

	public void setThreadNo(int threadNo) {
		this.threadNo = threadNo;
	}

	private boolean printNoSuccessLog() {

		log.info("entity not created/fetched -- no download occurs");
		return false;
	}
}
