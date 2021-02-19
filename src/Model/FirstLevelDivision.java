package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

/**
 * This is the FirstLevelDivision class
 */
public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    /**
     * Default constructor
     */
    public FirstLevelDivision() {}

    /**
     * Constructor with parameters
     * @param divisionID the division ID
     * @param division the division's name
     * @param createDate the creation date of the division record
     * @param createdBy who created the division record
     * @param lastUpdate the last update of the division record
     * @param lastUpdatedBy who last updated the division record
     * @param countryID the country's ID
     */
    public FirstLevelDivision(int divisionID, String division, Date createDate, String createdBy, Date lastUpdate,
                              String lastUpdatedBy, int countryID) {
    }

    //getters
    /**
     *
     * @return ID
     */
    public int getDivisionID() { return divisionID; }

    /**
     *
     * @return division
     */
    public String getDivision() { return division; }

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

    /**
     *
     * @return countryID
     */
    public int getCountryID() { return countryID; }

    //setters

    /**
     *
     * @param divisionID the divisionID
     */
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }

    /**
     *
     * @param division the division
     */
    public void setDivision(String division) { this.division = division; }

    /**
     *
     * @param createDate the creation date
     */
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    /**
     *
     * @param createdBy the name of the person who created it
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    /**
     *
     * @param lastUpdate the last update
     */
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    /**
     *
     * @param lastUpdatedBy the name of the person who last updated it
     */
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }

    /**
     *
     * @param countryID the country's ID
     */
    public void setCountryID(int countryID) { this.countryID = countryID; }

}
