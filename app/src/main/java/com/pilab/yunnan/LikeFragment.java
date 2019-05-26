package com.pilab.yunnan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.support.constraint.Constraints.TAG;


public class LikeFragment extends Fragment {
    private List<Traditional> traditionalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private String userid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        initUser();
//        注册组件
        initView(view);
        initData();
        return view;
    }

    private void initUser() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("_id", "");

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.like_recycler_view);
        floatingActionButton = view.findViewById(R.id.like_fab_refresh);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        int spanCount=2;
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initData() {
        Toast.makeText(getContext(), "正在加载数据,请稍后...", Toast.LENGTH_LONG).show();
        Log.i(TAG, "intiTraditional: " + "Ssssssssssssssssssssssssssss");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
        Sql.GetInfoLikeService service = retrofit.create(Sql.GetInfoLikeService.class);
        Call<ResponseBody> call = service.getInfolike(userid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                traditionalList.clear();

                String jsonstr = null;
                try {
                    jsonstr = new String(response.body().bytes());
                    Log.i(TAG, "onResponse: jsonstr" + jsonstr);
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
                        info.setInfo(jsonObjectTemp.getString("info").replace("\n", "").replace("\r", "").replace(" ", ""));

                        info.setType(jsonObjectTemp.getString("type"));
                        String ss = jsonObjectTemp.getString("star");
                        ss = ss.replace("[", "");
                        ss = ss.replace("]", "");
                        final List<String> stars = new ArrayList<>();
                        for (int s = 0; i < ss.split(",").length; i++) {
                            stars.add(ss.split(",")[s].replace("\"", ""));
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
