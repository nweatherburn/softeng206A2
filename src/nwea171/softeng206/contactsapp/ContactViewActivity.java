package nwea171.softeng206.contactsapp;

import nwea171.softeng206.contacts.R;
import nwea171.softeng206.contactsapp.contacts.Contact;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactViewActivity extends Activity {
	
	Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_view);
		
		// Add contact information to Views;
		contact = (Contact) getIntent().getSerializableExtra(ContactListActivity.CONTACT_CLICKED); 
		
		// Make all fields will no 
		makeBlankFieldsVisible(contact, TextView.GONE);
		
		// Set EditText Fields to contacts values
		setTextValues(contact);
	}
	
	/**
	 * Makes all fields that have no value 
	 * @param contact
	 */
	private void makeBlankFieldsVisible(Contact contact, int vis) {
		// First Name and Surname are not set invisible for appearance reasons
		
		if (contact.getDateOfBirth() == null) {
			((TextView) findViewById(R.id.contact_date_of_birth)).setVisibility(vis);
		}
		if (contact.getMobileNumber() == null) {
			((TextView) findViewById(R.id.contact_mobile_number)).setVisibility(vis);
		}
		if (contact.getHomeNumber() == null) {
			((TextView) findViewById(R.id.contact_home_number)).setVisibility(vis);
		}
		if (contact.getWorkNumber() == null) {
			((TextView) findViewById(R.id.contact_work_number)).setVisibility(vis);
		}
		if (contact.getEmailAddress() == null) {
			((TextView) findViewById(R.id.contact_email)).setVisibility(vis);
		}
		if (contact.getAddress() == null) {
			((TextView) findViewById(R.id.contact_address)).setVisibility(vis);
		}
		if (contact.getNotes() == null) {
			((TextView) findViewById(R.id.contact_notes)).setVisibility(vis);
		}
	}
	
	/**
	 * Populates the EditText fields of this activity to the contacts details.
	 * @param contact
	 */
	private void setTextValues(Contact contact) {
		
		((TextView) findViewById(R.id.contact_first_name)).setText(contact.getFirstName());
		((TextView) findViewById(R.id.contact_surname)).setText(contact.getSurname());
		
		((TextView) findViewById(R.id.contact_date_of_birth)).setText(contact.getDateOfBirth());
		
		((TextView) findViewById(R.id.contact_mobile_number)).setText(contact.getMobileNumber());
		((TextView) findViewById(R.id.contact_home_number)).setText(contact.getHomeNumber());
		((TextView) findViewById(R.id.contact_work_number)).setText(contact.getWorkNumber());
		
		((TextView) findViewById(R.id.contact_email)).setText(contact.getEmailAddress());
		((TextView) findViewById(R.id.contact_address)).setText(contact.getAddress());
		((TextView) findViewById(R.id.contact_notes)).setText(contact.getNotes());
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.edit_contact:
			makeBlankFieldsVisible(contact, TextView.VISIBLE);
		
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_view, menu);
		return true;
	}

}
