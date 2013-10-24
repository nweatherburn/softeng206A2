package nwea171.softeng206.contactsapp.contacts;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
	
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
	private Bitmap image;
	
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
		this.image = null;
	}
	
	public Contact(Parcel in) {
		id = in.readInt();
		firstName = in.readString();
		surname = in.readString();
		dateOfBirth = in.readString();
		mobileNumber = in.readString();
		homeNumber = in.readString();
		workNumber = in.readString();
		emailAddress = in.readString();
		address = in.readString();
		notes = in.readString();
		image = in.readParcelable(Bitmap.class.getClassLoader());
	}
	
	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(firstName);
		dest.writeString(surname);
		dest.writeString(dateOfBirth);
		dest.writeString(mobileNumber);
		dest.writeString(homeNumber);
		dest.writeString(workNumber);
		dest.writeString(emailAddress);
		dest.writeString(address);
		dest.writeString(notes);
		dest.writeParcelable(image, 0);
	}
	
	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {  
	    
        public Contact createFromParcel(Parcel in) {  
            return new Contact(in);  
        }  
   
        public Contact[] newArray(int size) {  
            return new Contact[size];  
        }  
          
    };  
	

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
	
	public Bitmap getImage() {
		return image;
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
	
	public void setImage(Bitmap image) {
		this.image = image;
	}
}
