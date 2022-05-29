package launcher.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.CiUrl;
import entities.CiUrlPK;

@Repository
public interface UrlJpaRepo extends JpaRepository<CiUrl, CiUrlPK> {


	
}
