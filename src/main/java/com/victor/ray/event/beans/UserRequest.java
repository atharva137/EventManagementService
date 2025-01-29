package com.victor.ray.event.beans;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserRequest {


    @NotNull(message = "mobileNumber cannot be null")
    @NotEmpty(message = "mobile number cannot be empty")
    private  String mobileNumber;

    private String username;

    @Email(message = "Invalid emailId")
    private String emailId;

    private String role;

    @NotNull(message =  "profile complete field cannot be null")
    private Boolean profileComplete;

    public Boolean getProfileComplete() {
        return profileComplete;
    }

    public void setProfileComplete(Boolean profileComplete) {
        this.profileComplete = profileComplete;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
