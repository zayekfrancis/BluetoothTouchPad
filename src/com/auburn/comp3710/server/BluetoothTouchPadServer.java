package com.auburn.comp3710.server;
/**
 * Created by Robert on 4/2/2014.
 */


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import com.auburn.comp3710.serializable.BluetoothSerializable;

public class BluetoothTouchPadServer {
    //Generic UUID to connect to typical android devices
    //"00001101-0000-1000-8000-00805F9B34FB";
    private static final String DEVICE_UUID = "0000110100001000800000805F9B34FB";
    private static final String SERVER_URL = "btspp://localhost:" + DEVICE_UUID + ";name=BluetoothTouchPadServer";

    private StreamConnectionNotifier server;
    private LocalDevice localDevice;
    private StreamConnection connection;

    private DataInputStream dataInputStream;
    BluetoothEventWorker eventWorker;




    public BluetoothTouchPadServer() throws BluetoothStateException, IOException {
        System.setProperty("bluecove.jsr82.psm_minimum_off", "true");

        localDevice = LocalDevice.getLocalDevice();
        localDevice.setDiscoverable(DiscoveryAgent.GIAC); // Advertising the service
    }

    public void startServer(){
        new Thread() {
            public void run() {
                try {
                    initiateConnection();
                } catch(IOException e) {
                    System.out.println("IO Error occurred: \n" + e);
                } catch(InterruptedException e) {
                    System.out.println("Interrupted Execution error occurred: " + e);
                }
            }
        }.start();
    }

    public void initiateConnection() throws IOException, InterruptedException {

        server = (StreamConnectionNotifier) Connector.open(SERVER_URL);

        System.out.println("Waiting for connection...");
        try {
            connection = server.acceptAndOpen(); // Wait until client connects
            //=== At this point, two devices should be connected ===//
            System.out.println("Connection was a success!");
            //TODO: add timeout and connection check

            openDataInputStream();
            initializeEventWorker();
            handleIncomingRequests();
        } catch(InterruptedIOException ex) {

        }


    }

    private void handleIncomingRequests() {
        while(connection != null) {
            ObjectInputStream objectInputStream = null;

            try {
                objectInputStream = new ObjectInputStream(dataInputStream);

            } catch(EOFException e){
                System.out.println("Connection lost:\n" + e);
                try {
                    closeConnection();
                } catch(IOException innerException) {
                    System.out.println("Error occurred while closing connection:\n" + innerException);
                }
            } catch(IOException e) {
                System.out.println("Error occurred while reading bytes:\n" + e);
            }


            if (objectInputStream != null) {
                try {
                    handleEvent(objectInputStream);
                }
                catch(IOException e){
                        System.out.println("Error occurred while deserializing bytes:\n" + e);
                }catch(ClassNotFoundException e){
                        System.out.println("Unable to determine type of sent event:\n" + e);
                }
            }
        }
    }

    private void handleEvent(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        BluetoothSerializable event = (BluetoothSerializable) objectInputStream.readObject();//#deserialize(bytes);

        try {
            eventWorker.queue(event);
        } catch(InterruptedException e) {
            System.out.println("Unable to queue event " + event + " due to:\n" + e);
        }

    }

    private void openDataInputStream() throws IOException {
        dataInputStream = connection.openDataInputStream();
    }


    public void stopServer() throws IOException {
        new Thread() {
            public void run() {
                try {
                    closeConnection();
                } catch(IOException e) {
                    System.out.println("IO Error occurred: \n" + e);
                }
            }
        }.start();
    }

    public void closeConnection() throws IOException {
        System.out.println("Attempting to close connection...");

        if(connection != null) {
            connection.close();
        }

        if(server != null) {
            server.close();
        }

        if(dataInputStream != null) {
            dataInputStream.close();
        }

        connection = null;
        server = null;
        eventWorker.stop();

        System.out.println("Connection closed!\n");
    }

    private void initializeEventWorker() {
        eventWorker = new BluetoothEventWorker();
        new Thread() {
            public void run() {
                eventWorker.run();
            }
        }.start();

    }

    private static Serializable deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        Serializable event = (Serializable) o.readObject();
        return event;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
    }

}

