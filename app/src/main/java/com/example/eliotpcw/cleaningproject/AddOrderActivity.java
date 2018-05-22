package com.example.eliotpcw.cleaningproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eliotpcw.cleaningproject.Fragments.MainFragment;
import com.example.eliotpcw.cleaningproject.Model.Orders;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class AddOrderActivity extends AppCompatActivity {
    private static final String TAG = "AddOrderActivity";
    public static final int READ_EXTERNAL_STORAGE = 0;
    private static final int  GALLERY_INTENT = 2;
    private ProgressDialog progressDialog;
    private StorageReference storage;
    private ImageView choose_image, order_image;
    private Uri imageUri;
    private EditText title,price, squareM;
    private TextView location, date, time;
    private Button add;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String titleOrder, dateOrder, timeOrder, priceOrder, squareOrder, locationOrder, phoneFromDb, userPhoneNumber, id;
    private Intent intent;
    private int PLACE_PICKER_REQUEST = 1;
    private RelativeLayout relativeLayout;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        progressDialog = new ProgressDialog(this);
        choose_image = findViewById(R.id.choose_image);
        order_image = findViewById(R.id.order_image);
        title = findViewById(R.id.titleOrder);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        price = findViewById(R.id.price);
        squareM = findViewById(R.id.squareM);
        location = findViewById(R.id.addLocation);
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        add = findViewById(R.id.add);
        user = mAuth.getCurrentUser();
        phoneFromDb = user.getPhoneNumber();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };

        addLocation();

        picker();

        addOrders();
    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
    }

    private void addLocation() {

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    intent = builder.build(AddOrderActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Place place = PlacePicker.getPlace(data, this);
                    locationOrder = String.format("Place: %s", place.getAddress());
                    location.setText(locationOrder);
                }
                return;
            case 0:
                if (resultCode == RESULT_OK && data !=null && data.getData() != null)
                {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(order_image);
                }
                return;
        }
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//            Place place = PlacePicker.getPlace(data, this);
//            locationOrder = String.format("Place: %s", place.getAddress());
//            location.setText(locationOrder);
//        }

    }
//            imageUri = data.getData();
//            Picasso.get().load(imageUri).into(order_image);
            //                Place place = PlacePicker.getPlace(data, this);
//                locationOrder = String.format("Place: %s", place.getAddress());
//                location.setText(locationOrder);
//        }
//        if(requestCode == PLACE_PICKER_REQUEST){
//            if(resultCode==RESULT_OK){
//                Place place = PlacePicker.getPlace(data, this);
//                locationOrder = String.format("Place: %s", place.getAddress());
//                location.setText(locationOrder);
//            }
//        }


    private void addOrders() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationOrder = location.getText().toString();
                titleOrder = title.getText().toString();
                dateOrder = date.getText().toString();
                timeOrder = time.getText().toString();
                priceOrder = price.getText().toString();
                squareOrder = squareM.getText().toString();
                userPhoneNumber = user.getPhoneNumber().toString();
                Orders orders = new Orders(titleOrder,locationOrder,dateOrder,timeOrder,squareOrder,priceOrder, userPhoneNumber, im);
                reference.child("ordersToModerator").child(phoneFromDb).push().setValue(orders);
                backToInfo();
            }
        });

    }

    private void backToInfo() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mainFragment);
        transaction.commit();
    }

    private void picker() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddOrderActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, 2018, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                date.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(AddOrderActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener, hour, minute,true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(hourOfDay + ":" + minute);
            }
        };
    }

}
