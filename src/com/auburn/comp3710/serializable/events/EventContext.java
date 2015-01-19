package com.auburn.comp3710.serializable.events;

import com.auburn.comp3710.serializable.BluetoothSerializable;

/**
 * Created by Rabby on 4/24/2014.
 */
public class EventContext {
    EventStrategy strategy;

    public EventContext() {/*Do nothing*/}

    public EventContext(EventStrategy strategy) {
        this.strategy = strategy;
    }

    public void handleEvent(BluetoothSerializable event) {
        strategy.handleEvent(event);
    }
}
