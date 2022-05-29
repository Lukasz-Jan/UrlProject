package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CI_URL database table.
 * 
 */
@Embeddable
public class CiUrlPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "DOMAIN_PART", nullable = false, length = 64)
	private String domainPart;

	@Column(name = "PATH", nullable = false, length = 2084)
	private String path;

	@Column(name="PROTO", nullable = false, length = 5)
	private String proto;
	
	public CiUrlPK() {
	}

	public void setDomainPart(String domainPart) {
		this.domainPart = domainPart;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setProto(String proto) {
		this.proto = proto;
	}

	private CiUrlPK(String domainPart, String path, String proto) {
		this.domainPart = domainPart;
		this.path = path;
		this.proto = proto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CiUrlPK)) {
			return false;
		}
		CiUrlPK castOther = (CiUrlPK) other;
		return this.domainPart.equals(castOther.domainPart)
		        && this.path.equals(castOther.path)
		        && this.proto.equals(castOther.proto);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.domainPart.hashCode();
		hash = hash * prime + this.path.hashCode();
		hash = hash * prime + this.proto.hashCode();

		return hash;
	}
	
	public static class CiUrlPKBuilder {

		private String domainPart;
		private String path;
		private String proto;
		
		public CiUrlPKBuilder() {}

		public CiUrlPKBuilder setDomainPart(String domainPart) {
			this.domainPart = domainPart;
			return this;
		}

		public CiUrlPKBuilder setPath(String path) {
			this.path = path;
			return this;
		}

		public CiUrlPKBuilder setProto(String proto) {
			this.proto = proto;
			return this;
		}

		public CiUrlPK build() {
			return new CiUrlPK(this.domainPart, this.path, this.proto);
		}
	}
}