package com.example.contactapp;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;


public class ContactFragment extends Fragment {




    Button btnAdd;
    Button btnImport;
    TextView txtTest;
    List<PersonClass> personList;
    int contactId;
    private MenuData menuData;
    private RecyclerView recycview;
    private Adapter contactAdapter;
    private List<PersonClass> contactList;
    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;
    PersonDAO personDAO;

    ActivityResultLauncher<Intent> pickContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String contactName = "";
                    String emailAddress;
                    String phoneNumber;
                    //get name
                    Uri contactUri = data.getData();
                    String[] queryFields = new String[]{
                            ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME
                    };
                    Cursor cname = requireActivity().getContentResolver().query(
                            contactUri, queryFields, null, null, null);
                    try {
                        if (cname.getCount() > 0) {
                            cname.moveToFirst();
                            this.contactId = cname.getInt(0); // ID first
                            contactName = cname.getString(1); // Name second
                        }
                    } finally {
                        cname.close();
                    }
                    //get email
                    Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                    String[] queryFieldsa = new String[]{
                            ContactsContract.CommonDataKinds.Email.ADDRESS
                    };
                    String whereClause = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?";
                    String[] whereValues = new String[]{
                            String.valueOf(this.contactId)
                    };
                    Cursor cemail = requireActivity().getContentResolver().query(
                            emailUri, queryFieldsa, whereClause, whereValues, null);
                    try {
                        cemail.moveToFirst();
                        do {
                            emailAddress = cemail.getString(0);
                        }
                        while (cemail.moveToNext());

                    } finally {
                        cemail.close();
                    }
                    //get phone number
                    Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    String[] queryFieldsb = new String[]{
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };

                    String whereClausea = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
                    Cursor cphone = requireActivity().getContentResolver().query(
                            phoneUri, queryFieldsb, whereClausea, whereValues, null);
                    //
                    try {
                        cphone.moveToFirst();
                        do {
                            phoneNumber = cphone.getString(0);
                        }
                        while (cphone.moveToNext());

                    } finally {
                        cphone.close();
                    }
                    PersonClass person = new PersonClass(contactName, phoneNumber, emailAddress, null);
                    personDAO.insert(person);
                    contactList.add(person);
                    contactList = personDAO.getAllContacts();
                    //contactAdapter = new Adapter(contactList, menuData);
                    recycview.setAdapter(contactAdapter);
                    contactAdapter.notifyDataSetChanged();

                }
            });


    int id;
    Button btnAdd;
    Button btnImport;
    TextView txtTest;


    public ContactFragment() {

    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        MenuData menuDataViewModel = new ViewModelProvider(getActivity()).get(MenuData.class);

        btnAdd=view.findViewById(R.id.btnAdd);
        btnImport = view.findViewById(R.id.btnImport);
        txtTest=view.findViewById(R.id.txtTest);
        RecyclerView recycview = (RecyclerView) view.findViewById(R.id.recView);

        personDAO = DBInstance.getDatabase(getActivity().getApplicationContext()).personDAO();
        contactList = personDAO.getAllContacts();
        contactAdapter = new Adapter(contactList);
        recycview.setLayoutManager(new GridLayoutManager(getActivity(), GridLayoutManager.VERTICAL, GridLayoutManager.HORIZONTAL, false));
        recycview.setAdapter(contactAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {menuDataViewModel.setOption(2);}
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view)
            {

            }
        });

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

    private void onPickContactButtonClick(View view) {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_READ_CONTACT_PERMISSION);
        pickContact();
    }

    private void pickContact() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        pickContactLauncher.launch(intent);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Contact Reading Permission Granted",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}