package com.jan.threads.model;

import javafx.concurrent.Task;
import java.util.function.Supplier;

public class Worker extends Task {

    private int n;

    private Supplier<Boolean> work;

    private int counterIn;

    private int counterOut;

    public Worker(int n, Supplier<Boolean> work) {
        this.n = n;
        this.work = work;
    }

    @Override
    protected Object call() throws Exception {
        for (int i = 0; i < n; i++) {
            if (isCancelled()) {
                break;
            }
            if (work.get()) {
                counterIn++;
            } else {
                counterOut++;
            }
            updateProgress(i, n);
        }
        return null;
    }

    public int getCounterIn() {
        return counterIn;
    }

    public int getCounterOut() {
        return counterOut;
    }
}
