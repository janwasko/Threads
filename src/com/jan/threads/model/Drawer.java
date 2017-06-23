package com.jan.threads.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Drawer {

    private BufferedImage bi;

    public Drawer() {
        bi = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
    }

    public void registerPoint(double x, double y, boolean in) {
        y = 1 - y;
        bi.setRGB((int) (bi.getWidth() * x), (int) (bi.getHeight() * y), in ? Color.YELLOW.getRGB() : Color.BLUE.darker().getRGB());
    }

    public void draw(GraphicsContext gc) {
         gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
    }
}
