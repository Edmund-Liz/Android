package android.bignerdranch.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {
    @POST("login")
    @FormUrlEncoded
    Call<ApiResult> loginTest(@Field("username")String username,@Field("password")String password);
}
