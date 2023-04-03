package dynamic_beat_17;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {

	private Player player;
	private boolean isLoop;
	private File file;
	
	// 파일 객체가 가리키는 파일을 바이트 스트림으로 읽기 위해 객체 생성
	private FileInputStream fis;
	
	// BufferedInputStream은 파일을 읽어올때 8192byte의 버퍼를 두고 작업을 하기 때문에 속도가 굉장히 빨라 집니다
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// music의 재생 위치를 알려줌
	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}
	
	// 음악을 종료시킬 때 호출
	public void close() {
		isLoop = false;
		player.close();
		
		// interrupt() -> Thread 종료
		this.interrupt(); 
	}
	
	@Override
	public void run() {
		try {
			do {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isLoop);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
