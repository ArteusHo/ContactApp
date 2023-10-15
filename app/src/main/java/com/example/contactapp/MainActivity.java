package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AddFragment addFragment=new AddFragment();
    ContactFragment contactFragment= new ContactFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonDAO personDAO = DBInstance.getDatabase(getApplicationContext()).personDAO();
        Button btnAdd=findViewById(R.id.btnAdd);
        Button btnImport=findViewById(R.id.btnImport);

        //loadContactFragment();
        MenuData menudataviewmodel = new ViewModelProvider(this).get(MenuData.class);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddFragment();
            }
        });
        menudataviewmodel.option.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if(menudataviewmodel.getOption() == 1){
                    loadContactFragment();
                }
                else if(menudataviewmodel.getOption() == 2){
                    loadAddFragment();
                }

            }
        });

    }

    private void loadContactFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.main_container);
        try{
            if(frag == null)
            {
                fm.beginTransaction().add(R.id.main_container, contactFragment).commit();
            }
            else
            {
                fm.beginTransaction().replace(R.id.main_container, contactFragment).commit();
            }
        }
        catch(IllegalStateException e){}
    }
    private void loadAddFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.main_container);
        try{
            if(frag == null)
            {
                fm.beginTransaction().add(R.id.main_container, addFragment).commit();
            }
            else
            {
                fm.beginTransaction().replace(R.id.main_container, addFragment).commit();
            }
        }
        catch(IllegalStateException e){}
    }
}