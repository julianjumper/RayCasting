package jmjumper.rayCast;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame implements ActionListener {

    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    private final SimulationPanel simulationPanel;

    public Gui () {
        super("RayCasting - Simulation");
        setResizable(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        simulationPanel = new SimulationPanel(WIDTH, HEIGHT);
        simulationPanel.startUp();
        add(simulationPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized ( ComponentEvent e ) {
                Rectangle windowSize = getBounds();
                simulationPanel.setLightRadius((int) Math.sqrt(windowSize.height * windowSize.height + windowSize.width * windowSize.width));
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
        simulationPanel.tick();
    }
}
