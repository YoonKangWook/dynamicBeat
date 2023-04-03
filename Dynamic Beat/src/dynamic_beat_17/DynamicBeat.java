package dynamic_beat_17;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {

	// 게임 배경 이미지 사이즈 1280 X 720
	private Image screenImage;

	// screenGraphic = screenImage.getGraphics(); -> screenDraw((Graphics2D)screenGraphic); 
	private Graphics screenGraphic;

	// Image 상호작용 X
	private Image background = new ImageIcon(Main.class.getResource("../images/introBackground2.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	// ImageIcon 상호작용 O
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png")); 
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png")); 
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png")); 
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png")); 
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
	private ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easyButtonEntered.png")); 
	private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyButtonBasic.png"));
	private ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardButtonEntered.png")); 
	private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardButtonBasic.png"));
	private ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonEntered.png")); 
	private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));

	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);	
	private JButton quitButton = new JButton(quitButtonBasicImage); 
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);
	private JButton backButton = new JButton(backButtonBasicImage);

	// 창 이동시 사용할 좌표
	private int mouseX, mouseY;
	
	private boolean isMainScreen = false;
	private boolean isGameScreen = false;
	
	// introMusic 객체 생성, 생성자 매개변수(mp3파일 명, 불린 값), true -> 반복 재생, false -> 1번 재생
	Music introMusic = new Music("introMusic.mp3", true);
	
	// 노래에 대한 정보를 배열로 관리, Track : 제네릭 사용
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	// 곡에 대한 설명이 있는 이미지
	private Image titleImage;
	
	// 선택한 곡 이미지
	private Image selectedImage;
	
	// 선택한 곡 음악 
	private Music selectedMusic;
	
	// 처음 시작시 0번 곡이 자동으로 선택
	private int nowSelected = 0;
	
	public static Game game;
	
	// Main에서 실행하는 유일한 함수
	public DynamicBeat() {
		// 배열에 곡에대한 정보 추가, 추가 순서에 따른 곡 순서 지정
		// 0번 트랙
		trackList.add(new Track("Wine Title Image.png", "Wine Start Image.jpg",
				"Wine Game Image.jpg", "구리리 - 와인 HighLight.mp3", "구리리 - 와인.mp3", "구리리 - 와인"));
		// 1번 트랙		
		trackList.add(new Track("경화수월 Title Image.png", "경화수월 Start Image.jpg",
				"경화수월 Game Image.jpg", "구리리 - 경화수월 HighLight.mp3", "구리리 - 경화수월.mp3", "구리리 - 경화수월"));
		// 2번 트랙
		trackList.add(new Track("Fly to High Title Image.png", "Fly to High Start Image.jpg",
				"Fly to High Game Image.jpg", "Fly to high Guriri HighLight.mp3", "Fly to high Guriri.mp3", "Fly to high - Guriri"));
			
		// 프레임 타이틀바 없애기
		setUndecorated(true);
		// 프레임 타이틀 지정
//		setTitle("Dynamic Beat");
		
		// GUI 창 크기 조절
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		
		// 프레임 크기를 조절할 수 없게 만듬
		setResizable(false);
		
		// 창을 화면의 가운데에 띄우게 해줌
		setLocationRelativeTo(null);
		
		// 창을 닫으면 프로세스까지 종료됨
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// GUI를 화면에 띄움
		setVisible(true);
		
		// GUI 백그라운드 색 설정, 미 설정시 draw가 안됨
		setBackground(new Color(0, 0, 0, 0));
		
		// 배치관리자 제거
		setLayout(null);
	
		// 키보드의 키 상태 변경을 처리하는 인터페이스
		addKeyListener(new KeyListener());
		
		introMusic.start();
		
		// 창 닫기 버튼
		exitButton.setBounds(1245, 0, 30, 30);
		
		// 이미지 기본 테두리, 채우기, 색 없애기
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0); 
			}
		});
		add(exitButton);
		
		// 시작하기 버튼
		startButton.setBounds(40, 200, 400, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				introMusic.close();
			
				// 시작하기 클릭시 이벤트 -> 메인화면으로 이동
				enterMain();
			}
		});
		add(startButton);
		
		// 종료하기 버튼
		quitButton.setBounds(40, 330, 400, 100);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton);
		
		// 메인화면의 곡 변경시 사용되는 버튼
		leftButton.setVisible(false);
		leftButton.setBounds(140, 310, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				
				// 버튼 클릭시 이벤트
				selectLeft();
			}
		});
		add(leftButton);
		
		// 메인화면의 곡 변경시 사용되는 버튼
		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 60, 60);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				
				// 버튼 클릭시 이벤트
				selectRight();
			}
		});
		add(rightButton);
		
		// 쉬움 난이도 버튼
		easyButton.setVisible(false);
		easyButton.setBounds(375, 580, 250, 67);
		easyButton.setBorderPainted(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setFocusPainted(false);
		
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				
				// 쉬움 난이도 선택시 이벤트 -> 게임시작
				gameStart(nowSelected, "Easy");
				
			}
		});
		add(easyButton);
		
		// 어려움 난이도 버튼
		hardButton.setVisible(false);
		hardButton.setBounds(655, 580, 250, 67);
		hardButton.setBorderPainted(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setFocusPainted(false);
		
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardButton.setIcon(hardButtonEnteredImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonBasicImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				
				// 어려움 난이도 선택시 이벤트 -> 게임시작
				gameStart(nowSelected, "Hard");
			}
		});
		add(hardButton);
		
		// 게임화면에서 다시 메인화면으로 나가기 위한 버튼
		backButton.setVisible(false);
		backButton.setBounds(1150, 50, 120, 92);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setIcon(backButtonEnteredImage);
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(backButtonBasicImage);
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				
				// 메인화면으로 돌아가는 함수
				backMain();
			}
		});
		add(backButton);
		
		// 메뉴바 배치, 창 위치 이동 구현
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);

		
	} // DynamicBeat() 끝

	// 윈도우 화면을 그려주는 함수
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D)screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	// 이미지를 윈도우 화면 위에 그려주는 함수
	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		if (isMainScreen)
		{
			g.drawImage(selectedImage, 340, 100, null); // add가 된게 아닌 이미지를 보여줌, 이미지나 움직이는 이미지에 사용
			g.drawImage(titleImage, 340, 70, null);
		}
		if (isGameScreen)
		{
			game.screenDraw(g);
		}
		paintComponents(g); // add된 요소를 그려줌
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// true된 이미지와 false된 이미지를 다시 그려줌
		this.repaint();

	}
	
	// 곡 선택시 트랙에 대한 정보를 읽어옴
	public void selectTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close(); // 게임 시작시 하이라이트 종료
		titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
		selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);

		// 새로운 쓰레드 작업 시작
		selectedMusic.start();
	}

	public void selectLeft() {
		if (nowSelected == 0)
			nowSelected = trackList.size() - 1;
		else
			nowSelected--;
		selectTrack(nowSelected);
	}
	
	public void selectRight() {
		if (nowSelected == trackList.size() - 1)
			nowSelected = 0;
		else
			nowSelected++;
		selectTrack(nowSelected);
	}
	
	// 메인화면에서 게임화면으로 들어가는 함수 구현
	public void gameStart(int nowselected, String difficulty) {
		if (selectedMusic != null)
			selectedMusic.close();
		isMainScreen = false;
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		backButton.setVisible(true);
		background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
		backButton.setVisible(true);
		isGameScreen = true;
		
		game = new Game(trackList.get(nowSelected).gettitleName(), difficulty, trackList.get(nowSelected).getGameMusic());
		game.start();
		
		// 컴포넌트가 포커스를 받을 수 있도록 설정 -> 해당 컴포넌트로부터 먼저 키를 입력 받을 수 있다
		setFocusable(true);	
	}
	
	// 게임화면에서 메인화면으로 돌아가는 함수 구현
	public void backMain() {
		isMainScreen = true;
		isGameScreen = false;
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		backButton.setVisible(false);
		background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		selectTrack(nowSelected);
		
		// game 쓰레드 종료
		game.close();
	}
	
	// 게임 시작 버튼 클릭시 메인화면으로 들어감
	public void enterMain() {
		startButton.setVisible(false);
		quitButton.setVisible(false);
		background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		isMainScreen = true;
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		introMusic.close();
	
		// 0번 곡 자동 선택
		selectTrack(0);
	}

}
