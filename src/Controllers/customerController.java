package Controllers;

import Model.Customer;
import Utils.DBConnection;
import Utils.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static Controllers.loginController.user;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This is the customer controller class. You can add, update, and delete customer records
 */
public class customerController implements Initializable {
    /**
     * Text fields
     */
    @FXML
    private TextField customerIDText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField postalCodeText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneNumberText;

    /**
     * Tables and columns
     */
    @FXML
    private TableView<Customer> customerTable = new TableView<>();
    @FXML
    private TableColumn<Customer, Integer> customerID = new TableColumn<>();
    @FXML
    private TableColumn<Customer, String> customerName = new TableColumn<>();
    @FXML
    private TableColumn<Customer, String> address = new TableColumn<>();
    @FXML
    private TableColumn<Customer, String> postalCode = new TableColumn<>();
    @FXML
    private TableColumn<Customer, String> phone = new TableColumn<>();
    @FXML
    private TableColumn<Customer, Integer> divisionID = new TableColumn<>();

    /**
     * These are combo boxes
     */
    @FXML
    private ComboBox<String> countryList;
    @FXML
    private ComboBox<String> firstLevelDivisionList;
    @FXML
    private ComboBox<String> optionList;

    /**
     * The buttons
     */
    @FXML
    private Button addCustomer;
    @FXML
    private Button modifyCustomer;
    @FXML
    private Button deleteCustomer;
    @FXML
    private Button back;

    /**
     * observable collections
     */
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<String> divisions = FXCollections.observableArrayList();

    /**
     * These are strings arrays
     */
    String[] nations = {"U.S", "UK", "Canada"};
    String[] options = {"Delete", "Update", "Add"};
    ArrayList<String> firstA = new ArrayList<String>(); //These will store the divisions
    ArrayList<String> firstB = new ArrayList<String>();
    ArrayList<String> firstC = new ArrayList<String>();

    /**
     * Initializes the customer controller class
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Starts the connection to the database
        Connection con = DBConnection.startConnection();
        //Create a statement object
        try { DBQuery.setStatement(con); }
        catch (SQLException e) { e.printStackTrace(); }
        //Get statement object
        Statement statement = DBQuery.getStatement();
        //This populates the countries list.
        for(String i : nations) {
            countryList.getItems().add(i);
        }
        //This populates the options list.
        for(String i : options) {
            optionList.getItems().add(i);
        }
        //This populates the firstDivisionLevel list.
        for(int i = 0; i < 3; i++) {
            //SQL statement to get firstLevelDivision names from firstLevelDivisions
            String sqlStatement = "SELECT country_ID FROM countries WHERE country = '" + nations[i] + "'";

            //marks to help array strings
            String code;
            if (firstA.isEmpty()) { code = "red"; }
            else if (firstB.isEmpty()) { code = "yellow"; }
            else { code = "blue"; }
            //retrieving all the divisions and inserting them into their respective arraylists
            try {
                String id = String.valueOf(i + 1);
                String divisions = "SELECT division FROM first_level_divisions WHERE first_level_divisions.country_ID = " + id;

                ResultSet r = statement.executeQuery(divisions);
                String s;
                while(r.next()){
                    if(code == "red") {firstA.add(r.getString(1));}
                    if(code == "yellow") {firstB.add(r.getString(1));}
                    if(code == "blue") {firstC.add(r.getString(1));}
                }
            } catch (SQLException e) { e.printStackTrace(); }
        }

        //This pulls in the data for the customers from the database
        try {
            String test = "SELECT * FROM customers";
            ResultSet b = statement.executeQuery(test);
            while(b.next()) {
                int id = b.getInt(1);
                String name = b.getString(2);
                String address = b.getString(3);
                String code = b.getString(4);
                String phone = b.getString(5);
                Timestamp create = b.getTimestamp(6);
                String createBy = b.getString(7);
                Timestamp update = b.getTimestamp(8);
                String updateBy = b.getString(9);
                int divisionID = b.getInt(10);
                //creates customer and adds it to customers
                Customer customer = new Customer(id, name, address, code, phone,
                        create, createBy, update, updateBy, divisionID);
                customers.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //This displays the customers data in the table
        if(true) { // this if statement isn't necessary but I need this line to collapse
            customerTable.setItems(customers);
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            divisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        }
    }

    /**
     * This adds in a customer
     * @param event event
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws SQLException {
        //Starts the connection to the database
        Connection con = DBConnection.startConnection();

        //Create a statement object
        try {
            DBQuery.setStatement(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Get statement object
        Statement statement = DBQuery.getStatement();

        String option = optionList.getSelectionModel().getSelectedItem();
        if(!(option == "Add")) {
            infoBoxError("click add option", "ERROR");
            return;
        } else if(checkMark()) {
            //Takes in the variables
            String id = String.valueOf(getNewID());
            String name = nameText.getText();
            String address = addressText.getText();
            String postal = postalCodeText.getText();
            String phone = phoneNumberText.getText();
            String create = user.getUserName();
            String last = user.getUserName();
            Date date = new Date(); // This object contains the current date value
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = formatter.format(date).toString();
            String division = getDivisionID();
            //String for customer sql
            String customerSQL =
                    "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                            "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                            "Values ('" + id + "', '" + name + "', '" + address + "', '" + postal + "', '" + phone +
                            "', '" + now + "', '" + create + "', '" + now + "', '" + last + "', '" + division + "') ";
            //executes the string
            statement.executeUpdate(customerSQL);

            //creates the person so you can see it in the table
            int i = Integer.parseInt(id);
            Timestamp createDate = new Timestamp(date.getTime());
            Timestamp lastDate = new Timestamp(date.getTime());
            int d = Integer.parseInt(division);
            Customer customer = new Customer(i, name, address, postal, phone, createDate,
                    create, lastDate, last, d);

            customers.add(customer);
            clearText();
            infoBoxInformation("Customer has been added", "COMPLETED");
        }
    }

    /**
     * This deletes the customer
     * @param event event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException{

        String option = optionList.getSelectionModel().getSelectedItem();
        if(!(option == "Delete")) {
            infoBoxError("click delete option", "ERROR");
            return;
        } else if(!idExist(customerIDText.getText())) {
            infoBoxError("Customer ID does not exist", "ERROR");
            return;
        } else {
            //Starts the connection to the database
            Connection con = DBConnection.startConnection();

            //Create a statement object
            DBQuery.setStatement(con);

            //Get a statement object
            Statement statement = DBQuery.getStatement();

            //This deletes other appointments
            String appointmentSQL = "DELETE FROM appointments WHERE Customer_ID=" + customerIDText.getText();
            statement.executeUpdate(appointmentSQL);

            //This deletes the customer
            String customerSQL = "DELETE FROM customers WHERE Customer_ID=" + customerIDText.getText();
            statement.executeUpdate(customerSQL);

            for(Customer customer: customers) {
                String str = String.valueOf(customer.getCustomerID());
                if(str.equals(customerIDText.getText())) {
                    customers.remove(customer);
                    clearText();
                    customerTable.refresh();
                    infoBoxInformation("The customer has been successfully deleted", "COMPLETED");
                    return;
                }
            }
        }
    }

    /**
     * This modifies the customer
     * @param event event
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws SQLException {
        //Starts the connection to the database
        Connection con = DBConnection.startConnection();

        //Create a statement object
        DBQuery.setStatement(con);

        //Get a statement object
        Statement statement = DBQuery.getStatement();

        String option = optionList.getSelectionModel().getSelectedItem();
        if(!(option == "Update")) {
            infoBoxError("click update option", "ERROR");
            return;
        } else if(!idExist(customerIDText.getText())) {
            infoBoxError("Customer ID does not exist", "ERROR");
            return;
        } else {
            //These are the variables to be updated
            String name = nameText.getText();
            String address = addressText.getText();
            String postal = postalCodeText.getText();
            String phone = phoneNumberText.getText();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String last = formatter.format(date).toString();
            String lastUser = user.getUserName();
            String division = getDivisionID();


            String customerSQL = "UPDATE customers\n" +
                    "SET Customer_Name='" + name + "', Address='" + address + "', Postal_Code='" + postal +
                    "', Phone='" + phone + "', Last_Update='" + last + "', Last_Updated_By='" + lastUser +
                    "', Division_ID='" + division + "'\n" +
                    "WHERE Customer_ID='" + customerIDText.getText() + "'";
            statement.executeUpdate(customerSQL);

            for(Customer customer : customers) {
                if(String.valueOf(customer.getCustomerID()).equals(customerIDText.getText())) {
                    customer.setCustomerName(name);
                    customer.setAddress(address);
                    customer.setPostalCode(postal);
                    customer.setPhone(phone);
                    Timestamp lastDate = new Timestamp(date.getTime());
                    customer.setLastUpdate(lastDate);
                    customer.setLastUpdatedBy(user.getUserName());
                    customer.setDivisionID(Integer.parseInt(division));
                }
            }
            clearText();
            customerTable.refresh();
            infoBoxInformation("Customer has been updated", "COMPLETED");
        }
    }

    /**
     * This changes to either delete, update, or none
     * @param event event
     */
    @FXML
    void onActionOptions(ActionEvent event) {
        if(optionList.getSelectionModel().getSelectedItem() == "Update") {
            customerIDText.setDisable(false);
            nameText.setDisable(false);
            postalCodeText.setDisable(false);
            addressText.setDisable(false);
            phoneNumberText.setDisable(false);
            countryList.setDisable(false);
            firstLevelDivisionList.setDisable(false);
            clearText();
        }else if(optionList.getSelectionModel().getSelectedItem() == "Delete") {
            customerIDText.setDisable(false);
            nameText.setDisable(true);
            postalCodeText.setDisable(true);
            addressText.setDisable(true);
            phoneNumberText.setDisable(true);
            countryList.setDisable(true);
            firstLevelDivisionList.setDisable(true);
            clearText();
        } else {
            customerIDText.setDisable(true);
            nameText.setDisable(false);
            postalCodeText.setDisable(false);
            addressText.setDisable(false);
            phoneNumberText.setDisable(false);
            countryList.setDisable(false);
            firstLevelDivisionList.setDisable(false);
            clearText();
        }
    }

    /**
     * This helps which firstLevelDivision pop up in accordance to the country
     */
    @FXML
    void onActionChangeDivision() {
        //clears the list
        firstLevelDivisionList.getItems().clear();
        if(countryList.getValue() == "U.S") { for(String i : firstA) { firstLevelDivisionList.getItems().add(i); } }
        if(countryList.getValue() == "UK") { for(String i : firstB) { firstLevelDivisionList.getItems().add(i); } }
        if(countryList.getValue() == "Canada") { for(String i : firstC) { firstLevelDivisionList.getItems().add(i); } }
        firstLevelDivisionList.getSelectionModel().select(0);
    }

    /**
     * This gets an original id
     * @return a new id
     */
    public int getNewID() {
        int customID = -1;
        for (Customer customer: customers) {
            if(customer.getCustomerID() > customID) {
                customID = customer.getCustomerID();
            }
        }
        return customID + 1;
    }

    /**
     * This method tells if the id exist or not
     * @param id id
     * @return true or false
     */
    public boolean idExist(String id) {

        for(Customer customer: customers) {
            if(String.valueOf(customer.getCustomerID()).equals(id)) {
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @return the division id
     * @throws SQLException sql exception
     */
    public String getDivisionID() throws SQLException {
        //Starts the connection to the database
        Connection con = DBConnection.startConnection();
        //Create a statement object
        DBQuery.setStatement(con);
        //Get statement object
        Statement statement = DBQuery.getStatement();

        String first = firstLevelDivisionList.getSelectionModel().getSelectedItem();
        //if(first.isEmpty()) { infoBoxError("wrong", "wrong"); return "";}
        String divisionSQL = "SELECT Division_ID FROM first_level_divisions WHERE Division = '" + first + "'";
        ResultSet r = statement.executeQuery(divisionSQL);
        String division = "";
        while(r.next()) { division = r.getString(1);}
        return division;
    }

    /**
     * checks to see if the fields are filled in or not
     * @return true or false
     */
    public boolean checkMark() {
        //This checks if the fields are empty or not
        String option = optionList.getSelectionModel().getSelectedItem();
        if (nameText.getText().isEmpty() || postalCodeText.getText().isEmpty() ||
                addressText.getText().isEmpty() || phoneNumberText.getText().isEmpty()) {
            infoBoxError("fill in all of the text fields", "ERROR");
            return false;
        } else if ((option == "Add") && !customerIDText.getText().isEmpty()) {
            infoBoxError("customerID should not be filled out", "ERROR");
            return false;
        } else if (((option == "Delete") || (option == "Update")) && customerIDText.getText().isEmpty()){
            infoBoxError("CustomerID should be filled", "ERROR");
            return false;
        }
        return true;
    }

    /**
     * This clears the table and whatnot
     */
    public void clearText() {
        customerIDText.setText("");
        nameText.setText("");
        addressText.setText("");
        postalCodeText.setText("");
        phoneNumberText.setText("");
        countryList.getSelectionModel().clearSelection();
        firstLevelDivisionList.getSelectionModel().clearSelection();
    }

    /**
     * This logs off from the current controller screen and goes to the login screen directly
     * @param event the event
     * @throws IOException IO exception
     */
    @FXML
    void onActionLogOff(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/login.fxml"));
        Controllers.loginController controller = new Controllers.loginController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This goes back to main controller screen
     * @param event the event
     * @throws IOException
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/main.fxml"));
        mainController controller = new mainController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        //Need to fix the centering later on
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This is an infobox for errors
     *
     * @param infoMessage the message
     * @param headerText the headertext
     */
    public void infoBoxError(String infoMessage, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    /**
     * This is an infobox for informative
     *
     * @param infoMessage the message
     * @param headerText the header text
     */
    public void infoBoxInformation(String infoMessage, String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
