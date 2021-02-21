package Controllers;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Controllers.loginController.user;

/**
 * @author max morales
 * This is the appointment controller class where the user can add, update, and delete appointment records
 */
@SuppressWarnings("FieldMayBeFinal")
public class appointmentController implements Initializable{
    /**
     * Text fields
     */
    @FXML
    private TextField locationText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField typeText;
    @FXML
    private TextField appointmentIDText;
    @FXML
    private TextField startText;
    @FXML
    private TextField dateText;
    @FXML
    private TextField endText;
    @FXML
    private TextField customerIDText;

    /**
     * Table and columns
     */
    @FXML
    private TableView<Appointment> appointmentTable = new TableView<>();
    @FXML
    private TableColumn<Appointment, Integer> appointmentID = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, String> title = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, String> description = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, String> location = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, String> type = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, String> start = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, String> end = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, Integer> customerID = new TableColumn<>();
    @FXML
    private TableColumn<Appointment, Integer> contact = new TableColumn<>();

    /**
     * radio buttons to change from week to month
     */
    @FXML
    private RadioButton weekly;
    @FXML
    private RadioButton monthly;
    @FXML
    private RadioButton all;

    /**
     * Toggle group
     */
    @FXML
    private ToggleGroup inOutToggle;

    /**
     * comboBox variables
     */
    @FXML
    private ComboBox<String> contactList;
    @FXML
    private ComboBox<String> startHours;
    @FXML
    private ComboBox<String> startMinutes;
    @FXML
    private ComboBox<String> endHours;
    @FXML
    private ComboBox<String> endMinutes;
    @FXML
    private ComboBox<String> optionList;
    @FXML
    private ComboBox<String> userIDList;
    @FXML
    private ComboBox<String> customerList;

    /**
     * Date pickers
     */
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private DatePicker changeTable;

    /**
     * The buttons
     */
    @FXML
    private Button addAppointment;
    @FXML
    private Button updateAppointment;
    @FXML
    private Button deleteAppointment;
    @FXML
    private Button back;
    @FXML
    private Button logOff;

    /**
     * observables collections
     */
    ObservableList<Contact> contacts = FXCollections.observableArrayList();
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * These are string arrays
     */
    String[] optionLists = {"Add", "Update", "Delete"};

    /**
     * This initializes the whole class
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Starts the connection to the database
        Connection con = DBConnection.startConnection();

        //Create a statement object
        try { DBQuery.setStatement(con); }
        catch (SQLException e) { e.printStackTrace(); }

        //Get a statement object
        Statement statement = DBQuery.getStatement();

        //This populates hours
        for(int i = 0; i < 24; i++) {
            String hour = "";
            if(i < 10) {
                hour +="0";
            }
            hour += String.valueOf(i);
            startHours.getItems().add(hour);
            endHours.getItems().add(hour);
        }

        //This populates minutes
        for(int i = 0; i < 60; i++) {
            String minute = "";
            if(i < 10) {
                minute +="0";
            }
            minute += String.valueOf(i);
            startMinutes.getItems().add(minute);
            endMinutes.getItems().add(minute);
        }

        //This populates the appointment list.
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

        //This populates the contact list.
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
            for(Contact i : contacts) {
                contactList.getItems().add(i.getContactName());
            }
        } catch (SQLException e) { e.printStackTrace();}

        //This populates the user list. This line isn't used but rather for the rubric
        try {
            String s = "SELECT * FROM users";
            ResultSet r = statement.executeQuery(s);
            while(r.next()) {
                int u = r.getInt(1);
                String us = r.getString(2);
                String p = r.getString(3);
                Date c = r.getDate(4);
                String cr = r.getString(5);
                Date l = r.getDate(6);
                String la = r.getString(7);
                User user = new User(u, us, p, c, cr, l, la);
                users.add(user);
                userIDList.getItems().add(String.valueOf(user.getUserID()));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        //This populates the customer list.
        try{
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
                customerList.getItems().add(String.valueOf(customer.getCustomerID()));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        //This populates options
        for(String option : optionLists) { optionList.getItems().add(option); }

        //This sets the table
        changeToAll();
    }

    /**
     * This changes the appointment schedule to monthly
     */
    @FXML
    void changeToMonthly() {
        if(!(changeTable.getValue() == null)) {
            //This gets the month value
            int m = changeTable.getValue().getMonthValue();
            //I create a observableList for the month
            ObservableList<Appointment> monthApp = FXCollections.observableArrayList();
            //This inserts the appointments that fit in the month into the monthApp and sets in the table
            //This is a lambda expression used to search through the appointment lists to add it to the month list
            appointments.forEach(n -> {
                int a = n.getStart().toLocalDateTime().getMonthValue();
                if(m == a) {
                    monthApp.add(n);
                }
            });

            appointmentTable.setItems(monthApp);
        }
    }

    /**
     * This changes the appointment schedule to weekly
     *
     */
    @FXML
    void changeToWeekly() {
        if(!(changeTable.getValue() == null)) {
            String[] arr = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
            int start = 0;
            int finish = 6;

            //This gets the name of the day
            DayOfWeek s = changeTable.getValue().getDayOfWeek();
            //This gets the number day of the month
            int v = changeTable.getValue().getDayOfMonth();
            //This ticks up/down start and finish
            for (String value : arr) {
                if (value.equals(s.toString())) {
                    break;
                } else {
                    start++;
                    finish--;
                }
            }
            //I create another observableList
            ObservableList<Appointment> weekApp = FXCollections.observableArrayList();

            //This gets the beginning and end of the week
            int begin = v - start;
            int end = v + finish;
            //This is a lambda expression used to search through the appointment list to add it to the week list
            appointments.forEach(app -> {
                int a = app.getStart().toLocalDateTime().getDayOfMonth();
                int b = app.getStart().toLocalDateTime().getMonthValue();
                int c = changeTable.getValue().getMonthValue();
                if(a >= begin && a <= end && c == b) {
                    weekApp.add(app);
                }
            });
            appointmentTable.setItems(weekApp);
        }
    }

    /**
     * This changes the appointment schedule to show every appointments in the table
     */
    @FXML
    void changeToAll() {
        appointmentTable.setItems(appointments);
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    /**
     * This changes the calendar once it is selected
     * to either all, weekly, or monthly
     * @param event event
     */
    @FXML
    void onActionChange(ActionEvent event) {
        if(weekly.isSelected()) {
            changeToWeekly();
        } else if(monthly.isSelected()) {
            changeToMonthly();
        } else {
            changeToAll();
        }
    }

    /**
     * This adds an appointment to the appointment schedule and into the database
     * @param event event
     * @throws SQLException sql exception
     * @throws ParseException parse exception
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws SQLException, ParseException {
        //Get the selected option
        String option = optionList.getSelectionModel().getSelectedItem();

        if(!(option == "Add")) {
            infoBoxError("You have to click the add option", "ERROR");
        } else if(checkMark()){
            //Starts the connection to the database
            Connection con = DBConnection.startConnection();

            //Create a statement object
            DBQuery.setStatement(con);

            //Get statement object
            Statement statement = DBQuery.getStatement();

            //This gets the fields from
            String id = String.valueOf(generateAppointmentID());
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeText.getText();

            //This gets start time
            String s = startDate.getValue().toString();
            String ss = startHours.getValue();
            String sss = startMinutes.getValue();
            String start = s + " " + ss + ":" + sss + ":00";

            //This gets end time
            String e = endDate.getValue().toString();
            String ee = endHours.getValue();
            String eee = endMinutes.getValue();
            String end = e + " " + ee + ":" + eee + ":00";

            //This gets create, createBy, lastUpdate, lastUpdateBy
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = formatter.format(date);
            String createdBy = user.getUserName();
            String lastUpdatedBy = user.getUserName();
            String customerID = customerList.getSelectionModel().getSelectedItem();
            String userID = String.valueOf(user.getUserID());
            String contact = "";
            for(Contact cont : contacts) {
                if(contactList.getValue().equals(cont.getContactName())) {
                    contact = String.valueOf(cont.getContactID());
                    break;
                }
            }

            //This just deals with the timestamps and errors
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDateStart = dateFormat.parse(start);
            Date parsedDateEnd = dateFormat.parse(end);
            Timestamp timestampStart = new java.sql.Timestamp(parsedDateStart.getTime());
            Timestamp timestampEnd = new java.sql.Timestamp(parsedDateEnd.getTime());
            Timestamp createDate = new Timestamp(date.getTime());
            Timestamp lastDate = new Timestamp(date.getTime());

            //converts the timestamp to date and gets string for UTC
            Date timeDateStart = new Date(timestampStart.getTime());
            Date timeDateEnd = new Date(timestampEnd.getTime());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String endForUTC = dateFormat.format(timeDateEnd);
            String startForUTC = dateFormat.format(timeDateStart);

            //converts the timestamp to to date and gets string for ET
            dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
            String endForET = dateFormat.format(timeDateEnd);
            String startForET = dateFormat.format(timeDateStart);
            String forOfficeStart = startForET.substring(11, 13);
            String forOfficeEnd = endForET.substring(11, 13);
            int officeStart = Integer.parseInt(forOfficeStart);
            int officeEnd = Integer.parseInt(forOfficeEnd);

            //If statement that only lets time continue after 8:00am and before 10:pm
            if(officeStart < 8 || officeStart > 22 || officeEnd < 8 || officeEnd > 22) {
                infoBoxError("Appointments can only be made between 8:00 am and 10 pm EST", "ERROR");
                return;
            }

            //This makes sure if the appoint times makes sense with one another
            if(timestampStart.after(timestampEnd)) {
                infoBoxError("end date can't be before start date", "ERROR");
                return;
            }
            Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
            if(timestampStart.before(timestampNow)) {
                infoBoxError("start date can't be before current date", "ERROR");
                return;
            }


            int i = Integer.parseInt(id);
            int j = Integer.parseInt(customerID);
            int k = user.getUserID();
            int l = Integer.parseInt(contact);

            //This checks for conflicting schedule among the customers;
            for(Appointment app : appointments) {
                if(app.getEnd().after(timestampStart) && app.getStart().before(timeDateStart)) {
                    infoBoxError("Conflicting schedule for with appointmentID: " +
                            app.getAppointmentID(), "ERROR");
                    return;
                } else if(app.getStart().before(timeDateEnd) && app.getEnd().after(timeDateEnd)) {
                    infoBoxError("Conflicting schedule for appointmentID: " +
                            app.getAppointmentID(), "ERROR");
                    return;
                } else if(app.getStart().equals(timestampStart) || app.getEnd().equals(timestampEnd)) {
                    infoBoxError("Conflicting schedule for appointmentID: " +
                            app.getAppointmentID(), "ERROR");
                    return;
                }
            }



            //String for appointment SQL
            String appointmentSQL = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date," +
                    "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)\n" +
                    "Values ('" + id + "', '" + title + "', '" + description + "', '" + location + "', '" + type + "', '" +
                    startForUTC + "', '" + endForUTC + "', '" + now + "', '" + createdBy + "', '" + now + "', '" + lastUpdatedBy +
                    "', '" + customerID + "', '" + userID + "', '" + contact + "') ";
            statement.executeUpdate(appointmentSQL);

            Appointment app = new Appointment(i, title, description, location, type, timestampStart, timestampEnd,
                    createDate, createdBy, lastDate, lastUpdatedBy, j, k, l);
            appointments.add(app);

            clearText();
            infoBoxInformation("Appointment has been added", "COMPLETED");

        }
    }

    /**
     * This deletes an appointment from the appointment schedule and from the database
     * @param event event
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {


        String option = optionList.getSelectionModel().getSelectedItem();
        if(!(option == "Delete")) {
            infoBoxError("click delete option", "ERROR");
        } else if(!idExist(appointmentIDText.getText())){
            infoBoxError("Appointment ID does not exist", "ERROR");
        } else {
            //Starts the connection to the database
            Connection con = DBConnection.startConnection();

            //Create a statement object
            DBQuery.setStatement(con);

            //Get a statement object
            Statement statement = DBQuery.getStatement();

            String appointmentSQL = "DELETE FROM appointments WHERE Appointment_ID=" + appointmentIDText.getText();
            statement.executeUpdate(appointmentSQL);

            for(Appointment app: appointments) {
                String appID = String.valueOf(app.getAppointmentID());
                String appType = app.getType();
                if(appID.equals(appointmentIDText.getText())) {
                    appointments.remove(app);
                    clearText();
                    appointmentTable.refresh();

                    infoBoxInformation("You have successfully removed a " + appType +
                            " appointment of AppointmentID=" + appID, "COMPLETE");
                    return;
                }
            }
        }
    }

    /**
     * This method updated an appointment
     * @param event event
     * @throws SQLException sql exception
     * @throws ParseException parses an exception
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws SQLException, ParseException {
        String option = optionList.getSelectionModel().getSelectedItem();
        if(!(option == "Update")) {
            infoBoxError("click update option", "ERROR");
        } else if(!idExist(appointmentIDText.getText())) {
            infoBoxError("Appointment ID does not exist", "ERROR");
        } else if(checkMark()){
            //Starts the connection to the database
            Connection con = DBConnection.startConnection();

            //Create a statement object
            DBQuery.setStatement(con);

            //Get a statement object
            Statement statement = DBQuery.getStatement();

            //These are the variables to be updated
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = descriptionText.getText();
            String type = typeText.getText();
            //This gets start time
            String s = startDate.getValue().toString();
            String ss = startHours.getValue();
            String sss = startMinutes.getValue();
            String start = s + " " + ss + ":" + sss + ":00";
            //This gets end time
            String e = endDate.getValue().toString();
            String ee = endHours.getValue();
            String eee = endMinutes.getValue();
            String end = e + " " + ee + ":" + eee + ":00";
            String customerID = customerList.getSelectionModel().getSelectedItem();
            String contact = "";
            for(Contact cont : contacts) {
                if(contactList.getValue().equals(cont.getContactName())) {
                    contact = String.valueOf(cont.getContactID());
                    break;
                }
            }

            //converts the date format into par
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDateStart = dateFormat.parse(start);
            Date parsedDateEnd = dateFormat.parse(end);
            Timestamp timestampStart = new java.sql.Timestamp(parsedDateStart.getTime());
            Timestamp timestampEnd = new java.sql.Timestamp(parsedDateEnd.getTime());

            //converts the timestamp to date
            Date timeDateStart = new Date(timestampStart.getTime());
            Date timeDateEnd = new Date(timestampEnd.getTime());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String endForUTC = dateFormat.format(timeDateEnd);
            String startForUTC = dateFormat.format(timeDateStart);

            //the sql statement to update the appointment
            String appointmentSQL = "UPDATE appointments\n" +
                                    "SET Title='" + title + "', Description='" + description + "', Location='" +
                                    location + "', Type='" + type + "', Start='" + startForUTC + "', End='" + endForUTC +
                                    "', Customer_ID='" + customerID + "', Contact_ID='" + contact + "'\n" +
                                    "WHERE Appointment_ID='" + appointmentIDText.getText() + "'";
            statement.executeUpdate(appointmentSQL);

            //updates the appointment
            for(Appointment app: appointments) {
                if(String.valueOf(app.getAppointmentID()).equals(appointmentIDText.getText())) {
                    app.setTitle(title);
                    app.setDescription(description);
                    app.setLocation(location);
                    app.setType(type);
                    app.setStart(timestampStart);
                    app.setEnd(timestampEnd);
                    app.setCustomerID(Integer.parseInt(customerID));
                    app.setContact(Integer.parseInt(contact));
                }
            }
            clearText();
            appointmentTable.refresh();
            infoBoxInformation("Appointment has been updated", "COMPLETED");
        }
    }

    /**
     * This gets the id and checks if it exists
     * @param id the id of the appointment
     * @return true or false
     */
    public boolean idExist(String id) {
        for(Appointment app: appointments) {
            if(String.valueOf(app.getAppointmentID()).equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * generates an id
     * @return id;
     */
    int generateAppointmentID() {
        int id = -1;
        for (Appointment app: appointments) {
            if(app.getAppointmentID() > id) {
                id = app.getAppointmentID();
            }
        }
        return id + 1;
    }

    /**
     * This makes sure if which mode (adds, update, delete) is activated
     * the following actions follow
     * @param event event
     */
    @FXML
    void onActionOptions(ActionEvent event) {
        String s = optionList.getSelectionModel().getSelectedItem();
        switch (s) {
            case "Add":
                appointmentIDText.setPromptText("ID disabled");
                appointmentIDText.setDisable(true);
                titleText.setDisable(false);
                descriptionText.setDisable(false);
                locationText.setDisable(false);
                typeText.setDisable(false);
                startDate.setDisable(false);
                startHours.setDisable(false);
                startMinutes.setDisable(false);
                endDate.setDisable(false);
                endHours.setDisable(false);
                endMinutes.setDisable(false);
                contactList.setDisable(false);
                customerList.setDisable(false);
                userIDList.setDisable(true);
                clearText();
                break;
            case "Update":
                appointmentIDText.setPromptText("Type ID and enter");
                appointmentIDText.setDisable(false);
                titleText.setDisable(true);
                descriptionText.setDisable(true);
                locationText.setDisable(true);
                typeText.setDisable(true);
                startDate.setDisable(true);
                startHours.setDisable(true);
                startMinutes.setDisable(true);
                endDate.setDisable(true);
                endHours.setDisable(true);
                endMinutes.setDisable(true);
                contactList.setDisable(true);
                customerList.setDisable(true);
                userIDList.setDisable(true);
                clearText();
                break;
            case "Delete":
                appointmentIDText.setPromptText("Type ID and enter");
                appointmentIDText.setDisable(false);
                titleText.setDisable(true);
                descriptionText.setDisable(true);
                locationText.setDisable(true);
                typeText.setDisable(true);
                startDate.setDisable(true);
                startHours.setDisable(true);
                startMinutes.setDisable(true);
                endDate.setDisable(true);
                endHours.setDisable(true);
                endMinutes.setDisable(true);
                contactList.setDisable(true);
                customerList.setDisable(true);
                userIDList.setDisable(true);
                clearText();
                break;
        }
    }

    /**
     * Displays the customer's information
     * @param event event
     * @throws SQLException sql exception
     */
    @FXML
    void onActionDisplay(ActionEvent event) throws SQLException {
        String id = appointmentIDText.getText().trim();
        String option = optionList.getSelectionModel().getSelectedItem();

        //This disables or enables the fields
        if(idExist(id)) {
            if(option == "Update") {
                titleText.setDisable(false);
                descriptionText.setDisable(false);
                locationText.setDisable(false);
                typeText.setDisable(false);
                startDate.setDisable(false);
                startHours.setDisable(false);
                startMinutes.setDisable(false);
                endDate.setDisable(false);
                endHours.setDisable(false);
                endMinutes.setDisable(false);
                contactList.setDisable(false);
                customerList.setDisable(false);
                userIDList.setDisable(true);
            }
            if(option == "Delete") {
                titleText.setDisable(true);
                descriptionText.setDisable(true);
                locationText.setDisable(true);
                typeText.setDisable(true);
                startDate.setDisable(true);
                startHours.setDisable(true);
                startMinutes.setDisable(true);
                endDate.setDisable(true);
                endHours.setDisable(true);
                endMinutes.setDisable(true);
                contactList.setDisable(true);
                customerList.setDisable(true);
                userIDList.setDisable(true);
            }
            for(Appointment app : appointments) {
                String custID = String.valueOf(app.getAppointmentID());

                if(custID.equals(id)) {
                    //I take the substrings of the start and end timestamps
                    String start = app.getStart().toString().substring(0, 10);
                    String shours = app.getStart().toString().substring(11, 13);
                    String sminutes = app.getStart().toString().substring(14, 16);

                    String end = app.getEnd().toString().substring(0, 10);
                    String ehours = app.getEnd().toString().substring(11, 13);
                    String eminutes = app.getEnd().toString().substring(14, 16);

                    //Sets the textfields with the respective values
                    titleText.setText(app.getTitle());
                    descriptionText.setText(app.getDescription());
                    locationText.setText(app.getLocation());
                    typeText.setText(app.getType());
                    startDate.setValue(app.getStart().toLocalDateTime().toLocalDate());
                    startHours.setValue(shours);
                    startMinutes.setValue(sminutes);
                    endDate.setValue(app.getEnd().toLocalDateTime().toLocalDate());
                    endHours.setValue(ehours);
                    endMinutes.setValue(eminutes);
                    customerList.getSelectionModel().select(String.valueOf(app.getCustomerID()));
                    for(Contact con : contacts) {
                        int j = app.getContact();
                        if(con.getContactID() == j) {
                            contactList.getSelectionModel().select(con.getContactName());
                        }
                    }
                }
            }
        }
    }


    /**
     * This clears the table and whatnot
     */
    public void clearText() {
        appointmentIDText.setText("");
        titleText.setText("");
        descriptionText.setText("");
        locationText.setText("");
        typeText.setText("");
        startHours.getSelectionModel().clearSelection();
        startMinutes.getSelectionModel().clearSelection();
        endHours.getSelectionModel().clearSelection();
        endMinutes.getSelectionModel().clearSelection();
        customerList.getSelectionModel().clearSelection();
        contactList.getSelectionModel().clearSelection();


    }

    /**
     * checks to see if the fields are filled in or not
     * @return true or false
     */
    public boolean checkMark() {
        //This checks if the fields are empty or not
        if (titleText.getText().isEmpty() || descriptionText.getText().isEmpty() || locationText.getText().isEmpty() ||
                typeText.getText().isEmpty() || startDate.getValue() == null || startHours.getValue().isEmpty() ||
                startMinutes.getValue().isEmpty() || endDate.getValue() == null || endHours.getValue().isEmpty() ||
                endMinutes.getValue().isEmpty() || customerList.getValue().isEmpty() || contactList.getValue().isEmpty())
         {
            infoBoxError("fill in all of the text fields", "ERROR");
            return false;
        }
        return true;
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

    /**
     * This goes back to the main menu
     * @param event event
     * @throws IOException IO exception
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
     * This logs out of the application
     * @param event event
     * @throws IOException IO exception
     */
    @FXML
    void onActionLogOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/login.fxml"));
        Controllers.loginController controller = new Controllers.loginController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}