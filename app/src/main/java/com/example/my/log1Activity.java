package com.example.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class log1Activity extends AppCompatActivity {
    EditText et_email,et_password;
    RelativeLayout btn_login;
    TextView tv_sign_up;
    String passwordPattern="[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}";
    String emailPattern="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log1);

        et_email=findViewById(R.id.edt_email);
        et_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        tv_sign_up=findViewById(R.id.tv_signup);

        String sign_up_text="<font>Don't have an account?</font> <font color=#E26912><b> SIGNUP</b></font>";
        tv_sign_up.setText(Html.fromHtml(sign_up_text));
        
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=et_email.getText().toString().trim();
                password=et_password.getText().toString().trim();
//                Toast.makeText(log1Activity.this, "E mail is " + email + "" + "Password is" + password , Toast.LENGTH_SHORT).show();

                validateFielde(email,password);

            }
        });

    }

    private void validateFielde(String email, String password) {

        if(!(email.isEmpty()) && !(password.isEmpty())){
           if(email.matches(emailPattern)){
               if(password.matches(passwordPattern)){
                   Toast.makeText(log1Activity.this, "Successful" , Toast.LENGTH_SHORT).show();
                   LogUser(email,password);

               }else{
                   et_password.setError("Password should have minimum 8 letters");
               }
           }else{
               et_email.setError("Email is incorrect");
           }

        }else{
            Toast.makeText(log1Activity.this, "Please fill the fields " , Toast.LENGTH_SHORT).show();

        }
    }

    private void LogUser(String email, String password) {
        //firebase,API
        startActivity(new Intent(log1Activity.this,MainActivity.class));
    }
}