package org.example.pt2024_30421_marinescu_george_assignment_2;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Task;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int maxArrivalTime;
    private int minArrivalTime;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberOfServers;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    @FXML
    private TextArea textArea;

    private Scheduler scheduler;
    private List<Task> generatedTasks;

    private long totalWaitingTime = 0;
    private long totalServiceTime = 0;
    private long[] hourlyArrivals;


    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime,
                             int minArrivalTime, int numberOfServers, int numberOfClients, TextArea textArea) {

        this.scheduler = new Scheduler(numberOfServers, 100, SelectionPolicy.SHORTEST_TIME);
        this.generatedTasks = new ArrayList<>();

        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        this.textArea = textArea;

        generateNRandomTasks(numberOfClients);

        hourlyArrivals = new long[timeLimit];
    }

    public void generateNRandomTasks(int n) {
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            Task t = new Task(i + 1, rand.nextInt(minArrivalTime, maxArrivalTime), rand.nextInt(minProcessingTime, maxProcessingTime));
            generatedTasks.add(t);
        }

        generatedTasks.sort(Comparator.comparingInt(Task::getArrivalTime));
    }

    @Override
    public synchronized void run() {
        int currentTime = 0;

        while (currentTime < timeLimit) {

            Iterator<Task> iterator = generatedTasks.iterator();

            while (iterator.hasNext()) {
                Task t = iterator.next();
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);

                    totalServiceTime += t.getServiceTime();
                    hourlyArrivals[currentTime]++;

                    iterator.remove();
                } else if (t.getArrivalTime() > currentTime) {
                    totalWaitingTime++;
                    System.out.println(totalWaitingTime + " ");
                }
            }

            textArea.appendText(printSimulationState(currentTime));

            currentTime++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }

        textArea.appendText("Average Waiting Time: " + (double) totalWaitingTime / numberOfClients + "\n");
        textArea.appendText("Average Service Time: " + (double) totalServiceTime / numberOfClients + "\n");
        textArea.appendText("Peak Hour: " + findPeakHour(hourlyArrivals) + "\n");
    }

    public int findPeakHour(long[] hourlyArrivals) {
        long max = 0;
        int peakHour = 0;

        for (int i = 0; i < hourlyArrivals.length; i++) {
            if (hourlyArrivals[i] > max) {
                max = hourlyArrivals[i];
                peakHour = i;
            }
        }

        return peakHour;
    }

//    public void printSimulationState(int currentTime) {
//        System.out.println("Time " + currentTime);
//
//
//        System.out.print("Waiting clients: ");
//        for (Task t : generatedTasks) {
//            System.out.print("(" + t.getId() + "," + t.getArrivalTime() + "," + t.getServiceTime() + "); ");
//        }
//        System.out.println();
//
//
//        for (int i = 0; i < numberOfServers; i++) {
//            System.out.print("Queue " + (i + 1) + ": ");
//            Task[] tasks = scheduler.getServers().get(i).getTasks();
//
//            if (tasks.length == 0) {
//                System.out.println("closed");
//            } else {
//                for (Task task : tasks) {
//                    System.out.print("(" + task.getId() + "," + task.getArrivalTime() + "," + task.getServiceTime() + "); ");
//                }
//                System.out.println();
//            }
//        }
//        System.out.println();
//    }

    public String printSimulationState(int currentTime) {
        StringBuilder output = new StringBuilder();

        output.append("Time ").append(currentTime).append("\n");

        output.append("Waiting clients: ");
        for (Task t : generatedTasks) {
            output.append("(").append(t.getId()).append(",").append(t.getArrivalTime()).append(",").append(t.getServiceTime()).append("); ");
        }
        output.append("\n");

        for (int i = 0; i < numberOfServers; i++) {
            output.append("Queue ").append(i + 1).append(": ");
            Task[] tasks = scheduler.getServers().get(i).getTasks();

            if (tasks.length == 0) {
                output.append("closed\n");
            } else {
                for (Task task : tasks) {
                    output.append("(").append(task.getId()).append(",").append(task.getArrivalTime()).append(",").append(task.getServiceTime()).append("); ");
                }
                output.append("\n");
            }
        }
        output.append("\n");

        return output.toString();
    }

}
