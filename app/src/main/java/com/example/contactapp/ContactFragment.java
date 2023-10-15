package com.example.contactapp;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class ContactFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button btnAdd;
    Button btnImport;
    TextView txtTest;
    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        MenuData menuDataViewModel = new ViewModelProvider(getActivity()).get(MenuData.class);

        Button btnAdd=view.findViewById(R.id.btnAdd);
        btnImport = view.findViewById(R.id.btnImport);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {menuDataViewModel.setOption(2);}
        });


        RecyclerView recycview = (RecyclerView) view.findViewById(R.id.recView);
        recycview.setLayoutManager(new GridLayoutManager(getActivity(), GridLayoutManager.VERTICAL, GridLayoutManager.HORIZONTAL, false));



        return view;
    }

    public void importContacts(){
        ArrayList<PersonClass> contactList=new ArrayList<PersonClass>();
        ArrayList<String> testList=new ArrayList<String>();
        Cursor cursor=null;
        ContentResolver contentResolver=getActivity().getContentResolver();
        try{
            cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
            if(cursor!=null && cursor.getCount()>0)
            {
                while(cursor.moveToNext())
                {
                    PersonClass contact=new PersonClass();
                    //String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                }
            }
        }catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        cursor.close();
    }

    public void import2(){
        String[] queryFields = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor cursor =  getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, queryFields, null, null, null);
        try{
            if(cursor!=null && cursor.getCount()>0)
            {
                while(cursor.moveToNext())
                {
                    PersonClass contact=new PersonClass();
                    @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //this.id=cursor.getInt(0);
                    //String name=cursor.getString(1);
                    txtTest.setText(name);
                }
            }
        }catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }
        cursor.close();
    }

    public void testf()
    {
        txtTest.setText("ff");
    }

}