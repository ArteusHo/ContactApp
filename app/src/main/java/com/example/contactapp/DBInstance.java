package com.example.contactapp;

import android.content.Context;
import androidx.room.Room;
public class DBInstance {
    private static PersonDatabase database;

    public static PersonDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            PersonDatabase.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
