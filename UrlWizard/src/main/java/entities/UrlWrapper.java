package entities;

import java.util.Optional;
import java.util.function.Function;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import entities.exceptions.CreateEntityException;
import entities.exceptions.FetchEntityException;
import entities.exceptions.UrlPkDtoBuildException;
import launcher.Utils.Utils;
import launcher.repositories.UrlJpaRepo;
import launcher.services.DownloadWorker;

/*
 * UrlWrapper is wrapper for CiUrl entity creation and entity operations.
 */
@Component
@Scope("prototype")
public class UrlWrapper {

	private static final Logger log = LoggerFactory.getLogger(DownloadWorker.class);
	private static final String FETCH_ENTITY_ERR = "Entity fetch error";
	private static final String FETCH_ENTITY_ERR_NULL = "Entity fetch error repository is null";
	private static final String CREATE_ENTITY_ERR = "Create entity error";
	private static final String PK_DTO_BUILD_ERR = "Error building dto for pK";
	
	@Autowired private UrlJpaRepo jpaRepo;

	private CiUrlPK pk;

	public UrlWrapper() {}

	public CiUrl createDto(String urlStr) {

		Optional<CiUrlPK> urlPKOpt = Utils.createUrlPkDtoAfterUrlValidation(urlStr);

		CiUrl urlDto = urlPKOpt.map(pK->this.setPK(pK)).map(buildDtoFncWrapper(ob->ob.buildDtoForPK())).orElse(null);

		return urlDto;
	}


	// transactional annotation runs well
	// Participating in existing transactions runs properly
	@Transactional(Transactional.TxType.REQUIRED)
	public CiUrl createOrFetchEntity(String urlStr) {

		Optional<CiUrlPK> urlPKOpt = Utils.createUrlPkDtoAfterUrlValidation(urlStr);

		if(urlPKOpt.isPresent()) {

			CiUrlPK urlPK = urlPKOpt.get();
			try {
				// fetch
				Optional<CiUrl> urlEntOpt = Optional.ofNullable(fetchEntityIfExist(urlPK));

				if(urlEntOpt.isPresent())
					return urlEntOpt.get();
				// create
				CiUrl urlEnt = createEntity(urlPK);
				return urlEnt;

			} catch(FetchEntityException | CreateEntityException e) {
				log.info(e.getMessage());
			}
		}
		return null;
	}

	private UrlWrapper setPK(CiUrlPK pk) {
		this.pk = pk;
		return this;
	}

	private CiUrl buildDtoForPK() throws UrlPkDtoBuildException {

		CiUrl urlDto = Optional.ofNullable(this.pk).map(pK->new CiUrl.CiUrlBuilder().buildByPK(this.pk)).orElseThrow(()->new UrlPkDtoBuildException(PK_DTO_BUILD_ERR));
		return urlDto;
	}

	private CiUrl fetchEntityIfExist(CiUrlPK pK) throws FetchEntityException {

		if(jpaRepo == null)
			throw new FetchEntityException(FETCH_ENTITY_ERR_NULL);
		try {

			return jpaRepo.findById(pK).map(url->url).orElse(null);
		} catch(RuntimeException e) {

			if(log.isDebugEnabled()) {
				e.printStackTrace();
			}
			throw new FetchEntityException(FETCH_ENTITY_ERR);
		}
	}

	private CiUrl createEntity(CiUrlPK pK) throws CreateEntityException {

		CiUrl urlDto = new CiUrl.CiUrlBuilder().buildByPK(pK);
		try {
			CiUrl urlEnt = jpaRepo.saveAndFlush(urlDto);
			return urlEnt;
		} catch(RuntimeException e) {
			throw new CreateEntityException(CREATE_ENTITY_ERR);
		}
	}

	private <T, R> Function<T, R> buildDtoFncWrapper(FunctionThrow<T, R> fncThrow) {

		Function<T, R> fnc = t-> {
			try {
				return fncThrow.apply(t);
			} catch(Exception e) {

				log.info(e.toString());
				e.printStackTrace();
			}
			return null;
		};
		return fnc;
	}
}
