package com.example.eliotpcw.cleaningproject.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eliotpcw.cleaningproject.AddOrderActivity;
import com.example.eliotpcw.cleaningproject.Common.Common;
import com.example.eliotpcw.cleaningproject.Model.UserInformation;
import com.example.eliotpcw.cleaningproject.R;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * Created by eliotpcw on 12.04.2018.
 */

public class MainFragment extends Fragment {
    private FloatingActionButton fab;
    private FirebaseUser user;
    private UserInformation userInformation;
    private TextView fullName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainfragment, container, false);
        fullName = view.findViewById(R.id.FullName);
//        fullName.setText(Common.currentUser.getName());
        fab = view.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddOrder addOrder = new AddOrder();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
//                transaction.replace(R.id.container, addOrder);
//                transaction.addToBackStack(null);
//                transaction.commit();
                Intent intent = new Intent(getActivity(), AddOrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
