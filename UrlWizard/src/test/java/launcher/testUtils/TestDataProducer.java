package launcher.testUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataProducer {
	
	
	public static List<String> produceUrlListWithReapeats() {
		
		List<String> testUrlsList = new ArrayList<String>();
		
		testUrlsList.add("https://www.google.com/");
		testUrlsList.add("https://openjdk.java.net/projects/panama/");		
		//testUrlsList.add("https://www.defence24.com/ibcs-in-production-phase-more-tests-soon");
		testUrlsList.add("https://mkyong.com/java/what-is-new-in-java-11/");
		testUrlsList.add("https://openjdk.java.net/jeps/323");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://openjdk.java.net/jeps/361");
		testUrlsList.add("https://openjdk.java.net/jeps/366");		
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://www.google.com/");
		testUrlsList.add("https://www.nvidia.com/en-us/");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/gaming-laptops/?nvid=nv-int-csfg-19993#cid=gf40_nv-int-csfg_en-us");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/graphics-cards/30-series/");		
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/rtx/");		
		testUrlsList.add("https://www.nvidia.com/en-gb/design-visualization/desktop-graphics/");
		testUrlsList.add("https://www.energa.pl/dom");
		testUrlsList.add("https://www.energa.pl/dom/aktualnosci/news/zmiana-taryfy-Energa-Operator-2021");
		testUrlsList.add("https://www.energa.pl/dom/oferty.html");		
		testUrlsList.add("https://www.energa.pl/dom/oferty/prosument.html");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");		
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");
		//testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Jasrah.pdf");
		//testUrlsList.add("https://www.hapag-lloyd.com/en/ir/company/strategy-outlook.html");
		testUrlsList.add("https://viljasolutions.com/");		
		testUrlsList.add("https://viljasolutions.com/");
		testUrlsList.add("https://viljasolutions.com/");
		testUrlsList.add("http://localhost:8082/url/?url=https://www.defence24.com/polish-ministry-of-defence-four-uav-types-to-be-delivered-before-2022");
		testUrlsList.add("http://localhost:8082/url/?url=https://www.defence24.com/ibcs-in-production-phase-more-tests-soon");
		testUrlsList.add("https://www.kemira.com/");	
		testUrlsList.add("https://www.bbc.com/weather");
		//testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Salahuddin.pdf")
		
		return testUrlsList;
	}
	
	public static List<String> pageCollection(String pageSet) {

		switch(pageSet) {
			
		case "smallCollectionWithDuplicates": 
			return smallCollectionWithDuplicates();
		case "collectionWithDuplicates": 
			return collectionWithDuplicates();
		default:
			return smallCollectionWithDuplicates();
		}
	}
	
	
	public static List<String> smallCollectionWithDuplicates() {
	
		final List<String> testUrlsList = new ArrayList<String>();
		testUrlsList.add("https://openjdk.java.net/projects/panama/");		
		testUrlsList.add("https://www.defence24.com/ibcs-in-production-phase-more-tests-soon");
		testUrlsList.add("https://mkyong.com/java/what-is-new-in-java-11/");
		testUrlsList.add("https://openjdk.java.net/jeps/323");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://openjdk.java.net/jeps/361");
		testUrlsList.add("https://openjdk.java.net/jeps/366");		
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://www.google.com/");
		
		return testUrlsList;
	}
	

	public static List<String> collectionWithDuplicates() {
		
		final List<String> testUrlsList = new ArrayList<String>();
		
		testUrlsList.add("https://openjdk.java.net/projects/panama/");		
		testUrlsList.add("https://www.defence24.com/ibcs-in-production-phase-more-tests-soon");
		testUrlsList.add("https://mkyong.com/java/what-is-new-in-java-11/");
		testUrlsList.add("https://openjdk.java.net/jeps/323");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://openjdk.java.net/jeps/361");
		testUrlsList.add("https://openjdk.java.net/jeps/366");		
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://www.google.com/");
		testUrlsList.add("https://www.nvidia.com/en-us/");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/gaming-laptops/?nvid=nv-int-csfg-19993#cid=gf40_nv-int-csfg_en-us");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/graphics-cards/30-series/");		
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/rtx/");		
		testUrlsList.add("https://www.nvidia.com/en-gb/design-visualization/desktop-graphics/");
		testUrlsList.add("https://www.energa.pl/dom");
		testUrlsList.add("https://www.energa.pl/dom/aktualnosci/news/zmiana-taryfy-Energa-Operator-2021");
		testUrlsList.add("https://www.energa.pl/dom/oferty.html");		
		testUrlsList.add("https://www.energa.pl/dom/oferty/prosument.html");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");		
		//testUrlsList.add("https://www.hapag-lloyd.com/en/ir/company/strategy-outlook.html");
		testUrlsList.add("https://energa-ite.com.pl/");
		//testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Al_Jmeliyah.pdf");
		
		/*
		testUrlsList.add("https://www.hapag-lloyd.com/en/career/vacancies/job-search.html");		
		testUrlsList.add("https://www.hapag-lloyd.com/en/meta/sitemap.html");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_dahna.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Dahna.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_dhail.html");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_jasrah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Jasrah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Jasrah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_jmeliyah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Al_Jmeliyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Al_Jmeliyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_mashrab.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Mashrab.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Mashrab.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_murabba.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Murabba.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Murabba.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_nasriyah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Nasriyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Nasriyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/linah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Linah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Linah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/sajir.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Sajir_p.1%2b2.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Sajir.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/salahuddin.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Salahuddin.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Salahuddin.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/umm_qarn.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Umm_Qarn.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Umm_Qarn.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/ain_snan.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_AIN_SNAN.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_AIN_SNAN.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/alula.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Alula.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Alula.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_qibla.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Qibla.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Qibla.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_riffa.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Riffa.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Riffa.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/jebel_ali.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Jebel_Ali.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Jebel_Ali.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/malik_al_ashtar.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Malik_Al_Ashtar.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Malik_Al_Ashtar.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Tayma.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Tayma.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Tayma.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/umm_salal.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Umm_Salal.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Umm_Salal.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/unayzah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Unayzah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Unayzah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/antwerpen-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Antwerpen_Express(1).pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Antwerpen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/basle-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Basle_Express.PDF");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Basle_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/essen-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Essen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Essen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/hamburg-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_HAX.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_HAX.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/hong-kong-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Hong_Kong_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Hong_Kong_Express.pdf");		
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/leverkusen-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Leverkusen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Leverkusen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Ludwigshafen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC22802823604402_Ludwigshafen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/new-york-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_New_York_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISM_Certificate_New_York_Express.pdf");		
*/
		return testUrlsList;
	}
	
	
	public static List<String> pageSet3() {
		
		final List<String> testUrlsList = new ArrayList<String>();
		
		testUrlsList.add("https://openjdk.java.net/projects/panama/");		
		testUrlsList.add("https://www.defence24.com/ibcs-in-production-phase-more-tests-soon");
		testUrlsList.add("https://mkyong.com/java/what-is-new-in-java-11/");
		testUrlsList.add("https://openjdk.java.net/jeps/323");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://openjdk.java.net/jeps/361");
		testUrlsList.add("https://openjdk.java.net/jeps/366");		
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://www.google.com/");
		testUrlsList.add("https://www.nvidia.com/en-us/");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/gaming-laptops/?nvid=nv-int-csfg-19993#cid=gf40_nv-int-csfg_en-us");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/graphics-cards/30-series/");		
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/rtx/");		
		testUrlsList.add("https://www.nvidia.com/en-gb/design-visualization/desktop-graphics/");
		testUrlsList.add("https://www.energa.pl/dom");
		testUrlsList.add("https://www.energa.pl/dom/aktualnosci/news/zmiana-taryfy-Energa-Operator-2021");
		testUrlsList.add("https://www.energa.pl/dom/oferty.html");		
		testUrlsList.add("https://www.energa.pl/dom/oferty/prosument.html");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");		
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");
		//testUrlsList.add("https://www.hapag-lloyd.com/en/ir/company/strategy-outlook.html");
		testUrlsList.add("https://energa-ite.com.pl/");
		//testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Al_Jmeliyah.pdf");
		
		/*
		testUrlsList.add("https://www.hapag-lloyd.com/en/career/vacancies/job-search.html");		
		testUrlsList.add("https://www.hapag-lloyd.com/en/meta/sitemap.html");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_dahna.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Dahna.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_dhail.html");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_jasrah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Jasrah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Jasrah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_jmeliyah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Al_Jmeliyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Al_Jmeliyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_mashrab.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Mashrab.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Mashrab.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_murabba.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Murabba.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Murabba.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_nasriyah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Nasriyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Nasriyah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/linah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Linah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Linah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/sajir.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Sajir_p.1%2b2.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Sajir.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/salahuddin.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Salahuddin.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Salahuddin.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/umm_qarn.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Umm_Qarn.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Umm_Qarn.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/ain_snan.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_AIN_SNAN.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_AIN_SNAN.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/alula.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Alula.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Alula.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_qibla.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Qibla.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Qibla.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/al_riffa.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Al_Riffa.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Al_Riffa.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/jebel_ali.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Jebel_Ali.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Jebel_Ali.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/malik_al_ashtar.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Malik_Al_Ashtar.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Malik_Al_Ashtar.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Tayma.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Tayma.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Tayma.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/umm_salal.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Umm_Salal.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Umm_Salal.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/unayzah.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Unayzah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/pdf/SMC_Unayzah.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/antwerpen-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Antwerpen_Express(1).pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Antwerpen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/basle-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/charter/vessel_certificates/ISSC_Basle_Express.PDF");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Basle_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/essen-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Essen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Essen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/hamburg-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_HAX.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_HAX.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/hong-kong-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Hong_Kong_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Hong_Kong_Express.pdf");		
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/leverkusen-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Leverkusen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC_Leverkusen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_Ludwigshafen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/SMC22802823604402_Ludwigshafen_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/en/products/fleet/vessel/new-york-express.html");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISSC_New_York_Express.pdf");
		testUrlsList.add("https://www.hapag-lloyd.com/content/dam/website/downloads/fleet/vessel_certificates/ISM_Certificate_New_York_Express.pdf");		
*/
		return testUrlsList;
	}
	
	
	
	public static Map<String, String> produceUrlHashMap() {
		
		Map<String, String> testUrlsMap = new HashMap<String, String>();
		
		testUrlsMap.put("https://openjdk.java.net/projects/panama/", "native function calling from JVM");		
		testUrlsMap.put("https://www.defence24.com/ibcs-in-production-phase-more-tests-soon", "Defense Battle Command System IBCS");
		testUrlsMap.put("https://mkyong.com/java/what-is-new-in-java-11/", "New HTTP Client APIs");
		testUrlsMap.put("https://openjdk.java.net/jeps/323", "Alex Buckley");
		testUrlsMap.put("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1", "reference type");		
		testUrlsMap.put("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList", "The syntax for formal parameters");
		testUrlsMap.put("https://openjdk.java.net/jeps/361", "Switch expressions were");	
		testUrlsMap.put("https://openjdk.java.net/jeps/366", "XX:+UseParallelGC");
		testUrlsMap.put("https://www.nvidia.com/en-us/", "SHOP");
		testUrlsMap.put("https://www.nvidia.com/pl-pl/geforce/gaming-laptops/?nvid=nv-int-csfg-19993#cid=gf40_nv-int-csfg_en-us", "Druga generacja");
		testUrlsMap.put("https://www.nvidia.com/pl-pl/geforce/graphics-cards/30-series/", "funkcje SI");		
		testUrlsMap.put("https://www.nvidia.com/pl-pl/geforce/rtx/", "PRODUKTY");		
		testUrlsMap.put("https://www.nvidia.com/en-gb/design-visualization/desktop-graphics/", "NVIDIA Quadro");
		testUrlsMap.put("https://www.energa.pl/dom", "przez internet");
		testUrlsMap.put("https://www.energa.pl/dom/aktualnosci/news/zmiana-taryfy-Energa-Operator-2021", "OFERTA");
		testUrlsMap.put("https://www.energa.pl/dom/oferty.html", "należy do Ciebie");
		testUrlsMap.put("https://www.energa.pl/dom/oferty/prosument.html", "Sprawdź czy spełniasz warunki");
		testUrlsMap.put("https://www.defence24.com/polish-modernization-of-bwp-1-analysis", "structure design is to be born");
		testUrlsMap.put("https://datatracker.ietf.org/doc/html/rfc3986", "A Uniform Resource Identifier");		

		return testUrlsMap;
	}	

	public static List<String> produceUrlListWithReapeats_V1() {
		
		List<String> testUrlsList = new ArrayList<String>();
		
		testUrlsList.add("https://openjdk.java.net/projects/panama/");		
		testUrlsList.add("https://mkyong.com/java/what-is-new-in-java-11/");
		testUrlsList.add("https://openjdk.java.net/jeps/323");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-15.27.1");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://docs.oracle.com/javase/specs/jls/se9/html/jls-15.html#jls-InferredFormalParameterList");
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://openjdk.java.net/jeps/361");
		testUrlsList.add("https://openjdk.java.net/jeps/366");		
		testUrlsList.add("https://openjdk.java.net/jeps/366");
		testUrlsList.add("https://www.nvidia.com/en-us/");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/gaming-laptops/?nvid=nv-int-csfg-19993#cid=gf40_nv-int-csfg_en-us");
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/graphics-cards/30-series/");		
		testUrlsList.add("https://www.nvidia.com/pl-pl/geforce/rtx/");		
		testUrlsList.add("https://www.nvidia.com/en-gb/design-visualization/desktop-graphics/");
		testUrlsList.add("https://www.energa.pl/dom");
		testUrlsList.add("https://www.energa.pl/dom/aktualnosci/news/zmiana-taryfy-Energa-Operator-2021");
		testUrlsList.add("https://www.energa.pl/dom/oferty.html");		
		testUrlsList.add("https://www.energa.pl/dom/oferty/prosument.html");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://www.defence24.com/polish-modernization-of-bwp-1-analysis");
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");		
		testUrlsList.add("https://datatracker.ietf.org/doc/html/rfc3986");
		
		return testUrlsList;
	}
}
