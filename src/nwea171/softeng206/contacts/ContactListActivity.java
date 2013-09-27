package nwea171.softeng206.contacts;

import java.util.ArrayList;

import nwea171.softeng206.contacts.swipetodismiss.SwipeDismissListViewTouchListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

		// Adds onItemCickListener to the list of contacts
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

		// The following code in this method, is taken from a open source Github.
		// https://github.com/romannurik/android-swipetodismiss

		// Create a ListView-specific touch listener. ListViews are given special treatment because
		// by default they handle touches for their list items... i.e. they're in charge of drawing
		// the pressed state (the list selector), handling list item clicks, etc.
		SwipeDismissListViewTouchListener touchListener =
				new SwipeDismissListViewTouchListener(
						contactListView,
						new SwipeDismissListViewTouchListener.DismissCallbacks() {
							@Override
							public boolean canDismiss(int position) {
								return true;
							}

							@Override
							public void onDismiss(ListView listView, int[] reverseSortedPositions) {
								for (int position : reverseSortedPositions) {
									((ContactListAdapter) listAdapter).remove((Contact) listAdapter.getItem(position));
								}
								((ContactListAdapter) listAdapter).notifyDataSetChanged();
							}
						});
		contactListView.setOnTouchListener(touchListener);
		// Setting this scroll listener is required to ensure that during ListView scrolling,
		// we don't look for swipes.
		contactListView.setOnScrollListener(touchListener.makeScrollListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.contact_list, menu);


		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.add_new_contact:
			Intent intent = new Intent();
			intent.setClass(ContactListActivity.this, EditContactActivity.class);
			startActivity(intent);
		}
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
