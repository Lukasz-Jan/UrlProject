package launcher.services;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionProvider {

	private static final String USER_AGENT_PROPERTY = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";
	private static final String ACCEPT_PROPERTY = "*/*";
	
	public static URLConnection provideConnection(String urlStr) throws IOException {

		URL url = new URL(urlStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", USER_AGENT_PROPERTY);
		connection.setRequestProperty("accept", ACCEPT_PROPERTY);
		return connection;
	}

}
