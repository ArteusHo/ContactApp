package com.example.contactapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName="PersonTable")
public class PersonClass {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "Name")
    String name;
    @ColumnInfo(name = "Phone")
    String phone;
    @ColumnInfo(name = "Email")
    String email;
    @ColumnInfo(name = "Pfp")
    int pfp;

    public PersonClass()
    {
        name="";
        phone="";
        email="";
        pfp=0;
    }

    public PersonClass(String pname,String pphone, String pemail,int ppfp)
    {
        name=pname;
        phone=pphone;
        email=pemail;
        pfp=ppfp;
    }
}
