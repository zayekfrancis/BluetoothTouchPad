package com.auburn.comp3710.serializable.events;

import com.auburn.comp3710.serializable.BluetoothSerializable;
import com.auburn.comp3710.serializable.MouseClickRelease;

/**
 * Created by Rabby on 4/24/2014.
 */
public class MouseClickReleaseStrategy extends EventStrategy {
    public void handleEvent(BluetoothSerializable event) {
        MouseClickRelease mouseClickRelease = (MouseClickRelease) event;
        getRobot().mouseRelease(mouseClickRelease.getButtonMask());
    }
}
