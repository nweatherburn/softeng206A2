package nwea171.softeng206.contactsapp;

import nwea171.softeng206.contacts.R;
import nwea171.softeng206.contactsapp.contacts.Contact;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactViewActivity extends Activity {
	
	/**
	 * Activity to edit contact
	 * 
	 * @author nwea171
	 */
	
	// Code for editing contact on startActivityForResult
	private final int EDIT_CONTACT_CODE = 0;
	
	Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_view);
		
		// Set the name of the activity in the action bar.
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.contact_list_title);
		
		// Add contact information to Views;
		contact = (Contact) getIntent().getParcelableExtra(ContactListActivity.CONTACT); 
		
		// Make all fields will no 
		changeFieldVisibility(contact, TextView.GONE, TextView.VISIBLE);
		
		// Set EditText Fields to contacts values
		setTextValues(contact);
	}
	
	/**
	 * Makes all fields that have no value  
	 * @param contact
	 */
	private void changeFieldVisibility(Contact contact, int emptyVis, int fullVis) {
		// First Name and Surname are not set invisible for appearance reasons
		
		if (contact.getDateOfBirth() == null) {
			((TextView) findViewById(R.id.contact_date_of_birth)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_date_of_birth)).setVisibility(fullVis);
		}
		
		if (contact.getMobileNumber() == null) {
			((TextView) findViewById(R.id.contact_mobile_number)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_mobile_number)).setVisibility(fullVis);
		}
		
		if (contact.getHomeNumber() == null) {
			((TextView) findViewById(R.id.contact_home_number)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_home_number)).setVisibility(fullVis);
		}
		
		if (contact.getWorkNumber() == null) {
			((TextView) findViewById(R.id.contact_work_number)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_work_number)).setVisibility(fullVis);
		}
		
		if (contact.getEmailAddress() == null) {
			((TextView) findViewById(R.id.contact_email)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_email)).setVisibility(fullVis);
		}
		
		if (contact.getAddress() == null) {
			((TextView) findViewById(R.id.contact_address)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_address)).setVisibility(fullVis);
		}
		
		if (contact.getNotes() == null) {
			((TextView) findViewById(R.id.contact_notes)).setVisibility(emptyVis);
		} else {
			((TextView) findViewById(R.id.contact_notes)).setVisibility(fullVis);
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
		
		// Only change image if image is not null
		if (contact.getImage() != null) {
			((ImageView) findViewById(R.id.contact_image)).setImageBitmap(contact.getImage());
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which event this is responding to.
		switch (requestCode) {
		case EDIT_CONTACT_CODE:
			if (resultCode == RESULT_OK) {
				
				// Load the edited values into the contact
				contact.setFirstName((String) data.getCharSequenceExtra(getString(R.string.first_name_prompt)));
				contact.setSurname((String) data.getCharSequenceExtra(getString(R.string.surname_prompt)));
				contact.setMobileNumber((String) data.getCharSequenceExtra(getString(R.string.mobile_number_prompt)));
				contact.setHomeNumber((String) data.getCharSequenceExtra(getString(R.string.home_number_prompt)));
				contact.setWorkNumber((String) data.getCharSequenceExtra(getString(R.string.work_number_prompt)));
				contact.setDateOfBirth((String) data.getCharSequenceExtra(getString(R.string.date_of_birth_prompt)));
				contact.setEmailAddress((String) data.getCharSequenceExtra(getString(R.string.email_address_prompt)));
				contact.setAddress((String) data.getCharSequenceExtra(getString(R.string.address_prompt)));
				contact.setNotes((String) data.getCharSequenceExtra(getString(R.string.notes_prompt)));
				contact.setImage((Bitmap) data.getParcelableExtra(getString(R.string.image_prompt)));
				
				changeFieldVisibility(contact, TextView.GONE, TextView.VISIBLE);
				
				setTextValues(contact);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.edit_contact:
			Intent intent = new Intent();
			intent.putExtra(ContactListActivity.CONTACT, contact);
			intent.setClass(ContactViewActivity.this, NewContactActivity.class);
			startActivityForResult(intent, EDIT_CONTACT_CODE);
			break; // Break switch statement.
		case R.id.delete_contact:
			AlertDialog.Builder builder = new AlertDialog.Builder(ContactViewActivity.this);
			
			// Create the title for the dialog
			String title = "Are you sure you wish to delete";
			if (contact.getFirstName() != null) {
				title += " " + contact.getFirstName();
			}
			if (contact.getSurname() != null) {
				title += " " + contact.getSurname();
			}
			if (contact.getFirstName() == null && contact.getSurname() == null) {
				title += " this contact";
			}
			title += "?";
			
			
			builder.setTitle(title);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				   
		           public void onClick(DialogInterface dialog, int id) {
		        	   finish(ContactListActivity.DELETE_CONTACT_CODE);
		           }
			});
			
			builder.setNegativeButton(R.string.cancel, null);
			builder.show();
			
		} 
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_view, menu);
		return true;
	}
	
	// Default finish
	@Override
	public void finish() {
		Intent i = new Intent();
		i.putExtra(ContactListActivity.CONTACT, contact);
		setResult(RESULT_OK, i);
		super.finish();
	}
	
	// Finish with a custom result
	public void finish(int result) {
		Intent i = new Intent();
		i.putExtra(ContactListActivity.CONTACT, contact);
		setResult(result, i);
		super.finish();
	}

}
