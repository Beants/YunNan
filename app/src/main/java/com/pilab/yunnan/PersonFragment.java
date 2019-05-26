package com.pilab.yunnan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PersonFragment extends Fragment {
    private LinearLayoutCompat mylike;
    private LinearLayoutCompat changepwd;
    private LinearLayoutCompat checkupdate;
    private LinearLayoutCompat about;
    private LinearLayoutCompat logout;
    private TextView username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        initView(view);


        return view;
    }

    private void initView(final View view) {
        username = view.findViewById(R.id.user_username);
        final SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username", ""));


        mylike = view.findViewById(R.id.mylike);
        mylike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigation.setSelectedItemId(MainActivity.navigation.getMenu().getItem(3).getItemId());
//                Intent intent = new Intent(getActivity(), LikeFragment.class);
//                startActivity(intent);
            }
        });
//        changepwd = view.findViewById(R.id.changepwd);
        checkupdate = view.findViewById(R.id.checkupdate);
        checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "您已经是最新版本", Snackbar.LENGTH_SHORT)
                        .setAction("好的", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
        about = view.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "about", Snackbar.LENGTH_SHORT)
                        .setAction("好的", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });


    }
}
