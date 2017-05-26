package com.example.seniordesign.smartlighthub.models;

/**
 * Created by jamesharrison on 5/26/17.
 */

public class User
{
    public String Email;
    public String Username;
    public String Name;
    public String password;

    public User()
    {

    }


    public User(String Name, String Username, String Email)
    {
        this.Name = Name;
        this.Username = Username;
        this.Email = Email;

    }


}
