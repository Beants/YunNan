package com.pilab.yunnan.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class Sql {
    public static String server = "http://39.96.222.175:5000";
//    public static String server = "http://192.168.2.193:5000/";

    public interface LoginService {
        @FormUrlEncoded
        @POST("/login")
        Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);
    }

    public interface StarService {
        @FormUrlEncoded

        @POST("/star")
        Call<ResponseBody> star(@Field("info_id") String info_id, @Field("user_id") String user_id);
    }

    public interface GetInfoAllService {
        @POST("/get_info_all")
        Call<ResponseBody> getInfoAll();
    }

    public interface GetInfoLikeService {
        @FormUrlEncoded
        @POST("/get_users_like")
        Call<ResponseBody> getInfolike(@Field("user_id") String user_id);
    }
    public interface GetInfoKeyService {
        @FormUrlEncoded
        @POST("/get_info_key")
        Call<ResponseBody> getInfoKey(@Field("key") String key);
    }
    public interface GetInfoTypeService {
        @FormUrlEncoded
        @POST("/get_info_type")
        Call<ResponseBody> getInfoType(@Field("type") String type);
    }

}

