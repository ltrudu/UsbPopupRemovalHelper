package com.zebra.usbpopupremovalhelper;

public enum EDeviceClass {
    AINPUT_SOURCE_DPAD               ("AINPUT_SOURCE_DPAD"),
    AINPUT_SOURCE_GAMEPAD            ("AINPUT_SOURCE_GAMEPAD"),
    AINPUT_SOURCE_KEYBOARD           ("AINPUT_SOURCE_KEYBOARD"),
    AINPUT_SOURCE_MOUSE              ("AINPUT_SOURCE_MOUSE"),
    AINPUT_SOURCE_MOUSE_RELATIVE     ("AINPUT_SOURCE_MOUSE_RELATIVE"),
    AINPUT_SOURCE_JOYSTICK           ("AINPUT_SOURCE_JOYSTICK"),
    AINPUT_SOURCE_ROTARY_ENCODER     ("AINPUT_SOURCE_ROTARY_ENCODER"),
    AINPUT_SOURCE_STYLUS             ("AINPUT_SOURCE_STYLUS"),
    AINPUT_SOURCE_TOUCH_NAVIGATION   ("AINPUT_SOURCE_TOUCH_NAVIGATION"),
    AINPUT_SOURCE_TOUCHPAD           ("AINPUT_SOURCE_TOUCHPAD"),
    AINPUT_SOURCE_TOUCHSCREEN        ("AINPUT_SOURCE_TOUCHSCREEN"),
    AINPUT_SOURCE_TRACKBALL          ("AINPUT_SOURCE_TRACKBALL"),
    USB_CLASS_APP_SPEC               ("USB_CLASS_APP_SPEC"),
    USB_CLASS_AUDIO                  ("USB_CLASS_AUDIO"),
    USB_CLASS_CDC_DATA               ("USB_CLASS_CDC_DATA"),
    USB_CLASS_COMM                   ("USB_CLASS_COMM"),
    USB_CLASS_CONTENT_SEC            ("USB_CLASS_CONTENT_SEC"),
    USB_CLASS_CSCID                  ("USB_CLASS_CSCID"),
    USB_CLASS_HID                    ("USB_CLASS_HID"),
    USB_CLASS_HUB                    ("USB_CLASS_HUB"),
    USB_CLASS_MASS_STORAGE           ("USB_CLASS_MASS_STORAGE"),
    USB_CLASS_MISC                   ("USB_CLASS_MISC"),
    USB_CLASS_PER_INTERFACE          ("USB_CLASS_PER_INTERFACE"),
    USB_CLASS_PHYSICA                ("USB_CLASS_PHYSICA"),
    USB_CLASS_PRINTER                ("USB_CLASS_PRINTER"),
    USB_CLASS_STILL_IMAGE            ("USB_CLASS_STILL_IMAGE"),
    USB_CLASS_VENDOR_SPEC            ("USB_CLASS_VENDOR_SPEC"),
    USB_CLASS_VIDEO                  ("USB_CLASS_VIDEO"),
    USB_CLASS_WIRELESS_CONTROLLER    ("USB_CLASS_WIRELESS_CONTROLLER");


    String stringContent = "";
    EDeviceClass(String stringContent)
    {
        this.stringContent = stringContent;
    }

    @Override
    public String toString() {
        return stringContent;
    }
}
