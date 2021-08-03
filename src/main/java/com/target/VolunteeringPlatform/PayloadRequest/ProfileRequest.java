package com.target.VolunteeringPlatform.PayloadRequest;

import java.util.Date;
import javax.validation.constraints.NotBlank;
public class ProfileRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String mobile_number;

    @NotBlank
    private Date dob;

    @NotBlank
    private String about;

    @NotBlank
    private String gender;

    @NotBlank
    private String location;

    @NotBlank
    private String address;

    public ProfileRequest() {

    }

    public ProfileRequest(@NotBlank String mobile_number, @NotBlank Date dob, @NotBlank String about,
                          @NotBlank String gender, @NotBlank String location, @NotBlank String address) {
        super();
        this.mobile_number = mobile_number;
        this.dob = dob;
        this.about = about;
        this.gender = gender;
        this.location = location;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
