package com.pilab.yunnan.data;

import org.json.JSONArray;

import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class Sql {
        public static String server = "http://39.96.222.175:5000";
//    public static String server = "http://192.168.2.193:5000/";

    public interface LoginService {
        @POST("/login")
        Call<User> login(@Body User user);
    }

    public interface StarService {
        @POST("/star")
        Call<String> star(@Field("info_id") String info_id, @Field("user_id") String user_id);
    }

    public interface GetInfoAllService {
        @POST("/get_info_all")
        Call<ResponseBody> getInfoAll();
    }
}

