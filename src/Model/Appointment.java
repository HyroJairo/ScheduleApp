package Model;

import java.sql.Timestamp;

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
    //fills in customer
    private Customer customer;

    //default constructor
    public Appointment() {};

    //database constructor
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
    public int getAppointmentID() { return appointmentID; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getType() { return type; }
    public Timestamp getStart() { return start; }
    public Timestamp getEnd() { return end; }
    public Timestamp getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public Timestamp getLastUpdate() { return lastUpdate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    public int getCustomerID() { return customerID; }
    public int getUserID() { return userID; }
    public int getContact() { return contact; }


    //setters
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setType(String type) { this.type = type; }
    public void setStart(Timestamp start) { this.start = start; }
    public void setEnd(Timestamp end) { this.end = end; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public void setUserID(int userID) { this.userID = userID; }
    public void setContact(int contact) { this.contact = contact; }

}
