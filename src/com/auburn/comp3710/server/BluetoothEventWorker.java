package com.auburn.comp3710.server;

import java.util.concurrent.LinkedBlockingQueue;

import com.auburn.comp3710.serializable.BluetoothSerializable;
import com.auburn.comp3710.serializable.events.EventContext;
import com.auburn.comp3710.serializable.events.EventStrategy;
import com.auburn.comp3710.serializable.events.EventStrategyFactory;

/**
 * Created by Rabby on 4/24/2014.
 */
public class BluetoothEventWorker implements Runnable {
    private LinkedBlockingQueue<BluetoothSerializable> eventQueue;
    private boolean shouldRun;

    public BluetoothEventWorker() {
        eventQueue = new LinkedBlockingQueue<BluetoothSerializable>();
        shouldRun = true;
    }

    public void queue(BluetoothSerializable event) throws InterruptedException {
        eventQueue.put(event);
    }

    public void run() {
        BluetoothSerializable event = null;
        while(shouldRun) {
            try {
                event = take();
            } catch (InterruptedException e) {
                System.out.println("Unable to dequeue event due to:\n" + e);
            }

            if (event != null) {
                EventStrategy strategy = EventStrategyFactory.getStrategy(event.getClass());
                EventContext context = new EventContext(strategy);
                context.handleEvent(event);
            }
        }
    }

    private BluetoothSerializable take() throws InterruptedException {
        return eventQueue.take();
    }

    public void stop() {
        shouldRun = false;
        eventQueue = null;
    }

}
