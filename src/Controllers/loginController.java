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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

public class loginController implements Initializable {
    @FXML
    private Button loginText;

    @FXML
    private TextField userIDText;

    @FXML
    private TextField passwordText;

    @FXML
    private Label titleText;

    @FXML
    private Label zoneID;

    //Below these are strings for errors
    private String empty;
    private String incorrect;


    /**
     * This is the login controller constructor
     */
    public loginController() {
        //default constructor
    }

    /**
     * This is the static method for user
     */
    public static User user = new User("", "");

    /**
     * This initializes the loginController class
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //This plugs in all the info from the database;

        //This figures out the geological issues
        ZoneId id = ZoneId.systemDefault();
        zoneID.setText(id.toString());

        //Getting DateFormat Instance
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //parsing date from the appointment string
        Date parsedDateStart = new Date();
        try {
            parsedDateStart = dateFormat.parse("2020-05-28 12:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestampStart = new java.sql.Timestamp(parsedDateStart.getTime());

        //converts the timestamp to date
        Date timeDate = new Date(timestampStart.getTime());

        //Setting time for UTC
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(dateFormat.format(timeDate).toString());

        String s = dateFormat.format(timeDate).toString();
        System.out.println(s);

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

    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        boolean correct = false;

        //Strings for the username and password
        String str = userIDText.getText().trim();
        String str1 = passwordText.getText().trim();

        //If either username or password isn't filled out it sends an error
        if(str.length() == 0 || str1.length() == 0) {
            infoBoxError(empty, "error");
            return;
        }

        //Check if they user exist and if the password matches
        if(checkUserName()) {
            correct = checkPassword(getID());
        }

        //If the username and password are met then this would transport to another page
        if(correct) {
            user.setUserID(getID());
            user.setUserName(userIDText.getText());
            user.setPassword(passwordText.getText());
            user.setCreatedBy(user.getUserName());
            user.setLastUpdatedBy(user.getUserName());
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
            userActivity(true);
        } else {
            infoBoxError(incorrect, "error");
            userActivity(true);
        }

        //This creates
    }

    //This method returns the userID
    int getID() throws SQLException {

        Connection con = DBConnection.startConnection();
        //Create statement object
        DBQuery.setStatement(con);
        //Get statement object
        Statement statement = DBQuery.getStatement();
        //Get username
        String str = userIDText.getText().trim();
        //SQL statement
        String sqlStatement = "SELECT User_ID FROM users WHERE User_name = '" + str + "'";

        //create result set object
        ResultSet result = statement.executeQuery(sqlStatement);

        //creates the id and gets it
        int id = -1;

        while(result.next()) {
            id = result.getInt("User_ID");
        }
        return id;
    }


    //This method checks if the username exist
    boolean checkUserName() throws SQLException {

        if(-1 < getID()) {
            //Get username
            String str = userIDText.getText().trim();
            //Sets name for user
            user.setUserName(str);
            return true;
        }
       return false;
    }

    //This method checks if the password to that username is correct
    boolean checkPassword(int id) throws SQLException {

        //create statement object
        Statement statement = DBQuery.getStatement();

        //write SQL statement
        String sqlStatement = "SELECT password FROM users WHERE user_Id = " + id;

        //create result set object
        ResultSet result = statement.executeQuery(sqlStatement);

        //Gets the password
        String password = passwordText.getText().trim();
        while (result.next()) {
            if (result.getString("password").equals(password)) {
                //Sets password for user
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
     * This appends the info to the document
     */
    public void userActivity(Boolean authorized) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
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
}
