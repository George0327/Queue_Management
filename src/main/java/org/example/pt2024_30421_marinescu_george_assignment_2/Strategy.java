package org.example.pt2024_30421_marinescu_george_assignment_2;

import model.Server;
import model.Task;
import java.util.List;

public interface Strategy {
    void addTask(List<Server> servers, Task t);
}
