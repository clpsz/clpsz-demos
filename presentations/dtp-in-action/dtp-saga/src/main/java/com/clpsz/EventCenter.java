package com.clpsz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.concurrent.*;


/**
 * @author clpsz
 */
public class EventCenter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static final EventCenter INSTANCE = new EventCenter();

    private final BlockingQueue<Event> queue = new ArrayBlockingQueue<>(100);

    private EventCenter() {
    }

    public void putEvent(Event event) {
        try {
            queue.put(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startEventLoop() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        Event event = queue.take();
                        logger.info("event={}, succeed={}", event.getName(), event.getSucceed());

                        if (event.getClass().equals(CreateOrderEvent.class)) {
                            CreateOrderEvent event1 = (CreateOrderEvent)event;
                            try {
                                event1.processEvent();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
