package nwea171.softeng206.contacts;

public class Contact {
	
	/**
	 * Class to represent a single contact.
	 * 
	 * @author Nicholas Weatherburn
	 */
	
	private String firstName;
	private String surname;
	private String mobileNumber;
	private String homeNumber;
	private String workNumber;
	private String emailAddress;
	private String address;
	private String notes;
	
	public Contact() {
		// No implementation as of yet.
	}
	
	
	/*
	 * Getters and Setters
	 */
	
	public String getFirstName() {
		//return firstName;
		return "Nicholas";
	}

	public String getSurname() {
		//return surname;
		return "Weatherburn";
	}

	public String getMobileNumber() {
		//return mobileNumber;
		return "02" + (int) (Math.random()*90000000+10000000);
	}

	public String getHomeNumber() {
		//return homeNumber;
		return "091111111";
	}

	public String getWorkNumber() {
		//return workNumber;
		return "092222222";
	}

	public String getEmailAddress() {
		//return emailAddress;
		return "nwea171@aucklanduni.ac.nz";
	}

	public String getAddress() {
		//return address;
		return "Example address";
	}

	public String getNotes() {
		//return notes;
		return "Example note";
	}

}
