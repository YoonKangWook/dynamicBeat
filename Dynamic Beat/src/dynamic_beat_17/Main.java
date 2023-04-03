package dynamic_beat_17;

public class Main {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int NOTE_SPEED = 5;
	public static final int SLEEP_TIME = 10;
	public static final int REACH_TIME = 2; // 노트 생성 후 판정포인트에 도달하는 시간
	
	public static void main(String[] args) {

		new DynamicBeat();
		
	}

}
