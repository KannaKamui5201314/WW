package com.kanna.ww.hintView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kanna.ww.MainActivity;
import com.kanna.ww.R;
import com.kanna.ww.login.Login;
import com.kanna.ww.utils;

public class Hint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hint);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent();
                    if(utils.isLogin()){
                        intent.setClass(Hint.this, MainActivity.class);
                    }else {
                        intent.setClass(Hint.this, Login.class);
                    }
                    Hint.this.startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
