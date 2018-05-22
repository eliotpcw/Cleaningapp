package com.example.eliotpcw.cleaningproject.Fragments;

import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eliotpcw.cleaningproject.Common.Common;
import com.example.eliotpcw.cleaningproject.MainActivity;
import com.example.eliotpcw.cleaningproject.Model.UserInformation;
import com.example.eliotpcw.cleaningproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * Created by eliotpcw on 05.04.2018.
 */

public class signup extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference ref;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText userphone, usersms;
    private View view;
    private Button sendAll;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private int Type = 0;
    private String userID;
    private String PhoneNumber, UserName, phoneFromDb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup, container, false);

        userphone = view.findViewById(R.id.userphone);
        usersms = view.findViewById(R.id.usersms);
        sendAll = view.findViewById(R.id.sendAll);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        usersms.setVisibility(View.INVISIBLE);
        if(user!=null){
            phoneFromDb = user.getPhoneNumber();

        }
        sendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber = userphone.getText().toString();
                ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(PhoneNumber.equals(phoneFromDb)){
                            toastMessage("wtf");
                        } else{
                            requestPhoneFromDb();
                            UserInformation userInformation = dataSnapshot.child(userphone.getText().toString()).getValue(UserInformation.class);
                            Common.currentUser = userInformation;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                toastMessage("error");
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;

                Type = 1;

                userphone.setVisibility(View.INVISIBLE);
                usersms.setVisibility(View.VISIBLE);
                sendAll.setText("Подтвердить код");
                sendAll.setEnabled(true);

                // ...
            }

        };
        return view;
    }

    private void requestPhoneFromDb() {
        if (Type == 0) {

            userphone.setEnabled(false);
            sendAll.setEnabled(false);

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    PhoneNumber,        // Phone number to verify
                    60,               // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    getActivity(),      // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        } else {
            sendAll.setEnabled(false);
            usersms.setVisibility(View.VISIBLE);

            String verificationCode = usersms.getText().toString();

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            toastMessage("error");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


    private void toastMessage(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }
}
