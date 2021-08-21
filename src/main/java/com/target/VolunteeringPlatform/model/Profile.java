package com.target.VolunteeringPlatform.model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name ="profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="profile_id")
    private int pid;

    @Size(min = 10 ,max =10)
    @Column(name ="mobile_number")
    private String mobile_number;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @NotNull(message = "Please provide a date.")
    @Column(name ="DOB")
    private Date dob;

    @Column(name ="about")
    @NotBlank
    private String about;

    @Column(name ="gender")
    @NotBlank
    private String gender;

    @Column(name ="location")
    @NotBlank
    private String location;

    @Column(name ="address")
    @NotBlank
    private String address;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_profile",
            joinColumns = @JoinColumn(name = "profiles_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "user_user_id")
    )
    private User user;

    public Profile() {
    }

    public Profile( @Size(min = 10, max = 10) String mobile_number,
                    @NotNull(message = "Please provide a date.") Date dob, String about, String gender, String location,
                    String address) {
        super();
        this.mobile_number = mobile_number;
        this.dob = dob;
        this.about = about;
        this.gender = gender;
        this.location = location;
        this.address = address;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile [mobile_number="+ mobile_number + ", dob=" + dob + ", about=" + about + ", gender=" + gender + ", location="
                + location + ", address=" + address + "]";
    }
}