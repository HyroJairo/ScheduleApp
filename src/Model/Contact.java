package Model;

/**
 * This is the Contact class
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Default constructor
     */
    public Contact() {}

    /**
     * Constructor with parameters
     * @param contactId the contactID
     * @param contactName the contact's name
     * @param contactEmail the contact's email
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        setContactID(contactId);
        setContactName(contactName);
        setContactEmail(contactEmail);
    }

    //getters

    /**
     *
     * @return contactID
     */
    public int getContactID() { return contactID; }

    /**
     *
     * @return contactName
     */
    public String getContactName() { return contactName; }

    /**
     *
     * @return contactEmail
     */
    public String getContactEmail() { return contactEmail; }

    //setters

    /**
     *
     * @param contactID sets contactID
     */
    public void setContactID(int contactID) { this.contactID = contactID; }

    /**
     *
     * @param contactName sets contactName
     */
    public void setContactName(String contactName) { this.contactName = contactName; }

    /**
     *
     * @param contactEmail sets contactEmail
     */
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail;

    }
}
