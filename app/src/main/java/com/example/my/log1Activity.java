package com.example.my;

import androidx.appcompat.app.AppCompatActivity;

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
    String email,password;

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

                email=et_email.getText().toString().trim();
                password=et_password.getText().toString().trim();
                Toast.makeText(log1Activity.this, "E mail is " + email + "" + "Password is" + password , Toast.LENGTH_SHORT).show();

                if(email.isEmpty()){
                    et_email.setError("Please fill the field!");
                }else{
                    if(password.isEmpty()){
                        et_password.setError("please fill the field");
                    }else{
                        Toast.makeText(log1Activity.this, "ok" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}