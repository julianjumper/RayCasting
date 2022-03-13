package jmjumper.rayCast.lightComponents;

import javax.swing.*;
import java.awt.*;

public class LightSource extends JComponent {

    private int NUMBER_RAYS = 200;
    private int radius;

    private LightRay rays[];

    private int pos_x;
    private int pos_y;

    public LightSource ( int pos_x, int pos_y, int window_width, int window_height ) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        rays = new LightRay[NUMBER_RAYS];
        radius = (int) Math.sqrt(window_height * window_height + window_width * window_width);
    }

    private void createRays () {
        double distanceAngle = Math.toRadians(360f / NUMBER_RAYS);

        for ( int i = 0; i < NUMBER_RAYS; i++ ) {
            Vector posVec = new Vector(pos_x, pos_y);
            int x = (int) Math.round(radius * Math.sin(i * distanceAngle));
            int y = (int) Math.round(radius * Math.cos(i * distanceAngle));
            Vector lightVec = new Vector(x, y);
            lightVec = lightVec.add(posVec);
            rays[i] = new LightRay(posVec, lightVec);
        }
    }

    public void tick () {
        createRays();
    }

    @Override
    public void paintComponent ( Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        for ( int x = 0; x < NUMBER_RAYS; x++ ) {
            if ( rays[x] != null )
                rays[x].draw(g);
        }
    }

    // Setters and stuff
    public void setPos_x ( int pos_x ) {
        this.pos_x = pos_x;
    }

    public void setPos_y ( int pos_y ) {
        this.pos_y = pos_y;
    }

    public LightRay[] getRays () {
        return rays;
    }

    public void setLIGHT_RADIUS ( int r ) {
        this.radius = r;
    }

    public void setNUMBER_RAYS ( int num ) {
        NUMBER_RAYS = num;
        rays = new LightRay[NUMBER_RAYS];
    }

}
