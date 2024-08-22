package com.rvrs.bestie.security.domain;

import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "customers")
public class Customer extends UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private PersonalInfo personalInfo;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", orphanRemoval = true)
	private Set<ParticipateRequest> participateRequests = new HashSet<>();

	public Customer() {
		super(null, null);
	}

	public Customer(String username, String password, PersonalInfo personalInfo, byte[] image) {
		super(username, password);
		this.personalInfo = personalInfo;
		this.image = image;
	}

	public UUID getId() {
		return id;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}

	public byte[] getImage() {
		return image;
	}

	public Set<ParticipateRequest> getParticipateRequests() {
		return participateRequests;
	}

	public void addParticipateRequest(ParticipateRequest participateRequest) {
		this.participateRequests.add(participateRequest);
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", username='" + username + '\'' +
				'}';
	}
}
