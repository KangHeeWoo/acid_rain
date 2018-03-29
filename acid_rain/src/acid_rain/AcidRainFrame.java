package acid_rain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class AcidRainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel mainPan = new JPanel();
	ImageIcon bg = new ImageIcon("bg_acid.png");
	JLabel mainBg = new JLabel(bg);
	JLabel input = new JLabel("입력");
	JTextField inputField = new JTextField(10);
	int life = 3;
	JLabel lifeLa = new JLabel("생명력 : ");
	JButton start = new JButton("시작");
	JButton exit = new JButton("종료");
	JPanel subPan = new JPanel();
	JPanel inputPan = new JPanel();
	JPanel btnPan = new JPanel();
	int score = 0;
	JLabel scoreLa = new JLabel("점수 : ");
	JPanel scorePan = new JPanel();

	GameThread game = null;

	ArrayList<JLabel> word = new ArrayList<>();
	ArrayList<String[]> rankInfo = new ArrayList<>();

	BufferedReader br = null;
	PrintWriter pw = null;
	Random rnd = new Random();
	int lastIndex = 0;
	int inteval = 1000, speed = 30;
	String name, rankName;
	boolean nameState = false;

	public AcidRainFrame() {
		super("산성비");
		
		//프레임 화면 구성
		setLayout(new BorderLayout());
		mainBg.setBounds(0, 0, 750, 750);
		mainPan.add(mainBg, 0);
		mainPan.setLayout(null);
		add(mainPan, BorderLayout.CENTER);
		subPan.setLayout(new GridLayout(1, 4));
		subPan.add(lifeLa);
		inputPan.add(input);
		inputPan.add(inputField);
		subPan.add(inputPan);
		scorePan.add(scoreLa);
		subPan.add(scorePan);
		exit.setEnabled(false);
		btnPan.add(start);
		btnPan.add(exit);
		subPan.add(btnPan);
		add(subPan, BorderLayout.SOUTH);

		setSize(750, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//시작, 종료버튼 클릭시 발생할 이벤트 추가
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == start) {
					gameStart();
				} else {
					gameEnd();
				}
			}
		};

		start.addActionListener(listener);
		exit.addActionListener(listener);

		inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					for (int i = 0; i <= lastIndex; i++) {
						if (inputField.getText().equals(word.get(i).getText())) {
							word.get(i).setText("");
							score += 10;
							scoreLa.setText("점수 : " + score);
							if (score % 200 == 0) {
								speed += 3;
								inteval -= 50;
							}
						}
					}
					inputField.setText("");
				}
			}
		});

		nickName();
	}

	public void saveRank() {
		rankInfo.removeAll(rankInfo);
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("랭킹.txt")));
			
			String info = "1," + name + "," + score;
			int tmp;
			String[] tmpRank;
			int topRank = 3;
			String printRank = "현재랭킹\n";
			rankInfo.add(info.split(","));
			while ((info = br.readLine()) != null) {
				rankInfo.add(info.split(","));
			}
			br.close();

			for(int i=0;i<rankInfo.size();i++) {
				if(score > Integer.parseInt(rankInfo.get(i)[2])) {
					tmp = Integer.parseInt(rankInfo.get(i)[0])+1;
					rankInfo.get(i)[0] = String.valueOf(tmp);
				}else if(score < Integer.parseInt(rankInfo.get(i)[2])) {
					tmp = Integer.parseInt(rankInfo.get(0)[0])+1;
					rankInfo.get(0)[0] = String.valueOf(tmp);
				}
			}
			
			for(int i=0;i<rankInfo.size()-1;i++) {
				for(int j=i+1;j<rankInfo.size();j++) {
					if(Integer.parseInt(rankInfo.get(i)[0]) > Integer.parseInt(rankInfo.get(j)[0])) {
						tmpRank = rankInfo.get(i);
						rankInfo.set(i, rankInfo.get(j));
						rankInfo.set(j, tmpRank);
					}
				}
			}
			
			if(topRank > rankInfo.size()) topRank = rankInfo.size();
			
			for(int i=0;i<topRank;i++) {
				printRank += rankInfo.get(i)[0] + ". " + rankInfo.get(i)[1] + ". " + rankInfo.get(i)[2] + "\n";
			}
			
			pw = new PrintWriter("랭킹.txt");
			for(int i=0;i<rankInfo.size();i++) {
				pw.println(rankInfo.get(i)[0] + "," + rankInfo.get(i)[1] + "," + rankInfo.get(i)[2]);
				pw.flush();
			}
			pw.close();
			
			JOptionPane.showMessageDialog(AcidRainFrame.this, printRank + "생명력을 모두 소모하였습니다.\n" + name + "의 최종점수 : " + score);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void nickName() {
		try {
			do {
				name = JOptionPane.showInputDialog(AcidRainFrame.this, "닉네임을 입력해주세요");
				nameState = false;

				br = new BufferedReader(new InputStreamReader(new FileInputStream("랭킹.txt")));

				if (name != null && !"".equals(name)) {
					while ((rankName = br.readLine()) != null) {
						String[] rankInfo = rankName.split(",");
						rankName = rankInfo[1];
						if (name.equals(rankName)) {
							JOptionPane.showMessageDialog(AcidRainFrame.this, "중복된 닉네임이 존재합니다.");
							nameState = true;
						}
					}
				} else {
					JOptionPane.showMessageDialog(AcidRainFrame.this, "닉네임을 입력해주세요.");
					nameState = true;
				}
			} while (nameState);
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
	}

	public void gameStart() {
		score = 0;
		life = 3;
		inteval = 1000;
		speed = 30;
		scoreLa.setText("점수 : " + score);
		lifeLa.setText("생명력 : " + life);
		exit.setEnabled(true);
		start.setEnabled(false);
		game = new GameThread();
		game.start();
	}
	
	public void gameEnd() {
		exit.setEnabled(false);
		start.setEnabled(true);
		game.interrupt();

		for (int i = 0; i <= lastIndex; i++) {
			word.get(i).setText("");
		}
		saveRank();
	}
	
	class MoveWord extends Thread {
		JLabel word;

		public MoveWord(JLabel word) {
			this.word = word;
		}

		@Override
		public void run() {
			word.setBounds(rnd.nextInt(650), 0, 100, 20);
			word.setForeground(Color.WHITE);
			mainPan.add(word, 0);
			while (word.getY() < 750) {
				int nextY = word.getY() + speed;
				if (nextY >= 750) {
					nextY = 750;
					if (life > 0 && !word.getText().equals("")) {
						life--;
						lifeLa.setText("생명력 : " + life);
						if (life == 0) {
							gameEnd();
						}
					}
				}
				word.setLocation(word.getX(), nextY);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		}
	}	
	
	class GameThread extends Thread {
		@Override
		public void run() {
			try {
				new ListSetting().start();
				for (int i = 3; i > 0; i--) {
					inputField.setText(i + "초 뒤 시작");
					Thread.sleep(1000);
				}
				inputField.setText("");
				inputField.requestFocus();
				synchronized (word) {
					for (int i = 0; i < word.size(); i++) {
						new MoveWord(word.get(i)).start();
						lastIndex = i;
						Thread.sleep(inteval);
					}
				}
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	class ListSetting extends Thread {
		@Override
		public void run() {
			synchronized (word) {
				String fiWord;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream("단어집.txt")));

					while ((fiWord = br.readLine()) != null) {
						word.add(new JLabel(fiWord));
					}
					System.out.println(word.get(word.size() - 1).getText());
					for (int i = 0; i < word.size(); i++) {
						int rndIndex = rnd.nextInt(word.size());
						fiWord = word.get(i).getText();
						word.get(i).setText(word.get(rndIndex).getText());
						word.get(rndIndex).setText(fiWord);
					}
					System.out.println(word.get(word.size() - 1).getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
		}
	}
}