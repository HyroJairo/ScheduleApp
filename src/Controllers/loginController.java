package Controllers;

import Model.User;
import Utils.DBConnection;
import Utils.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.*;

/**
 * This is the log in class. You enter username and password to enter the main menu
 */
public class loginController implements Initializable {
    /**
     * Labels
     */
    @FXML
    private Label titleText;
    @FXML
    private Label zoneID;

    /**
     * TextFields
     */
    @FXML
    private TextField userIDText;
    @FXML
    private TextField passwordText;

    /**
     * Buttons
     */
    @FXML
    private Button loginText;


    //Below these are strings for errors
    private String empty;
    private String incorrect;

    /**
     * This is the static method for user
     */
    public static User user = new User("", "");

    /**
     * This initializes the loginController class which you need the correct username and password
     * to successfully log in. A
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //This figures out the geological issues
        ZoneId id = ZoneId.systemDefault();
        zoneID.setText(id.toString());

        //This translates the page according the default language of the computer
        resourceBundle = ResourceBundle.getBundle("Properties/login", Locale.getDefault());
        try {
            titleText.setText(resourceBundle.getString("title"));
            userIDText.setPromptText(resourceBundle.getString("username"));
            passwordText.setPromptText(resourceBundle.getString("password"));
            loginText.setText(resourceBundle.getString("signin"));
            empty = resourceBundle.getString("empty");
            incorrect = resourceBundle.getString("incorrect");
        } catch(MissingResourceException e) {
            System.out.println("Missing resource");
        }
    }

    /**
     *
     * @param event event
     * @throws IOException an IO exception
     * @throws SQLException an sql exception
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {

        //Strings for the username and password
        String str = userIDText.getText().trim();
        String str1 = passwordText.getText().trim();

        //If either username or password isn't filled out it sends an error
        if(str.length() == 0 || str1.length() == 0) {
            infoBoxError(empty, "error");
            return;
        }

        //checks if either if either the username or password are correct
        if(checkCredentials()) {
            //id, name, and password are already been set
            user.setCreatedBy(user.getUserName());
            user.setLastUpdatedBy(user.getUserName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/main.fxml"));
            mainController controller = new mainController();

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            userActivity(true);
            appointmentIn15();

        } else {
            infoBoxError(incorrect, "ERROR");
            userActivity(false);
        }
    }

    /**
     * Checks to see if the credentials are valid
     * @return true or false
     * @throws SQLException an SQL exception
     */
    public boolean checkCredentials() throws SQLException {
        //Starts the connection
        Connection con = DBConnection.startConnection();
        //Create statement object
        DBQuery.setStatement(con);
        //Get statement object
        Statement statement = DBQuery.getStatement();
        //Get username
        String name = userIDText.getText().trim();
        //SQL statement for the id
        String sqlStatementID = "SELECT User_ID FROM users WHERE User_Name = '" + name + "'";
        //create result set object
        ResultSet resultID = statement.executeQuery(sqlStatementID);

        //assigns an int and checks if the id exists
        int id = -1;
        while(resultID.next()) { id = resultID.getInt("User_ID"); }
        if(id == -1) { return false; }

        //writes SQL statement for password
        String sqlStatementPassword = "SELECT password FROM users WHERE User_ID = " + id;
        //create result set object
        ResultSet resultPass = statement.executeQuery(sqlStatementPassword);
        //Gets the password and sees if it matches the one the user typed.
        String password = passwordText.getText().trim();
        while (resultPass.next()) {
            if (resultPass.getString("password").equals(password)) {
                //Sets password for user
                user.setUserID(id);
                user.setUserName(name);
                user.setPassword(password);
                return true;
            }
        }
        return false;
    }

    /**
     * This is an infobox for errors
     * @param infoMessage the message
     * @param headerText the headerText
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
     * This records a successful or a failure attempt to a txt file
     * @param authorized a boolean
     * @throws IOException an IO exception
     */
    public void userActivity(Boolean authorized) throws IOException {
        FileWriter fw;
        BufferedWriter bw;
        PrintWriter pw;
        fw = new FileWriter("login_activity.txt",true);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw);
        Timestamp loginTime = new Timestamp(System.currentTimeMillis());
        String s = "User failed to log in at " + loginTime.toString();
        if(authorized) {
            s = "User successfully logged in at " + loginTime.toString();
        }
        pw.println(s);
        pw.flush();
        pw.close();
        bw.close();
        fw.close();
    }

    /**
     * This checks if the user has an appointment within 15 minutes of successfully logging in
     */
    public void appointmentIn15() throws SQLException {
        //Starts the connection
        Connection con = DBConnection.startConnection();
        //Create statement object
        DBQuery.setStatement(con);
        //Get statement object
        Statement statement = DBQuery.getStatement();

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
                    infoBoxInformation(upComing, "INFO");
                }
            }
            if(free) {
                infoBoxInformation("You have no appointments", "INFO");
            }

        } catch (SQLException e) { e.printStackTrace(); }
    }

}
