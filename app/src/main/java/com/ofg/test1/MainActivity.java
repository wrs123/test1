package com.ofg.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Button m_btnGetActReturnMainAct=null;
    private Button m_btnBroadCastMainAct=null;
    private EditText m_etxtGetActReturnMainAct=null;
    private EditText m_etxtBroadCastMainAct=null;
    private static final int INFORMATIONACT=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test10);
        m_btnGetActReturnMainAct=(Button)findViewById(R.id.getActivityValue);  //获取activity返回数据按钮
        m_btnBroadCastMainAct=(Button)findViewById(R.id.returnValue); //传递信息按钮
        m_etxtGetActReturnMainAct=(EditText)findViewById(R.id.actReturnValue); //获取返回的数据
        m_etxtBroadCastMainAct=(EditText)findViewById(R.id.getReturnValue); //获取传递的数据

        m_btnGetActReturnMainAct.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //获取Activity返回值启动SecondActivity
                Intent inttMainAct=new Intent(MainActivity.this,GetValueActivity.class);
                startActivityForResult(inttMainAct,INFORMATIONACT);
            }
        });
        m_btnBroadCastMainAct.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //传递信息值
                Intent inttMainAct=new Intent(MainActivity.this,DisplayValueActivity.class);
                String strMessage=m_etxtBroadCastMainAct.getText().toString();
                inttMainAct.putExtra("strMessage", strMessage);
                startActivity(inttMainAct);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==INFORMATIONACT&& resultCode==RESULT_OK)
        {
            Uri uriInfo=data.getData();
            m_etxtGetActReturnMainAct.setText(uriInfo.toString());
        }
        else
        {
            m_etxtGetActReturnMainAct.setText("未获取到任何信息");
        }
    }




}