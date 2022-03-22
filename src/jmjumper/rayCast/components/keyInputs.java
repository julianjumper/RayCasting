package jmjumper.rayCast.components;

import jmjumper.rayCast.SimulationPanel;
import jmjumper.rayCast.lightComponents.LightSource;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyInputs implements KeyListener {

    private final LightSource lightSource;
    private final SimulationPanel simulationPanel;

    public keyInputs ( SimulationPanel simulationPanel ) {
        this.simulationPanel = simulationPanel;
        this.lightSource = simulationPanel.getLightSource();
    }

    @Override
    public void keyTyped ( KeyEvent e ) {

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
            case KeyEvent.VK_A -> lightSource.pressedA();
            case KeyEvent.VK_D -> lightSource.pressedD();
            case KeyEvent.VK_W -> lightSource.pressedW();
            case KeyEvent.VK_S -> lightSource.pressedS();
            case KeyEvent.VK_BACK_SPACE -> simulationPanel.deleteObsticals();
        }
    }

    @Override
    public void keyReleased ( KeyEvent e ) {

    }
}
