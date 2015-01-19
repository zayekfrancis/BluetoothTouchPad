package com.auburn.comp3710.serializable.events;

import com.auburn.comp3710.serializable.CursorMovement;
import com.auburn.comp3710.serializable.KeyboardPress;
import com.auburn.comp3710.serializable.KeyboardRelease;
import com.auburn.comp3710.serializable.MouseClickPress;
import com.auburn.comp3710.serializable.MouseClickRelease;
import com.auburn.comp3710.serializable.VerticalScroll;

/**
 * Created by Rabby on 4/24/2014.
 */
public class EventStrategyFactory {
    private static final Class CURSOR_MOVEMENT = CursorMovement.class;
    private static final Class MOUSE_CLICK_PRESS = MouseClickPress.class;
    private static final Class MOUSE_CLICK_RELEASE = MouseClickRelease.class;
    private static final Class KEYBOARD_PRESS = KeyboardPress.class;
    private static final Class KEYBOARD_RELEASE = KeyboardRelease.class;
    private static final Class VERTICAL_CURSOR_SCROLL = VerticalScroll.class;

    public static EventStrategy getStrategy(Class eventClass) {
        EventStrategy strategy = null;

        if (eventClass == CURSOR_MOVEMENT) {
            strategy = new CursorMovementStrategy();

        } else if (eventClass == MOUSE_CLICK_PRESS) {
            strategy = new MouseClickPressStrategy();
        } else if (eventClass == MOUSE_CLICK_RELEASE) {
            strategy = new MouseClickReleaseStrategy();
        } else if (eventClass == KEYBOARD_PRESS) {
            //Need to do
        } else if (eventClass == VERTICAL_CURSOR_SCROLL) {
            strategy = new VerticalScrollStrategy();
        }
        else {
            throw new IllegalArgumentException("Passed class is not supported. Please add " + eventClass +
                    "To the event factory");
        }

        return strategy;
    }

}
