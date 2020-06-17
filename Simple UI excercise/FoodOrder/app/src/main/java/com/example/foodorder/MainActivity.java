package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.telephony.SmsManager;
import android.os.Build.*;
import android.os.Build.*;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import android.Manifest;
import android.annotation.SuppressLint;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.app.Activity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity{
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    Button sendBtn;
    EditText txtPhoneno;
    EditText editTextNameFood;
    TextView txtMessage;
    CheckBox checkBoxRice, checkBoxCheese, checkBoxFish, checkBoxBeef;
    RadioButton radioButtonCheck;
    RadioGroup radioGroupDrink;
    String phoneno;
    String message, txtRadio = "", txtCheckBox = "";
//    String SENT = "SMS_SENT";
//    String DELIVERED = "SMS_DELIVERED";
//    PendingIntent sentPI, deliveredPI;
//    BroadcastReceiver smsSentReceiver;
//    BroadcastReceiver smsDeliveredReceiver;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        smsSentReceiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(MainActivity.this,"SMS Sent", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(MainActivity.this,"Generis failure", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(MainActivity.this,"No service", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(MainActivity.this,"Null PDU", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(MainActivity.this,"Radio off", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        };
//        smsDeliveredReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(MainActivity.this,"SMS Delivered", Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(MainActivity.this,"SMS cannot delivered", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        };
//        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
//        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
//    }


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (VERSION_CODES.KITKAT <= VERSION.SDK_INT) {
//            ((ActivityManager)MainActivity.this.getSystemService(ACTIVITY_SERVICE))
//                    .clearApplicationUserData(); // note: it has a return value!
//        } else {
//            // use old hacky way, which can be removed
//            // once minSdkVersion goes above 19 in a few years.
//        }

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneno = (EditText) findViewById(R.id.editTextPhone);
        txtMessage = findViewById(R.id.textViewMessage);
        editTextNameFood = findViewById(R.id.editTextNameFood);
        checkBoxRice = findViewById(R.id.checkBoxRice);
        checkBoxBeef = findViewById(R.id.checkBoxBeef);
        checkBoxCheese = findViewById(R.id.checkBoxCheese);
        checkBoxFish = findViewById(R.id.checkBoxFish);
        radioGroupDrink = findViewById(R.id.radioGroupDrink);

        // sự kiện của checkbox
        isCheckBox(checkBoxRice);
        isCheckBox(checkBoxBeef);
        isCheckBox(checkBoxCheese);
        isCheckBox(checkBoxFish);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });

        //lấy text radio của group radio
        radioGroupDrink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadioButton = radioGroupDrink.getCheckedRadioButtonId();
                radioButtonCheck = findViewById(idRadioButton);
                txtRadio = radioButtonCheck.getText().toString();
                viewTxtMessage();
            }
        });

//        sentPI = PendingIntent.getBroadcast(this, 0 , new Intent(SENT), 0);
//        deliveredPI= PendingIntent.getBroadcast(this, 0 , new Intent(DELIVERED), 0);

//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//                sendSMSMessage();
//

               /* String phoneNumber = "5556";
                String message = "HUNGRY AGAIN, more tacos ";
                Intent intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", message);
                startActivity(intent);*/
//            }
//        });
    }

    //lấy thông tin checkbox
    public void isCheckBox(final CheckBox checkBoxTemp){
        checkBoxTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String nameCheckBox = (String) checkBoxTemp.getText();

                //kiểm tra check được check
                if (checkBoxTemp.isChecked()){  //được check
                    if (txtCheckBox.length() == 0) //kiểm tra check vừa chọn có phải là Checkbox đầu tiên?
                        txtCheckBox = nameCheckBox;
                    else
                        txtCheckBox += ", " + nameCheckBox;
                }
                else { //bỏ check
                    if(txtCheckBox.contains(", ") == false) txtCheckBox =""; //có duy nhất 1 checkbox được chọn
                    else {//nhiều checkbox được chọn
                        if (txtCheckBox.contains(", " + nameCheckBox)) //kiểm tra check vừa bỏ có đứng đầu tiên không?
                            txtCheckBox = txtCheckBox.replace(", " + nameCheckBox, "");
                        else //đứng đầu
                            txtCheckBox = txtCheckBox.replace(nameCheckBox + ", ", "");
                    }
                }
                viewTxtMessage();
            }
        });
    }

    //view txtMessage
    public void viewTxtMessage(){
        String foodname = editTextNameFood.getText().toString();
        if(txtCheckBox.length() == 0 && foodname.length() == 0){
            message = "Drinks: " + txtRadio+".";
        }
        else if (txtRadio.length() == 0 && foodname.length() == 0){
            message = "Fillings: " + txtCheckBox;
        }
        else if (foodname.length() == 0) {
            message = "Fillings: " + txtCheckBox + ". Drinks: " + txtRadio + ".";
        }
        else
        {
            message ="Food: " + foodname + "Fillings: " + txtCheckBox + ". Drinks: " + txtRadio +".";
        }
        txtMessage.setText(message);
    }



    public void sendSMSMessage() {
        phoneno = txtPhoneno.getText().toString();
        message = txtMessage.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneno, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneno, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
    //gửi tin nhắn
//    protected void sendSMSMessage() {
//        phoneno = txtPhoneno.getText().toString();
//        message = "Bạn đã order như sau. " + txtMessage.getText().toString();
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.SEND_SMS},
//                    MY_PERMISSIONS_REQUEST_SEND_SMS);
//        }
//        else
//        {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneno, null, message, sentPI, deliveredPI);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneno, null, message, null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }
}