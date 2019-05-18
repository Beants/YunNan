package com.pilab.yunnan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pilab.yunnan.data.Sql;

import org.json.JSONArray;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;


public class SearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button materialButton = view.findViewById(R.id.search_test);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "intiTraditional: " + "Ssssssssssssssssssssssssssss");

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
                Sql.GetInfoAllService service = retrofit.create(Sql.GetInfoAllService.class);
                Call<ResponseBody> call = service.getInfoAll();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String jsonstr = null;
                        try {
                            jsonstr = new String(response.body().bytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "intiTraditional: " + jsonstr);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "intiTraditional: " + t);

                    }
                });
            }
        });


        return view;
    }
}
