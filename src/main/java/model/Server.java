package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server() {
        this.tasks = new LinkedBlockingQueue<>(100);
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task task) {
        tasks.add(task);
        waitingPeriod.addAndGet(task.getServiceTime());
    }

    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    //END GET AND SET

    public synchronized void run() {
        while (true) {
            Task task = tasks.peek();

            if (task != null) {
                for (int i = 0; i < task.getServiceTime(); i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Thread was interrupted", e);
                    }
                    waitingPeriod.addAndGet(-1);
                    task.setServiceTime(task.getServiceTime() - 1);
                }

                if (task.getServiceTime() == 0) {
                    tasks.remove(task);
                }
            }
        }
    }
}
