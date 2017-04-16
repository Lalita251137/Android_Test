package com.example.katunyooh.android_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
        TextView txtNameStudent = (TextView) findViewById(R.id.txtNameStudent);
        TextView txtLastNameStudent = (TextView) findViewById(R.id.txtLastNameStudent);
        TextView txtIdStudent = (TextView) findViewById(R.id.txtIdStudent);
        TextView txtNameSubject = (TextView) findViewById(R.id.txtNameSubject);
        TextView txtNameTeacher = (TextView) findViewById(R.id.txtNameTeacher);

        try{

            GetDetailSubject getDetailSubject = new GetDetailSubject(ShowSubjectDetail.this );
            getDetailSubject.execute(subjectString,usernameString,urlPHP);
            String strJSON = getDetailSubject.get();
            Log.d(tag,"JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            final String[] member_nameStrings = new String[jsonArray.length()];
            String[] member_lastnameStrings = new String[jsonArray.length()];
            String[] member_codeStrings = new String[jsonArray.length()];
            String[] subject_nameStrings = new String[jsonArray.length()];
            String[] subject_tutorStrings = new String[jsonArray.length()];



            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                member_nameStrings[i] = jsonObject.getString("member_name");
                member_lastnameStrings[i] = jsonObject.getString("member_lastname");
                member_codeStrings[i] = jsonObject.getString("member_code");
                subject_nameStrings[i] = jsonObject.getString("subject_name");
                subject_tutorStrings[i] = jsonObject.getString("subject_tutor");

                txtNameStudent.setText(member_nameStrings[i]);
                txtLastNameStudent.setText(member_lastnameStrings[i]);
                txtIdStudent.setText(member_codeStrings[i]);
                txtNameSubject.setText(subject_nameStrings[i]);
                txtNameTeacher.setText(subject_tutorStrings[i]);

            }//for



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

    public void checkLocation(View view){
        Toast.makeText(ShowSubjectDetail.this,"เช็คชื่อไม่สำรเร็จ กรุณาตรวจสอบพื้นที่ \nเพื่อเช็คชื่ออีกครั้ง", Toast.LENGTH_SHORT).show();

    }
}//main
