import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

public class MenuController {
/*	controller class used for controlling the JavaFX graphics	*/
	
    @FXML
    private GridPane grid;					// the grid on which all the text fields wiil be
	private TextField textFields[][];		// an array of text fields for positioning the menu and boxes on the screen
	private CheckBox check[];				// an array for all the check boxes
	private ComboBox combo[];				// an array for all the combo boxes
	private Menu menu;
	private int amounts[];					// an array for the amounts of each item the user has picked
	private Order order = new Order();		// current order
	@SuppressWarnings("unchecked")
	
	
	public void initialize() {				// initialize the graphics using the data in menu	
		menu = new Menu();
		if(menu.getFileReadIndicator()) {											// if reading from file was successful
		int itemCount = menu.getAmountOfItems();									// amount of lines is the amount of available items on the menu
		int gridWidth = (int)grid.getPrefWidth();									// get the grid width
		int size[] = {gridWidth / 10, gridWidth / 6,gridWidth / 5,gridWidth / 14,gridWidth / 12, 0 };	// arrange the width to hold all the items and boxes
		size [5] = gridWidth - size[4] - size[3] - size[2] - size[1] - size[0];		// compute the final size in the row be the remaining space
		textFields = new TextField[itemCount][4];									// set the size of the text field array
		check = new CheckBox[itemCount];											// set the size of the CheckBox array
		combo = new ComboBox[itemCount];											// set the size of the ComboBox array
		amounts = new int[itemCount];												// set the size of the items amounts array
		Integer[] options = {1,2,3,4,5,6,7};										// set the options for the combo boxes
    	for (int i =0; i < itemCount; i++) {										// insert the text fields
    		check[i] = new CheckBox();												// create a new instance of check box
    		grid.add(check[i], 0, i % itemCount);									// add the check box into the current row
    		combo[i] = new ComboBox<>(FXCollections.observableArrayList(options));	// create a new instance of combo box
    		grid.add(combo[i], 1, i % itemCount);									// add the combo box into the current row
    		combo[i].setDisable(true);												// disable the combo box until it's check box is been pressed
    		final int index = i; 													// create a final copy of i
    		check[i].setOnAction(new EventHandler<ActionEvent>() {					// action on check box pressed

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if (((CheckBox) event.getSource()).isSelected()) {				// check the value selected/not selected
						combo[index].setDisable(false);								// if selected then give permission the user to use the combo box
						order.addItem(menu.getItem(index),0);						// add the item selected to the order
					}
					else {
						combo[index].setValue(null);								// reset the value of the combo box
						combo[index].setDisable(true);								// if not selected then deny access to the combo box
						order.removeItem(menu.getItem(index));						// remove the item selected from the order
					}
				}
    			
    		});
    		combo[i].setOnAction(new EventHandler<ActionEvent>() {					// action on combo box selected option

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(((ComboBox) arg0.getSource()).getValue() != null) {			// if the value is null do nothing
					order.updateAmount(menu.getItem(index),  (int) ((ComboBox) arg0.getSource()).getValue());		// update the amount of the chosen item in the order
					}
				}
    			
    		});
    		for (int j = 0; j < 4; j++) {											// go over all the attributes of the item
    			
    			textFields[i][j] = new TextField();									// create a new instance
    			switch(j) {
    			case 0:{
    				textFields[i][j].setText(menu.getItem(i).getName());			// set the text to be the item name
    				break;
    			}
    			case 1:{
    				textFields[i][j].setText(menu.getItem(i).getPrice() + "$");		// set the text to be the item price
    				break;
    			}
    			case 2:{
    				textFields[i][j].setText(menu.getItem(i).getType());			// set the text to be the item type
    				break;
    			}
    			case 3:{
    				textFields[i][j].setText(menu.getItem(i).getDescription());		// set the text to be the item description
    				break;
    			}
    				}
    			textFields[i][j].setPrefSize(size[j+2], grid.getPrefHeight() / itemCount);		// text field size
    			grid.add(textFields[i][j], j+2, i % itemCount);									// text field position
    			textFields[i][j].setEditable(false);											// disable editing the text fields
    		}
    	}
		}
	}
	
    @FXML
    void orderPressed(ActionEvent event) {											// action on combo box selected option

    	if(menu.getFileReadIndicator()) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);						// raise an alert
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Choose an action:");
    	alert.setContentText("Confirm - Approve your order.\nEdit - Change your CURRENT order.\nDelete - Remove current order and start a new order.\nYour ordre is:\n" + order.desplayOrder());
    																				// create corresponding buttons
    	ButtonType confirmButton = new ButtonType("Confirm");
    	ButtonType editButton = new ButtonType("Edit");
    	ButtonType deleteButton = new ButtonType("Delete");
    																				// add buttons to the alert window
    	alert.getButtonTypes().setAll(confirmButton, editButton, deleteButton);
    																				// for each button it's on code
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.isPresent()) {
    	    if (result.get() == confirmButton) {
    	    	TextInputDialog dialog = new TextInputDialog();						// show an input dialog alert
    	    	dialog.setHeaderText("Please enter your name and ID (exemple: Yossi123456789)");
    	    	Optional<String> str = dialog.showAndWait();
    	    	if (str.isPresent()) {
    	    		if(!order.setNameId(str.get())) {								// if the input isn't valid
    	    		Boolean validNameID = true;
    	    		       while(validNameID)
    	    		       {
    	    		    	   Alert alertTwo = new Alert(AlertType.CONFIRMATION);	// ask to give a valid input in another dialog alert
    	    		    	   alertTwo.setTitle("Wrong Input");
    	    		    	   dialog.setHeaderText("Please enter a name following a 9 digit id number (exemple: Yossi123456789)");
    	    		    	   Optional<String> strTwo = dialog.showAndWait();
    	    		    	   if(order.setNameId(strTwo.get())) {					// if the input is valid exit loop
    	    		    		   validNameID = false;
    	    		    	   }
    	    		       }
    	    		}
    	    		    String text = order.desplayOrder();							// get full order description
   	    		        String fileName = order.getNameID() + ".txt";				// get the file name to save in it the order

   	    		        try {
   	    		            FileWriter writer = new FileWriter(fileName);			// try to create and write in a file
   	    		            writer.write(text);
   	    		            writer.close();
   	    		            // System.out.println("Text saved successfully to " + fileName);
   	    		        } catch (IOException e) {
   	    		        	Alert alertFile = new Alert(AlertType.CONFIRMATION);	// raise an alert
   	    		        	alertFile.setTitle("File Error");
   	    		        	alertFile.setHeaderText("An error occurred while trying to save the text to " + fileName);
   	    			    	Optional<ButtonType> option = alertFile.showAndWait();
   	    		        }
   	    		       resetOrder(order);											// resets the graphic menu and order
    	    	}
    	    } else if (result.get() == editButton) {
    	        // do nothing and close the window
    	    } else if (result.get() == deleteButton) {
    	    	resetOrder(order);													// resets the graphic menu and order
    	    	}
    	}
    	}
    }
    
    private void resetOrder(Order order) {											// resets the graphic menu and order
    	for (int i =0; i < menu.getAmountOfItems(); i++) {							// reseting the graphic menu
    		check[i].setSelected(false);
    		combo[i].setValue(null);
    		combo[i].setDisable(true);
    	}
    	order.clearOrder();															// clear the order
    	
    }

}
	

