package com.rvrs.bestie.security.domain;

import com.rvrs.bestie.core.audit.domain.Auditable;
import com.rvrs.bestie.core.participate.domain.ParticipateRequest;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.*;

@Entity
@Table(name = "customers")
@Audited
public class Customer extends User implements Auditable {

	@PrimaryKeyJoinColumn
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	private PersonalInfo personalInfo;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] image;

	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<ParticipateRequest> participateRequests = new HashSet<>();

	public Customer() {
		super(null, null);
	}

	public Customer(String username, String password, PersonalInfo personalInfo, byte[] image) {
		super(username, password);
		this.personalInfo = personalInfo;
		this.image = image;
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
				"id=" + getId() +
				", username='" + getUsername() + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Customer customer = (Customer) o;
		return Objects.equals(personalInfo, customer.personalInfo) && Objects.deepEquals(image, customer.image) && Objects.equals(participateRequests, customer.participateRequests);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), personalInfo, Arrays.hashCode(image), participateRequests);
	}
}
