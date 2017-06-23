package com.jan.threads.model;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.util.Random;

public class Controller {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField textField;

    @FXML
    private Canvas canvas;

    @FXML
    private Label label;

    private Worker worker;

    private Random random = new Random();

    private final int minX = -8;

    private final int maxX = 8;

    private final int minY = -8;

    private final int maxY = 8;

    @FXML
    private void clickStart() {
        if (worker != null) {
            worker.cancel();
        }
        Drawer drawer = new Drawer();
        worker = new Worker(Integer.parseInt(textField.getText()), () -> {
            double randomX = random.nextDouble();
            double randomY = random.nextDouble();
            double x = getRandomNumberFromRange(minX, maxX, randomX);
            double y = getRandomNumberFromRange(minY, maxY, randomY);
            boolean rv = Equation.calc(x, y);
            drawer.registerPoint(randomX, randomY, rv);
            return rv;
        });
        EventHandler<WorkerStateEvent> handler = new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                drawer.draw(canvas.getGraphicsContext2D());
                Double frac = (double)worker.getCounterIn() / (double)(worker.getCounterOut() + worker.getCounterIn());
                Double perc = frac * 100;
                Double value = (maxX - minX) * (maxY - minY) * frac;
                label.setText("Integral of f(x,y) = " + value.toString() + " which is " + perc.toString() + "% of the image.");
            }
        };
        worker.setOnSucceeded(handler);
        worker.setOnCancelled(handler);
        progressBar.progressProperty().bind(worker.progressProperty());
        new Thread(worker).start();
    }

    @FXML
    private void clickStop() {
        if (worker != null) {
            worker.cancel();
        }
    }

    private double getRandomNumberFromRange(double min, double max, double random) {
        return min + random * (max - min);
    }
}
