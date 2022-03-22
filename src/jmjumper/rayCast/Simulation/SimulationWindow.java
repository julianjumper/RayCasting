package jmjumper.rayCast.Simulation;

import jmjumper.rayCast.SimulationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SimulationWindow extends JFrame implements ActionListener {

    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    private final ThreeDimensionalPanel threeDimensionalPanel;
    private final SimulationPanel simulationPanel;

    public SimulationWindow (SimulationPanel simulationPanel) {
        super("3D Simulation");
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.simulationPanel = simulationPanel;

        threeDimensionalPanel = new ThreeDimensionalPanel(simulationPanel, WIDTH, HEIGHT);
        threeDimensionalPanel.startUp();
        add(threeDimensionalPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized ( ComponentEvent e ) {
                Rectangle windowSize = getBounds();
                threeDimensionalPanel.setPreferredSize(new Dimension(windowSize.width, windowSize.height));
            }
        });

        // Start Simulation
        Timer timer = new Timer(15, this);
        timer.start();


        setVisible(true);
        setLocationRelativeTo(null);
        pack();
    }

    // actionPerformed aufgerufen durch den Timer
    @Override
    public void actionPerformed ( ActionEvent e ) {
        threeDimensionalPanel.tick();
    }

}
