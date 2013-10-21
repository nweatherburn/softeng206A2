package nwea171.softeng206.contactsapp.contacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;


@SuppressWarnings("serial")
public class ContactsList extends ArrayList<Contact>{
	
	/**
	 * Class used to store a list of contacts
	 * 
	 * @author Nicholas Weatherburn
	 */

	
	/* *********************************
	 * 
	 * Sorting Methods below.
	 * 
	 ***********************************/
	
	/**
	 * Sort contacts by first name.
	 */
	public void sortByFirstName(boolean reverseSort) {
		Comparator<Contact> comparator = 
				ContactComparator.getComparator(ContactComparator.FIRST_NAME_SORT, 
												ContactComparator.SURNAME_SORT,
												ContactComparator.MOBILE_NUMBER_SORT);
		if (reverseSort) {
			comparator = ContactComparator.reverse(comparator);
		}
		
		Collections.sort(this, comparator); 
	}
	
	/**
	 * Sort contacts by surname.
	 */
	public void sortBySurname(boolean reverseSort) {
		Comparator<Contact> comparator = 
				ContactComparator.getComparator(ContactComparator.SURNAME_SORT, 
												ContactComparator.FIRST_NAME_SORT,
												ContactComparator.MOBILE_NUMBER_SORT);
		if (reverseSort) {
			comparator = ContactComparator.reverse(comparator);
		}
		
		Collections.sort(this, comparator); 
	}
	
	
	/**
	 * Sort contacts by mobile number.
	 */
	public void sortByMobileNumber(boolean reverseSort) {
		Comparator<Contact> comparator = 
				ContactComparator.getComparator(ContactComparator.MOBILE_NUMBER_SORT,
												ContactComparator.FIRST_NAME_SORT, 
												ContactComparator.SURNAME_SORT);
		if (reverseSort) {
			comparator = ContactComparator.reverse(comparator);
		}
		
		Collections.sort(this, comparator); 
	}
	
	/**
	 * Comparator used to compare contacts. Use getComparator method and pass in the order of other comparators for sorting.
	 * 
	 * @author Nicholas Weatherburn
	 */
	public enum ContactComparator implements Comparator<Contact> {
		FIRST_NAME_SORT {
			public int compare(Contact first, Contact second) {
				String firstName = first.getFirstName();
				String secondName = second.getFirstName();
				if (firstName == null && secondName == null) {
					return 0;
				} else if (firstName == null) {
					return 1;
				} else if (secondName == null) {
					return -1;
				}
				return firstName.compareTo(secondName);
			}
		},
		
		SURNAME_SORT {
			public int compare(Contact first, Contact second) {
				String firstName = first.getSurname();
				String secondName = second.getSurname();
				if (firstName == null && secondName == null) {
					return 0;
				} else if (firstName == null) {
					return 1;
				} else if (secondName == null) {
					return -1;
				}
				return firstName.compareTo(secondName);
			}
		},
		
		MOBILE_NUMBER_SORT {
			public int compare(Contact first, Contact second) {
				String firstNumber = first.getMobileNumber();
				String secondNumber = second.getMobileNumber();
				if (firstNumber == null && secondNumber == null) {
					return 0;
				} else if (firstNumber == null) {
					return 1;
				} else if (secondNumber == null) {
					return -1;
				}
				return firstNumber.compareTo(secondNumber);
			}
		};
		
		/**
		 * Returns a comparator that sorts the reverse of the input parameter.
		 * 
		 * @param Comparator to use to sort that will be reversed.
		 * @return Comparator that is the reverse of the input Comparator.
		 */
		public static Comparator<Contact> reverse(final Comparator<Contact> comparator) {
			return new Comparator<Contact>() {
				public int compare(Contact first, Contact second) {
	                return comparator.compare(second, first);
	            }
			};
		}
		
		/**
		 * Returns a comparator that sorts the contacts by the order given in the comparators input.
		 * 
		 * @param comparators The comparators in order of preference to sort by.
		 * @return Comparator that sorts contacts with primary, secondary, etc order.
		 */
		public static Comparator<Contact> getComparator(final ContactComparator... comparators) {
	        return new Comparator<Contact>() {
	            public int compare(Contact first, Contact second) {
	                for (ContactComparator comparator : comparators) {
	                    int result = comparator.compare(first, second);
	                    if (result != 0) {
	                        return result;
	                    }
	                }
	                return 0;
	            }
	        };
	    }
		
		
	}
	
}
