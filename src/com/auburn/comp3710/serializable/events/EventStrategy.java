package com.auburn.comp3710.serializable.events;

import java.awt.AWTException;
import java.awt.Robot;

import com.auburn.comp3710.serializable.BluetoothSerializable;

/**
 * Created by Rabby on 4/24/2014.
 */
public class EventStrategy implements EventStrategyInterface {

    public void handleEvent(BluetoothSerializable event) {
        throw new UnsupportedOperationException("Cannot call handleEvent from eventStrategy class");
    }

    public Robot getRobot() {
        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Error occurred while creating robot:\n" + e);
        }

        return robot;
    }

}
