package com.pilab.yunnan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.pilab.yunnan.data.Sql;
import com.pilab.yunnan.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "";
    private EditText et_username;
    private EditText et_password;
    private Button bt_login;
    private ProgressBar pb_login;
    private android.support.constraint.ConstraintLayout constraintLayout;
    private String username;

    private void initUser() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        Log.i(TAG, "initUser: "+username);
        initUser();
//        if (username!=null){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        }
//        else
            initView();

    }


    private void initView() {
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        bt_login = findViewById(R.id.login);
        constraintLayout = findViewById(R.id.constraintLayout);
        pb_login = findViewById(R.id.loading);
        bt_login.setOnClickListener(new View.OnClickListener() {
            private String TAG;


            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Log.d(TAG, "onClick: " + password + "1" + username + "1");
                if (username.equals("") || password.equals("")) {
                    Snackbar.make(constraintLayout, " 请检查输入!", Snackbar.LENGTH_SHORT)
                            .setAction("好的", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    et_password.setText("");
                                }
                            }).show();
                } else {

                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    initPost(user);
                    pb_login.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void initPost(final User user) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Sql.server).build();
        Sql.LoginService service = retrofit.create(Sql.LoginService.class);
        Call<ResponseBody> call = service.login(user.getUsername(), user.getPassword());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String json = "";
                try {
                    json = new String(response.body().bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // get json from server

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String login_type = null;
                String login_data = null;

                try {
                    assert jsonObject != null;
                    login_type = jsonObject.getString("type");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    login_data = jsonObject.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert login_type != null;
                Log.i("Login ", "onResponse: " + login_type + login_data);


                assert login_data != null;
                if (login_type.equals("login") && login_data.equals("password error")) {
                    pb_login.setVisibility(View.INVISIBLE);
                    Snackbar.make(constraintLayout, "密码错误!", Snackbar.LENGTH_SHORT)
                            .setAction("好的", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    et_password.setText("");
                                }
                            }).show();

                } else {
                    user.set_id(login_data);
                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("username", user.getUsername());
                    editor.putString("password", user.getPassword());
                    editor.putString("_id", user.get_id());
                    editor.apply();//提交修改
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(constraintLayout, "网络错误!", Snackbar.LENGTH_SHORT)
                        .setAction("好的", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                et_password.setText("");
                            }
                        }).show();
            }
        });

    }
}
