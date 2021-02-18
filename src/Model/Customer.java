package Model;


import java.sql.Timestamp;

public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    //default constructor
    public Customer() {};

    //constructor
    public Customer(int customerID, String customerName, String address, String postalCode, String phone,
                    Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID) {
        setCustomerID(customerID);
        setCustomerName(customerName);
        setAddress(address);
        setPostalCode(postalCode);
        setPhone(phone);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
        setDivisionID(divisionID);
    }

    //getters
    public int getCustomerID() { return customerID; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public String getPostalCode() { return postalCode; }
    public String getPhone() { return phone; }
    public Timestamp getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public Timestamp getLastUpdate() { return lastUpdate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    public int getDivisionID() { return divisionID; }

    //setters
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAddress(String address) { this.address = address; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }
}