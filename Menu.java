import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class Menu extends Application{ 
/*	designed for holding all the information taken from a .txt file	*/
	private Item menuItems[];											// an array that holds all the available item to buy
	private Boolean fileReadIndicator;									// false - means that reading the file has failed and user chose to close program
	
	public Menu() {
		String input = "-1";											// -1 = retrying to open the file, -2 = for closing the program after file error
		while(input == "-1") {
			input = readFromFile();										// formated menu is inserted into parameter input
		}
		if(input != "-2") {
		fileReadIndicator = true;										// reading file was successful
		String[] parts = input.split("\n");								// split menu into individual lines 
		menuItems = new Item[parts.length];								// set the size of the Item array
    	for (int i =0; i < parts.length; i++) {							// insert the text fields
    		menuItems[i] = new Item();									// create a new item
    		for (int j = 0; j < 4; j++) {								// go over all the attributes of the item
    			updateItem(menuItems[i],j,menuFormat(parts[i], j));		// set the corresponding attribute in the matching item
    		}
    	}
    	sortItems();													// sort the menu items by type
		}
		else {
			fileReadIndicator = false;									// reading file has failed -> close program
		}
		
	}
	
	public Boolean getFileReadIndicator() {								// return the fileReadIndicator
		return this.fileReadIndicator;
	}
	
	public int getAmountOfItems() {										// return the amount of available items from the menu
		return this.menuItems.length;
	}
	
	public Item getItem(int i) {										// return a requested item by index
		if(i<0 || i>=this.menuItems.length ) {
			Item item = new Item();										// if index is out of bound return a null instance
			return item;
		}else {
			return this.menuItems[i];
		}
		
	}
	
	private void sortItems() {											// sort the menu items by type
		int index = 0;													// keep track of the sorted array index
		Item sortedItems[];												// array to save the ordered items in
		sortedItems = new Item[this.menuItems.length];					// initializing it's size
		for (int i = 0; i<4; i++) {										// for each item type
			for(int j = 0; j<this.menuItems.length; j++) {				// go over all the items
				
				switch(i) {
				case 0:{												// item type: First
					if(this.menuItems[j].getType().substring(1,this.menuItems[j].getType().length()-1).equals("First")) {
						sortedItems[index] = this.menuItems[j];
						index++;
					}
					break;
				}
				case 1:{												// item type: Main
					if(this.menuItems[j].getType().substring(1,this.menuItems[j].getType().length()-1).equals("Main")) {
						sortedItems[index] = this.menuItems[j];
						index++;
					}
					break;
				}
				case 2:{												// item type: Last
					if(this.menuItems[j].getType().substring(1,this.menuItems[j].getType().length()-1).equals("Last")) {
						sortedItems[index] = this.menuItems[j];
						index++;
					}
					break;
				}
				case 3:{												// item type: Drink
					if(this.menuItems[j].getType().substring(1,this.menuItems[j].getType().length() -1).equals("Drink")) {
						sortedItems[index] = this.menuItems[j];
						index++;
					}
					break;
				}
			}	// end of switch
			}	// end of second for
		}		// end of first for
		this.menuItems = sortedItems;						// replace the unsorted array with the sorted array
	}
	
	private String menuFormat(String st, int part) {		// divide the given row to it's components which represent the item attributes
		String[] parts = st.split("\\|");					// split the row
		return parts[part];									// return the requested part
	}
	
	private void updateItem(Item item, int i, String st) {	// updates the requested attribute of the given item
		switch(i) {
		case 0:{
			item.setName(st);								// update the name
			break;
		}
		case 1:{
			st = st.substring(1, st.length()-2);			// remove all non digit characters
			item.setPrice(Integer.parseInt(st));			// update the price
			break;
		}
		case 2:{
			item.setType(st);								// update the type
			break;
		}
		case 3:{
			item.setDescription(st);						// update description
			break;
		}
			}
	}
	
	private String readFromFile() {							// reads from a file and return the text
		String st = "";										// initialize the String to have nothing
		try {												// try to open the file
			Scanner input = new Scanner(new File("menu.txt"));
			while (input.hasNext()){						// go over all the elements in the file
				 st = st + " " + input.next();				// add a space between each element
				 if (st.charAt(st.length()-1) == ':') {		// check if the row is the header row
					 st = "";
					 continue;								// if so then skip it
				 }
				 if (st.charAt(st.length()-1) == '.' || st.charAt(st.length()-1) == '!') {
					 st = st + "\n";						// check end of row, if so add a new line character
				 }
				}
			input.close();									// close the file
			
		} catch (FileNotFoundException e) {					// catch an error
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.CONFIRMATION);				// raise an alert
	    	alert.setTitle("File Error");
	    	alert.setHeaderText("Can't open the file "+ '"'+"menu.txt"+'"'+", or it's empty.");
	    	alert.setContentText("Pressing OK will make the program try opening the file again. Pressing Cancle will terminate the program.");
	    	Optional<ButtonType> option = alert.showAndWait();
	    	if (option.get() == ButtonType.OK) {			// try opening the file again
	    		return "-1";
	    	}
	    	if (option.get() == ButtonType.CANCEL) {		// close the program
	    		return "-2";
	    	}
		}
		return st;
	}
	
	public void start(Stage stage) throws Exception{ 
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("Menu.fxml")); 
		Scene scene = new Scene(root); 
		stage.setTitle("Menu"); 
		stage.setScene(scene); 
		stage.show(); 
	} 
	
	public static void main(String[] args) { 
		launch(args);
		System.out.println();
	} 
}
