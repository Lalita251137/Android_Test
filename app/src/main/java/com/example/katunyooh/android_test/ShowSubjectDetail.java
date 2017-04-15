package com.example.katunyooh.android_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ShowSubjectDetail extends AppCompatActivity {

    private String subjectString;
    private String usernameString;
    private String tag = "10AprilV3";
    private String urlPHP = "https://ranking.studio/demo/app/detail_courses.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subject_detail);

        //Get Value

        getValueIntent();

        showText();


    }//main method

    private void showText() {

        try{

            GetDetailSubject getDetailSubject = new GetDetailSubject(ShowSubjectDetail.this );
            getDetailSubject.execute(subjectString,usernameString,urlPHP);
            String strJSON = getDetailSubject.get();
            Log.d(tag,"JSON ==> " + strJSON);


        }catch (Exception e){

            Log.d(tag,"e showText ==> " +e.toString());
        }
    }


    private void getValueIntent() {
        subjectString = getIntent().getStringExtra("Subject");
        usernameString = getIntent().getStringExtra("USERNAME");
        Log.d(tag,"Subject ==> " + subjectString);
        Log.d(tag,"Username ==> " + usernameString);
    }
}//main
