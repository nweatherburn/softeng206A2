package nwea171.softeng206.contacts;

import java.io.Serializable;

@SuppressWarnings("serial") // To suppress complier warnings
public class Contact implements Serializable {
	
	/**
	 * Class to represent a single contact.
	 * 
	 * @author Nicholas Weatherburn
	 */
	
	private String firstName;
	private String surname;
	private String dateOfBirth;
	private String mobileNumber;
	private String homeNumber;
	private String workNumber;
	private String emailAddress;
	private String address;
	private String notes;
	
	public Contact() {
		this.firstName = "Nicholas";
		this.surname = "Weatherburn";
		this.dateOfBirth = "23/07/1992";
		this.mobileNumber = "02" + (int) (Math.random()*90000000+10000000);
		this.homeNumber = "091111111";
		this.workNumber = "092222222";
		this.emailAddress =  "nwea171@aucklanduni.ac.nz";
		this.address = "Example address";
		this.notes = "Example note";
				
	}
	
	
	/*
	 * Getters and Setters
	 */
	
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

}
