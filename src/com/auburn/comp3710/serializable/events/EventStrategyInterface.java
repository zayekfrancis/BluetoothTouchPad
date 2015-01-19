package com.auburn.comp3710.serializable.events;

import java.awt.Robot;

import com.auburn.comp3710.serializable.BluetoothSerializable;


/**
 * Created by Rabby on 4/24/2014.
 */
public interface EventStrategyInterface {
    //Takes an event object and runs the appropriate action for the event
    void handleEvent(BluetoothSerializable event);

    Robot getRobot();

}
