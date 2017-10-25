package gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	public static void main(String args[]){
		Window w = new Window();
	}
	
	public Window(){
		//画面サイズ，サイズ固定，終了処理
		setBounds(100, 100, 640, 480);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);
		
		JButton stop = new JButton("停止");
		JButton forward = new JButton("前進");
		JButton backward = new JButton("後退");
		mainPanel.add(stop);
		mainPanel.add(forward);
		mainPanel.add(backward);
		
		
		setVisible(true);
	}
}
