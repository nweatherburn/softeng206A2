package nwea171.softeng206.contactsapp;

import java.io.FileNotFoundException;
import java.io.InputStream;

import nwea171.softeng206.contacts.R;
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
import android.widget.ImageButton;
import android.widget.Toast;

public class NewContactActivity extends Activity {
	
	protected static final int TAKE_PHOTO = 0;
	protected static final int GET_PHOTO_FROM_GALLERY = 1;
	
	Button cancelButton, saveContactButton;
	ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);
		
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
				// Code to save contact to relevant database
				finish();
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PHOTO) {
			if (resultCode == RESULT_OK) {
				// Photo has ben taken
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
