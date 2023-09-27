package com.zebra.usbpopupremovalhelper;

public enum EUsbHostModePeripherals {
    DO_NOT_CHANGE("0"),
    CONTROLLED("1"),
    UNCONTROLLED("2");

    String stringContent = "";
    EUsbHostModePeripherals(String stringContent)
    {
        this.stringContent = stringContent;
    }

    @Override
    public String toString() {
        return stringContent;
    }
}
