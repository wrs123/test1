package com.ofg.test1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetValueActivity extends Activity {
    private Button m_btnSetActOKReturnSecondAct=null;
    private Button m_btnSetActCancelReturnSecondAct=null;
    private EditText m_etxtSetActReturnSecondAct=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test10getvalue);
        m_btnSetActOKReturnSecondAct=(Button)findViewById(R.id.button1);
        m_btnSetActCancelReturnSecondAct=(Button)findViewById(R.id.button2);
        m_etxtSetActReturnSecondAct=(EditText)findViewById(R.id.returnValue);
        //确定按钮执行事件
        m_btnSetActOKReturnSecondAct.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String strSetInfo=m_etxtSetActReturnSecondAct.getText().toString();
                Uri uriSetInfo=Uri.parse(strSetInfo);
                Intent inttSetInfo=new Intent(null,uriSetInfo);
                setResult(RESULT_OK,inttSetInfo);
                finish();
            }
        });
        //取消按钮执行事件
        m_btnSetActCancelReturnSecondAct.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                setResult(RESULT_CANCELED,null);
                finish();
            }
        });
    }
}
