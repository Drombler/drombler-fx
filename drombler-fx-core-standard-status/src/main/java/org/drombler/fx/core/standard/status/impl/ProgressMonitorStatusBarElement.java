package org.drombler.fx.core.standard.status.impl;

import java.util.List;
import java.util.stream.Collectors;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.status.StatusBarElement;
import org.drombler.commons.client.geometry.HorizontalAlignment;
import org.drombler.commons.context.ApplicationContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.concurrent.WorkerUtils;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.ProgressMonitor;

/**
 *
 * @author puce
 */
@StatusBarElement(horizontalAlignment = HorizontalAlignment.RIGHT, position = 20)
public class ProgressMonitorStatusBarElement extends BorderPane implements ApplicationContextSensitive, LocalContextProvider {

    @FXML
    private ProgressMonitor progressMonitor;

//    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private Context applicationContext;

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext localContext = new SimpleContext(contextContent);

    public ProgressMonitorStatusBarElement() throws Exception {
        FXMLLoaders.loadRoot(this);

//        for (int i = 0; i < 3; i++) {
//            SampleTask sampleTask = new SampleTask(i + 1);
//            contextContent.add(sampleTask);
//            sampleTask.stateProperty().addListener((observable, oldValue, newValue) -> {
//                if (WorkerUtils.getFinishedStates().contains(newValue)) {
//                    contextContent.remove(sampleTask);
//                }
//            });
//            executorService.execute(sampleTask);
//        }
    }

    @Override
    public void setApplicationContext(Context applicationContext) {

        this.applicationContext = applicationContext;

        if (this.applicationContext != null) {
            this.applicationContext.addContextListener(Worker.class, event -> {
                updateProgressMonitor();
            });
            updateProgressMonitor();
        }
    }

    private void updateProgressMonitor() {
        List<? extends Worker<?>> workers = applicationContext.findAll(Worker.class).stream()
                .map(worker -> (Worker<?>) worker)
                .filter(worker -> !WorkerUtils.getFinishedStates().contains(worker.getState()))
                .collect(Collectors.toList());
        progressMonitor.getWorkers().retainAll(workers);
        workers.removeAll(progressMonitor.getWorkers());
        progressMonitor.getWorkers().addAll(workers);
    }

    @Override
    public Context getLocalContext() {
        return localContext;
    }

//    private class SampleTask extends Task<Object> {
//
//        private final int index;
//
//        public SampleTask(int index) {
//            this.index = index;
//            updateTitle("Some Title " + index);
//            updateProgress(2, 10);
//        }
//
//        @Override
//        protected Object call() throws Exception {
//            for (int i = 0; i < 20; i++) {
//                if (isCancelled()) {
//                    updateMessage("Cancelled");
//                    break;
//                }
//                updateProgress(i + 1, 20);
//                updateMessage((i + 1) + "Some very long message adf a dsf asdf dasfd sdaf dafd as");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException interrupted) {
//                    if (isCancelled()) {
//                        updateMessage("Cancelled");
//                        break;
//                    }
//                }
//            }
//            return true;
//        }
//
//        @Override
//        public boolean cancel(boolean mayInterruptIfRunning) {
//            return super.cancel(mayInterruptIfRunning); //To change body of generated methods, choose Tools | Templates.
//        }
//
//    }

}
