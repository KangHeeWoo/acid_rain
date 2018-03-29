package calendar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class calendar {
	public static void main(String[] args) {
		new ShowCalendar();
	}
}

class ShowCalendar extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel inYear = new JLabel("연도 입력");
	JTextField fieldYear = new JTextField(10);
	JPanel panYear = new JPanel();

	JPanel panShow = new JPanel();

	JLabel[] day = new JLabel[49];
	String[] week = {"일", "월", "화", "수", "목", "금", "토"};
	JButton[] btnMonth = new JButton[12];
	JPanel panBtn = new JPanel();

	Calendar ca;
	int month = 0;

	public ShowCalendar() {
		setLayout(new BorderLayout());
		panYear.add(inYear);
		panYear.add(fieldYear);
		add(panYear, BorderLayout.NORTH);
		panShow.setLayout(new GridLayout(7, 7));
		for(int i=0;i<49;i++) {
			day[i] = new JLabel(week[i/7]);
			panShow.add(day[i]);
		}
		add(panShow, BorderLayout.CENTER);
		panBtn.setLayout(new GridLayout(12, 1));
		for (int i = 0; i < btnMonth.length; i++) {
			btnMonth[i] = new JButton("" + (i + 1));
			btnMonth[i].addActionListener(listener);
			panBtn.add(btnMonth[i]);
		}
		add(panBtn, BorderLayout.EAST);
		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ca = Calendar.getInstance();
	}

	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnMonth[0]) {
				month = Integer.parseInt(btnMonth[0].getText()) - 1;
			} else if (e.getSource() == btnMonth[1]) {
				month = Integer.parseInt(btnMonth[1].getText()) - 1;
			} else if (e.getSource() == btnMonth[2]) {
				month = Integer.parseInt(btnMonth[2].getText()) - 1;
			} else if (e.getSource() == btnMonth[3]) {
				month = Integer.parseInt(btnMonth[3].getText()) - 1;
			} else if (e.getSource() == btnMonth[4]) {
				month = Integer.parseInt(btnMonth[4].getText()) - 1;
			} else if (e.getSource() == btnMonth[5]) {
				month = Integer.parseInt(btnMonth[5].getText()) - 1;
			} else if (e.getSource() == btnMonth[6]) {
				month = Integer.parseInt(btnMonth[6].getText()) - 1;
			} else if (e.getSource() == btnMonth[7]) {
				month = Integer.parseInt(btnMonth[7].getText()) - 1;
			} else if (e.getSource() == btnMonth[8]) {
				month = Integer.parseInt(btnMonth[8].getText()) - 1;
			} else if (e.getSource() == btnMonth[9]) {
				month = Integer.parseInt(btnMonth[9].getText()) - 1;
			} else if (e.getSource() == btnMonth[10]) {
				month = Integer.parseInt(btnMonth[10].getText()) - 1;
			} else {
				month = Integer.parseInt(btnMonth[11].getText()) - 1;
			}
			ca.set(Integer.parseInt(fieldYear.getText()), month, 1);
			printCalendar();
		}
	};

	public void printCalendar() {
		panShow.removeAll();	
		int max = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		int today = 1;
		int day = 1;
		
		panShow.repaint();
	}
}