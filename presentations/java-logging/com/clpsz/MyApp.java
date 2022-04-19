package com.clpsz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author clpsz
 */
public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

    public void greeting(String message) {
        logger.trace("enter {}", "greeting");

        for (int i = 0; i < 3; i++) {
            System.out.println("hello " + message);
            logger.info("in app: hello {}, count = {}", message, i);
        }

        logger.trace("exit {}", "greeting");
    }
}
