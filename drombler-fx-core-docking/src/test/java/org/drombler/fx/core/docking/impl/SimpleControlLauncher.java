package org.drombler.fx.core.docking.impl;

import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author puce
 */


public class SimpleControlLauncher extends Application {
    static CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
        stage.close();
        latch.countDown();
    }
    
    public static void main(String [] args) {
        launch(args);
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
} 
