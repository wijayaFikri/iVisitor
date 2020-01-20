package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Person {
    private String id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String dob;
    private String position;
    private String address;
    @SerializedName("office_desk_location")
    private String officeDeskLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfficeDeskLocation() {
        return officeDeskLocation;
    }

    public void setOfficeDeskLocation(String officeDeskLocation) {
        this.officeDeskLocation = officeDeskLocation;
    }

    @NonNull
    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", position='" + position + '\'' +
                ", address='" + address + '\'' +
                ", officeDeskLocation='" + officeDeskLocation + '\'' +
                '}';
    }
}
