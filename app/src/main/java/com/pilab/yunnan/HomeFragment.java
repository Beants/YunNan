package com.pilab.yunnan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pilab.yunnan.data.Sql;
import com.pilab.yunnan.data.Traditional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment {
    private List<Traditional> traditionalList = new ArrayList<>();
    private String username;
    private String _id;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private void initUser() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        _id = sharedPreferences.getString("_id", "");
    }

//    @Override
//    public void onResume() {
//        initData(recyclerView);
//        super.onResume();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUser();

        recyclerView = view.findViewById(R.id.recycler_view);
        floatingActionButton=view.findViewById(R.id.fab_home);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(recyclerView);
            }
        });

        int spanCount = 2;
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);


        initData(recyclerView);


        Log.i(TAG, "onCreateView: " + Arrays.toString(Objects.requireNonNull(traditionalList.toArray())));


        return view;
    }

    // get data from server and refresh ui
    private void initData(final RecyclerView recyclerView) {
        Toast.makeText(getContext(), "正在加载数据,请稍后...", Toast.LENGTH_LONG).show();

        Log.i(TAG, "intiTraditional: " + "Ssssssssssssssssssssssssssss");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
        Sql.GetInfoAllService service = retrofit.create(Sql.GetInfoAllService.class);
        Call<ResponseBody> call = service.getInfoAll();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                traditionalList.clear();

                String jsonstr = null;
                try {
                    jsonstr = new String(response.body().bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = new JSONObject(jsonstr);

                    String type = jsonObject.getString("type");
                    Log.i(TAG, "intiTraditional: " + type);
                    JSONArray j = jsonObject.getJSONArray("res");
                    Log.i(TAG, "initData: 开始生成traditionalList");

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject jsonObjectTemp = new JSONObject(j.get(i).toString());
                        Traditional info = new Traditional();
                        info.setId(jsonObjectTemp.getString("_id"));
                        info.setDetail(jsonObjectTemp.getString("detail"));
                        info.setImage(jsonObjectTemp.getString("title"));
                        info.setTitle(jsonObjectTemp.getString("image"));
                        info.setInfo(jsonObjectTemp.getString("info").replace("\n", "").replace("\r", "").replace(" ",""));

                        info.setType(jsonObjectTemp.getString("type"));
                        String ss = jsonObjectTemp.getString("star");
                        ss = ss.replace("[", "");
                        ss = ss.replace("]", "");
                        final List<String> stars = new ArrayList<>();
                        for (int s = 0; i < ss.split(",").length; i++) {
                            stars.add(ss.split(",")[s].replace("\"",""));
                        }
                        info.setStar(stars);
//                        Log.i(TAG, "onResponse: " + info.getId());
                        boolean add = traditionalList.add(info);
//                        Log.i(TAG, "onResponse: info.add" + add);
                    }

                    Log.i(TAG, "initData: traditionalList finshed size:" + traditionalList.size());
                    TraditionalAdapter adapter = new TraditionalAdapter(traditionalList);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "intiTraditional: " + t);

            }
        });


    }


}
