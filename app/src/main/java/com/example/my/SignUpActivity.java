package com.example.my;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
 TextView tv_login;
 RelativeLayout bt_signup;
 EditText et_username,et_email,et_contact_no,et_password;
    String passwordPattern="[a-zA-Z0-9\\\\!\\\\@\\\\#\\\\$]{8,24}";
    String emailPattern="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String phonePattern="^[+]?[0-9]{10,13}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_login=findViewById(R.id.tv_login_screen);
        bt_signup=findViewById(R.id.btn_sign_up);
        et_email=findViewById(R.id.edt_email);
        et_username=findViewById(R.id.edt_user);
        et_contact_no=findViewById(R.id.edt_contact_number);
        et_password=findViewById(R.id.edt_password);
        String sign_up_text="<font>Already have an account?</font> <font color=#E26912><b> LogIn</b></font>";
        tv_login.setText(sign_up_text);
        
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
        
        
    }

    private void validateFields() {
        //do validation here
        String user_name,email,phone,password;
        user_name=et_username.getText().toString();
        email=et_email.getText().toString();
        phone=et_contact_no.getText().toString();
        password=et_password.getText().toString();

        if(!(email.isEmpty()) && !(password.isEmpty()) && !(phone.isEmpty())){
            if(email.matches(emailPattern)){
                if(password.matches(passwordPattern)){
                    if(phone.matches(phonePattern)) {
                        Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                    LogUser(email,password);
                        saveUser(user_name, email, phone, password);
                    }else{
                        et_contact_no.setError("enter phone no correctly");
                    }

                }else{
                    et_password.setError("Password should have minimum 8 letters");
                }
            }else{
                et_email.setError("Email is incorrect");
            }

        }else{
            Toast.makeText(SignUpActivity.this, "Please fill the fields " , Toast.LENGTH_SHORT).show();

        }



    }

    private void saveUser(String user_name, String email, String phone, String password) {
       //firebase code

    }
}