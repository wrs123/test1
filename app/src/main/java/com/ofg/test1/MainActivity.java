package com.ofg.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private EditText name;
    private EditText pwd;
    private TextView rt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test10);
        name=(EditText)findViewById(R.id.name);
        pwd=(EditText)findViewById(R.id.pwd);
        rt=(TextView)findViewById(R.id.rt);
    }
    public void click_login(View v) {
        String s1=name.getText().toString();
        String s2=pwd.getText().toString();
        rt.setText("你输入的用户名："+s1+"|你输入的密码："+s2);
    }
    public void click_reset(View v) {
        name.setText("");
        pwd.setText("");
        rt.setText("");
    }



}