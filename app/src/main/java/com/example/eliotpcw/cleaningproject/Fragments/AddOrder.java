//package com.example.eliotpcw.cleaningproject.Fragments;
//
//import android.app.Activity;
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import com.example.eliotpcw.cleaningproject.Model.Orders;
//import com.example.eliotpcw.cleaningproject.R;
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
//import com.google.android.gms.common.GooglePlayServicesRepairableException;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.squareup.picasso.Picasso;
//
//import java.util.Calendar;
//
//import static android.app.Activity.RESULT_OK;
//
///**
// * Created by eliotpcw on 11.04.2018.
// */
//
//public class AddOrder extends Fragment {
//    private static final String TAG = "AddOrder";
//    private static final int PICK_IMG_REQUEST = 1;
//    private ImageView choose_image, imageOrder;
//    private Uri imageUri;
//    private EditText title,price, squareM;
//    private View view;
//    private TextView location, date, time;
//    private Button add;
//    private DatePickerDialog.OnDateSetListener dateSetListener;
//    private TimePickerDialog.OnTimeSetListener timeSetListener;
//    private TimePickerDialog timePickerDialog;
//    private DatePickerDialog datePickerDialog;
//    private Calendar calendar;
//    private FirebaseAuth mAuth;
//    private FirebaseDatabase database;
//    private DatabaseReference reference;
//    private FirebaseUser user;
//    private String titleOrder, dateOrder, timeOrder, priceOrder, squareOrder, locationOrder, phoneFromDb, userPhoneNumber, id;
//    private Intent intent;
//    private int PLACE_PICKER_REQUEST = 1;
//    private RelativeLayout relativeLayout;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.addorder, container, false);
////        choose_image = view.findViewById(R.id.chooseImg);
////        imageOrder = view.findViewById(R.id.imageOrder);
//        title = view.findViewById(R.id.titleOrder);
//        date = view.findViewById(R.id.date);
//        time = view.findViewById(R.id.time);
//        price = view.findViewById(R.id.price);
//        squareM = view.findViewById(R.id.squareM);
//        location = view.findViewById(R.id.addLocation);
//        relativeLayout = view.findViewById(R.id.relLayout);
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        reference = database.getReference();
//        add = view.findViewById(R.id.add);
//        user = mAuth.getCurrentUser();
//        phoneFromDb = user.getPhoneNumber();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//
//                }
//                // ...
//            }
//        };
//
//        addLocation();
//
//        picker();
//
//        addOrders();
//
//        return view;
//    }
//
//    private void addLocation() {
//
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                try {
//                    intent = builder.build(getActivity());
//                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PLACE_PICKER_REQUEST){
//            if(resultCode==RESULT_OK){
//                Place place = PlacePicker.getPlace(data, getContext());
//                locationOrder = String.format("Place: %s", place.getAddress());
//                location.setText(locationOrder);
//            }
//        }
//    }
//
//
//    private void addOrders() {
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locationOrder = location.getText().toString();
//                titleOrder = title.getText().toString();
//                dateOrder = date.getText().toString();
//                timeOrder = time.getText().toString();
//                priceOrder = price.getText().toString();
//                squareOrder = squareM.getText().toString();
//                userPhoneNumber = user.getPhoneNumber().toString();
//                Orders orders = new Orders(titleOrder,locationOrder,dateOrder,timeOrder,squareOrder,priceOrder, userPhoneNumber);
//                reference.child("ordersToModerator").child(phoneFromDb).push().setValue(orders);
//                backToInfo();
//            }
//        });
//
//    }
//
//    private void backToInfo() {
//        MainFragment mainFragment = new MainFragment();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
//        transaction.replace(R.id.container, mainFragment);
//        transaction.commit();
//        Snackbar.make(relativeLayout, "Ваше объявление будет рассмотрена модератором", Snackbar.LENGTH_SHORT).show();
//    }
//
//    private void picker() {
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, 2018, month, day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//
//        dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month +1;
//                date.setText(dayOfMonth + "/" + month + "/" + year);
//            }
//        };
//
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//                int hour = calendar.get(Calendar.HOUR);
//                int minute = calendar.get(Calendar.MINUTE);
//                timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, timeSetListener, hour, minute,true);
//                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                timePickerDialog.show();
//            }
//        });
//
//        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                time.setText(hourOfDay + ":" + minute);
//            }
//        };
//}
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
//
//}
