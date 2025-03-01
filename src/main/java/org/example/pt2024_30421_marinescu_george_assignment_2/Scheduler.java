package org.example.pt2024_30421_marinescu_george_assignment_2;

import java.util.ArrayList;
import java.util.List;
import model.Server;
import model.Task;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer, SelectionPolicy policy) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.servers = new ArrayList<>();

        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server();
            servers.add(server);
            Thread t = new Thread(server);
            t.start();
        }
        changeStrategy(policy);
    }

    public void changeStrategy(SelectionPolicy policy) {
        if(policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new QueueStrategy();
        }

        if(policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public synchronized void dispatchTask(Task t) {
        strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }
}
