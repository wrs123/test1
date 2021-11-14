package com.ofg.test1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Test12Activity extends Activity {
    private Button On;
    private Button Off;
    private Button Visible;
    private Button listB;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;//声明的一个成员变量pairedDevices,它是一个set集合对象，可以放的是BluetoothDevice.由于集合是一种容器，相当于是一个瓶子，那么java提供了一种功能，就是在尖括号中声明这种容器是什么类型的容器，比如说是酒瓶，只能装酒。这是集合对象泛型的概念。
    private ListView lv1;
    private ListView lv2;
    private Button searchB;

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

        On.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//
                if(!BA.isEnabled()){
                    Intent turnOn =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn,0);
                    Toast.makeText(getApplicationContext(),"Turned on",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Already on",Toast.LENGTH_LONG).show();
                }
            }
        });


        listB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                pairedDevices = BA.getBondedDevices();

                ArrayList list =new ArrayList();
                for(BluetoothDevice bt : pairedDevices)
                    list.add(bt.getName());

                Toast.makeText(getApplicationContext(),String.valueOf(list.size()),Toast.LENGTH_SHORT).show();
                ArrayAdapter adapter1 =new ArrayAdapter(Test12Activity.this,android.R.layout.simple_list_item_multiple_choice, list);
                lv1.setAdapter(adapter1);


            }

        });


        Off.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
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

    }


}
