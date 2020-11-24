package com.example.user.jobsportal;

public class Users {

    String name;
    String email;
    String password;
    String profile_type;

    public Users(String name, String email, String password, String profile_type){
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile_type = profile_type;
    }

    public Users(){}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile_type(String profile_type) {
        this.profile_type = profile_type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile_type() {
        return profile_type;
    }
}
