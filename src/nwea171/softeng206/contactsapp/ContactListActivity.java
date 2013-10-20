package nwea171.softeng206.contactsapp;

import nwea171.softeng206.contacts.R;
import nwea171.softeng206.contactsapp.contacts.Contact;
import nwea171.softeng206.contactsapp.contacts.ContactsList;
import de.timroes.swipetodismiss.SwipeDismissList;
import de.timroes.swipetodismiss.SwipeDismissList.SwipeDirection;
import de.timroes.swipetodismiss.SwipeDismissList.UndoMode;
import de.timroes.swipetodismiss.SwipeDismissList.Undoable;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends Activity {
	
	public static final String CONTACT_CLICKED = "Contact Clicked";
	
	// Details for shared preferences
	private final String PREFERENCES_NAME = "Contacts App Prefences";
	private final String CONTACT_ID = "Unique Contact ID";
	private final String SORT_ORDER = "Sort Order";
	
	// Code for creating new contact on startActivityForResult
	private final int NEW_CONTACT_CODE = 0;
	
	
	SwipeDismissList swipeList;  // SwipeList
	ListView contactListView;  // ListView, will display contacts
	ContactListAdapter listAdapter;	// List Adapter
	ContactsDatabaseHandler dbHandler; // Database Handler

	ContactsList contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		
		dbHandler = new ContactsDatabaseHandler(ContactListActivity.this);

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
		        		dbHandler.deleteContact(itemToDelete);
		        	}
		        };
		    }
		};
		
		UndoMode mode = SwipeDismissList.UndoMode.SINGLE_UNDO;
		swipeList = new SwipeDismissList(contactListView, callback, mode);
		swipeList.setSwipeDirection(SwipeDirection.START);
		
		// Create the list view and adapter
		setupListView();
	}

	// Instantiates the values in the list view.
	private void setupListView() {
		
		/*// Instantiates 10 contacts for the listView.
		contacts.add(new Contact(getNextUniqueID(), "Nicholas", "Weatherburn", "0211111111", "094807169", null, "23/07/1992", "nwea171@aucklanduni.ac.nz", "17 Clarence Rd Northcote Pt", "This guy is one awesome, awesome person! He really is the best guy around."));
		contacts.add(new Contact(getNextUniqueID(), "Karen", "Goedeke", "0222222222", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Michael", "Shafer", "0233333333", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Lauren", "Romano", "0244444444", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Chris", "Morgan", "0255555555", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Emma", "McMillan", "0266666666", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Anthony", "Wiseman", "0277777777", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Alice", "Burney", "0288888888", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Kit", "Adamson", "0299999999", null, null, null, null, null, null));
		contacts.add(new Contact(getNextUniqueID(), "Marisa", "Kirkbride", "0200000000", null, null, null, null, null, null));
		
		for (int i = 0; i < contacts.size(); i++) {
			dbHandler.addContact(contacts.get(i));
		}*/
		
		contacts = dbHandler.getAllContacts();
		sort(contacts); // Sorts the contacts

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
	
	/**
	 * Gets the next unique ID from preferences, and increments that unique ID.
	 */
	public int getNextUniqueID() {
		SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		int id = settings.getInt(CONTACT_ID, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(CONTACT_ID, id + 1); // Increments the next unique ID.
		editor.apply(); // Apply changes
		
		return id;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.contact_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.add_new_contact:
			Intent intent = new Intent();
			intent.setClass(ContactListActivity.this, NewContactActivity.class);
			startActivityForResult(intent, NEW_CONTACT_CODE);
			break; // Break switch statement.
		case R.id.sort_contacts:
			AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
			builder.setTitle(R.string.sort_dialog_title);
			
			builder.setSingleChoiceItems(R.array.sort_options_array, -1, null);
			//builder.setMultiChoiceItems(R.array.sort_reverse_option, null, null);
			
			// Set the confirm button
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				   
		           public void onClick(DialogInterface dialog, int id) {
		        	   // Gets sort option and sorts contacts by selected order.
		        	   ListView lv = ((AlertDialog) dialog).getListView();
		        	   if (lv.getCheckedItemCount() == 0) {
		        		   return;
		        	   }
		        	   String selection = (String) lv.getAdapter().getItem(lv.getCheckedItemPosition());
		               sort(contacts, selection);
		               listAdapter.notifyDataSetChanged();
		               
		               // Update sort order in shared preferences
		               SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES_NAME, 0).edit();
		               editor.putString(SORT_ORDER, selection);
		               editor.apply();
		               
		               // Remove any undo dialogs that may be active.
		               swipeList.discardUndo();
		           }
		       });
			
			// Set the cancel button
			builder.setNegativeButton(R.string.cancel, null); // Nothing needs to be changed
			AlertDialog dialog = builder.create();
			dialog.show();
			break; // Break switch statement.
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which event this is responding to.
		Log.d("Result", ""+resultCode);
		switch (requestCode) {
		case NEW_CONTACT_CODE:
			if (resultCode == RESULT_OK) {
				Log.d("Result", ""+resultCode);
				Contact contact = new Contact(
						getNextUniqueID(),
						(String) data.getCharSequenceExtra(getString(R.string.first_name_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.surname_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.mobile_number_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.home_number_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.work_number_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.date_of_birth_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.email_address_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.address_prompt)),
						(String) data.getCharSequenceExtra(getString(R.string.notes_prompt)));
				contacts.add(contact); // Add the contact to the list of Contacts
				dbHandler.addContact(contact); // Add the contact to the database
				sort(contacts); // Sort the contacts list
				listAdapter.notifyDataSetChanged(); // Notify the listadapter that the dataset has changed
			}
			
		}
	}
	
	/**
	 * Sorts the contacts by the order already in shared preferences.
	 * Note, this does not call notifyDataSetChanged.
	 * @param contacts to be sorted
	 */
	private void sort(ContactsList contacts) {
		SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		sort(contacts, settings.getString(SORT_ORDER, getString(R.string.first_name)));
	}
	
	/**
	 * Sort the given contactList by the given order and updates shared preferences.
	 * Note, this does not call notifyDataSetChanged.
	 * @param contacts to be sorted
	 */
	private void sort(ContactsList contacts, String order) {
		// Sorts the contacts
		if (order.equals(getString(R.string.first_name))) {
     	   contacts.sortByFirstName(false);
        } else if (order.equals(getString(R.string.surname))) {
     	   contacts.sortBySurname(false);
        } else if (order.equals(getString(R.string.mobile_number))) {
     	   contacts.sortByMobileNumber(false);
        }
		
		// Update sort order in shared preferences
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES_NAME, 0).edit();
        editor.putString(SORT_ORDER, order);
        editor.apply();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		// Update database of any changes
		for (Contact contact: contacts) {
			dbHandler.updateContact(contact);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		swipeList.discardUndo();
	}

	private class ContactListAdapter extends ArrayAdapter<Contact> {
		
		/**
		 * ContactListAdapter used to adapt a List of contacts to be used in a ListView.
		 */

		ContactListAdapter() {
			super(ContactListActivity.this, android.R.layout.simple_list_item_1, contacts);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// Create a layout inflater to inflate our xml for each item in the list
			LayoutInflater inflater = (LayoutInflater) ContactListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// Inflate the list item layout. Keep a reference to the inflated view. Note there is no view root specified.
			View listItemView = inflater.inflate(R.layout.contact_list_item_layout, null);

			// Access view elements inside the view (note we must specify the parent to look in)
			TextView firstName = (TextView) listItemView.findViewById(R.id.first_name);
			TextView surname = (TextView) listItemView.findViewById(R.id.last_name);
			TextView number = (TextView) listItemView.findViewById(R.id.mobile_number);

			//ImageView imageView = (ImageView) listItemView.findViewById(R.id.contact_image);

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
