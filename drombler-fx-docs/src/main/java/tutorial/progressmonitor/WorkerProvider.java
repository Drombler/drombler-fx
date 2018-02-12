package tutorial.progressmonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.LocalContextProvider;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.fx.concurrent.WorkerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerProvider implements LocalContextProvider {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerProvider.class);

    private final SimpleContextContent contextContent = new SimpleContextContent();
    private final SimpleContext context = new SimpleContext(contextContent);
    private final ExecutorService executorService
            = Executors.newCachedThreadPool(runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                return thread;
            });

    public void runTask() {
        SampleTask sampleTask = new SampleTask();
        // ! add the task to the local context
        // the progress monitor will pick it up automatically
        sampleTask.setOnRunning(event -> contextContent.add(sampleTask));
        sampleTask.setOnSucceeded(event -> {
            Foo foo = sampleTask.getValue();
            // do something
        });
        sampleTask.setOnFailed(event -> {
            String errorMessage = "Sample task failed!";
            Throwable exception = sampleTask.getException();
            if (exception != null) {
                LOG.error(errorMessage, exception);
            } else {
                LOG.error(errorMessage);
            }
            // show error dialog
        });
        sampleTask.stateProperty().addListener((observable, oldValue, newValue) -> {
            // you can use org.drombler.commons.fx.concurrent.WorkerUtils 
            // to easily check for finished states
            if (WorkerUtils.getFinishedStates().contains(newValue)) {
                // ! remove the task from the local context
                contextContent.remove(sampleTask);
            }
        });
        executorService.execute(sampleTask);
    }

    @Override
    public Context getLocalContext() {
        return context;
    }

    public static class SampleTask extends Task<Foo> {

        @Override
        protected Foo call() throws Exception {
            return new Foo();
        }

    }
}
