package com.example.globalrestorationchurch.ui.sermon;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskRunner {
    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    //    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());
    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    // Creates a thread pool manager
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue
    );

    public <T> void executeAsync(Callable<T> callable, Callback<T> callback) {
        threadPoolExecutor.execute(() -> {
            final T result;
            try {
                result = callable.call();
                handler.post(() -> callback.onComplete(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public interface Callback<R> {
        void onComplete(R result);
    }
}