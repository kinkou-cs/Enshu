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
		//��ʃT�C�Y�C�T�C�Y�Œ�C�I������
		setBounds(100, 100, 640, 480);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);
		
		JButton stop = new JButton("��~");
		JButton forward = new JButton("�O�i");
		JButton backward = new JButton("���");
		mainPanel.add(stop);
		mainPanel.add(forward);
		mainPanel.add(backward);
		
		
		setVisible(true);
	}
}
