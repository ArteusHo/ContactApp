package com.example.contactapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PersonClass.class}, version = 1)
public abstract class PersonDatabase extends RoomDatabase{
    public abstract PersonDAO personDAO();
}
