package launcher.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import entities.CiUrl;
import launcher.repositories.UrlJpaRepo;
import launcher.services.interfaces.Downloader;

@Component
@Scope("prototype")
public class DownloaderImpl implements Downloader {

	public static final Logger log = LoggerFactory.getLogger(DownloaderImpl.class);
	private static final int STRING_BUILDER_SIZE = 512_000;
	private static final String EMPTY_CONTENT_MESS = "Content is empty";

	@Autowired private UrlJpaRepo jpaRepo;
	
	public boolean downloadContent(CiUrl urlEnt, String urlStr) {

		Optional<String> pageHtmlContentOpt = Optional.empty();
		try {

			pageHtmlContentOpt = Optional.ofNullable(downloadContentInvoke(urlStr));
		} catch(IOException e) {
			log.info("Exception while downloading url" + urlStr);
			e.printStackTrace();
			return false;
		}

		pageHtmlContentOpt.ifPresent(contentString -> saveContentToUrlTable(urlEnt, contentString));
		return true;
	}

	private String downloadContentInvoke(String urlStr) throws IOException {

		URLConnection urlConnection = ConnectionProvider.provideConnection(urlStr);

		StringBuilder contentStrBld = new StringBuilder(STRING_BUILDER_SIZE);

		try(InputStream inputStream = urlConnection.getInputStream(); InputStreamReader inputStreamRead = new InputStreamReader(inputStream, "UTF-8"); BufferedReader br = new BufferedReader(inputStreamRead)) {

			String line = null;
			while((line = br.readLine()) != null) {

				if(log.isDebugEnabled())
					System.out.println(line);
				contentStrBld.append(line);
				contentStrBld.append(System.lineSeparator());
			}
		}

		return contentStrBld.toString();
	}

	private void saveContentToUrlTable(CiUrl urlEnt, String contentStr) {

		if(contentStr.isEmpty())
			log.warn("Content is empty");
		else {
			urlEnt.setContent(contentStr);
			jpaRepo.save(urlEnt);
		}
	}
}
