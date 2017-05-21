package com.example.katunyooh.android_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    static final Integer LOCATION = 0x1;

    public static final String USER_NAME = "USERNAME";

    String username;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

    }

    public void invokeLogin(View view) {
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();

//        login(username,password);

        try {
            String urlPHP = "https://ranking.studio/demo/app/login.php";
            CheckUserPassword checkUserPassword = new CheckUserPassword(MainActivity.this);
            checkUserPassword.execute(username, password, urlPHP);
            String result = checkUserPassword.get();
            Log.d("10AprilV1", "Result ==> " + result);

            if (Boolean.parseBoolean(result)) {

                //Intent to list_corese
                Intent intent = new Intent(MainActivity.this, SubjectUserListView.class);
                intent.putExtra(USER_NAME, username);
                finish();
                startActivity(intent);

            } else {
                Toast.makeText(MainActivity.this, "Username Password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.d("10AprilV1", "e invoke ==>" + e.toString());
        }

    }

protected void onStart()
{
    super.onStart();
    askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
}

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();

        }
    }

}