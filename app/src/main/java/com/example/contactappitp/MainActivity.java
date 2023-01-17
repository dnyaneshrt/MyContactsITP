package com.example.contactappitp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= findViewById(R.id.listview);

        //step 1: get the status

        int status= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

       //check this permission is granted or not?
        if(status== PackageManager.PERMISSION_GRANTED)
        {
            getContactDetails();
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},345);
        }

    }
//step 3
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            getContactDetails();
        }else
        {
            Toast.makeText(this, "user is not allowed to access contacts..", Toast.LENGTH_SHORT).show();
        }
    }

    private void getContactDetails() {

        //step 1: get the object of  getContentResolver()

        ContentResolver contentResolver=getContentResolver();
        //step 2

        @SuppressLint({"NewApi", "LocalSuppress"})
        Cursor cursor= contentResolver.query(ContactsContract.CommonDataKinds.Contactables.CONTENT_URI,null,null,null);

        String[] from=new String[]
                {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};
        int[] to={R.id.tv1,R.id.tv2};
        //step 3
        SimpleCursorAdapter adapter= new SimpleCursorAdapter(this,R.layout.my_design,cursor,from,to);
        listView.setAdapter(adapter);
    }
}