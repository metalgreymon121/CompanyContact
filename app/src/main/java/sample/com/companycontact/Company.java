package sample.com.companycontact;

/**
 * Created by Marco on 5/25/2017.
 */

public class Company {

    Company company;
    public String storeLogoURL;
    public String phone;
    public String address;
    public String name;
    public String city;
    public String state;
    public String zipcode;
    public String latitude;
    public String longitude;
    public String storeID;

    public String getLogo() {
        return storeLogoURL;
    }


    public String getPhone() {
        return phone;
    }


    public String getAddress() {
        return address;
    }


    public String getCompany_Name() {
        return name;
    }


    public String getCity() {
        return city;
    }


    public String getState() {
        return state;
    }


    public String getZip_Code() {
        return zipcode;
    }


    public String getLatitude() {
        return latitude;
    }


    public String getLongitude() {
        return longitude;
    }


    public String getStore_Id() {
        return storeID;
    }

    public void setStoreLogoURL(String storeLogoURL) {
        this.storeLogoURL = storeLogoURL;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCompany_Name(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip_Code(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setStore_Id(String storeID) {
        this.storeID = storeID;
    }

    @Override
    public String toString() {
        return "{" + "\"address\":" + String.format("\"%s\"", address) + ",\"city\":" + String.format("\"%s\"", city)
                + ",\"name\":" + String.format("\"%s\"", name) + ",\"latitude\":" + String.format("\"%s\"", latitude)
                + ",\"zipcode\":" + String.format("\"%s\"", zipcode) + ",\"storelogoUR\":" + String.format("\"%s\"", storeLogoURL)
                + ",\"phone\":" + String.format("\"%s\"", phone) + ",\"longitude\":" + String.format("\"%s\"", longitude)
                + ",\"storeID\":" + String.format("\"%s\"", storeID) + ",\"state\":" + String.format("\"%s\"", state) + "}";
    }
}
