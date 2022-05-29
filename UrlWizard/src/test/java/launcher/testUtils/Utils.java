package launcher.testUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entities.CiUrlPK;


public class Utils {

	public static final Logger log = LoggerFactory.getLogger(Utils.class);
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";	
	
	public static Optional<URL> fetchURL(String urlStr) {

		URL url = null;
		try {
			url = new URL(urlStr);
		} catch(MalformedURLException e1) {
			e1.printStackTrace();
		}
		return Optional.ofNullable(url);
	}	
	
	public static Optional<URLConnection> openConnection(URL url) {

		URLConnection connection = null;

		try {
			connection = url.openConnection();
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setRequestProperty("accept", "*/*");
			connection.connect();

		} catch(MalformedURLException me) {
			me.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(connection);
	}	
	
	public static Optional<CiUrlPK> parseAndValidateUrl(String urlStr) {

		URL url = null;
		URI uri = null;
		try {
			url = new URL(urlStr);
			uri = new URI(urlStr);
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
			return Optional.empty();
		}

		if (log.isDebugEnabled()) {
			log.debug("uri.getPath()        : " + uri.getPath());
			log.debug("uri.getRawPath()     : " + uri.getRawPath());
			log.debug("uri.getRawQuery()    : " + uri.getRawQuery());
			log.debug("uri.getScheme()      : " + uri.getScheme());
			log.debug("uri.getPath()       : " + url.getPath());
			log.debug("url.toExternalForm(): " + url.toExternalForm());
		}
		
		CiUrlPK pk = new CiUrlPK.CiUrlPKBuilder().setDomainPart(uri.getAuthority()).setPath(url.toExternalForm())
		        .setProto(url.getProtocol()).build();

		return Optional.ofNullable(pk);
	}	
}
