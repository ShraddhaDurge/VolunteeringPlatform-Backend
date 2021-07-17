package com.target.VolunteeringPlatform.Response;


public class LoginResponse {
    private int id;
    private String email;
    private String roles;

    public LoginResponse(int id,String email, String roles) {
        super();
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

