package com.auburn.comp3710.server;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import java.io.IOException;
import java.util.Vector;


/**
 * Created by Robert on 4/2/2014.
 */
public class AndroidDeviceListener implements DiscoveryListener {
    private static final Vector<RemoteDevice> devicesDiscovered = new Vector<RemoteDevice>();
    private final Object inquiryCompletedEvent = new Object();

    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
        devicesDiscovered.addElement(btDevice);
        try {
            System.out.println("     name " + btDevice.getFriendlyName(false));
            //btDevice.authorize()
        } catch (IOException cantGetDeviceName) {
        }
    }

    public void inquiryCompleted(int discType) {
        System.out.println("Device Inquiry completed!");
        synchronized(inquiryCompletedEvent){
           inquiryCompletedEvent.notifyAll();
        }
    }

    public void serviceSearchCompleted(int transID, int respCode) {
    }

    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
    }

    public Vector<RemoteDevice> getDevicesDiscovered() {
        return devicesDiscovered;
    }

    public Object getInquiryCompletedEvent() {
        return inquiryCompletedEvent;
    }
}
