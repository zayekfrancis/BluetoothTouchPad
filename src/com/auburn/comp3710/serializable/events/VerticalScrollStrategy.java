package com.auburn.comp3710.serializable.events;

import com.auburn.comp3710.serializable.BluetoothSerializable;
import com.auburn.comp3710.serializable.VerticalScroll;

/**
 * Created by Rabby on 4/24/2014.
 */
public class VerticalScrollStrategy extends EventStrategy {

    public void handleEvent(BluetoothSerializable event) {
        VerticalScroll verticalScrollEvent = (VerticalScroll) event;
        getRobot().mouseWheel(verticalScrollEvent.getScrollClicks());
    }

}
