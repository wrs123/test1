package com.ofg.test1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

public class Test12Activity extends Activity {
    private Button On;
    private Button Off;
    private Button Visible;
    private Button listB;
    private BluetoothAdapter BA = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices;//声明的一个成员变量pairedDevices,它是一个set集合对象，可以放的是BluetoothDevice.由于集合是一种容器，相当于是一个瓶子，那么java提供了一种功能，就是在尖括号中声明这种容器是什么类型的容器，比如说是酒瓶，只能装酒。这是集合对象泛型的概念。
    private ListView lv1;
    private ListView lv2;
    private Button searchB;
    Switch switchTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test12);
        On=(Button)findViewById(R.id.button1);
        Visible=(Button)findViewById(R.id.button2);
        listB=(Button)findViewById(R.id.button3);
        Off =(Button)findViewById(R.id.button4);
        searchB =(Button)findViewById(R.id.button5);
        lv1 =(ListView)findViewById(R.id.listView1);
        lv2 =(ListView)findViewById(R.id.listView2);
        BA =BluetoothAdapter.getDefaultAdapter();
        Switch switchTest = (Switch) findViewById(R.id.BlueTouchSwitch);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);


        pairedDevices = BA.getBondedDevices();

        ArrayList list =new ArrayList();
        for(BluetoothDevice bt : pairedDevices)
            list.add(bt.getName());

        ArrayAdapter adapter1 =new ArrayAdapter(Test12Activity.this,android.R.layout.simple_list_item_multiple_choice, list);
        lv1.setAdapter(adapter1);

        if (BA.isEnabled() == true) {
            switchTest.setChecked(true);
        } else {
            switchTest.setChecked(false);
        }


        switchTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        Visible.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent getVisible =new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible,0);
            }

        });

        searchB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                BA.startDiscovery();
            }
        });

    }



    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Toast t = Toast.makeText(context,deviceName+deviceHardwareAddress, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP,0,0);//方便录屏，将土司设置在屏幕顶端
                t.show();
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


