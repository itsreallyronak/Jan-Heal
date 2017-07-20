package com.sodevan.bhamaheal.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sodevan.bhamaheal.Interface.APIservice;
import com.sodevan.bhamaheal.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class profile extends AppCompatActivity {
    public static final String MyPREFERENCES="login_prefs";
    SharedPreferences sharedPreferences;
    String bhID,age,name,uid;
    Retrofit retrofit;
    APIservice apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView tv1,tv2,tv3,tv4 ;

        tv1 = (TextView)findViewById(R.id.named) ;
        tv2 = (TextView)findViewById(R.id.aged) ;
        tv3 = (TextView)findViewById(R.id.bidu) ;
        tv4 = (TextView)findViewById(R.id.uidu) ;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit=new Retrofit.Builder()
                .baseUrl(getString(R.string.hosturl))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiservice=retrofit.create(APIservice.class);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this) ;
        bhID=sharedPreferences.getString("add","");
        age=sharedPreferences.getString("age","");
        name=sharedPreferences.getString("name","");
        uid=sharedPreferences.getString("uid","");

        tv1.setText("Name : "+name);
        tv2.setText("Age : "+age);
        tv3.setText("Bh-Id : "+bhID);
        tv4.setText("U-Id : "+uid);


        Log.d("TAGa",bhID);
//        retrofit=new Retrofit.Builder()
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call=apiservice.writebasic(bhID,uid,name);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                Intent in=new Intent(profile.this,AddMedical.class);
                startActivity(in);
            }
        });
    }

}
