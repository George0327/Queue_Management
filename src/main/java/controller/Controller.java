package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.pt2024_30421_marinescu_george_assignment_2.SimulationManager;

public class Controller {
    @FXML
    private TextField clientsTF;

    @FXML
    private TextField queuesTF;

    @FXML
    private TextField simTimeTF;

    @FXML
    private TextField minAT;

    @FXML
    private TextField maxAT;

    @FXML
    private TextField minST;

    @FXML
    private TextField maxST;

    @FXML
    private TextArea textArea;

    @FXML
    protected void onStartButtonClick() {
        textArea.clear();

        int clients = Integer.parseInt(clientsTF.getText());
        int queues = Integer.parseInt(queuesTF.getText());
        int simTime = Integer.parseInt(simTimeTF.getText());
        int minArrivalTime = Integer.parseInt(minAT.getText());
        int maxArrivalTime = Integer.parseInt(maxAT.getText());
        int minServiceTime = Integer.parseInt(minST.getText());
        int maxServiceTime = Integer.parseInt(maxST.getText());

        SimulationManager gen = new SimulationManager(simTime, maxServiceTime, minServiceTime, maxArrivalTime, minArrivalTime, queues, clients, textArea);
        Thread t = new Thread(gen);
        t.start();

    }

}