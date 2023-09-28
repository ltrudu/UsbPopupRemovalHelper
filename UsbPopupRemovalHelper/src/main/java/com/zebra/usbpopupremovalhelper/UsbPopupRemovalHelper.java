package com.zebra.usbpopupremovalhelper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

import java.util.Base64;

public class UsbPopupRemovalHelper {
    public static Signature apkCertificate = null;

    public static void whitelistDeviceWithIDs(Context context, int vendorId, int productId, EDeviceClass deviceClass, IResultCallbacks callbackInterface) {
        String profileName = "UsbMgr-1";
        String profileData = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
            String path = context.getApplicationInfo().sourceDir;
            final String strPackageName = packageInfo.packageName;

            // Use custom signature if it has been set by the user
            Signature sig = UsbPopupRemovalHelper.apkCertificate;

            // Let's check if we have a custom certificate
            if (sig == null) {
                // Nope, we will get the first apk signing certificate that we find
                // You can copy/paste this snippet if you want to provide your own
                // certificate
                // TODO: use the following code snippet to extract your custom certificate if necessary
                final Signature[] arrSignatures = packageInfo.signingInfo.getApkContentsSigners();
                if (arrSignatures == null || arrSignatures.length == 0) {
                    if (callbackInterface != null) {
                        callbackInterface.onError("Error : Package has no signing certificates... how's that possible ?","");
                        return;
                    }
                }
                sig = arrSignatures[0];
            }

            /*
             * Get the X.509 certificate.
             */
            final byte[] rawCert = sig.toByteArray();

            // Get the certificate as a base64 string
            String encoded = Base64.getEncoder().encodeToString(rawCert);
            if(callbackInterface != null)
            {
                callbackInterface.onDebugStatus("Unencoded signature: " + encoded);
            }

            encoded = TextUtils.htmlEncode(encoded);
            if(callbackInterface != null)
            {
                callbackInterface.onDebugStatus("HTML encoded signature: " + encoded);
            }
            profileData =
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                            "<characteristic type=\"Profile\">" +
                            "<parm name=\"ProfileName\" value=\"" + profileName + "\"/>" +
                            "  <characteristic version=\"11.8\" type=\"UsbMgr\">\n" +
                            "    <parm name=\"HostModePeripherals\" value=\"1\" />\n" +
                            "    <parm name=\"ControlRules\" value=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?" +
                            "&gt;&lt;popupsuppress&gt;"+
                            "&lt;rule&gt;"+"" +
                            "&lt;pkg&gt;" + strPackageName +
                            "&lt;/pkg&gt;" +
                            "&lt;cert&gt;" + encoded +
                            "&lt;/cert&gt;" +
                            "&lt;vid vendorid=&quot;" + vendorId +
                            "&quot;&gt;&lt;pid&gt;" +productId +
                            "&lt;/pid&gt;&lt;/vid&gt;&lt;/rule&gt;&lt;/popupsuppress&gt;"+
                            "&lt;usbconfig mode=&quot;whitelist&quot;&gt;" +
                            "&lt;class&gt;USB_CLASS_PRINTER&lt;/class&gt;" +
                            //"&lt;vid vendorid=&quot;" + vendorId +
                            //"&quot;&gt;&lt;pid&gt;"+ productId +"&lt;/pid&gt;&lt;/vid&gt;" +
                            "&lt;/usbconfig&gt;"+
                            "\" />\n" +
                            "  </characteristic>\n"+
                            "</characteristic>";


            profileData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<characteristic type=\"Profile\">" +
                    "<parm name=\"ProfileName\" value=\"" + profileName + "\"/>" +
                    "  <characteristic version=\"11.8\" type=\"UsbMgr\">\n" +
                    "    <parm name=\"HostModePeripherals\" value=\"1\" />\n" +
                    "    <parm name=\"ControlRules\" value=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;&lt;popupsuppress&gt;&lt;rule&gt;&lt;pkg&gt;"+ strPackageName + "&lt;/pkg&gt;&lt;cert&gt;" + encoded + "&lt;/cert&gt;&lt;vid vendorid=&quot;" + vendorId + "&quot;&gt;&lt;pid&gt;" + productId + "&lt;/pid&gt;&lt;/vid&gt;&lt;autoLaunch&gt;false&lt;/autoLaunch&gt;&lt;/rule&gt;&lt;/popupsuppress&gt;&lt;usbconfig mode=&quot;whitelist&quot;&gt;&lt;class&gt;"+ deviceClass.toString() + "&lt;/class&gt;&lt;/usbconfig&gt;\" />\n" +
                    "  </characteristic>\n" +
                    "</characteristic>";

            ProfileManagerCommand profileManagerCommand = new ProfileManagerCommand(context);
            profileManagerCommand.execute(profileData, profileName, callbackInterface);
            //}
        } catch (Exception e) {
            e.printStackTrace();
            if (callbackInterface != null) {
                callbackInterface.onError("Error on profile: " + profileName + "\nError:" + e.getLocalizedMessage() + "\nProfileData:" + profileData, "");
            }
        }
    }

    public static void whitelistDeviceWithClass(Context context, EDeviceClass deviceClass, IResultCallbacks callbackInterface) {
        String profileName = "UsbMgr-1";
        String profileData = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
            String path = context.getApplicationInfo().sourceDir;
            final String strPackageName = packageInfo.packageName;

            // Use custom signature if it has been set by the user
            Signature sig = UsbPopupRemovalHelper.apkCertificate;

            // Let's check if we have a custom certificate
            if (sig == null) {
                // Nope, we will get the first apk signing certificate that we find
                // You can copy/paste this snippet if you want to provide your own
                // certificate
                // TODO: use the following code snippet to extract your custom certificate if necessary
                final Signature[] arrSignatures = packageInfo.signingInfo.getApkContentsSigners();
                if (arrSignatures == null || arrSignatures.length == 0) {
                    if (callbackInterface != null) {
                        callbackInterface.onError("Error : Package has no signing certificates... how's that possible ?","");
                        return;
                    }
                }
                sig = arrSignatures[0];
            }

            /*
             * Get the X.509 certificate.
             */
            final byte[] rawCert = sig.toByteArray();

            // Get the certificate as a base64 string
            String encoded = Base64.getEncoder().encodeToString(rawCert);

            profileData =
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                            "<characteristic type=\"Profile\">" +
                            "<parm name=\"ProfileName\" value=\"" + profileName + "\"/>" +
                            "  <characteristic version=\"11.8\" type=\"UsbMgr\">\n" +
                            "    <parm name=\"HostModePeripherals\" value=\"1\" />\n" +
                            "    <parm name=\"ControlRules\" value=\"&lt;?xml version=&quot;1.0&quot;" +
                            "encoding=&quot;UTF-8&quot;?&gt;" +
                            //"&lt;popupsuppress&gt;&lt;rule&gt;&lt;pkg&gt;"+ strPackageName +
                            //"&lt;/pkg&gt;&lt;cert&gt;" + encoded +
                            //"&lt;/cert&gt;&lt;class&gt;" + deviceClass.toString() +
                            //"&lt;/class&gt;&lt;/rule&gt;&lt;/popupsuppress&gt;&lt;"+
                            "usbconfig mode=&quot;whitelist&quot;&gt;&lt;class&gt;" + deviceClass.toString() +
                            "&lt;/class&gt;&lt;/usbconfig&gt;\" />\n" +
                            "  </characteristic>"+
                            "</characteristic>";
            ProfileManagerCommand profileManagerCommand = new ProfileManagerCommand(context);
            profileManagerCommand.execute(profileData, profileName, callbackInterface);
            //}
        } catch (Exception e) {
            e.printStackTrace();
            if (callbackInterface != null) {
                callbackInterface.onError("Error on profile: " + profileName + "\nError:" + e.getLocalizedMessage() + "\nProfileData:" + profileData, "");
            }
        }
    }
}
