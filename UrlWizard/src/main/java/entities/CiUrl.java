package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CI_URL database table.
 * 
 */
@Entity
@Table(name = "CI_URL")
public class CiUrl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CiUrlPK id;

	@Lob
	@Column(name = "CONTENT")
	private String content;

	@Version
	@Column(name = "VERSION", nullable = false, precision = 19)
	private Long version;
	
	public CiUrl() {
	}

	public CiUrlPK getId() {
		return this.id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private CiUrl(CiUrlPK id) {
		this.id = id;
	}	

	public static class CiUrlBuilder {

		private String domainPart;
		private String path;
		private String proto;
	
		public CiUrlBuilder() { }

		public CiUrl build() {
		
			CiUrlPK id = new CiUrlPK.CiUrlPKBuilder().setDomainPart(this.domainPart).setPath(this.path).setProto(this.proto)
			        .build();
			return new CiUrl(id);
		}

		public CiUrl buildByPK(CiUrlPK pk) {			
			return new CiUrl(pk);
		}		
	}
}