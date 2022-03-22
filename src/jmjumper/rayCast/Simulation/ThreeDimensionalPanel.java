package jmjumper.rayCast.Simulation;

import jmjumper.rayCast.Screen;
import jmjumper.rayCast.SimulationPanel;
import jmjumper.rayCast.components.keyInputs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class ThreeDimensionalPanel extends JPanel implements Screen {

    private final int width;
    private final int height;
    private final int maxDistance = 507;
    private SimulationPanel simulationPanel;
    private int numberRayColumns;
    private ArrayList<Integer> lengthList;

    public ThreeDimensionalPanel ( SimulationPanel simulationPanel, int width, int height ) {
        this.width = width;
        this.height = height;
        this.simulationPanel = simulationPanel;

        setFocusable(true);
    }

    private void initialise () {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        addKeyListener(new keyInputs(simulationPanel));
        lengthList = simulationPanel.getLengthList();
    }

    public void tick () {
        repaint();
    }

    @Override
    public void paint ( Graphics g ) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        numberRayColumns = simulationPanel.getNumberRays();
        int columnWidth = width / numberRayColumns;
        lengthList = simulationPanel.getLengthList();
        int oldColor = 0;
        for ( int i = 0; i < numberRayColumns; i++ ) {
            int size = lengthList.get(i);
            int maxSize = Collections.max(lengthList);
            // System.out.println(color);
            // int color = 255 - (size / 7);
            float colorDecimal = size / 1000f;
            int color = 0;
            if ( colorDecimal < 1f ) {
                color = (int) (colorDecimal * 255);
                color = 255 - color;
            }

            int reminderHeight = size / 2;
            int bottom = height - size;

            //System.out.println( reminderHeight);
        g2d.setColor(new Color(0, 255, 255));
        g2d.fillRect(i * columnWidth, 0, i * columnWidth + columnWidth, reminderHeight);

            g2d.setColor(new Color(0, 0, color >= maxDistance ? 0 : color));
            g2d.fillRect(i * columnWidth, reminderHeight, i * columnWidth + columnWidth, bottom);

         g2d.setColor(new Color(187, 83, 0));
         g2d.fillRect(i * columnWidth, Math.max(bottom + reminderHeight, (int) (height / 2f)), i * columnWidth + columnWidth, reminderHeight);

            oldColor = color;
        }
    }

    @Override
    public void startUp () {
        initialise();
    }

    @Override
    public void close () {

    }
}
