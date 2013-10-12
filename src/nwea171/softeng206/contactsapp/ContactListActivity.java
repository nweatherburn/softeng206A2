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
import android.widget.Toast;

public class ContactListActivity extends Activity {
	
	public static final String CONTACT_CLICKED = "Contact Clicked";
	
	
	SwipeDismissList swipeList;
	ListView contactListView;	// ListView, will display contacts
	ContactListAdapter listAdapter;	// List Adapter

	ContactsList contacts = new ContactsList();

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
		        		// Will remove from the backing database
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
		// Instantiates 10 contacts for the listView.
		contacts.add(new Contact("Nicholas", "Weatherburn", "0211111111", null, null, null, null, null, null));
		contacts.add(new Contact("Karen", "Goedeke", "0222222222", null, null, null, null, null, null));
		contacts.add(new Contact("Michael", "Shafer", "0233333333", null, null, null, null, null, null));
		contacts.add(new Contact("Lauren", "Romano", "0244444444", null, null, null, null, null, null));
		contacts.add(new Contact("Chris", "Morgan", "0255555555", null, null, null, null, null, null));
		contacts.add(new Contact("Emma", "McMillan", "0266666666", null, null, null, null, null, null));
		contacts.add(new Contact("Anthony", "Wiseman", "0277777777", null, null, null, null, null, null));
		contacts.add(new Contact("Alice", "Burney", "0288888888", null, null, null, null, null, null));
		contacts.add(new Contact("Kit", "Adamson", "0299999999", null, null, null, null, null, null));
		contacts.add(new Contact("Marisa", "Kirkbride", "0200000000", null, null, null, null, null, null));

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
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.add_new_contact:
			Intent intent = new Intent();
			intent.setClass(ContactListActivity.this, NewContactActivity.class);
			startActivity(intent);
			break; // Break switch statement.
		case R.id.sort_contacts:
			AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
			builder.setTitle(R.string.sort_dialog_title);
			
			builder.setSingleChoiceItems(R.array.sort_options_array, -1, null);
			
			// Set the confirm button
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				   
		           public void onClick(DialogInterface dialog, int id) {
		        	   ListView lv = ((AlertDialog) dialog).getListView();
		        	   int position = lv.getCheckedItemPosition();
		               switch (position) {
		               case 0:
		            	   contacts.sortByFirstName(false);
		            	   break;
		               case 1:
		            	   contacts.sortBySurname(false);
		            	   break;
		               case 2:
		            	   contacts.sortByMobileNumber(false);
		            	   break;
		               }
		               listAdapter.notifyDataSetChanged();
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
	public void onStop() {
		super.onStop();
		swipeList.discardUndo();
	}

	private class ContactListAdapter extends ArrayAdapter<Contact> {

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
