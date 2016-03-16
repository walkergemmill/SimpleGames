package Maze;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Keyboard implements KeyListener { 
	
	private boolean[] keys = new boolean[120]; //120 = number of keys
	
	public boolean up, down,left,right,w, space;
	
	public void keyPressed (KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased ( KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	public void keyTyped (KeyEvent e) {
		
	}
	public void update() {
		up=keys[KeyEvent.VK_UP];
		down=keys[KeyEvent.VK_DOWN];
		right=keys[KeyEvent.VK_RIGHT];
		left=keys[KeyEvent.VK_LEFT];
		w=keys[KeyEvent.VK_W];
		space=keys[KeyEvent.VK_SPACE];
	}
}