package com.example.contactapp;
import android.app.Person;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PersonDAO {
    @Insert
    void insert(PersonClass ...person);
    @Insert
    void insertList(List<PersonClass> list);

    @Update
    void update(PersonClass... person);

    @Delete
    void delete(PersonClass ...person);

    @Query("select * from PersonTable")
    List<PersonClass> getAllContacts();


}
