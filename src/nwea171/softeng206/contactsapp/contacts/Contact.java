package nwea171.softeng206.contactsapp.contacts;

import java.io.Serializable;

@SuppressWarnings("serial") // To suppress complier warnings
public class Contact implements Serializable {
	
	/**
	 * Class to represent a single contact.
	 * 
	 * @author Nicholas Weatherburn
	 */
	
	
	private int id;
	private String firstName;
	private String surname;
	private String dateOfBirth;
	private String mobileNumber;
	private String homeNumber;
	private String workNumber;
	private String emailAddress;
	private String address;
	private String notes;
	
	
	/**
	 * Contact constructor not to be used except by the ContactBuilder.
	 */
	public Contact(int id,
			String firstName, 
			String surname, 
			String mobileNumber,
			String homeNumber,
			String workNumber,
			String dateOfBirth,
			String emailAddress,
			String address,
			String notes) { 

		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.mobileNumber = mobileNumber;
		this.homeNumber = homeNumber;
		this.workNumber = workNumber;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.address = address;
		this.notes = notes;
	}
	

	/* *********************************
	 * 
	 * Getters and setters.
	 * 
	 ***********************************/
	
	public int getID() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getHomeNumber() {
		return homeNumber;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public String getNotes() {
		return notes;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setHomeNumber(String homeNumber) {
		this.homeNumber  = homeNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
