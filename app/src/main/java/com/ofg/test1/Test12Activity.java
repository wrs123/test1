package com.ofg.test1;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ofg.test1.tools.BtUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Test12Activity extends Activity {

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;//声明的一个成员变量pairedDevices,它是一个set集合对象，可以放的是BluetoothDevice.由于集合是一种容器，相当于是一个瓶子，那么java提供了一种功能，就是在尖括号中声明这种容器是什么类型的容器，比如说是酒瓶，只能装酒。这是集合对象泛型的概念。
    private ListView lv1;
    private ListView lv2;
//    private ImageButton refresh;
    private List<Map<String, String>> deviceList = new ArrayList<Map<String, String>>();
    Switch blueToothSwitch;
    Switch visibleSwitch;
    ListItemAdapter adapter1;
    ListItemAdapter deviceListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test12);

//        refresh = (ImageButton) findViewById(R.id.refresh);
        lv1 =(ListView)findViewById(R.id.listView1);
        lv2 =(ListView)findViewById(R.id.listView2);
        BA =BluetoothAdapter.getDefaultAdapter();
        blueToothSwitch = (Switch) findViewById(R.id.BlueToothSwitch);
        visibleSwitch = (Switch) findViewById(R.id.VisibleSwitch);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        //广播注册
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);


        //已配对设备列表
        pairedDevices = BA.getBondedDevices();
        ArrayList list =new ArrayList();
        for(BluetoothDevice bt : pairedDevices){
            Map<String, String> result = new HashMap<>();

            result.put("name", bt.getName());
            result.put("address", bt.getAddress());
            result.put("icon", String.valueOf(BtUtils.getDeviceType(bt.getBluetoothClass())));
            list.add(result);
        }
        adapter1 =new ListItemAdapter(Test12Activity.this, list);
        lv1.setAdapter(adapter1);

        initPermission();

        //第一次进入页面刷新可用设备列表
        BA.startDiscovery();
        deviceListAdapter = new ListItemAdapter(Test12Activity.this, deviceList);
        lv2.setAdapter(deviceListAdapter);

        //如果返回true表示缺少权限





        //初始化蓝牙开关状态
        if (BA.isEnabled() == true) {
            blueToothSwitch.setChecked(true);
        } else {
            blueToothSwitch.setChecked(false);
        }

        //蓝牙开关点
        blueToothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!BA.isEnabled()){
                        Intent turnOn =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(turnOn,0);
                        Toast.makeText(getApplicationContext(),"Turned on",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Already on",Toast.LENGTH_LONG).show();
                    }
                    return ;
                }
                BA.disable();
                Toast.makeText(getApplicationContext(),"Turned off", Toast.LENGTH_LONG).show();
            }
        });

        //可发现开关
        visibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent getVisible =new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(getVisible,0);
                    return ;
                }
            }
        });



    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    /**
                     * 用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                     * 第一次授权失败之后，退出App再次进入时，再此处重新调出允许权限提示框
                     */
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    /**
                     * 用户未彻底拒绝授予权限
                     * 第一次安装时，调出的允许权限提示框，之后再也不提示
                     */
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        }
    }


    //刷新按钮
    public void searchDevice(View view){
        deviceList.clear();
        BA.startDiscovery();
    };

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        //设备数据格式转换
//        public Map<String, String> deviceChange(BluetoothDevice device){
//
//
//            return result;
//        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName() != null){
                    Map<String, String> result = new HashMap<>();

                    result.put("name", device.getName());
                    result.put("address", device.getAddress());
                    result.put("icon", String.valueOf(BtUtils.getDeviceType(device.getBluetoothClass())));
                    deviceList.add(result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            deviceListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }


}


