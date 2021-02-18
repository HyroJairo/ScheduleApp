package Model;

import java.util.Date;

//This is the class of users
public class User {


    //this is the modified version below
    private int userID;
    private String userName;
    private String password;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;

    //default constructor
    public User() {}

    //gui constructor
    public User(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(password);
    }

    //database constructor
    public User(int userID, String userName, String password, Date createDate, String createdBy, Date lastUpdate,
                String lastUpdatedBy) {
        setUserID(userID);
        setUserName(userName);
        setPassword(password);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
    }

    //getters
    public int getUserID() { return userID; }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public Date getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public Date getLastUpdate() { return lastUpdate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }


    //setters
    public void setUserID(int userID) { this.userID = userID; }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }


}
