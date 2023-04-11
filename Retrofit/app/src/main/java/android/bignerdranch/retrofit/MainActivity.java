package android.bignerdranch.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button mButton1;
    private Button mButton2;
    private TextView mTextView;

    private Retrofit mRetrofit;
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8899/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);

        mTextView=findViewById(R.id.text);

        mButton2=findViewById(R.id.token);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText("获取到token："+getToken());
            }
        });

        mButton1=findViewById(R.id.test_btn);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ApiResult> apiResult=mApi.loginTest("2022214305","lzq1836491284...");
                apiResult.enqueue(new Callback<ApiResult>() {
                    @Override
                    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                       String token=(response.body().getToken());
                       mTextView.setText(token);
                       saveToken(token);
                    }

                    @Override
                    public void onFailure(Call<ApiResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"Failed!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void saveToken(String token){
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token",token);
        editor.apply();
    }

    public String getToken(){
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context .MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");
        return token;
    }

}