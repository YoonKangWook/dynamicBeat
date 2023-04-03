package dynamic_beat_17;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {

	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x, y = 500 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	public Note(String noteType) {
		if (noteType.equals("VK_LEFT")) {
			x = 275;
		}
		else if (noteType.equals("VK_DOWN")) {
			x = 575;
		}
		else if (noteType.equals("VK_RIGHT")) {
			x = 875;
		}
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(noteBasicImage, x, y, null);
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		if (y > 620) {
			System.out.println("Miss");
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				drop();
				if (proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					interrupt();
					break;
				}
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String judge() {
		if (y >= 600) {
			System.out.println("Bad");
			close();
			return "Bad";
		}
		else if (y >= 590) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 580) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 570) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if (y >= 560) {
			System.out.println("Great");
			close();
			return "Bad";
		}
		else if (y >= 550) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 540) {
			System.out.println("Bad");
			close();
			return "Bad";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
	
}
