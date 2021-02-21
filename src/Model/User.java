package Model;

import java.util.Date;

/**
 * @author max morales
 * This is the User class
 */
public class User {

    //this is the modified version below
    private int userID;
    private String userName;
    private String password;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;

    /**
     * Default constructor
     */
    public User() {}

    /**
     * Constructor with parameters for username and password
     * @param userName username
     * @param password password
     */
    public User(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(password);
    }

    /**
     * Constructor with parameters that reflects database
     * @param userID userID
     * @param userName username
     * @param password password
     * @param createDate date of creation
     * @param createdBy who created it
     * @param lastUpdate date of last update
     * @param lastUpdatedBy who last updated it
     */
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
    /**
     *
     * @return userID
     */
    public int getUserID() { return userID; }

    /**
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return createDate
     */
    public Date getCreateDate() { return createDate; }

    /**
     *
     * @return createdBy
     */
    public String getCreatedBy() { return createdBy; }

    /**
     *
     * @return lastUpdate
     */
    public Date getLastUpdate() { return lastUpdate; }

    /**
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() { return lastUpdatedBy; }


    //setters
    /**
     *
     * @param userID sets userID
     */
    public void setUserID(int userID) { this.userID = userID; }

    /**
     *
     * @param userName sets username
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     *
     * @param password sets password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     *
     * @param createDate sets create
     */
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    /**
     *
     * @param createdBy sets createdBy
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     *
     * @param lastUpdate sets lastUpdate
     */
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    /**
     *
     * @param lastUpdatedBy sets lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }


}
