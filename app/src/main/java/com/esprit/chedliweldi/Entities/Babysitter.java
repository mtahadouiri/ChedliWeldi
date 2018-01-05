package com.esprit.chedliweldi.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.stfalcon.chatkit.commons.models.IUser;

import java.sql.Date;

import com.esprit.chedliweldi.AppController;

/**
 * Created by PC on 16/11/2017.
 */

/*public class Babysitter implements IUser {
    private String firstName;
    private String lastName;
    private String imgURL;
    private String email;
    private String descr;
    private String phone;
    private Date birthDate;
    private float altitude;
    private float longitude;
    private int id;
    private float distance;

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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}*/
public class Babysitter implements IUser, Parcelable {
    private String firstName;
    private String lastName;
    private String imgURL;
    private String email;
    private String descr;
    private String phone;
    private Date birthDate;
    private float altitude;
    private float longitude;
    private int id;
    private float distance;

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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    protected Babysitter(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        imgURL = in.readString();
        email = in.readString();
        descr = in.readString();
        phone = in.readString();
        long tmpBirthDate = in.readLong();
        birthDate = tmpBirthDate != -1 ? new Date(tmpBirthDate) : null;
        altitude = in.readFloat();
        longitude = in.readFloat();
        id = in.readInt();
        distance = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(imgURL);
        dest.writeString(email);
        dest.writeString(descr);
        dest.writeString(phone);
        dest.writeLong(birthDate != null ? birthDate.getTime() : -1L);
        dest.writeFloat(altitude);
        dest.writeFloat(longitude);
        dest.writeInt(id);
        dest.writeFloat(distance);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Babysitter> CREATOR = new Parcelable.Creator<Babysitter>() {
        @Override
        public Babysitter createFromParcel(Parcel in) {
            return new Babysitter(in);
        }

        @Override
        public Babysitter[] newArray(int size) {
            return new Babysitter[size];
        }
    };
}
