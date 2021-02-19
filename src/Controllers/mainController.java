package Controllers;

import Model.Appointment;
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

/**
 * This is the main controller. It shows the appointments as well as directing you to
 * the customer, appointment, and report screens
 */
@SuppressWarnings("FieldMayBeFinal")
public class mainController implements Initializable {

    /**
     * Labels
     */
    @FXML
    private Label titleText;

    /**
     * Tables and columns
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
    private TableColumn<Appointment, String> contact = new TableColumn<>();

    /**
     * The buttons
     */
    @FXML
    private Button customerScreen;
    @FXML
    private Button appointmentScreen;
    @FXML
    private Button reportScreen;
    @FXML
    private Button logOff;

    /**
     * The radio buttons
     */
    @FXML
    private RadioButton Weekly;
    @FXML
    private RadioButton Monthly;
    @FXML
    private RadioButton all;

    /**
     * The date picker
     */
    @FXML
    private DatePicker changeTable;

    /**
     * list for appointments
     */
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * Initializes the whole class
     *
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
            if(appointmentTable.getItems().isEmpty()) {
                System.out.println("Nothing is in here");
            }
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
     * This changes the appointment schedule to full view
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
     * @param event the event
     * @throws IOException an IO exception
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
     * @param event the event
     * @throws IOException an IO exception
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
     * @param event the event
     * @throws IOException an IO exception
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
     * @param event the event
     * @throws IOException an IO exception
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

