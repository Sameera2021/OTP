package com.example.my_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView lblPath ;
    private Button btn_choose ,btn_enc , btn_dec;
    private String fileName ;
    private static final int REQUST_CODE = 43;
    private Intent myFileIntent ;
    private Encryption E = new Encryption();
    private Decryption D = new Decryption();
    private MyDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lblPath = findViewById(R.id.lbl_filePath);
        btn_choose = findViewById(R.id.btn_chooseFile);
        btn_enc = findViewById(R.id.btn_encrypt);
        btn_dec = findViewById(R.id.btn_decrypt);

        db = new MyDatabase(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            String [] perm = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,perm,1);
            Toast.makeText(MainActivity.this,"Permision Done",Toast.LENGTH_LONG).show();
        }

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        btn_enc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblPath.getText().equals("Hello World!"))
                    Toast.makeText(MainActivity.this,"You Should Choose file first",Toast.LENGTH_LONG).show();
                else  if(db.check_file(fileName))
                    Toast.makeText(MainActivity.this,"File is Encryppted ",Toast.LENGTH_SHORT).show();
                else
                {

                    File fin = new File(fileName);
                    //Generate OTP key
                    int n = (int) fin.length();
                    if(n > 65)
                        n = 64 ; //if the file length more than 64 , encrypt the file header

                    byte[] key = Key_Generate(n);


                    if( E.Encrypt(fileName,key) ) {
                        //insert the key with path in the data base
                        Info data = new Info(new String(key) , fileName);
                        boolean res = db.isert_key(data);
                        if(res)
                            Toast.makeText(MainActivity.this, "Encryption is Done", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Error in TOP key ",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this,"Encryption Did not done",Toast.LENGTH_LONG).show();
                   
                }
            }
        });

        btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lblPath.getText().equals("Hello World!"))
                    Toast.makeText(MainActivity.this,"You Should Choose file first",Toast.LENGTH_LONG).show();
                else
                {
                    //Get the OTP key from database


                    if(! db.check_file(fileName))
                        Toast.makeText(MainActivity.this,"File Did not Encryppted",Toast.LENGTH_SHORT).show();
                    else{
                        byte[] key = db.get_key(fileName).getBytes();
                        if(D.Decrypt(fileName,key)){
                            Toast.makeText(MainActivity.this,"Decryption Done",Toast.LENGTH_SHORT).show();
                            db.delete_file(fileName);
                        }//end if decrypt
                        else
                            Toast.makeText(MainActivity.this,"File did not Decrypt",Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });




        }

    public void search(){
        myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("*/*");
        myFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(myFileIntent,REQUST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri u = data.getData();
                fileName = FileUtils.getPath(MainActivity.this, u);
                //String path = u.getPath();
                lblPath.setText(fileName);
            }
        }//inner if
    }//end function

    private byte[] Key_Generate ( int n ){
        char oval[] = new char[n];
        String all_str = "ACGT";
        Random ran = new Random();
        for(int i = 0 ; i<n ; i++ ){
            int radNo = ran.nextInt(all_str.length());
            oval[i] = all_str.charAt(radNo);
        }
        System.out.println("OTP = " + new String (oval));
        byte[] b = new byte[n];
        for(int i = 0 ; i<n ; i++ ){
            b[i] = (byte) oval[i];
        }
        return b;
    }//end key generating function


    }