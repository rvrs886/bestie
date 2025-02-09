package com.rvrs.bestie.security.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

import java.util.Objects;

@Entity
@Table(name = "opeartors")
@Audited
public class Operator extends User {

	private Long accessLevel;

	private Operator() {}

	public Operator(String username, String password, PersonalInfo personalInfo, Long accessLevel) {
		super(username, password, personalInfo);
		this.accessLevel = accessLevel;
	}

	public Long getAccessLevel() {
		return accessLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Operator operator = (Operator) o;
		return Objects.equals(accessLevel, operator.accessLevel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), accessLevel);
	}
}
