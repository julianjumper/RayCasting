package jmjumper.rayCast.lightComponents;

import jmjumper.rayCast.components.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LightSource extends JComponent {

    private int NUMBER_RAYS = 400;
    private int radius;

    private LightRay rays[];
    private HashMap<Integer, LightRay> activeRays;

    private int pos_x;
    private int pos_y;
    private int numberRays;
    private int fovOffset = 0;
    private int fovStep;
    private final int fovStepDenominator = 50;
    private final float FOV_ANGLE = 40f;

    private Vector movementVec;

    public LightSource ( int pos_x, int pos_y, int window_width, int window_height ) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.rays = new LightRay[NUMBER_RAYS];
        this.activeRays = new HashMap<>();
        this.movementVec = new Vector(pos_x, pos_y);
        fovStep = NUMBER_RAYS / fovStepDenominator;
        radius = (int) Math.sqrt(window_height * window_height + window_width * window_width);
    }

    private void createRays () {
        double distanceAngle = Math.toRadians(360f / NUMBER_RAYS);
        double fovAngle = Math.toRadians(FOV_ANGLE);

        rays = new LightRay[NUMBER_RAYS];
//        System.out.println(rays.length);

        numberRays = (int) (fovAngle / distanceAngle);
        // int offset = fovOffset;

        for ( int i = fovOffset; i < numberRays + fovOffset; i++ ) {            // Beginnt unten zu zählen bei ray.y > posVector.y; dann nach recht
            Vector posVec = new Vector(pos_x, pos_y);
            int x = (int) Math.round(radius * Math.sin(i * distanceAngle));
            int y = (int) Math.round(radius * Math.cos(i * distanceAngle));
            Vector lightVec = new Vector(x, y);
            lightVec = lightVec.add(posVec);
            if (i > rays.length-1)
                System.out.println("Zu groß: " + i);
            else if ( i < 0)
                System.out.println("zu klein: " + i);
            else {
                // System.out.println(i);
                rays[i] = new LightRay(posVec, lightVec);
            }
        }
        int index = 0;
        for ( LightRay ray : rays ) {
            if ( ray != null ) {
                activeRays.put(index, ray);
            }
            index++;
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

    public void pressedA () {
        System.out.println((fovOffset + fovStep) > NUMBER_RAYS);
        if ( (fovOffset + fovStep) > NUMBER_RAYS - 1 ) fovOffset = 0;
        else fovOffset += fovStep;
    }

    public void pressedD () {
        if ( (fovOffset - fovStep) < 0 ) fovOffset = NUMBER_RAYS - 10;
        else fovOffset -= fovStep;
    }

    public void pressedW () {
        LightRay movementRay = activeRays.get(activeRays.size() / 2);
        Vector movementRayPosVec = movementRay.getPosEnd();
        movementVec = new Vector(pos_x, pos_y).substract(movementRayPosVec);
        pos_x += movementVec.getX() / 10;
        pos_y += movementVec.getY() / 10;
    }

    public void pressedS () {
        LightRay movementRay = activeRays.get(activeRays.size() / 2);
        Vector movementRayPosVec = movementRay.getPosEnd();
        movementVec = new Vector(pos_x, pos_y).substract(movementRayPosVec);
        pos_x -= movementVec.getX() / 10;
        pos_y -= movementVec.getY() / 10;
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
        fovOffset = 0;
        NUMBER_RAYS = num;
        fovStep = NUMBER_RAYS / fovStepDenominator;
        rays = new LightRay[NUMBER_RAYS];
    }

    public int getNumberRays () {
        return numberRays;
    }


}
