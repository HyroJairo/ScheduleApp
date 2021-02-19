package Model;


import java.sql.Timestamp;

/**
 * The is the Customer class
 */
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

    /**
     * Default constructor
     */
    public Customer() {};

    /**
     * Constructor with parameters
     * @param customerID the customer's ID
     * @param customerName the customer's Name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param createDate the creation date of the customer record
     * @param createdBy who created the customer record
     * @param lastUpdate the last update of the customer record
     * @param lastUpdatedBy who last updated the customer record
     * @param divisionID the division ID of the customer
     */
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
    /**
     *
     * @return customerID
     */
    public int getCustomerID() { return customerID; }

    /**
     *
     * @return customerName
     */
    public String getCustomerName() { return customerName; }

    /**
     *
     * @return address
     */
    public String getAddress() { return address; }

    /**
     *
     * @return postalCode
     */
    public String getPostalCode() { return postalCode; }

    /**
     *
     * @return phone
     */
    public String getPhone() { return phone; }

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
     * @return lastUpdateBy
     */
    public String getLastUpdatedBy() { return lastUpdatedBy; }

    /**
     *
     * @return divisionID
     */
    public int getDivisionID() { return divisionID; }

    //setters
    /**
     *
     * @param customerID sets CustomerID
     */
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    /**
     *
     * @param customerName sets customerName
     */
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    /**
     *
     * @param address sets address
     */
    public void setAddress(String address) { this.address = address; }

    /**
     *
     * @param postalCode sets postalCode
     */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    /**
     *
     * @param phone sets phone
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     *
     * @param createDate sets createDate
     */
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    /**
     *
     * @param createdBy sets createdBy
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
     * @param divisionID sets divisionID
     */
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }
}