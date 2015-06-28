package ru.tandemservice.test.task2.solution;


import ru.tandemservice.test.task2.ElementExampleImpl;

public class Counter implements Runnable {

    final long start;
    ElementExampleImpl.Context context;

    public Counter(ElementExampleImpl.Context context) {
        this.context = context;
        this.start = System.currentTimeMillis();
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis() - start;
        System.out.println("Time of work:\t" + String.valueOf(time));
        if (context != null) {
            System.out.println("Operation count:\t" + String.valueOf(context.getOperationCount()));
        }
    }
}
