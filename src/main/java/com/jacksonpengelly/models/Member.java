package com.jacksonpengelly.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Member {
	private int memberID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	private LocalDate joinDate;
	private boolean premium;
	private boolean active;
	private LocalDateTime creationDate;

	// empty constructor
	public Member() {

	}

	// constructor for adding new member
	public Member(String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	// constructor for loading from db
	public Member(int memberID, String firstName, String lastName, String email, String phoneNumber, String address,
			LocalDate joinDate, boolean premium, boolean active, LocalDateTime creationDate) {
		this.memberID = memberID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.joinDate = joinDate;
		this.premium = premium;
		this.active = active;
		this.creationDate = creationDate;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String toString() {
		return String.format("ID: %d | Name: %s %s | Email: %s | Phone: %s | Type: %s | Active: %s", memberID,
				firstName, lastName, email, phoneNumber, premium ? "Premium" : "Standard", active ? "Yes" : "No");
	}

	// getters
	public int getMemberID() {
		return memberID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public boolean isPremium() {
		return premium;
	}

	public boolean isActive() {
		return active;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	// setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
}
