package com.example.grocery.helperclass;

public class AddressModel {
    String name;
    String cityName;
    String stateName;
    String landMark;
    String phoneNumber;
    String houseNumber;

    public AddressModel() {
    }

    public AddressModel(String name, String cityName, String stateName, String landMark, String phoneNumber, String houseNumber) {
        this.name = name;
        this.cityName = cityName;
        this.stateName = stateName;
        this.landMark = landMark;
        this.phoneNumber = phoneNumber;
        this.houseNumber = houseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
