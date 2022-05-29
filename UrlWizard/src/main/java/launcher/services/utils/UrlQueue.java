package launcher.services.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UrlQueue {

	//volatile private static ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<String>();
	volatile private static BlockingQueue<String> urlQueue = new LinkedBlockingQueue<String>();
	
	public static BlockingQueue<String> getQueue() {
		
		return urlQueue;
	}
}
