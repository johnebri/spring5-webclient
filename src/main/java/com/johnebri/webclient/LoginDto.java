package com.johnebri.webclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class LoginDto {
//	
//	private String EmailAddress;
//	private String Password;
//	private String SubscriberID;
//
//}

public class LoginDto {

	private String emailAddress;
	private String password;
	private String subscriberID;

	public LoginDto() {
	}

	public LoginDto(String emailAddress, String password, String subscriberID) {
		super();
		this.emailAddress = emailAddress;
		this.password = password;
		this.subscriberID = subscriberID;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubscriberID() {
		return subscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}

	@Override
	public String toString() {
		return "LoginDto [emailAddress=" + emailAddress + ", password=" + password + ", subscriberID=" + subscriberID
				+ "]";
	}

}
