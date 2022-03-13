package jmjumper.rayCast;

import jmjumper.rayCast.lightComponents.LightRay;
import jmjumper.rayCast.lightComponents.LightSource;
import jmjumper.rayCast.lightComponents.Obstacle;
import jmjumper.rayCast.lightComponents.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class SimulationPanel extends JPanel implements Screen, MouseMotionListener, MouseListener, KeyListener {

    private final int width;
    private final int height;
    private LightSource lightSource;
    private final ArrayList<Obstacle> obstacles;

    private Vector startDrag;

    Point point1;
    Point point2;
    Line2D line2d;

    public SimulationPanel ( int width, int height ) {
        this.width = width;
        this.height = height;
        obstacles = new ArrayList<>();

        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    private void initialise () {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        lightSource = new LightSource(50, 50, width, height);
    }

    private void addObstacle ( Vector start, Vector end ) {
        obstacles.add(new Obstacle(start, end));
    }

    private void checkCollision () {
        LightRay rays[] = lightSource.getRays();


        for ( LightRay ray : rays ) {
            Vector rayPosOrigin = ray.getPosOrigin();
            Vector rayPosEnd = ray.getPosEnd();
            int x1 = rayPosOrigin.getX();
            int x2 = rayPosEnd.getX();
            int y1 = rayPosOrigin.getY();
            int y2 = rayPosEnd.getY();
            ArrayList<Vector> intersections = new ArrayList<>();
            for ( Obstacle obst : obstacles ) {
                Vector obstPosOrigin = obst.getPosOrigin();
                Vector obstPosEnd = obst.getPosEnd();
                int x3 = obstPosOrigin.getX();
                int y3 = obstPosOrigin.getY();
                int x4 = obstPosEnd.getX();
                int y4 = obstPosEnd.getY();
                int denominator = ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
                if ( denominator != 0 ) {
                    int interSectionX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
                    int interSectionY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;
                    Vector intersection = new Vector(interSectionX, interSectionY);
                    if ( (intersection.getX() <= Math.max(x3, x4) && intersection.getX() >= Math.min(x3, x4)) && (intersection.getY() <= Math.max(y3, y4) && intersection.getY() >= Math.min(y3, y4)) ) {
                        if ( (intersection.getX() <= Math.max(x1, x2) && intersection.getX() >= Math.min(x1, x2)) && (intersection.getY() <= Math.max(y1, y2) && intersection.getY() >= Math.min(y1, y2)) ) {
                            intersections.add(intersection);
                            ray.setPosEnd(intersection);
                        }
                    }
                } else System.out.println("denominator is zero!");
            }
            if ( !intersections.isEmpty() ) {
                Vector nearest = new Vector(5000, 5000);
                int maxValue = nearest.substract(rayPosOrigin).length();
                for ( Vector vec : intersections ) {
                    int currentDistance = vec.substract(rayPosOrigin).length();
                    if ( currentDistance < maxValue ) {
                        maxValue = currentDistance;
                        nearest = vec;
                    }
                }
                ray.setPosEnd(nearest);
            }
        }
    }

    @Override
    public void paint ( Graphics g ) {
        super.paint(g);
        lightSource.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for ( Obstacle obst : obstacles )
            if ( obst != null )
                obst.tick(g);

        if ( point1 != null && point2 != null ) {
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.draw(line2d);
        }
    }

    public void tick () {
        lightSource.tick();
        checkCollision();
        repaint();
    }

    public void handleMouse ( int mouseX, int mouseY ) {
        lightSource.setPos_x(mouseX); // - width / 100); // dieses "... - width / ..." ist für einen leichten Offset
        lightSource.setPos_y(mouseY);
    }


    @Override
    public void startUp () {
        initialise();
    }

    @Override
    public void close () {

    }

    public void setLightRadius ( int radius ) {
        lightSource.setLIGHT_RADIUS(radius);
    }

    // Mouseevents
    @Override
    public void mouseDragged ( MouseEvent e ) {
        if ( SwingUtilities.isRightMouseButton(e) ) {
            point2 = e.getPoint();
            line2d = new Line2D.Double(point1, point2);
            repaint();
        }
        if ( SwingUtilities.isLeftMouseButton(e) )
            handleMouse(e.getX(), e.getY());
    }

    @Override
    public void mousePressed ( MouseEvent e ) {
        if ( SwingUtilities.isRightMouseButton(e) ) {
            point1 = e.getPoint();
            startDrag = new Vector(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased ( MouseEvent e ) {
        if ( SwingUtilities.isRightMouseButton(e) ) {
            Vector endDrag = new Vector(e.getX(), e.getY());
            addObstacle(startDrag, endDrag);
        }
    }

    @Override
    public void keyPressed ( KeyEvent e ) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_1 -> lightSource.setNUMBER_RAYS(50);
            case KeyEvent.VK_2 -> lightSource.setNUMBER_RAYS(100);
            case KeyEvent.VK_3 -> lightSource.setNUMBER_RAYS(200);
            case KeyEvent.VK_4 -> lightSource.setNUMBER_RAYS(300);
            case KeyEvent.VK_5 -> lightSource.setNUMBER_RAYS(400);
            case KeyEvent.VK_6 -> lightSource.setNUMBER_RAYS(500);
            case KeyEvent.VK_7 -> lightSource.setNUMBER_RAYS(750);
            case KeyEvent.VK_8 -> lightSource.setNUMBER_RAYS(1000);
            case KeyEvent.VK_9 -> lightSource.setNUMBER_RAYS(1500);
            case KeyEvent.VK_0 -> lightSource.setNUMBER_RAYS(5000);
        }
    }

    // unused methods
    @Override
    public void mouseMoved ( MouseEvent e ) {
    }

    @Override
    public void mouseClicked ( MouseEvent e ) {
    }

    @Override
    public void mouseEntered ( MouseEvent e ) {
    }

    @Override
    public void mouseExited ( MouseEvent e ) {
    }

    @Override
    public void keyTyped ( KeyEvent e ) {

    }

    @Override
    public void keyReleased ( KeyEvent e ) {

    }
}
