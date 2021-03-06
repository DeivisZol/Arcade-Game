package game.main;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

    public InputHandler(Game game) {
        game.addKeyListener((this));
    }

    public static class Key {
        private boolean pressed = false;

        public boolean isPressed() {
            return pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
        }
    }

    public List<Key> keys = new ArrayList<>();

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key shootUp = new Key();
    public Key shootDown = new Key();
    public Key shootLeft = new Key();
    public Key shootRight = new Key();

    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(),true);
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(),false);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void toggleKey(int keyCode, boolean isPressed) {
        //walking buttons
        if(keyCode == KeyEvent.VK_W) {
            up.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_S) {
            down.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_A) {
            left.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_D) {
            right.toggle(isPressed);
        }

        //shooting buttons
        if(keyCode == KeyEvent.VK_UP){
            shootUp.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_DOWN){
            shootDown.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_LEFT){
            shootLeft.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_RIGHT){
            shootRight.toggle(isPressed);
        }
    }
}
