package com.example.my_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogActivity extends AppCompatActivity {
    private EditText ed_pass;
    private Button btn_done,btn_res;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor ;
    private String PASSWORD_KEY ="password";
    private String pass;
    Intent in ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ed_pass = findViewById(R.id.ed_pass);
        btn_done = findViewById(R.id.btn_done);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit() ;
        pass = sp.getString(PASSWORD_KEY,"null");

        btn_res =findViewById(R.id.btn_res);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ch = ed_pass.getText().toString();

                if(ch.isEmpty())
                    Toast.makeText(LogActivity.this,"Should Enter password",Toast.LENGTH_SHORT).show();


                else if(pass.equals("null")){
                    if(Check(ch)){
                        editor.putString(PASSWORD_KEY,ch);
                        editor.apply();
                        finish();
                        //Intent
                        in = new Intent(LogActivity.this,MainActivity.class);
                        startActivity(in);

                    }//end if check
                    else{
                        Toast.makeText(LogActivity.this,"Weak password",Toast.LENGTH_SHORT).show();
                        ed_pass.setText("");
                    }//end else
                }//end else if pass null
                else if ( pass !=null ){
                    if(  ch.equals (pass)  )
                    {
                        //Intent
                        in = new Intent(LogActivity.this,MainActivity.class);
                        startActivity(in);
                    }
                    else{
                        Toast.makeText(LogActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        ed_pass.setText("");
                    }

                }//end if else
            }
        });
        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_res.setText(pass);
            }
        });

    }
    private boolean Check(String text) {
        boolean result = false;
        int cap = 0, smal = 0, digit = 0, sym = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) >= 65 && text.charAt(i) <= 90)//A-Z
                cap++;
            else if (text.charAt(i) >= 97 && text.charAt(i) <= 122)//a-z
                smal++;
            else if (text.charAt(i) >= 48 && text.charAt(i) <= 57)//0-9
                digit++;
            else if ((text.charAt(i) >= 33 && text.charAt(i) <= 46) ||
                    (text.charAt(i) >= 58 && text.charAt(i) <= 64) ||
                    (text.charAt(i) >= 91 && text.charAt(i) <= 96))
                sym++;

            if (cap >= 1 && smal >= 1 && digit >= 1 && sym >= 1) {
                Toast.makeText(LogActivity.this, "Strong password", Toast.LENGTH_LONG).show();
                result = true;
                break;
            }
        }//end for
        if (result == false)
            Toast.makeText(LogActivity.this, "Weak Password", Toast.LENGTH_LONG).show();
        return result;
    }//enf check

}
