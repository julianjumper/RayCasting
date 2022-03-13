package jmjumper.rayCast.lightComponents;

import javax.swing.*;
import java.awt.*;

public class Obstacle extends JComponent {

    private final Vector posOrigin;
    private final Vector posEnd;

    public Obstacle ( Vector posOrigin, Vector posEnd ) {
        this.posOrigin = posOrigin;
        this.posEnd = posEnd;
    }

    public void tick ( Graphics g ) {
        draw(g);
    }

    public void draw ( Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.drawLine(posOrigin.getX(), posOrigin.getY(), posEnd.getX(), posEnd.getY());
    }

    // Getters:
    public Vector getPosOrigin () {
        return posOrigin;
    }

    public Vector getPosEnd () {
        return posEnd;
    }
}
