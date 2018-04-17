package in.blogspot.theiitiansway.carelightstrial1;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageButton btnOn, btnOff;
    TextView requiredintensity,currentintensitydisplay;
    LinearLayout viewwhenon;
    public RelativeLayout viewwhenoff,viewwhenconnecting,viewwhenerror,viewreadingmode;
    SeekBar amberseekbar;
    EditText nightlightstartedit;
    int lightincidnt,reqintensityint;
    private SensorManager mSensorManager;
    private Sensor mLight;
    String mode="NORMAL";
    SendBrightness sendBrightness;


    ImageButton manualbrightness,settingsactivity,autobrightness;
    TextView txtArduino, txtString, txtStringLength, lightincidenttxtview,sensorView0, sensorView1, sensorView2, sensorView3;
    Handler bluetoothIn;
    boolean load=true,finishedSending;


    final int handlerState = 0;
    //used to identify handler message
    public BluetoothAdapter btAdapter = null;
    public BluetoothSocket btSocket = null;
    private int currentbrightness=100;
    private int lastbrightness;
    private StringBuilder recDataString = new StringBuilder();
    private ConnectedThread mConnectedThread;



    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishedSending=true;
        lightincidnt=50;
        reqintensityint=45;
        lightincidnt=0;
        nightlightstartedit=(EditText) findViewById(R.id.nightlightstart);
        viewreadingmode=(RelativeLayout)findViewById(R.id.radingmodeview);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        currentintensitydisplay=(TextView)findViewById(R.id.currentintesitydisplay);
        requiredintensity=(TextView)findViewById(R.id.requiredintensity) ;
        currentintensitydisplay=(TextView)findViewById(R.id.currentintesitydisplay);
        setContentView(R.layout.activity_main);
        amberseekbar=(SeekBar) findViewById(R.id.seekBarAmber);
        //Link the buttons and textViews to respective views
        btnOff=(ImageButton)findViewById(R.id.buttonwhenOn) ;
        btnOn=(ImageButton)findViewById(R.id.poweroffbutton);
        viewwhenerror=(RelativeLayout)findViewById(R.id.connectionfailure);
        viewwhenconnecting=(RelativeLayout)findViewById(R.id.connectingview);
        final SeekBar seekBar= (SeekBar)findViewById(R.id.seekBar);
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();
        viewwhenon=(LinearLayout)findViewById(R.id.screenwhenon);
        viewwhenoff=(RelativeLayout)findViewById(R.id.screenwhenoff);
        btnOn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectedThread.write("B"+lastbrightness);
                showonview();
            }
        });


        // LED On OnClickButtonListener LED
        btnOff.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("B"+0);
                lastbrightness=currentbrightness;
                showoffview();
            }
        });
        // TURN ON
        viewwhenon=(LinearLayout)findViewById(R.id.screenwhenon);
        viewwhenoff=(RelativeLayout)findViewById(R.id.screenwhenoff);

        autobrightness=(ImageButton) findViewById(R.id.autoBrightnessOn);
        autobrightness.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                autobrightness.setVisibility(View.INVISIBLE);
                manualbrightness.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), "Auto Brightness Off", Toast.LENGTH_SHORT).show();
                mConnectedThread.write("A0");

            }
        });
        manualbrightness = (ImageButton)findViewById(R.id.autoBrightnessOff);
        manualbrightness.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                manualbrightness.setVisibility(View.INVISIBLE);
                autobrightness.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(), "Auto Brightness Off", Toast.LENGTH_SHORT).show();
                mConnectedThread.write("A1");
            }
        });
        settingsactivity=(ImageButton)findViewById(R.id.settingsButton);
        settingsactivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opensettings=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(opensettings);
            }
        });
        viewwhenon=(LinearLayout)findViewById(R.id.screenwhenon);
        viewwhenoff=(RelativeLayout)findViewById(R.id.screenwhenoff);
        amberseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mConnectedThread.write("C"+i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mConnectedThread.write("C"+seekBar.getProgress());
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mConnectedThread.write("B"+i);
                currentbrightness=i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mConnectedThread.write("B"+seekBar.getProgress());
                currentbrightness=seekBar.getProgress();
            }
        });
    }



    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();
        checkBTState();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();
        String er="ERROR";
        if (intent.hasExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS)){
            address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        }
        else{
            address=s.getString("BA",er);

            if (address==er){
                //Launch Device Picker
                Intent k=new Intent(MainActivity.this,DeviceListActivity.class);
                startActivity(k);
                finish();
                load=false;
            }
        }



        //Get the MAC address from the DeviceListActivty via EXTRA


        //create device and set the MAC address
        if (load) {
            connectBluetooth b = new connectBluetooth();
            b.execute(1);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            lightincidnt=(int)sensorEvent.values[0];
            writetoscreen(lightincidnt);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void writetoscreen(int i){
        currentintensitydisplay=(TextView)findViewById(R.id.currentintesitydisplay) ;
        currentintensitydisplay.setText("Incident Light : "+i+" lx");

    }


    public void addintensity(View view){

        if (reqintensityint<94){
            reqintensityint+=5;
        }
        requiredintensity=(TextView)findViewById(R.id.requiredintensity) ;
        requiredintensity.setText(reqintensityint+"");
        mConnectedThread.write("N"+reqintensityint);

    }

    public void subintensity(View view){
        if (reqintensityint>6){
            reqintensityint-=5;
        }
        requiredintensity=(TextView)findViewById(R.id.requiredintensity) ;
        requiredintensity.setText(reqintensityint+"");
        mConnectedThread.write("N"+reqintensityint);
    }
    public void showonnnviewfromview(View view){
        showonview();
        sendBrightness.cancel(true);
    }

    public void tryagain(View view){


        connectBluetooth c=new connectBluetooth();
        c.execute(1);

    }
    public void bulblist(View v){
        Intent k=new Intent(MainActivity.this,DeviceListActivity.class);
        startActivity(k);

    }

    public void showerrorview(){
        mode="ERROR";
        viewreadingmode=(RelativeLayout)findViewById(R.id.radingmodeview);
        viewwhenoff.setVisibility(View.INVISIBLE);
        viewwhenon.setVisibility(View.INVISIBLE);
        viewwhenconnecting.setVisibility(View.INVISIBLE);
        viewwhenerror.setVisibility(View.VISIBLE);
        viewreadingmode.setVisibility(View.INVISIBLE);}

    public void showmatchbrightnessview(View view){
        mode="READING";
        showonview();
        viewreadingmode=(RelativeLayout)findViewById(R.id.radingmodeview);
        viewwhenon.setVisibility(View.INVISIBLE);
        viewreadingmode.setVisibility(View.VISIBLE);
        mConnectedThread.write("R1");
        sendBrightness=new SendBrightness();
        sendBrightness.execute("Hello");
    }

    public void showonview(){
        mode="NORMAL";
        viewreadingmode=(RelativeLayout)findViewById(R.id.radingmodeview);
        viewwhenoff.setVisibility(View.INVISIBLE);
        viewwhenconnecting.setVisibility(View.INVISIBLE);
        viewwhenerror.setVisibility(View.INVISIBLE);
        viewwhenon.setVisibility(View.VISIBLE);
        viewreadingmode.setVisibility(View.INVISIBLE);}

    public void showoffview(){
        mode="OFF";
        viewreadingmode=(RelativeLayout)findViewById(R.id.radingmodeview);
        viewwhenon.setVisibility(View.INVISIBLE);
        viewwhenconnecting.setVisibility(View.INVISIBLE);
        viewwhenerror.setVisibility(View.INVISIBLE);
        viewwhenoff.setVisibility(View.VISIBLE);
        viewreadingmode.setVisibility(View.INVISIBLE);}



    public void showconnectingview(){
        mode="CONNECTING";
        viewreadingmode=(RelativeLayout)findViewById(R.id.radingmodeview);
        viewwhenoff.setVisibility(View.INVISIBLE);
        viewwhenon.setVisibility(View.INVISIBLE);
        viewwhenerror.setVisibility(View.INVISIBLE);
        viewwhenconnecting.setVisibility(View.VISIBLE);
        viewreadingmode.setVisibility(View.INVISIBLE);}

    class SendBrightness extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            mConnectedThread.write("N"+reqintensityint);
            int a=1;
            while (! isCancelled()){
            mConnectedThread.write("M"+lightincidnt);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    class connectBluetooth extends AsyncTask<Number,Number,Number>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showconnectingview();
        }

        @Override
        protected Number doInBackground(Number... view) {
            BluetoothDevice device = btAdapter.getRemoteDevice(address);
            try {
                btSocket = createBluetoothSocket(device);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
            }
            // Establish the Bluetooth socket connection.
            try {
                btSocket.connect();
            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                    //insert code to deal with this
                }
            }
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();
            //I send a character when resuming.beginning transmission to check device is connected
            //If it is not an exception will be thrown in the write method and finish() will be called

            return view[0];

        }
        @Override
        protected void onPostExecute(Number s) {
            super.onPostExecute(s);
            if(mConnectedThread.write("x")){
                switch (s.intValue()){
                    case 1: showonview();
                        break;
                    case 2: showoffview();
                        break;
                    case 3: showerrorview();break;
                    default:showonview();}}
            else {showerrorview();}

        }
    }
    @Override
    public void onPause()
    {
        super.onPause();
        if (mode!="CONNECTING"){
        mSensorManager.unregisterListener(this);

        if (load){
            try
            {
                //Don't leave Bluetooth sockets open when leaving activity
                btSocket.close();
            } catch (IOException e2) {
                e2.printStackTrace();//insert code to deal with this
            }}
    }}

    @Override
    public void onBackPressed() {
        if (mode=="READING"){
            showonnnviewfromview(new View(this));
        }
        else {
        super.onBackPressed();}
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    public void checkBTState() {

        {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    public class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        //write method
        public boolean write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);
                return true;//write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application

                return false;


            }
        }
    }
}