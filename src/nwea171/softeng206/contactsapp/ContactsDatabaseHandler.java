package nwea171.softeng206.contactsapp;

import nwea171.softeng206.contactsapp.contacts.Contact;
import nwea171.softeng206.contactsapp.contacts.ContactsList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactsDatabaseHandler extends SQLiteOpenHelper {
	
	// Database Version
	static final int DATABASE_VERSION = 1;
	
	// Database Name
	static final String DATABASE_NAME = "Contacts Manager";
	
	// Contacts table name
	static final String TABLE_CONTACTS = "contacts";
	
	// Contact information
	static final String CONTACT_ID = "id";
	static final String CONTACT_FIRST_NAME = "first_name";
	static final String CONTACT_SURNAME = "surname";
	static final String CONTACT_MOBILE_NUMBER = "mobile_number";
	static final String CONTACT_HOME_NUMBER = "home_number";
	static final String CONTACT_WORK_NUMBER = "work_number";
	static final String CONTACT_DOB = "date_of_birth";
	static final String CONTACT_ADDRESS = "address";
	static final String CONTACT_EMAIL = "email";
	static final String CONTACT_NOTES = "notes";
	
	static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
			+ CONTACT_ID + " INTEGER PRIMARY KEY,"
			+ CONTACT_FIRST_NAME + " TEXT,"
			+ CONTACT_SURNAME + " TEXT,"
			+ CONTACT_MOBILE_NUMBER + " TEXT,"
			+ CONTACT_HOME_NUMBER + " TEXT,"
			+ CONTACT_WORK_NUMBER + " TEXT,"
			+ CONTACT_DOB + " TEXT,"
			+ CONTACT_EMAIL + " TEXT,"
			+ CONTACT_ADDRESS + " TEXT,"
			+ CONTACT_NOTES + " TEXT);";
	
	
	/**
	 * Constructor
	 */
	public ContactsDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("ON CREATE", "ON CREATE");
		db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		onCreate(db);
	}
	
	/**
	 * Add a contact to the database
	 * @param contact The Contact to add
	 */
	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Get a ContentValues object holding the values of the contact
		ContentValues values = getValues(contact);
		
		// Insert Row
		Log.d("HELLO", "THIS HAPPENED");
		db.insert(TABLE_CONTACTS, null, values);
		db.close();
	}
	
	/**
	 * Returns a contact at given ID
	 * 
	 * @param id The ID of the contact whose data to retrieve
	 * @return contact
	 */
	public Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_CONTACTS, 
				new String[] { CONTACT_ID, CONTACT_FIRST_NAME, CONTACT_SURNAME, CONTACT_MOBILE_NUMBER,
				CONTACT_HOME_NUMBER, CONTACT_WORK_NUMBER, CONTACT_DOB, CONTACT_EMAIL, CONTACT_ADDRESS, CONTACT_NOTES },
				"=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Contact contact = new Contact(
				Integer.parseInt(cursor.getString(0)),
				cursor.getString(1),
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4),
				cursor.getString(5),
				cursor.getString(6),
				cursor.getString(7),
				cursor.getString(8),
				cursor.getString(9));
		return contact;
	}
	
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = getValues(contact);
	    
	   	int result = db.update(TABLE_CONTACTS, values, CONTACT_ID + " = ?",
	            new String[] { String.valueOf(contact.getID()) });
	   	db.close();
	    // updating row
	    return result;
	}
	/**
	 * Delete a contact from the database
	 * @param contact Delete a contact from the database
	 */
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		
	    db.delete(TABLE_CONTACTS, CONTACT_ID + " = ?",
	            new String[] { String.valueOf(contact.getID()) });
	    
	    db.close();
	}
	
	/**
	 * Gets all contacts in the database and returns a contacts list of those contacts
	 * @return ContactsList of all contacts
	 */
	public ContactsList getAllContacts() {
		ContactsList contacts = new ContactsList();
		
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// Loop through all contacts in the database and add them to the contacts list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact(
						Integer.parseInt(cursor.getString(0)),
						cursor.getString(1),
						cursor.getString(2),
						cursor.getString(3),
						cursor.getString(4),
						cursor.getString(5),
						cursor.getString(6),
						cursor.getString(7),
						cursor.getString(8),
						cursor.getString(9));
				
				contacts.add(contact);
			} while (cursor.moveToNext());
		}
		db.close();
		return contacts;
	}
	
	/** 
	 * Get a ContentValues object storing the contacts values
	 * @param contact
	 * @return ContentValues 
	 */
	private ContentValues getValues(Contact contact)  {
		ContentValues values = new ContentValues();
		
		values.put(CONTACT_ID, contact.getID());
		values.put(CONTACT_FIRST_NAME, contact.getFirstName());
		values.put(CONTACT_SURNAME, contact.getSurname());
		values.put(CONTACT_MOBILE_NUMBER, contact.getMobileNumber());
		values.put(CONTACT_HOME_NUMBER, contact.getWorkNumber());
		values.put(CONTACT_WORK_NUMBER, contact.getHomeNumber());
		values.put(CONTACT_DOB, contact.getDateOfBirth());
		values.put(CONTACT_EMAIL, contact.getEmailAddress());
		values.put(CONTACT_ADDRESS, contact.getAddress());
		values.put(CONTACT_NOTES, contact.getNotes());
		
		return values;
	}
	
}
