package com.kanna.ww;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kanna.ww.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView m_ListView;//消息列表容器
    private EditText m_EditText;//输入框
    private Button m_Button;//发送按钮
    private List<Message> list = new ArrayList<>();
    private MessageAdapter adapter;
    private MyReceiver myReceiver;
    private SimpleChatClient simpleChatClient;

    private static final String GET_MESSAGE = "android.net.GET_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(TAG + ":"+"onCreate");
        setContentView(R.layout.activity_main);
        initView();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GET_MESSAGE);
        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, intentFilter);

        simpleChatClient = new SimpleChatClient(this);
        simpleChatClient.connect();

        adapter = new MessageAdapter(this, R.layout.item_layout, list);
        m_ListView.setAdapter(adapter);

        m_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEditTextMessage();
            }
        });

        //回车键发送消息
        m_EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    sendEditTextMessage();
                }
                return false;
            }
        });
    }

    private void sendEditTextMessage(){
        if(m_EditText.getText().toString().length() != 0){//阻止发送空消息
            Message m_Every_Message = new Message("resist", m_EditText.getText().toString());
            list.add(m_Every_Message);
            simpleChatClient.sendData(m_EditText.getText().toString());
            m_EditText.setText("");//清空输入框内容
            adapter.notifyDataSetChanged();//刷新listView内容
        }
    }

    private void initView(){
        m_ListView = findViewById(R.id.m_ListView);
        m_EditText = findViewById(R.id.m_EditText);
        m_Button = findViewById(R.id.m_Button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(TAG + ":"+"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(TAG + ":"+"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println(TAG + ":"+"onStop");
        hintKeyBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(TAG + ":"+"onDestroy");
        //simpleChatClient.closeConnect();
        unregisterReceiver(myReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(GET_MESSAGE.equals(intent.getAction())){
                String playerName = intent.getStringExtra("playerName");
                String MsgString = intent.getStringExtra("message");
                Message m_Every_Message = new Message(playerName, MsgString);
                list.add(m_Every_Message);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
