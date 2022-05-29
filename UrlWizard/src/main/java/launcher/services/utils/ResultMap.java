package launcher.services.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResultMap {

	private static final Map<Long, Set<String>> resultMap = new HashMap<Long, Set<String>>();

	public static void addResult(Long seqNo, String url) {

		synchronized(resultMap) {

			Set<String> linksSet = resultMap.get(seqNo);
			if(linksSet == null) {
				linksSet = new HashSet<String>();
				linksSet.add(url);
				resultMap.put(seqNo, linksSet);
			}
			linksSet.add(url);
		}
	}

	public static Set<String> getResultList(Long seqNo) {

		Set<String> set;
		synchronized(resultMap) {
			set = resultMap.get(seqNo);
		}
		return set;
	}

	public static void removeSearchForSequence(Long seqNo) {

		if(resultMap.containsKey(seqNo)) {

			synchronized(resultMap) {

				resultMap.remove(seqNo);
			}
		}
	}
}