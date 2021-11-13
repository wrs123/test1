package com.ofg.test1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Test11Activity extends Activity {

    private Button doCal = null;
    private TextView line1 = null;
    private TextView line2 = null;
    private TextView line3 = null;
    private TextView result = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test11);
        doCal = (Button)findViewById(R.id.doCal);
        line1 = (TextView)findViewById(R.id.line1);
        line2 = (TextView)findViewById(R.id.line2);
        line3 = (TextView)findViewById(R.id.line3);
        result = (TextView)findViewById(R.id.result);

        doCal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                double l1 = Double.parseDouble(line1.getText().toString());
                double l2 = Double.parseDouble(line2.getText().toString());
                double l3 = Double.parseDouble(line3.getText().toString());

                if((l1+l2>l3) && (l1+l3>l2) &&(l3+l2 >l1)){
                    double p=l1+l2+l3;
                    double s=Math.sqrt(p*(p-l1)*(p-l2)*(p-l3));
                    result.setText("三角形面积:"+s);
                }else {
                    result.setText("不是三角型");
                }

            }
        });
    }


}
