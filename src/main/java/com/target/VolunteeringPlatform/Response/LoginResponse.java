package com.target.VolunteeringPlatform.Response;

import java.util.List;

public class LoginResponse {
    private int id;
    private String email;
    private List<String> roles;

    public LoginResponse(int id,String email, List<String> roles) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

