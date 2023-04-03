package dynamic_beat_17;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter { 

	@Override
	public void keyPressed(KeyEvent e) {
		if (DynamicBeat.game == null) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			DynamicBeat.game.pressL();
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			DynamicBeat.game.pressD();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			DynamicBeat.game.pressR();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (DynamicBeat.game == null) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			DynamicBeat.game.releaseL();
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			DynamicBeat.game.releaseD();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			DynamicBeat.game.releaseR();
		}
	}
	
}
