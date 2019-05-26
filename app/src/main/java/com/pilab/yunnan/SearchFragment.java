package com.pilab.yunnan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;

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


public class SearchFragment extends Fragment {
    private android.support.v7.widget.SearchView searchView;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Traditional> traditionalList = new ArrayList<>();
    private TraditionalAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        searchView = view.findViewById(R.id.searchview);
        spinner = view.findViewById(R.id.search_spinner);
        recyclerView = view.findViewById(R.id.search_recycler_view);
        progressBar = view.findViewById(R.id.search_loading);


        int spanCount = 2;
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  Log.i(TAG, "onItemSelected: " + position);

                                                  initDataType(position);
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          }
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i(TAG, "onQueryTextSubmit: " + s);
                progressBar.setVisibility(View.VISIBLE);

                initDataKey(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "onQueryTextChange: " + s);
                return false;
            }
        });
    }
    private void initDataType(int type_) {
        progressBar.setVisibility(View.VISIBLE);
        Log.i(TAG, "initDataType: strat");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
        Sql.GetInfoTypeService service = retrofit.create(Sql.GetInfoTypeService.class);
        Call<ResponseBody> call = service.getInfoType(type_);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (traditionalList != null) {
                traditionalList.clear();
//                }
                JSONArray j;

                String jsonstr = null;
                try {
                    jsonstr = new String(response.body().bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = null;


                    jsonObject = new JSONObject(jsonstr);
                    String type = jsonObject.getString("type");
                    Log.i(TAG, "intiTraditional: " + type);
                    j = jsonObject.getJSONArray("res");


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
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.i(TAG, "initData: traditionalList finshed size:" + traditionalList.size());
                adapter = new TraditionalAdapter(traditionalList);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void initDataKey(String key) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
        Sql.GetInfoKeyService service = retrofit.create(Sql.GetInfoKeyService.class);
        Call<ResponseBody> call = service.getInfoKey(key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (traditionalList != null) {
                    traditionalList.clear();
//                }
                JSONArray j;

                String jsonstr = null;
                try {
                    jsonstr = new String(response.body().bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonObject = null;


                    jsonObject = new JSONObject(jsonstr);
                    String type = jsonObject.getString("type");
                    Log.i(TAG, "intiTraditional: " + type);
                    j = jsonObject.getJSONArray("res");


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
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.i(TAG, "initData: traditionalList finshed size:" + traditionalList.size());
                adapter = new TraditionalAdapter(traditionalList);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
