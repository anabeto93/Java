package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Buttons{
	JLabel label;
	JTextField text;
	JButton button;
	public Buttons(){
		label = new JLabel("");
		text = new JTextField("",20);
		button = new JButton("Click here");
		JFrame frame = new JFrame("Using TextFields");
		frame.setLayout(new FlowLayout());
		frame.setSize(400,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		button.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent occur){
				label.setText(text.getText());
			}
		});

		text.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent occur){
				label.setText(text.getText());
			}
		});

		frame.add(text);
		frame.add(button);
		frame.add(label);
		frame.setVisible(true);
	}

	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Buttons();
			}
		});

		
	}
}