package nwea171.softeng206.contactsapp;

import java.io.FileNotFoundException;
import java.io.InputStream;

import nwea171.softeng206.contacts.R;
import nwea171.softeng206.contactsapp.contacts.Contact;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewContactActivity extends Activity {
	
	protected static final int TAKE_PHOTO = 0;
	protected static final int GET_PHOTO_FROM_GALLERY = 1;
	
	
	Button cancelButton, saveContactButton;
	ImageButton imageButton;
	Contact contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		
		contact = (Contact) getIntent().getSerializableExtra(ContactListActivity.CONTACT); 
		loadValues(contact);
		
		// Add listener to the image button
		imageButton = (ImageButton) findViewById(R.id.contact_image_button);
		imageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(NewContactActivity.this);
				builder.setTitle(R.string.add_contact_image_dialog_title);
				builder.setItems(R.array.add_photo_options, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int selection) {
						if (selection == TAKE_PHOTO) {
							takePhoto();
						} else if (selection == GET_PHOTO_FROM_GALLERY) {
							getPhotoFromGallery();
						}
					}
					
					private void takePhoto() {
						Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(takePhotoIntent, TAKE_PHOTO);
						
					}
					
					private void getPhotoFromGallery() {
						Intent choosePhotoIntent = new Intent(Intent.ACTION_PICK);
						choosePhotoIntent.setType("image/*");
						startActivityForResult(choosePhotoIntent, GET_PHOTO_FROM_GALLERY);
					}
				});
				builder.show();
				
				
			}
		});

		// Add listeners to the buttons
		cancelButton = (Button) findViewById(R.id.cancel_add_contact);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		
		saveContactButton = (Button) findViewById(R.id.save_contact);
		saveContactButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK, createContactIntent());
				finish();
			}
		});
	}
	
	/**
	 * Load the values of the given contact into the edit text fields
	 */
	private void loadValues(Contact contact) {
		if (contact.getFirstName() != null) {
			((EditText) findViewById(R.id.contact_first_name)).setText(contact.getFirstName());
		}
		if (contact.getSurname() != null) {
			((EditText) findViewById(R.id.contact_surname)).setText(contact.getSurname());
		}
		if (contact.getDateOfBirth() != null) {
			((EditText) findViewById(R.id.contact_date_of_birth)).setText(contact.getDateOfBirth());
		}
		if (contact.getMobileNumber() != null) {
			((EditText) findViewById(R.id.contact_mobile_number)).setText(contact.getMobileNumber());
		}
		if (contact.getHomeNumber() != null) {
			((EditText) findViewById(R.id.contact_home_number)).setText(contact.getHomeNumber());
		}
		if (contact.getWorkNumber() != null) {
			((EditText) findViewById(R.id.contact_work_number)).setText(contact.getWorkNumber());
		}
		if (contact.getEmailAddress() != null) {
			((EditText) findViewById(R.id.contact_email)).setText(contact.getEmailAddress());
		}
		if (contact.getAddress() != null) {
			((EditText) findViewById(R.id.contact_address)).setText(contact.getAddress());
		}
		if (contact.getNotes() != null) {
			((EditText) findViewById(R.id.contact_notes)).setText(contact.getNotes());
		}
	}
	
	
	/**
	 * Creates an intent will all the contacts values
	 * @return an intent with all contacts values
	 */
	private Intent createContactIntent() {
		// Get values from fields
		String firstName = ((EditText) findViewById(R.id.contact_first_name)).getText().toString();
		String surname = ((EditText) findViewById(R.id.contact_surname)).getText().toString();
		String mobileNumber = ((EditText) findViewById(R.id.contact_mobile_number)).getText().toString();
		String homeNumber = ((EditText) findViewById(R.id.contact_home_number)).getText().toString();
		String workNumber = ((EditText) findViewById(R.id.contact_work_number)).getText().toString();
		String DOB = ((EditText) findViewById(R.id.contact_date_of_birth)).getText().toString();
		String email = ((EditText) findViewById(R.id.contact_email)).getText().toString();
		String address = ((EditText) findViewById(R.id.contact_address)).getText().toString();
		String notes = ((EditText) findViewById(R.id.contact_notes)).getText().toString();
		
		Intent i = new Intent();
		// Put all contact values in the intent, null if no value has been entered
		i.putExtra(getString(R.string.first_name_prompt), firstName.length() == 0 ? null : firstName);
		i.putExtra(getString(R.string.surname_prompt), surname.length() == 0 ? null : surname);
		i.putExtra(getString(R.string.mobile_number_prompt), mobileNumber.length() == 0 ? null : mobileNumber);
		i.putExtra(getString(R.string.home_number_prompt), homeNumber.length() == 0 ? null : homeNumber);
		i.putExtra(getString(R.string.work_number_prompt), workNumber.length() == 0 ? null : workNumber);
		i.putExtra(getString(R.string.date_of_birth_prompt), DOB.length() == 0 ? null : DOB);
		i.putExtra(getString(R.string.email_address_prompt), email.length() == 0 ? null : email);
		i.putExtra(getString(R.string.address_prompt), address.length() == 0 ? null : address);
		i.putExtra(getString(R.string.notes_prompt), notes.length() == 0 ? null : notes);
		return i;
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PHOTO) {
			if (resultCode == RESULT_OK) {
				// Photo has been taken
				Bundle extras = data.getExtras();
				imageButton.setImageBitmap((Bitmap) extras.get("data"));
				// Add image to Contact
			}
		} else if (requestCode == GET_PHOTO_FROM_GALLERY) {
			if (resultCode == RESULT_OK) {
				try {
					Uri chosenImage = data.getData();
					InputStream imageStream = getContentResolver().openInputStream(chosenImage);
					imageButton.setImageBitmap(BitmapFactory.decodeStream(imageStream));
					// Add image to contact
				} catch (FileNotFoundException e) {
					// Shouldn't occur, error message just in case.
					Toast.makeText(this, "Error Occurred: Could not load image", Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}
