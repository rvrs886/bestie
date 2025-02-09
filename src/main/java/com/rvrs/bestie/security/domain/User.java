package com.rvrs.bestie.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Audited
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String username;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@PrimaryKeyJoinColumn
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
	private PersonalInfo personalInfo;

	public User(String username, String password, PersonalInfo personalInfo) {
		this.username = username;
		this.password = password;
		this.personalInfo = personalInfo;
	}

	public User() {}

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(username, user.username) &&
				Objects.equals(password, user.password) &&
				Objects.equals(personalInfo, user.personalInfo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, personalInfo);
	}
}
