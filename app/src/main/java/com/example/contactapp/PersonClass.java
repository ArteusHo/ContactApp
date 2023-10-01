package com.example.contactapp;

public class PersonClass {
    int id;
    String name;
    String phone;
    String email;
    int pfp;

    public PersonClass()
    {
        id=0;
        name="";
        phone="";
        email="";
        pfp=0;
    }

    public PersonClass(int pid,String pname,String pphone, String pemail,int ppfp)
    {
        id=pid;
        name=pname;
        phone=pphone;
        email=pemail;
        pfp=ppfp;
    }
}
