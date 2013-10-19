package nwea171.softeng206.contactsapp;

import nwea171.softeng206.contacts.R;
import nwea171.softeng206.contactsapp.contacts.Contact;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ContactViewActivity extends Activity {
	
	Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_view);
		
		// Add contact information to Views;
		contact = (Contact) getIntent().getSerializableExtra(ContactListActivity.CONTACT_CLICKED); 
		
		((TextView) findViewById(R.id.contact_first_name)).setText(contact.getFirstName());
		((TextView) findViewById(R.id.contact_surname)).setText(contact.getSurname());
		
		// Makes the unused fields invisible
		if (contact.getDateOfBirth() == null) {
			((TextView) findViewById(R.id.contact_date_of_birth)).setVisibility(TextView.GONE);
		}
		if (contact.getMobileNumber() == null) {
			((TextView) findViewById(R.id.contact_mobile_number)).setVisibility(TextView.GONE);
		}
		if (contact.getHomeNumber() == null) {
			((TextView) findViewById(R.id.contact_home_number)).setVisibility(TextView.GONE);
		}
		if (contact.getWorkNumber() == null) {
			((TextView) findViewById(R.id.contact_work_number)).setVisibility(TextView.GONE);
		}
		if (contact.getEmailAddress() == null) {
			((TextView) findViewById(R.id.contact_email)).setVisibility(TextView.GONE);
		}
		if (contact.getAddress() == null) {
			((TextView) findViewById(R.id.contact_address)).setVisibility(TextView.GONE);
		}
		if (contact.getNotes() == null) {
			((TextView) findViewById(R.id.contact_notes)).setVisibility(TextView.GONE);
		}
		
		// Populates the textview of the contacts details
		((TextView) findViewById(R.id.contact_date_of_birth)).setText(contact.getDateOfBirth());
		((TextView) findViewById(R.id.contact_mobile_number)).setText(contact.getMobileNumber());
		((TextView) findViewById(R.id.contact_home_number)).setText(contact.getHomeNumber());
		((TextView) findViewById(R.id.contact_work_number)).setText(contact.getWorkNumber());
		((TextView) findViewById(R.id.contact_email)).setText(contact.getEmailAddress());
		((TextView) findViewById(R.id.contact_address)).setText(contact.getAddress());
		((TextView) findViewById(R.id.contact_notes)).setText(contact.getNotes());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_view, menu);
		return true;
	}

}
