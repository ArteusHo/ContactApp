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
