package com.example.user.jobsportal;

public class Jobs {
    String Job_Title;
    String Job_Description;
    String Type;
    String Address;
    String Posted_by;

    public Jobs(String Job_Title, String Job_Description, String Type, String Address, String Posted_by){
        this.Job_Title = Job_Title;
        this.Job_Description = Job_Description;
        this.Type = Type;
        this.Address = Address;
        this.Posted_by = Posted_by;
    }

    public Jobs(){}

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setJob_Description(String Job_Description) {
        this.Job_Description = Job_Description;
    }

    public void setJob_Title(String Job_Title) {
        this.Job_Title = Job_Title;
    }

    public void setPosted_by(String Posted_by) {
        this.Posted_by = Posted_by;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getType() {
        return Type;
    }

    public String getAddress() {
        return Address;
    }

    public String getJob_Description() {
        return Job_Description;
    }

    public String getJob_Title() {
        return Job_Title;
    }

    public String getPosted_by() {
        return Posted_by;
    }

}
