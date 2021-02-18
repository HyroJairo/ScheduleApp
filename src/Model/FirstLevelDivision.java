package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    private int countryID;
    //this contains the list
    private static ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();

    //default constructor
    public FirstLevelDivision() {}

    //gui constructor
    public FirstLevelDivision(int divisionID, String division, Date createDate, String createdBy, Date lastUpdate,
                              String lastUpdatedBy, int countryID) {

    }

    //getters
    public int getDivisionID() { return divisionID; }
    public String getDivision() { return division; }
    public Date getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public Date getLastUpdate() { return lastUpdate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }
    public int getCountryID() { return countryID; }

    //setters
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }
    public void setDivision(String division) { this.division = division; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
    public void setCountryID(int countryID) { this.countryID = countryID; }

    //returns all firstLevelDivisions
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {
        return allFirstLevelDivisions;
    }

}
