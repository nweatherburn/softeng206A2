package nwea171.softeng206.contacts;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class NewContactActivity extends Activity {
	
	Button cancelButton, saveContactButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

}
