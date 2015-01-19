package com.auburn.comp3710.serializable.events;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import com.auburn.comp3710.serializable.BluetoothSerializable;
import com.auburn.comp3710.serializable.CursorMovement;

/**
 * Created by Rabby on 4/24/2014.
 */
public class CursorMovementStrategy extends EventStrategy {
    public void handleEvent(BluetoothSerializable event) {
        Robot robot = getRobot();
        CursorMovement cursorMovement = (CursorMovement) event;

        Point cursor = MouseInfo.getPointerInfo().getLocation();
        double x = cursor.getX() + cursorMovement.getxValue();
        double y = cursor.getY() + cursorMovement.getyValue();

        robot.mouseMove((int) Math.ceil(x), (int) Math.ceil(y));

    }
}
