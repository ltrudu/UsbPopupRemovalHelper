package com.zebra.usbpopupremovalhelper;

import android.content.Context;
import android.text.TextUtils;

public class UsbPopupRemovalHelper {

    public static void processControlRuleXMLAlreadyHTMLEncoded(Context context, String controlRuleXMLAlreadyHTMLEncoded, IResultCallbacks iResultCallbacks)
    {
        processControlRule(context, controlRuleXMLAlreadyHTMLEncoded, true, iResultCallbacks);
    }
    public static void processRawControlRuleXML(Context context, String controlRuleAsRawXML, IResultCallbacks iResultCallbacks)
    {
        processControlRule(context, controlRuleAsRawXML, false, iResultCallbacks);
    }

    private static void processControlRule(Context context, String controlRuleXML, boolean isHtmlEncoded, IResultCallbacks iResultCallbacks)
    {
        String tmpControlRule = controlRuleXML;
        String profileName = "UsbMgr-1";
        String profileData = "";
        try {

            if(iResultCallbacks != null)
            {
                iResultCallbacks.onDebugStatus("Unencoded control rule: " + tmpControlRule);
            }

            if(!isHtmlEncoded) {
                tmpControlRule = tmpControlRule.replace("\n","").replace("\r","").replace("\r\n","");
                tmpControlRule = TextUtils.htmlEncode(tmpControlRule);
                if (iResultCallbacks != null) {
                    iResultCallbacks.onDebugStatus("HTML encoded control rule: " + tmpControlRule);
                }
            }

            profileData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<characteristic type=\"Profile\">" +
                    "<parm name=\"ProfileName\" value=\"" + profileName + "\"/>" +
                    "  <characteristic version=\"11.8\" type=\"UsbMgr\">\n" +
                    "    <parm name=\"HostModePeripherals\" value=\"1\" />\n" +
                    "    <parm name=\"ControlRules\" value=\"" + tmpControlRule + "\" />\n" +
                    "  </characteristic>\n" +
                    "</characteristic>";

            ProfileManagerCommand profileManagerCommand = new ProfileManagerCommand(context);
            profileManagerCommand.execute(profileData, profileName, iResultCallbacks);
            //}
        } catch (Exception e) {
            e.printStackTrace();
            if (iResultCallbacks != null) {
                iResultCallbacks.onError("Error on profile: " + profileName + "\nError:" + e.getLocalizedMessage() + "\nProfileData:" + profileData, "");
            }
        }

    }

    public static void whitelistDeviceWithIDs(Context context, int vendorId, int productId, EDeviceClass deviceClass, IResultCallbacks iResultCallbacks) {
        String profileName = "UsbMgr-1";
        String profileData = "";
        try {
            String strPackageName = PackageManagementHelper.getPackageName(context, iResultCallbacks);

            String encoded = PackageManagementHelper.getSignature(context, iResultCallbacks);

            if(iResultCallbacks != null)
            {
                iResultCallbacks.onDebugStatus("Unencoded signature: " + encoded);
            }

            encoded = TextUtils.htmlEncode(encoded);
            if(iResultCallbacks != null)
            {
                iResultCallbacks.onDebugStatus("HTML encoded signature: " + encoded);
            }

            profileData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<characteristic type=\"Profile\">" +
                    "<parm name=\"ProfileName\" value=\"" + profileName + "\"/>" +
                    "  <characteristic version=\"11.8\" type=\"UsbMgr\">\n" +
                    "    <parm name=\"HostModePeripherals\" value=\"1\" />\n" +
                    "    <parm name=\"ControlRules\" value=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;&lt;popupsuppress&gt;&lt;rule&gt;&lt;pkg&gt;"+ strPackageName + "&lt;/pkg&gt;&lt;cert&gt;" + encoded + "&lt;/cert&gt;&lt;vid vendorid=&quot;" + vendorId + "&quot;&gt;&lt;pid&gt;" + productId + "&lt;/pid&gt;&lt;/vid&gt;&lt;autoLaunch&gt;false&lt;/autoLaunch&gt;&lt;/rule&gt;&lt;/popupsuppress&gt;&lt;usbconfig mode=&quot;whitelist&quot;&gt;&lt;class&gt;"+ deviceClass.toString() + "&lt;/class&gt;&lt;/usbconfig&gt;\" />\n" +
                    "  </characteristic>\n" +
                    "</characteristic>";

            ProfileManagerCommand profileManagerCommand = new ProfileManagerCommand(context);
            profileManagerCommand.execute(profileData, profileName, iResultCallbacks);
            //}
        } catch (Exception e) {
            e.printStackTrace();
            if (iResultCallbacks != null) {
                iResultCallbacks.onError("Error on profile: " + profileName + "\nError:" + e.getLocalizedMessage() + "\nProfileData:" + profileData, "");
            }
        }
    }

    public static void whitelistDeviceWithClass(Context context, EDeviceClass deviceClass, IResultCallbacks callbackInterface) {
        String profileName = "UsbMgr-1";
        String profileData = "";
        try {
            String encoded = PackageManagementHelper.getSignature(context, callbackInterface);

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
