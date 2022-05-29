package launcher.services.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sequence {

	private static final Map<Long, Boolean> resultMap = new ConcurrentHashMap<Long, Boolean>();
	
	public static void addSearch(Long counter) {
		
		resultMap.put(counter, false);
	}

	public static boolean isFinished(Long counter) {

		return resultMap.get(counter);		
	}

	public static boolean markFinished(Long counter) {

		return resultMap.replace(counter, true);		
	}	
}
