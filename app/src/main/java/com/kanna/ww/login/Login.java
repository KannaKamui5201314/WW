package com.kanna.ww.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kanna.ww.MainActivity;
import com.kanna.ww.R;

public class Login extends AppCompatActivity {

    private EditText m_EditText_Email;
    private EditText m_EditText_Password;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmailAndPassword()){
                    Intent intent = new Intent();
                    intent.setClass(Login.this, MainActivity.class);
                    Login.this.startActivity(intent);
                }else {
                    Toast.makeText(Login.this, "账号或密码错误，登录失败，请重新登录^-^", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        m_EditText_Email = findViewById(R.id.m_EditText_Email);
        m_EditText_Password = findViewById(R.id.m_EditText_Password);
        login_button = findViewById(R.id.login_button);
    }

    private boolean checkEmailAndPassword() {
        if("root".equals(m_EditText_Email.getText().toString()) && "root".equals(m_EditText_Password.getText().toString())){
            return true;
        }
        return false;
    }
}
