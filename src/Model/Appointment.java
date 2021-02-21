package Model;

import java.sql.Timestamp;

/**
 * @author max morales
 * This is the Appointment class
 */
public class Appointment {

    private int appointmentID; //auto generated
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID; //auto generated
    private int contact; // A contact name is assigned to an appointment using a drop-down menu or combo box
    private Customer customer;

    /**
     * Default constructor
     */
    public Appointment() {};

    /**
     * Constructor with parameters
     * @param appointmentID the id
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start time
     * @param end the end time
     * @param createDate the timestamp for createDate
     * @param createdBy who created the appointment
     * @param lastUpdate the timestamp for lastUpdate
     * @param lastUpdatedBy when was the last person who updated it
     * @param customerID the customerID
     * @param userID  the userID
     * @param contact the contact ID
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate,
                       String lastUpdatedBy, int customerID, int userID, int contact) {
        setAppointmentID(appointmentID);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
        setCustomerID(customerID);
        setUserID(userID);
        setContact(contact);
    }

    //getters

    /**
     *
     * @return appointmentID
     */
    public int getAppointmentID() { return appointmentID; }

    /**
     *
     * @return title
     */
    public String getTitle() { return title; }

    /**
     *
     * @return description
     */
    public String getDescription() { return description; }

    /**
     *
     * @return location
     */
    public String getLocation() { return location; }

    /**
     *
     * @return type
     */
    public String getType() { return type; }

    /**
     *
     * @return start
     */
    public Timestamp getStart() { return start; }

    /**
     *
     * @return end
     */
    public Timestamp getEnd() { return end; }

    /**
     *
     * @return createDate
     */
    public Timestamp getCreateDate() { return createDate; }

    /**
     *
     * @return createdBy
     */
    public String getCreatedBy() { return createdBy; }

    /**
     *
     * @return lastUpdate
     */
    public Timestamp getLastUpdate() { return lastUpdate; }

    /**
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() { return lastUpdatedBy; }

    /**
     *
     * @return customerID
     */
    public int getCustomerID() { return customerID; }

    /**
     *
     * @return userID
     */
    public int getUserID() { return userID; }

    /**
     *
     * @return contact
     */
    public int getContact() { return contact; }


    //setters
    /**
     *
     * @param appointmentID sets appointmentID
     */
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    /**
     *
     * @param title sets title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     *
     * @param description sets description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     *
     * @param location sets location
     */
    public void setLocation(String location) { this.location = location; }

    /**
     *
     * @param type sets type
     */
    public void setType(String type) { this.type = type; }

    /**
     *
     * @param start sets start
     */
    public void setStart(Timestamp start) { this.start = start; }

    /**
     *
     * @param end sets end
     */
    public void setEnd(Timestamp end) { this.end = end; }

    /**
     *
     * @param createDate sets createDate
     */
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    /**
     *
     * @param createdBy sets createdBY
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     *
     * @param lastUpdate sets lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }

    /**
     *
     * @param lastUpdatedBy sets lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }

    /**
     *
     * @param customerID sets customerID
     */
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    /**
     *
     * @param userID sets userID
     */
    public void setUserID(int userID) { this.userID = userID; }

    /**
     *
     * @param contact sets contact
     */
    public void setContact(int contact) { this.contact = contact; }

}
