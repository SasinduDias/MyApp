package com.example.my;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class log1Activity extends AppCompatActivity {
    EditText et_email,et_password;
    RelativeLayout btn_login;
    TextView tv_sign_up;

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

    }
}