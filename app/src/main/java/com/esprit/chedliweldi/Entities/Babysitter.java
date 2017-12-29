package com.esprit.chedliweldi.Entities;

import com.stfalcon.chatkit.commons.models.IUser;

import java.sql.Date;

import com.esprit.chedliweldi.AppController;

/**
 * Created by PC on 16/11/2017.
 */

public class Babysitter implements IUser {
    private String firstName,lastName,imgURL,email,descr,phone;
    private Date birthDate;
    private float altitude,longitude;
    private int id;

    public Babysitter() {
    }

    public Babysitter(String firstName, String lastName, String imgURL, String email, String descr, Date birthDate, float altitude, float longitude, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgURL = imgURL;
        this.email = email;
        this.descr = descr;
        this.birthDate = birthDate;
        this.altitude = altitude;
        this.longitude = longitude;
        this.id = id;
    }

    public Babysitter(String firstName, String lastName, String imgURL, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgURL = imgURL;
        this.id = id;
    }

    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public String getName() {
        return firstName+" "+lastName;
    }

    @Override
    public String getAvatar() {
        return imgURL;
    }

    public void setId(int id) {
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

    public String getImgURL() {
        return AppController.IMAGE_SERVER_ADRESS+imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Babysitter{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", email='" + email + '\'' +
                ", descr='" + descr + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", altitude=" + altitude +
                ", longitude=" + longitude +
                ", id=" + id +
                '}';
    }
}
