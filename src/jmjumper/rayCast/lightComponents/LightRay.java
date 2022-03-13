package jmjumper.rayCast.lightComponents;

import javax.swing.*;
import java.awt.*;

public class LightRay extends JComponent {

    private final Vector posOrigin;
    private Vector posEnd;

    public LightRay ( Vector pos1, Vector pos2 ) {
        this.posOrigin = pos1;
        this.posEnd = pos2;
    }

    public void draw ( Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.drawLine(posOrigin.getX(), posOrigin.getY(), posEnd.getX(), posEnd.getY());
    }


    // Setters and stuff
    public void setPosEnd ( Vector vec ) {
        this.posEnd = vec;
    }

    public Vector getPosOrigin () {
        return posOrigin;
    }

    public Vector getPosEnd () {
        return posEnd;
    }
}
