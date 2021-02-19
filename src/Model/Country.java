package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

/**
 * This is the Country class
 */
public class Country {
    private int countryID;
    private String country;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;

    /**
     * Default constructor
     */
    public Country() {}

    /**
     * Constructor with parameters
     * @param countryID the countryID
     * @param country the country's name
     * @param createDate the timestamp of creation
     * @param createdBy name of the creator
     * @param lastUpdate the timestamp of last update
     * @param lastUpdatedBy name of who last updated
     */
    public Country(int countryID, String country, Date createDate, String createdBy, Date lastUpdate,
                      String lastUpdatedBy) {
        setCountryID(countryID);
        setCountry(country);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
    }


    //getters
    /**
     *
     * @return countryID
     */
    public int getCountryID() { return countryID; }

    /**
     *
     * @return country
     */
    public String getCountry() { return country; }

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
     * @param countryID sets countryID
     */
    public void setCountryID(int countryID) { this.countryID = countryID; }

    /**
     *
     * @param country sets country
     */
    public void setCountry(String country) { this.country = country; }

    /**
     *
     * @param createdBy sets createdBy
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     *
     * @param createDate sets createDate
     */
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

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
