#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.concurrent.WorkerUtils;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ViewDocking(areaId = "bottom", position = 10, displayName = "%displayName", icon = "bottom-test-pane.png",
accelerator = "Shortcut+2",
menuEntry =
@WindowMenuEntry(path = "", position = 30))
public class BottomTestPane extends BorderPane implements LocalContextProvider {
  private static final Logger LOG = LoggerFactory.getLogger(BottomTestPane.class);

    @FXML
    private Button createTestWorkerButton;
    private final List<Worker<?>> workers = new ArrayList<>();

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(contextContent);
    private final ExecutorService executorService
            = Executors.newCachedThreadPool(runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                return thread;
            });
    private long counter = 1;

    public BottomTestPane() {
        loadFXML();
        createTestWorkerButton.setOnAction(event -> addTestWorker(counter++));
    }

    private void loadFXML() {
        FXMLLoaders.loadRoot(this);
    }

    private void addTestWorker(long id) {
        final TestWorker testWorker = new TestWorker(id);
        testWorker.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (WorkerUtils.getFinishedStates().contains(newValue)) {
                workers.remove(testWorker);
            }
        });
        workers.add(testWorker);
        executorService.execute(testWorker);

        // ! add the task to the local context
        // the progress monitor will pick it up automatically
        testWorker.setOnRunning(event -> contextContent.add(testWorker));
        testWorker.setOnSucceeded(event -> {
            Long counter = testWorker.getValue();
            LOG.info(counter.toString());
            // do something
        });
        testWorker.setOnFailed(event -> {
            String errorMessage = "Sample task failed!";
            Throwable exception = testWorker.getException();
            if (exception != null) {
                LOG.error(errorMessage, exception);
            } else {
                LOG.error(errorMessage);
            }
            // show error dialog
        });
        testWorker.stateProperty().addListener((observable, oldValue, newValue) -> {
            // you can use org.drombler.commons.fx.concurrent.WorkerUtils 
            // to easily check for finished states
            if (WorkerUtils.getFinishedStates().contains(newValue)) {
                // ! remove the task from the local context
                contextContent.remove(testWorker);
            }
        });
        executorService.execute(testWorker);
    }

    @Override
    public Context getLocalContext() {
        return context;
    }

    private static class TestWorker extends Task<Long> {

        private static final long MAX_ITERATIONS = 50L;
        private final long id;
        private long counter = 0;

        public TestWorker(long id) {
            this.id = id;
            updateTitle("Test Worker " + id);
            updateMessage("Iteration " + counter);
            updateProgress(-1, 1);
        }

        @Override
        protected Long call() throws Exception {
            while (!isCancelled() && counter < MAX_ITERATIONS) {
                counter++;
                updateMessage("Iteration " + counter);
                Thread.sleep(1000l);
            }
            return counter;
        }

        @Override
        public String toString() {
            return getTitle();
        }

    }
}
