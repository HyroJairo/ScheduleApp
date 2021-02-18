package Controllers;

import Model.Appointment;
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
import java.time.DayOfWeek;
import java.util.ResourceBundle;

import static Controllers.loginController.user;

public class mainController implements Initializable {

    @FXML
    private Button customerScreen;

    @FXML
    private Label titleText;

    @FXML
    private Button appointmentScreen;

    @FXML
    private Button reportScreen;

    @FXML
    private Button logOff;

    @FXML
    private RadioButton Weekly;

    @FXML
    private RadioButton Monthly;

    @FXML
    private RadioButton all;

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
    private TableColumn<Appointment, String> contact = new TableColumn<>();

    @FXML
    private DatePicker changeTable;

    ObservableList<Appointment> appointments = FXCollections.observableArrayList();


    /**
     * Initializes the whole class
     *
     * @param url
     * @param resourceBundle
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

        //This sets the table
        changeToAll();

        //This will show if there's any appointments within 15 minutes of logging in
        String appointmentIn15 = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND ADDTIME(NOW(), '00:15:00')" +
                " AND User_ID=" + user.getUserID();
        try {
            ResultSet b  = statement.executeQuery(appointmentIn15);

            String upComing;
            boolean free = true;
            if(b.next()) {
                String id = b.getString(1);
                String start = b.getString(6);
                upComing = "You have an upcoming appointment. It's ID is " + id + " and its start date and time is " +
                        start;
                if(id.length() > 0) { free = false;}
                if(!free) {
                    infoBoxInformation(upComing, "Info");
                }
            }
            if(free) {
                infoBoxInformation("You have no appointments", "Info");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This changes the appointment schedule to monthly
     */
    @FXML
    void changeToMonthly() {
        //This gets the month value
        int m = changeTable.getValue().getMonthValue();
        //I create a observableList for the month
        ObservableList<Appointment> monthApp = FXCollections.observableArrayList();
        //This inserts the appointments that fit in the month into the monthApp and sets in the table
        appointments.forEach(n -> {
            int a = n.getStart().toLocalDateTime().getMonthValue();
            if(m == a) {
                monthApp.add(n);
            }
        });

        appointmentTable.setItems(monthApp);
        if(appointmentTable.getItems().isEmpty()) {
            System.out.println("Nothing is in here");
        }
    }

    /**
     * This changes the appointment schedule to weekly
     *
     */
    @FXML
    void changeToWeekly() {
        String[] arr = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        int start = 0;
        int finish = 6;

        //This gets the name of the day
        DayOfWeek s = changeTable.getValue().getDayOfWeek();
        //This gets the number day of the month
        int v = changeTable.getValue().getDayOfMonth();
        //This ticks up/down start and finish
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == s.toString()) {
                break;
            } else {
                start++;
                finish--;
            }
        }
        //I create another observableList
        ObservableList<Appointment> weekApp = FXCollections.observableArrayList();
        //This inserts the appointments that fit in the week into the weekapp and sets it in the table
        int begin = v - start;
        int end = v + finish;
        appointments.forEach(app -> {
            int a = app.getStart().toLocalDateTime().getDayOfMonth();
            int b = app.getStart().toLocalDateTime().getMonthValue();
            int c = changeTable.getValue().getMonthValue();
            if(a >= begin && a <= end && c == b) {
                weekApp.add(app);
            }
        });
        appointmentTable.setItems(weekApp);

        //If theres nothing I will create a warning in here
        if(appointmentTable.getItems().isEmpty()) {
            System.out.println("Nothing is in here");
        }
    }

    /**
     * This changes the appointment schedule to all
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
     * This goes to the appointment screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/appointment.fxml"));
        Controllers.appointmentController controller = new Controllers.appointmentController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This goes to the customer screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/customer.fxml"));
        Controllers.customerController controller = new Controllers.customerController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This goes to the report screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReport(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/report.fxml"));
        Controllers.reportController controller = new Controllers.reportController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This goes back to the log in screen
     * @param event
     * @throws IOException
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

    /**
     * This is an infobox for errors
     *
     * @param infoMessage the message
     * @param headerText the headertext
     */
    public void infoBoxInformation(String infoMessage, String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}

