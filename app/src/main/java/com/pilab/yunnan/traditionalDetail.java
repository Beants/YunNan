package com.pilab.yunnan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pilab.yunnan.data.Sql;

import org.json.JSONException;
import org.json.JSONObject;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class traditionalDetail extends AppCompatActivity {

    private static final String TAG = "traditionalDetail";
    private FloatingActionButton fab;
    private ImageView imageView;
    private WebView webView;
    private String username;
    private String user_id;
    private String info_id;
    private String detail;
    private String title;
    private String image;
    private ArrayList<String> star;
    private String star_state;

    private void initUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        user_id = sharedPreferences.getString("_id", "");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional_detail);
        initUser();
        Intent intent = getIntent();

        info_id = intent.getStringExtra("_id");
        detail = intent.getStringExtra("detail");
        title = intent.getStringExtra("title");
        image = intent.getStringExtra("image");
        star = intent.getStringArrayListExtra("star");
        star_state = "0";
        for (int i=0;i<star.size();i++){
//            Log.i(TAG, "onCreate: "+star.get(i)+user_id);
            if (star.get(i).equals(user_id)){
                star_state="1";
            }
        }


//        Log.i(TAG, "onCreate: info_id" + info_id);
//        Log.i(TAG, "onCreate: user_id" + user_id);
//        Log.i(TAG, "onCreate: star" + star.toString()+star.contains(user_id));
//
//        Log.i(TAG, "onCreate: " + star_state + title + image + detail);
        initView(title, image, detail);


    }

    private void initView(String title, String image, String detail) {
        fab = findViewById(R.id.fab);
        if (star_state.equals("0")) {
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        ImageView imageView = findViewById(R.id.image_detail);
        Glide.with(getBaseContext()).load(image).into(imageView);

        WebView webView = findViewById(R.id.info_text);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);

        // 设置标题栏
        collapsingToolbar.setTitle(title);
        collapsingToolbar.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);


        try {
//            Log.i(TAG, "initView: detail"+detail);
            String html = new Markdown4jProcessor().process(detail.replace("\n\r\n\t", ""));
            webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
                Sql.StarService service = retrofit.create(Sql.StarService.class);
                Call<ResponseBody> call = service.star(info_id, user_id);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String jsonstr = null;
                        JSONObject jsonObject = null;
                        String type;
                        String state = star_state;


                        try {
                            jsonstr = new String(response.body().bytes());
                            Log.i(TAG, "onResponse: " + jsonstr);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonObject = new JSONObject(jsonstr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            assert jsonObject != null;
                            type = jsonObject.getString("type");
                            state = jsonObject.getString("state");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (state.equals("0")) {
                            star_state = "0";
                            Snackbar.make(view, "取消点赞", Snackbar.LENGTH_SHORT)
                                    .setAction("好的", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    }).show();
                            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        } else {

                            Snackbar.make(view, "点赞成功", Snackbar.LENGTH_SHORT)
                                    .setAction("好的", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    }).show();
                            star_state = "1";
                            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Snackbar.make(view, "点赞失败", Snackbar.LENGTH_SHORT)
                                .setAction("好的", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                    }
                });



            }
        });

    }
}
