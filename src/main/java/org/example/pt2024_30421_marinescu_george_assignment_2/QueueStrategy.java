package org.example.pt2024_30421_marinescu_george_assignment_2;

import model.Server;
import model.Task;

import java.util.List;

public class QueueStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {
        int minTasks = Integer.MAX_VALUE;
        Server minServer = null;

        for (Server s : servers) {
            if (s.getTasks().length < minTasks) {
                minTasks = s.getTasks().length;
                minServer = s;
            }
        }

        if (minServer != null) {
            minServer.addTask(t);
        }
    }
}
