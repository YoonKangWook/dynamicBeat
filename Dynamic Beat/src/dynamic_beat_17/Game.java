package dynamic_beat_17;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Window.Type;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	// 노트 판정 포인트 이미지
	private Image judgeNote_L = new ImageIcon(Main.class.getResource("../images/judgeNote.png")).getImage();
	private Image judgeNote_D = new ImageIcon(Main.class.getResource("../images/judgeNote.png")).getImage();
	private Image judgeNote_R = new ImageIcon(Main.class.getResource("../images/judgeNote.png")).getImage();
	
	// 키 입력시 판정 포인트 이미지
	private Image judgePressed_L = new ImageIcon(Main.class.getResource("../images/judgePressed.png")).getImage();
	private Image judgePressed_D = new ImageIcon(Main.class.getResource("../images/judgePressed.png")).getImage();
	private Image judgePressed_R = new ImageIcon(Main.class.getResource("../images/judgePressed.png")).getImage();
	
	// 노트 히트시 이미지
	private Image yellowFlareImage;
	private Image judgeImage;
	
	// 곡 이름
	private String titleName;
	private String difficulty;
	private Music gameMusic;
	
	// 곡 이름.mp3
	private String musicTitle;
	
	ArrayList<Note> noteList = new ArrayList<Note>();
	
	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
	}
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(judgeNote_L, 250, 500, null);
		g.drawImage(judgeNote_D, 550, 500, null);
		g.drawImage(judgeNote_R, 850, 500, null);
		
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if(note.getY() > 620) {
				judgeImage = new ImageIcon(Main.class.getResource("../images/judgeMiss.png")).getImage();
			}
			if (!note.isProceeded()) {
				noteList.remove(i);
				i--;
			}
			else {
				note.screenDraw(g);	
			}
		}

		// 게임 UI
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(titleName, 20, 700);
		g.drawString(difficulty, 1190, 700);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString("0000000", 25, 100);
		g.drawImage(yellowFlareImage, 450, 450, null);
		g.drawImage(judgeImage, 300, 400, null);
	}
	
	// 키보드 입력시 이벤트 함수
	public void pressL() {
		judge("VK_LEFT");
		judgeNote_L = new ImageIcon(Main.class.getResource("../images/judgePressed.png")).getImage();
		new Music("judgePressed.mp3", false).start();
	}
	public void pressD() {
		judge("VK_DOWN");
		judgeNote_D = new ImageIcon(Main.class.getResource("../images/judgePressed.png")).getImage();
		new Music("judgePressed.mp3", false).start();
	}
	public void pressR() {
		judge("VK_RIGHT");
		judgeNote_R = new ImageIcon(Main.class.getResource("../images/judgePressed.png")).getImage();
		new Music("judgePressed.mp3", false).start();
	}
	public void releaseL() {
		judgeNote_L = new ImageIcon(Main.class.getResource("../images/judgeNote.png")).getImage();
	}
	public void releaseD() {
		judgeNote_D = new ImageIcon(Main.class.getResource("../images/judgeNote.png")).getImage();
	}
	public void releaseR() {
		judgeNote_R = new ImageIcon(Main.class.getResource("../images/judgeNote.png")).getImage();
	}
	
	
	@Override
	public void run() {
		dropNotes(this.titleName);
	}
	
	// 게임 종료 함수
	public void close() {
		gameMusic.close();
		this.interrupt();
	}
	
	// 노트 찍기, 쉬움 난이도만 구현
	public void dropNotes(String titleName) {
		// VK_LEFT Note x좌표 : 275
		// VK_DOWN Note x좌표 : 575
		// VK_RIGHT Note x좌표 : 875
		Beat[] beats = null;
		if (titleName.equals("구리리 - 와인") && difficulty.equals("Easy")) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125; // 최소 박자의 간격
			beats = new Beat[] {
					new Beat(startTime, "VK_LEFT"),
					new Beat(startTime + gap * 2, "VK_LEFT"),
					new Beat(startTime + gap * 6, "VK_DOWN"),
					new Beat(startTime + gap * 10, "VK_RIGHT"),
					new Beat(startTime + gap * 12, "VK_LEFT"),
					new Beat(startTime + gap * 15, "VK_LEFT"),
					new Beat(startTime + gap * 18, "VK_RIGHT"),
					new Beat(startTime + gap * 22, "VK_DOWN"),
			};
		}
		else if (titleName.equals("구리리 - 경화수월") && difficulty.equals("Easy")) {
			int startTime = 1000;
			int gap = 250;
			beats = new Beat[] {
					new Beat(startTime, "VK_LEFT"),
					new Beat(startTime + gap * 2, "VK_LEFT"),
					new Beat(startTime + gap * 6, "VK_DOWN"),
					new Beat(startTime + gap * 10, "VK_RIGHT"),
					new Beat(startTime + gap * 12, "VK_LEFT"),
					new Beat(startTime + gap * 15, "VK_LEFT"),
					new Beat(startTime + gap * 18, "VK_RIGHT"),
					new Beat(startTime + gap * 22, "VK_DOWN"),
			};
		}
		else if (titleName.equals("Fly to high - Guriri") && difficulty.equals("Easy")) {
			int startTime = 1000;
			int gap = 125;
			beats = new Beat[] {
					new Beat(startTime, "VK_LEFT"),
					new Beat(startTime + gap * 2, "VK_LEFT"),
					new Beat(startTime + gap * 6, "VK_DOWN"),
					new Beat(startTime + gap * 10, "VK_RIGHT"),
					new Beat(startTime + gap * 12, "VK_LEFT"),
					new Beat(startTime + gap * 15, "VK_LEFT"),
					new Beat(startTime + gap * 18, "VK_RIGHT"),
					new Beat(startTime + gap * 22, "VK_DOWN"),
			};
		}
		
		
		int i = 0;
		gameMusic.start(); // start하면 해당 게임 인스턴스의 run함수 실행
		
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
				dropped = true;
			}
			if (!dropped) {
				try {
					Thread.sleep(5); // 노트가 떨어지지 않을 시 0.005초 쉬게 만듬
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// 노트 판정 함수
	public void judge(String input) {
		for (int i = 0; i< noteList.size(); i++) {
			Note note = noteList.get(i);
			if (input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
		}
	}
	
	// 노트 판정시 이벤트 구현 
	public void judgeEvent(String judge) {
		if (!judge.equals("None")) {
			yellowFlareImage = new ImageIcon(Main.class.getResource("../images/yellowFlare.png")).getImage();
		}
		if (judge.equals("Miss")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeMiss.png")).getImage(); 
		}
		else if (judge.equals("Bad")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeBad.png")).getImage(); 
		}
		else if (judge.equals("Good")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeGood.png")).getImage(); 
		}
		else if (judge.equals("Great")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgeGreat.png")).getImage(); 
		}
		else if (judge.equals("Perfect")) {
			judgeImage = new ImageIcon(Main.class.getResource("../images/judgePerfect.png")).getImage(); 
		}
	}
	
}
