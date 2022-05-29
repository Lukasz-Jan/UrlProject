package launcher.services.interfaces;

import entities.CiUrl;

public interface Downloader {

	boolean downloadContent(CiUrl urlEnt, String urlStr);
}
