package Model;

public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    //default constructor
    public Contact() {}

    //gui constructor
    public Contact(int contactId, String contactName, String contactEmail) {
        setContactID(contactId);
        setContactName(contactName);
        setContactEmail(contactEmail);
    }

    //getters
    public int getContactID() { return contactID; }
    public String getContactName() { return contactName; }
    public String getContactEmail() { return contactEmail; }

    //setters
    public void setContactID(int contactID) { this.contactID = contactID; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail;

    }
}
