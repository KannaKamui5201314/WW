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
                if(utils.isLogin()){
                    try {
                        Thread.sleep(1000);
                        Intent intent = new Intent();
                        intent.setClass(Hint.this, MainActivity.class);
                        Hint.this.startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        Thread.sleep(1000);
                        Intent intent = new Intent();
                        intent.setClass(Hint.this, Login.class);
                        Hint.this.startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
