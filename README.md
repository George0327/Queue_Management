# Queue Management Simulator

A multi-threaded JavaFX simulation of clients arriving at service queues, built for the Programming Techniques course at the Technical University of Cluj-Napoca (assignment 2).

## What it does

You set the simulation parameters in the UI:

- number of clients and number of queues
- simulation time
- minimum and maximum client **arrival** time
- minimum and maximum client **service** time

The program generates that many clients with random arrival and service times, then runs the simulation in real time. At each second it prints the clients still waiting and the content of every queue. When it finishes it reports:

- average waiting time
- average service time
- the **peak hour** (the time with the most clients in the queues)

`Simulation Results.txt` contains a sample log.

## Design

- Each queue is a `Server` that runs on its own thread. It holds a `BlockingQueue<Task>` and tracks its waiting period with an `AtomicInteger`, so threads can share state safely.
- A `Scheduler` dispatches each arriving client to a server using the **Strategy pattern**:
  - `QueueStrategy` picks the queue with the fewest clients (`SHORTEST_QUEUE`).
  - `TimeStrategy` picks the queue with the smallest waiting time (`SHORTEST_TIME`).
- `SimulationManager` generates the clients and drives the clock.
- The UI (`hello-view.fxml`) and `Controller` are separate from the model classes (`Server`, `Task`).

> The strategy is currently fixed to `SHORTEST_TIME` in `SimulationManager`. Switching to `SHORTEST_QUEUE` is a one-line change; there is no UI control for it yet.

## Run

Requirements: JDK 17+ and Maven.

```bash
./mvnw javafx:run
```

## Documentation

The assignment write-up is in `PT2024_Marinescu_George_Assignment2_Documentation.pdf`.
