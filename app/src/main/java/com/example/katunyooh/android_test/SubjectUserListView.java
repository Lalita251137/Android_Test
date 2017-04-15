package com.example.katunyooh.android_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SubjectUserListView extends AppCompatActivity {

    //Explicit
    private String usernameString;
    private String tag = "10AprilV2";
    private String urlPHP = "https://ranking.studio/demo/app/courses.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_user_list_view);

        //Get Valute From Intent

        getValuteFromIntent();

        //Get Course

        getCourse();


    } //main Method

    private void getCourse() {
        try {

            GetCourseByUsername getCourseByUsername = new GetCourseByUsername(SubjectUserListView.this);
            getCourseByUsername.execute(usernameString, urlPHP);
            String strJSON = getCourseByUsername.get();
            Log.d(tag, "e getCourse ==>" + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            final String[] subjectString = new String[jsonArray.length()];
            String[] descripStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                subjectString[i] = jsonObject.getString("subject_name");
                descripStrings[i] = jsonObject.getString("subject_description");


            }//for

            //Create ListView
            CourseAdapter courseAdapter = new CourseAdapter(SubjectUserListView.this,
                    subjectString,descripStrings);

            ListView listView = (ListView) findViewById(R.id.livCourse);
            listView.setAdapter(courseAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(SubjectUserListView.this,ShowSubjectDetail.class);
                    intent.putExtra("Subject",subjectString[position]);
                    intent.putExtra("USERNAME",usernameString);
                    startActivity(intent);

                }
            });


        } catch (Exception e) {
            Log.d(tag, "e getCourses ==>" + e.toString());
        }

    }// getCourses

    private void getValuteFromIntent() {
        usernameString = getIntent().getStringExtra("USERNAME");
        Log.d(tag, "username ==>" + usernameString);
    }


} //mainclass
