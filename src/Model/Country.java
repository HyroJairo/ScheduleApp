package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Country {
    private int countryID;
    private String country;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;
    //this contains the list
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    //default constructor
    public Country() {}

    //gui constructor
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
    public int getCountryID() { return countryID; }
    public String getCountry() { return country; }
    public Date getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public Date getLastUpdate() { return lastUpdate; }
    public String getLastUpdatedBy() { return lastUpdatedBy; }

    //setters
    public void setCountryID(int countryID) { this.countryID = countryID; }
    public void setCountry(String country) { this.country = country; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }

    //returns all countries
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }
}
