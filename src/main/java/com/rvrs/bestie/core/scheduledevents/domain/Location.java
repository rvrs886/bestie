package com.rvrs.bestie.core.scheduledevents.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Location {

	private String postCode;

	private String country;

	private String address;

	public Location() {
	}

	public Location(String postCode, String country, String address) {
		this.postCode = postCode;
		this.country = country;
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getCountry() {
		return country;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return Objects.equals(postCode, location.postCode) && Objects.equals(country, location.country) && Objects.equals(address, location.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postCode, country, address);
	}

	@Override
	public String toString() {
		return "Location{" +
				"postCode='" + postCode + '\'' +
				", country='" + country + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
