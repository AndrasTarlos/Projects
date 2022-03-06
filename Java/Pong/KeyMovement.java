package PongGame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyMovement implements Runnable, KeyListener {
    private Player p;
    private final JFrame frame;
    private boolean isArrowKeys;
    private boolean keyPressedUP;
    private boolean keyPressedDown;

    public KeyMovement(Player p, boolean isArrowKeys, JFrame frame) {
        this.p = p;
        this.isArrowKeys = isArrowKeys;
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if (isArrowKeys) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> keyPressedUP = true;
                case KeyEvent.VK_DOWN -> keyPressedDown = true;
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> keyPressedUP = true;
                case KeyEvent.VK_S -> keyPressedDown = true;
            }
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if (isArrowKeys) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> keyPressedUP = false;
                case KeyEvent.VK_DOWN -> keyPressedDown = false;
            }
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> keyPressedUP = false;
                case KeyEvent.VK_S -> keyPressedDown = false;
            }
        }
    }

    @Override
    public void run() {
        frame.addKeyListener(this);
        doWhile();
    }

    private void doWhile() {
        while (true) {
            if (keyPressedUP) {
                p.moveUp();
            } else if (keyPressedDown) {
                p.moveDown();
            }
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
