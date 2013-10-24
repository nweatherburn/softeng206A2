package nwea171.softeng206.contactsapp;

import java.io.File;
import java.io.IOException;

import nwea171.softeng206.contacts.R;
import nwea171.softeng206.contactsapp.contacts.Contact;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class NewContactActivity extends Activity {
	
	/**
	 * Class to be used to edit or create a new contact.
	 * This Activity returns a contact when closed.
	 * 
	 * @author nwea171
	 */
	
	protected static final int TAKE_PHOTO = 0;
	protected static final int GET_PHOTO_FROM_GALLERY = 1;
	
	
	Button cancelButton, saveContactButton; // Buttons to save and cancel contact add
	ImageButton imageButton;  // Contact image
	Contact contact; // Contact of this class

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		
		// Set the name of the activity in the action bar.
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.contact_list_title);
		
		// Find the imageButton
		imageButton = (ImageButton) findViewById(R.id.contact_image_button);
		
		contact = (Contact) getIntent().getParcelableExtra(ContactListActivity.CONTACT); 
		loadValues(contact);
		
		// Add listener to the image button
		imageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Creates a dialog to choose from 
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
						choosePhotoIntent.putExtra("crop", "true");
						
						// Set dimensions of photo
						choosePhotoIntent.putExtra("outputX", 96);
						choosePhotoIntent.putExtra("outputY", 96);
						choosePhotoIntent.putExtra("aspectX",  1);
						choosePhotoIntent.putExtra("aspectY",  1);
						
						choosePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTemporaryFile()));
						choosePhotoIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
						
						startActivityForResult(choosePhotoIntent, GET_PHOTO_FROM_GALLERY);
					}
				});
				builder.show();
				
				
			}
		});

		// Add listeners to the buttons
		
		// Button to cancel add
		cancelButton = (Button) findViewById(R.id.cancel_add_contact);
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		
		// Save the contact
		saveContactButton = (Button) findViewById(R.id.save_contact);
		saveContactButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK, createContactIntent());
				finish();
			}
		});
	}
	
	/**
	 * Code adapted (taken) from stackoverflow
	 * User: http://stackoverflow.com/users/548218/jennifer
	 * http://stackoverflow.com/questions/2085003/how-to-select-and-crop-an-image-in-android
	 * @return
	 */
	private File getTemporaryFile() {
		// Creates a temporary file to store the image
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
	        File file = new File(Environment.getExternalStorageDirectory(), "TEMP_FILE.jpg");
	        try {
	            file.createNewFile();
	        } catch (IOException e) {
	        	
	        }
	        return file;
	    } else {
	        return null;
	    }
	}
	
	/**
	 * Load the values of the given contact into the edit text fields
	 */
	private void loadValues(Contact contact) {
		// Only adds the text to the text field if the image is not null.
		
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
		if (contact.getImage() != null) {
			imageButton.setImageBitmap(contact.getImage());
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
		
		// Gets image as bitmap
		Bitmap image = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
		
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
		i.putExtra(getString(R.string.image_prompt), image);
		return i;
		
	}
	
	/**
	 * Gets image from result
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PHOTO) {
			if (resultCode == RESULT_OK) {
				// Photo has been taken
				Bundle extras = data.getExtras();
				Bitmap image = (Bitmap) extras.get("data");
				imageButton.setImageBitmap(image);
			}
		} else if (requestCode == GET_PHOTO_FROM_GALLERY) {
			if (resultCode == RESULT_OK && data != null) {
				// If the result it OK and the data returned is not null
				// Get the file from the temporary image.
				
				String filePath= Environment.getExternalStorageDirectory() + "/TEMP_FILE.jpg";

                imageButton.setImageBitmap(BitmapFactory.decodeFile(filePath));
			}
		}
	}

	/**
	 * Creates the options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}
