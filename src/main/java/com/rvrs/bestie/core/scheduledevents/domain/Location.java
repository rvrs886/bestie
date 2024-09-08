package com.rvrs.bestie.core.scheduledevents.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Location {

	private String postCode;

	private String city;

	private String country;

	private String address;

	private String name;

	public Location() {
	}

	public Location(String postCode, String city, String country, String address, String name) {
		this.postCode = postCode;
		this.city = city;
		this.country = country;
		this.address = address;
		this.name = name;
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

	public String getCity() {
		return city;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return Objects.equals(postCode, location.postCode) && Objects.equals(city, location.city) && Objects.equals(country, location.country) && Objects.equals(address, location.address) && Objects.equals(name, location.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postCode, city, country, address, name);
	}

	@Override
	public String toString() {
		return "Location{" +
				"postCode='" + postCode + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", address='" + address + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
