package com.auburn.comp3710.serializable.events;

import com.auburn.comp3710.serializable.BluetoothSerializable;
import com.auburn.comp3710.serializable.MouseClickPress;

/**
 * Created by Rabby on 4/24/2014.
 */
public class MouseClickPressStrategy extends EventStrategy {
    public void handleEvent(BluetoothSerializable event) {
        MouseClickPress mouseClickPress = (MouseClickPress) event;
        getRobot().mousePress(mouseClickPress.getButtonMask());
    }

}
