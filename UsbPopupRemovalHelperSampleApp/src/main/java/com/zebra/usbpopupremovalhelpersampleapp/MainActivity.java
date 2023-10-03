package com.zebra.usbpopupremovalhelpersampleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zebra.usbpopupremovalhelper.PackageManagementHelper;
import com.zebra.usbpopupremovalhelper.UsbPopupRemovalHelper;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static String status = "";
    TextView tvStatus;
    private UsbManager mUsbManager;

    private static String TAG = "UPRHSampleApp";

    private static String ACTION_USB_PERMISSION = "com.zebra.zebraprintservice.USB_PERMISSION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        Button process = (Button)findViewById(R.id.bt_processXML);
        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processAllPrinters();
            }
        });

        Button check = (Button)findViewById(R.id.bt_verif_permission);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermissions();
            }
        });

    }

    private void verifyPermissions() {
        if (mUsbManager != null) {
            status = "";
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            logMessages("--------------------------------------------------------------");
            logMessages("----------------------Verify Permissions----------------------");
            for (String deviceName : deviceList.keySet()) {
                UsbDevice device = deviceList.get(deviceName);
                if (device.getInterfaceCount() > 0 && device.getInterface(0).getInterfaceClass() == 0x07) {
                    // Check if we have permission to access to this device
                    // otherwise, the getSerialNumber method will lead to an exception
                    logMessages("Found Zebra printer : " + device.getProductName());
                    logMessages("Device is connected to : " + deviceName);
                    logMessages("Device: " + device.getProductName() + " has usb popup : " + (mUsbManager.hasPermission(device) ? "disabled." : "requested."));
                }
                else
                {
                    logMessages("Found not printer device: " + device.getProductName());
                    logMessages("Device is connected to : " + deviceName);
                    logMessages("Device: " + device.getProductName() + " has usb popup : " + (mUsbManager.hasPermission(device) ? "disabled." : "requested."));
                }
            }
        }
        logMessages("--------------------------------------------------------------");
    }

    private void processAllPrinters() {
        if (mUsbManager != null) {
            status = "";
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            logMessages("--------------------------------------------------------------");
            logMessages("-----------------Process USB Devices--------------------------");
            for (String deviceName : deviceList.keySet()) {
                UsbDevice device = deviceList.get(deviceName);
                if (device.getInterfaceCount() > 0 && device.getInterface(0).getInterfaceClass() == 0x07) {
                    // Check if we have permission to access to this device
                    // otherwise, the getSerialNumber method will lead to an exception
                    logMessages("Found Zebra printer : " + deviceName);

                    if (mUsbManager.hasPermission(device) == false) {
                        logMessages("Popup not removed.\nRemoving popup.");
                        autoAllowUSBDeviceIfPossible(device);
                    }
                    else
                    {
                        logMessages("The printer " + deviceName + " is already granted.");
                    }
                }
                else
                {
                    logMessages("Found not printer device: " + deviceName);
                    logMessages("Device: " + deviceName + " has usb popup : " + (mUsbManager.hasPermission(device) ? "granted" : "not granted."));
                }
            }
        }
    }

    private void autoAllowUSBDeviceIfPossible(UsbDevice device) {
        String deviceManufacturer = Build.MANUFACTURER;
        logMessages("*******************************************************************************");
        logMessages("********************Trying to auto grant USB permission************************");
        if ((deviceManufacturer.contains("Zebra") || deviceManufacturer.contains("ZEBRA")) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            String certificate = PackageManagementHelper.getSignature(this, null);
            if (certificate == null) {
                logMessages("getSignature error, couldn't retrieve app certificate");
                return;
            }
            logMessages("Found package certificate :" + certificate);
            String packageName = PackageManagementHelper.getPackageName(this, null);
            if (packageName == null) {
                logMessages("getSignature error, couldn't retrieve app certificate");
                return;
            }
            logMessages("Found package name: " + packageName);

            String controlRule = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<popupsuppress>\n" +
                    "<rule>\n" +
                    "<pkg>" + packageName + "</pkg>\n" +
                    "<cert>" + certificate + "</cert>\n" +
                    "<vid vendorid=\"" + device.getVendorId() + "\">\n" +
                    "<pid>" + device.getProductId() + "</pid></vid>\n" +
                    "</rule>\n" +
                    "<autoLaunch>true</autoLaunch>" +
                    "</popupsuppress>\n" +
                    "<usbconfig mode=\"whitelist\">\n" +
                    "<class>USB_CLASS_PRINTER</class>\n" +
                    "</usbconfig>";//*/
            UsbPopupRemovalHelper.processRawControlRuleXML(this, controlRule, new com.zebra.usbpopupremovalhelper.IResultCallbacks() {
                @Override
                public void onSuccess(String message, String ResultXML) {
                    logMessages("USBPermission success message: " + message);
                    logMessages("*******************************************************************************");
                }

                @Override
                public void onError(String message, String ResultXML) {
                    logMessages("USBPermission error message: " + message + "\nResultXML: " + ResultXML);
                    logMessages("*******************************************************************************");
                }

                @Override
                public void onDebugStatus(String s) {
                    //logMessages("Verbose: " + s);
                }
            });
        } else {
            logMessages("USBPermission: not a Zebra device");
            logMessages("*******************************************************************************");
        }
    }

    private void logMessages(String message)
    {
        status += message + "\n";
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                tvStatus.setText(status);
            }
        });
        Log.d(TAG, message);
    }
}
