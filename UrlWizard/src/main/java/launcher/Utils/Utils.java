 package launcher.Utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.CiUrlPK;
import launcher.services.DownloaderImpl;

public class Utils {

	public static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static Optional<CiUrlPK> createUrlPkDtoAfterUrlValidation(String urlStr) {

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
		
		CiUrlPK pk = createPrimaryKeyForUrlEntity(url, uri);

		return Optional.ofNullable(pk);
	}
	
	private static CiUrlPK createPrimaryKeyForUrlEntity(URL url, URI uri) {
		
		CiUrlPK pk = new CiUrlPK.CiUrlPKBuilder().setDomainPart(uri.getAuthority()).setPath(url.toExternalForm())
			        .setProto(url.getProtocol()).build();
		return pk;
	}
}
