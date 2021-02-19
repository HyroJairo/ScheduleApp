package Controllers;
import Model.Appointment;
import Model.Contact;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * This is the report controller where it produces reports
 */
public class reportController implements Initializable {

    @FXML
    private Label titleText;
    @FXML
    private TextArea textArea;
    @FXML
    private Button logOff;
    @FXML
    private Button Back;
    @FXML
    private ComboBox<String> typeList;
    @FXML
    private ComboBox<String> monthList;


    /**
     * These are observables lists for types, months,
     * appointments, contacts, and customers
     */
    ObservableList<String> types = FXCollections.observableArrayList();
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * This initializes the class
     * @param url url
     * @param resourceBundle a resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Starts the connection to the database
        Connection con = DBConnection.startConnection();

        //Create a statement object
        try {
            DBQuery.setStatement(con);
        } catch (SQLException e) { e.printStackTrace(); }

        //Get a statement object
        Statement statement = DBQuery.getStatement();

        //This adds in the types
        try {
            String typeSQL = "Select Type FROM appointments";
            ResultSet r = statement.executeQuery(typeSQL);
            while(r.next()) {
                String f = r.getString(1);
                if(!types.contains(f)) {
                    types.add(f);
                    typeList.getItems().add(f);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        //This adds in the months
        String[] monthly = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        for(String m : monthly) {
            months.add(m);
            monthList.getItems().add(m);
        }

        //This adds in the appointments
        try {
            String appointmentSQL = "SELECT * FROM appointments";
            ResultSet r = statement.executeQuery(appointmentSQL);
            while(r.next()) {
                int id = r.getInt(1);
                String title = r.getString(2);
                String description = r.getString(3);
                String location = r.getString(4);
                String type = r.getString(5);
                Timestamp start = r.getTimestamp(6);
                Timestamp end = r.getTimestamp(7);
                Timestamp create = r.getTimestamp(8);
                String createBy = r.getString(9);
                Timestamp last = r.getTimestamp(10);
                String lastBy = r.getString(11);
                int customID = r.getInt(12);
                int userID = r.getInt(13);
                int contactID = r.getInt(14);
                Appointment appointment = new Appointment(id, title, description, location, type, start, end,
                        create, createBy, last, lastBy, customID, userID, contactID);
                appointments.add(appointment);
            }
        } catch (SQLException e) { e.printStackTrace();}

        //This adds in the contacts
        try {
            String sql = "SELECT * FROM contacts";
            ResultSet r = statement.executeQuery(sql);
            while(r.next()) {
                int id = r.getInt(1);
                String name = r.getString(2);
                String email = r.getString(3);
                Contact contact = new Contact(id, name, email);
                contacts.add(contact);
            }
        } catch (SQLException e) { e.printStackTrace();}

        //This adds in the customers
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

                Customer customer = new Customer(id, name, address, code, phone,
                        create, createBy, update, updateBy, divisionID);
                customers.add(customer);
            }
        } catch (SQLException e) { e.printStackTrace(); }

    }

    /**
     * This shows a list of appointments based on types and months
     * @param event an event
     * @throws SQLException sql exception
     */
    @FXML
    void onActionAppointmentSubmit(ActionEvent event) throws SQLException {
        String m = monthList.getSelectionModel().getSelectedItem();
        String t = typeList.getSelectionModel().getSelectedItem();

        if(m == null || t == null) {
            infoBoxError("select a type and a month", "ERROR");
        } else {

            //Starts the connection to the database
            Connection con = DBConnection.startConnection();

            //Create a statement object
            DBQuery.setStatement(con);

            //Get a statement object
            Statement statement = DBQuery.getStatement();

            String tSQL = "Select * FROM appointments WHERE Type ='" + t + "'";
            ResultSet r = statement.executeQuery(tSQL);
            int count = 0;
            while(r.next()) {
                Timestamp create = r.getTimestamp(6);
                LocalDateTime d = create.toLocalDateTime();
                Month s = d.getMonth();
                if(m.equals(s.toString())) {
                    count++;
                }
            }

            String howMany;
            if(count == 0) {
                howMany = "There are no " + t + " type appointments for the mont of " + m;
            } else if(count == 1) {
                howMany = "There is only" + count + " " + t + " type appointment for the month of " + m;
            } else {
                howMany = "There are " + count + " " + t + " type appointments for the month of " + m;
            }
            textArea.setText(howMany);
            textArea.setStyle("-fx-font: 24 arial;");
        }
    }

    /**
     * This submits a contact and their appointments
     * @param event an event
     */
    @FXML
    void onActionContactSubmit(ActionEvent event) {
        String finalWord = "";
        for(Contact contact : contacts) {
            String name = contact.getContactName();
            String saying = "Here are " + name + "'s appointments\n";
            String line = "";
            int id = contact.getContactID();
            for(Appointment app : appointments) {
                String appoint = "";
                if(app.getContact() == id) {
                    String a = String.valueOf(app.getAppointmentID());
                    String b = app.getTitle();
                    String c = app.getDescription();
                    String d = app.getLocation();
                    String e = app.getType();
                    String f = app.getStart().toString();
                    String g = app.getEnd().toString();
                    String h = app.getCreateDate().toString();
                    String i = app.getCreatedBy();
                    String j = app.getLastUpdate().toString();
                    String k = app.getLastUpdatedBy();
                    String l = String.valueOf(app.getCustomerID());
                    String m = String.valueOf(app.getUserID());
                    String n = String.valueOf(app.getContact());
                    appoint += "AppointmentID:" + a + "  Title:" + b + "  Description:" + c + "  Location:" + d +
                            "  Type: " + e + "  Start:" + f + "  End:\n" + g + "  CreateDate:" + h + "  CreatedBy:" + i +
                            "  LastUpdate:" + j + "  LastUpdatedBy:" + k + "  CustomerID:" + l + "  UserID:" + m +
                            "  ContactID:" + n + "\n" + "\n";
                }
                line += appoint;

            }
            saying +=line;
            finalWord +=saying;
        }
        textArea.setText(finalWord);
        textArea.setStyle("-fx-font: 24 arial;");

    }

    /**
     * This shows a list of customer's names and their id
     * @param event an event
     */
    @FXML
    void onActionCustomerSubmit(ActionEvent event) {
        String line = "Here are the customers that are on file :\n";
        for(Customer customer: customers) {
            line += customer.getCustomerName() + " id: " + customer.getCustomerID() + "\n";
        }
        textArea.setText(line);
        textArea.setStyle("-fx-font: 24 arial;");
    }

    /**
     * This logs off and goes straight to the login page
     * @param event an event
     * @throws IOException an IO exception
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
     * This goes back the main menu
     * @param event an event
     * @throws IOException an IO exception
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
     * @param headerText the header text
     */
    public void infoBoxError(String infoMessage, String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}