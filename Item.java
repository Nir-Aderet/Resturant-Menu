
public class Item {
/*	item class designed for keeping attributes of the item	*/
	private String name;
	private String type;
	private String description;
	private int price;
	
	public Item() {		// empty constructor
		
	}
	public void setName(String name) {					// set the name 
		this.name = name;
	}
	public void setType(String type) {					// set the type
		this.type = type;
	}
	public void setDescription(String description) {	// set the description
		this.description = description;
	}
	public void setPrice(int price) {					// set the price
		this.price = price;
	}
	public String getType() {							// return the type
		return this.type;
	}
	public String getDescription() {					// return the description
		return this.description;
	}
	public String getName() {							// return the name
		return this.name;
	}
	public int getPrice() {								// return the price
		return this.price;
	}
	public String toString() {							// translate the item into text
		return "Item name: " + this.name + ", price per unit: " + this.price + "$";
	}

}
