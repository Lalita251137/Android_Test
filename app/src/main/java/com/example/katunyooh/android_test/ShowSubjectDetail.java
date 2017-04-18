package com.example.katunyooh.android_test;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowSubjectDetail extends AppCompatActivity {

    private TextView txtLat, txtLng;
    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean bolGPS, bolNetwork;
    private String subjectString;
    private String usernameString;
    private String tag = "10AprilV3";
    private String urlPHP = "https://ranking.studio/demo/app/detail_courses.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subject_detail);


//Bind Widget
        bindWidget();

        //Open Service
        openService();


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
        Toast.makeText(ShowSubjectDetail.this,"เช็คชื่อไม่สำเร็จ กรุณาตรวจสอบพื้นที่", Toast.LENGTH_SHORT).show();

    }

    public void logoutApp(View view){
        Intent intent = new Intent(ShowSubjectDetail.this, MainActivity.class);
        finish();
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();

        bolGPS = objLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (!bolGPS) {
            bolNetwork = objLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!bolNetwork) {
                Intent objIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(objIntent);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        setupAll();

    }

    private void setupAll() {

        objLocationManager.removeUpdates(objLocationListener);
        String strLat = "Unknow";
        String strLng = "Unknow";

        Location objNetworkLocation = requesUpdateFromProvider(LocationManager.NETWORK_PROVIDER, "Network not Connected");
        if (objNetworkLocation != null) {
            strLat = String.format("%.7f", objNetworkLocation.getLatitude());
            strLng = String.format("%.7f", objNetworkLocation.getLongitude());
        }

        Location objGPSLocation = requesUpdateFromProvider(LocationManager.GPS_PROVIDER, "GPS not Work");
        if (objGPSLocation != null) {
            strLat = String.format("%.7f", objGPSLocation.getLatitude());
            strLng = String.format("%.7f", objGPSLocation.getLongitude());
        }

        txtLat.setText(strLat);
        txtLng.setText(strLng);
    }

    @Override
    protected void onStop() {
        super.onStop();

        objLocationManager.removeUpdates(objLocationListener);

    }

    public Location requesUpdateFromProvider(final String strProvider, String strError) {

        Location objLocation = null;
        if (objLocationManager.isProviderEnabled(strProvider)) {

            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener);
            objLocation = objLocationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("bsru", "Error = " + strError);
        }

        return objLocation;
    }

    public final LocationListener objLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            txtLat.setText(String.format("%.7f", location.getLatitude()));
            txtLng.setText(String.format("%.7f", location.getLongitude()));

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void openService() {
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);
    }

    private void bindWidget() {
        txtLat = (TextView) findViewById(R.id.txtLat);
        txtLng = (TextView) findViewById(R.id.txtLong);
    }


}//main
