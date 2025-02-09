package com.rvrs.bestie.security.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "personal_info")
@Audited
public class PersonalInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotEmpty
	@Column(nullable = false)
	private String firstName;

	@NotEmpty
	@Column(nullable = false)
	private String lastName;

	@NotNull
	@Column(nullable = false)
	private Gender gender;

	@NotNull
	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@NotEmpty
	@Column(nullable = false, unique = true)
	private String phoneNumber;

	@NotEmpty
	@Column(nullable = false, unique = true)
	private String email;

	@NotEmpty
	@Column(nullable = false)
	private String nationality;

	public PersonalInfo() {
	}

	public PersonalInfo(String firstName,
	                    String lastName,
						Gender gender,
	                    LocalDate dateOfBirth,
	                    String phoneNumber,
	                    String email,
	                    String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.nationality = nationality;
	}

	public UUID getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getNationality() {
		return nationality;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PersonalInfo that = (PersonalInfo) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(firstName, that.firstName) &&
				Objects.equals(lastName, that.lastName) &&
				gender == that.gender &&
				Objects.equals(dateOfBirth, that.dateOfBirth) &&
				Objects.equals(phoneNumber, that.phoneNumber) &&
				Objects.equals(email, that.email) &&
				Objects.equals(nationality, that.nationality);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, gender, dateOfBirth, phoneNumber, email, nationality);
	}
}
