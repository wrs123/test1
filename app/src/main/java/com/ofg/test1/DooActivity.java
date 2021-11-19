package com.ofg.test1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DooActivity extends AppCompatActivity {

    DynamicReceiver dynamicReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doo);
        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("panhouye");
        dynamicReceiver = new DynamicReceiver();
        //注册广播接收
        registerReceiver(dynamicReceiver,filter);
    }
    //按钮点击事件
    public void send2(View v){
        Intent intent = new Intent();
        intent.setAction("panhouye");
        intent.putExtra("sele","潘侯爷");
        sendBroadcast(intent);
    }
    /*动态注册需在Acticity生命周期onPause通过
     *unregisterReceiver()方法移除广播接收器，
     * 优化内存空间，避免内存溢出
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(new DynamicReceiver());
    }
    //通过继承 BroadcastReceiver建立动态广播接收器
    class DynamicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //通过土司验证接收到广播
            Toast t = Toast.makeText(context,"动态广播："+ intent.getStringExtra("sele"), Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP,0,0);//方便录屏，将土司设置在屏幕顶端
            t.show();
        }
    }
}
