package nwea171.softeng206.contacts;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListActivity extends Activity {

	ListView contactListView;
	ListAdapter listAdapter;

	ArrayList<Contact> contacts = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);

		contactListView = (ListView) findViewById(R.id.contact_listview);
		setupListView();
		
	}

	// Instantiates the values in the list view.
	private void setupListView() {
		// Instantiates 10 contacts for the listView.
		for (int i = 0; i < 10; i++) {
			contacts.add(new Contact());
		}
		

		listAdapter = new ContactListAdapter();
		contactListView.setAdapter(listAdapter);
		contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id) {

				Intent intent = new Intent();
				intent.putExtra("contact", contacts.get(clickedViewPosition));
				intent.setClass(ContactListActivity.this, ContactViewActivity.class);
				startActivity(intent);
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	private class ContactListAdapter extends ArrayAdapter<Contact> {

		ContactListAdapter() {
			super(ContactListActivity.this, android.R.layout.simple_list_item_1, contacts);
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			// Create a layout inflater to inflate our xml for each item in the list
			LayoutInflater inflater = (LayoutInflater) ContactListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// Inflate the list item layout. Keep a reference to the inflated view. Note there is no view root specified.
			View listItemView = inflater.inflate(R.layout.contact_list_item_layout, null);

			// Access view elements inside the view (note we must specify the parent to look in)
			TextView firstName = (TextView) listItemView.findViewById(R.id.first_name);
			TextView surname = (TextView) listItemView.findViewById(R.id.last_name);
			TextView number = (TextView) listItemView.findViewById(R.id.mobile_number);

			ImageView imageView = (ImageView) listItemView.findViewById(R.id.contact_image);

			Contact contact = contacts.get(position);
			firstName.setText(contact.getFirstName());
			surname.setText(contact.getSurname());
			number.setText(contact.getMobileNumber());

			//if (contact.getImage() != null) {
			//	imageView.setImageURI(contact.getImage());
			//}

			return listItemView;

		}
	}

}
