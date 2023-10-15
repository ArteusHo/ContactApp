package com.example.contactapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class AddFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CAMERA_PERMISSION_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private String fileName;
    private File pfp;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private String currentPhotoPath;
    ImageView btnProfile;  // Declare btnProfile as a class variable

    EditText number;

    Button btnSave;

    Button btnBack;
    PersonDAO personDAO;

    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        PersonDAO personDAO = DBInstance.getDatabase(getActivity().getApplicationContext()).personDAO();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        MenuData menuDataViewModel = new ViewModelProvider(getActivity()).get(MenuData.class);

        // Initialize btnProfile here
        btnProfile = view.findViewById(R.id.profile);
        number = view.findViewById(R.id.number);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        EditText firstname = view.findViewById(R.id.firstname);
        EditText lastname = view.findViewById(R.id.lastname);
        EditText email = view.findViewById(R.id.email);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String first_name = firstname.getText().toString();
                String last_name = lastname.getText().toString();
                String name = first_name+last_name;
                String phone_number = number.getText().toString();
                String user_email = email.getText().toString();


                PersonClass person = new PersonClass(name, phone_number, user_email, "/document/primary:picture/"+fileName);
                personDAO.insert(person);

                menuDataViewModel.setOption(1);




            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDataViewModel.setOption(1);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        // Initialize the activity result launcher
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                                Bundle extras = data.getExtras();
                                    Bitmap photoBitmap = (Bitmap) extras.get("data");
                                    btnProfile.setImageBitmap(photoBitmap);

                                    saveImageToPhone(photoBitmap);
                        }
                    }
                });

        return view;
    }

    private void saveImageToPhone(Bitmap photoBitmap){
        File pfpstorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String userpfp = number.getText().toString();
        fileName = "IMG " + userpfp + ".jpg";

        File imageFile = new File(pfpstorage, fileName);

        try{
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScannerIntent.setData(Uri.fromFile(imageFile));

            // Use sendBroadcast(Intent, String) with a permission string
            requireContext().sendBroadcast(mediaScannerIntent, "your.custom.permission");
            Toast.makeText(requireContext(), "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Toast.LENGTH_LONG).show();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void captureImage() {
        // Check camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission if not granted
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION_CODE);
        } else {
            // Camera permission is granted, launch the camera intent
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(intent);
        }
    }
    class MyFilenameFilter implements FilenameFilter {

        String initials;

        // constructor to initialize object
        public MyFilenameFilter(String initials)
        {
            this.initials = initials;
        }

        // overriding the accept method of FilenameFilter
        // interface
        public boolean accept(File dir, String name)
        {
            return name.startsWith(initials);
        }
    }


    // ... (other methods)

}