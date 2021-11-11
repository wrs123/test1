package com.ofg.test1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayValueActivity extends Activity {

    private EditText m_etxtBroadCastSecondAct=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test10displayvalue);
        m_etxtBroadCastSecondAct=(EditText)findViewById(R.id.returnValue);
//获取传递过来的信息值
        Intent inttSecondAct=getIntent();
        String strMessage=inttSecondAct.getStringExtra("strMessage");
        m_etxtBroadCastSecondAct.setText(strMessage);
    }


}
