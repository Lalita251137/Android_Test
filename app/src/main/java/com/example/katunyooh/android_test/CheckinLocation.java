package com.example.katunyooh.android_test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by katunyoo on 20/4/2560.
 */

public class CheckinLocation extends AsyncTask<String,Void,String> {

    private Context context;

    public CheckinLocation(Context context) {
        this.context =context;
    }

    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("subject_name",params[0])
                    .add("Uname",params[1])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[2]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        }catch (Exception e)
        {
            Log.d("10AprilV5","e doIn ==>" + e.toString());
            return null;
        }
    }
}
