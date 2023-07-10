import java.util.ArrayList;

public class Order {
/*	order class is designed for holding only the items selected with different than null amount	for it	*/
	private ArrayList<Integer> amounts = new ArrayList<Integer>();				// array list for the amounts of selected items
	private ArrayList<Item> items = new ArrayList<Item>();						// array list for the selected items
	private String nameId = "";													// holds the nameID inserted by the user
	
	/*	empty constructor	*/
	public void addItem(Item item, int amount) {								// insert an item with it's amount to the order
		this.items.add(item);
		this.amounts.add(amount);
	}
	private int getFullPrice() {												// computes the full price of the order
		int sum = 0;
		for(int i=0; i<this.items.size();i++) {
			sum = sum + this.items.get(i).getPrice()*this.amounts.get(i);
		}
		return sum;
	}
	public Boolean setNameId(String input) {									// checks if the given nameID is valid and saves it
		int i;
		if (input == "" || input.isEmpty()) {									// check if empty reply
			return false;
		}
		if(input.length()<9) {													// check if length is too short
			return false;
		}
		for (i =0; i<input.length();i++) {										// get the first digit index
			if (Character.isDigit(input.charAt(i))) {
				break;
			}
		}
		if (!Character.isLetter(input.charAt(0))) {								// check if there is at least one letter at the beginning of the input
			return false;
		}
		if (input.substring(i, input.length()).length() != 9) {					// checks if the ID length is 9
	        return false;
	    }
	    for (int j = 0; j < input.substring(i, input.length()-1).length(); j++) {		// checks if all the characters in the ID segment are digits
	        if (!Character.isDigit(input.substring(i, input.length()-1).charAt(j))) {
	            return false;
	        }
	    }
		this.nameId = input;
		return true;
	}
	public String getNameID() {													// returns the nameID
		return this.nameId;
	}
	public void removeItem(Item item) {											// removes an item from the order
		this.amounts.remove(this.items.indexOf(item));
		this.items.remove(this.items.indexOf(item));
	}
	public void updateAmount(Item item, int newAmount) {						// update the amount of a certain item ALREADY in the order
		this.amounts.set(this.items.indexOf(item), newAmount);
	}
	public void clearOrder() {													// clears all the order attributes
		this.amounts.clear();
		this.items.clear();
		this.nameId = "";
	}
	public String desplayOrder() {												// translate the order into test and displays the final price
		String order = "";
		for(int i =0; i<this.items.size(); i++) {
			if(this.amounts.get(i) != 0) {
			order = order + this.items.get(i).toString() +".\nYou ordered " + this.amounts.get(i) + " units. Which is a total of " + this.amounts.get(i)*this.items.get(i).getPrice() + "$.\n";
			}
		}
		order = order + "Total to pay: " + getFullPrice() + "$";
		return order;
	}
	
}
