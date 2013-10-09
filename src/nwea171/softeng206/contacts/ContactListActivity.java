package nwea171.softeng206.contacts;

import java.util.ArrayList;

import de.timroes.swipetodismiss.SwipeDismissList;
import de.timroes.swipetodismiss.SwipeDismissList.SwipeDirection;
import de.timroes.swipetodismiss.SwipeDismissList.UndoMode;
import de.timroes.swipetodismiss.SwipeDismissList.Undoable;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListActivity extends Activity {
	
	public static final String CONTACT_CLICKED = "Contact Clicked";

	ListView contactListView;	// ListView, will display contacts
	ContactListAdapter listAdapter;	// List Adapter

	ArrayList<Contact> contacts = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);

		contactListView = (ListView) findViewById(R.id.contact_listview);
		SwipeDismissList.OnDismissCallback callback = new SwipeDismissList.OnDismissCallback() {
		    public Undoable onDismiss(AbsListView listView, final int position) {
		        // Delete the item from your adapter (sample code):
		        final Contact itemToDelete = listAdapter.getItem(position);
		        listAdapter.remove(itemToDelete);
		        return  new SwipeDismissList.Undoable() {
		        	@Override
		            public void undo() {
		                // Return the item at its previous position again
		                listAdapter.insert(itemToDelete, position);
		            }
		        	
		        	@Override
		        	public String getTitle() {
		        		String undoMessage = itemToDelete.getFirstName() + " " + itemToDelete.getSurname() + " deleted.";
		        		return undoMessage;
		        	}
		        	@Override
		        	public void discard() {
		        		// TODO
		        	}
		        };
		    }
		};
		UndoMode mode = SwipeDismissList.UndoMode.SINGLE_UNDO;
		SwipeDismissList swipeList = new SwipeDismissList(contactListView, callback, mode);
		swipeList.setSwipeDirection(SwipeDirection.START);
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
				intent.putExtra(CONTACT_CLICKED, contacts.get(clickedViewPosition));
				intent.setClass(ContactListActivity.this, ContactViewActivity.class);
				startActivity(intent);
			}

		});

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
			intent.setClass(ContactListActivity.this, NewContactActivity.class);
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
