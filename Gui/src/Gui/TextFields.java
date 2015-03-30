package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextFields{
	JLabel label;
	JTextField text;

	public TextFields(){
		label = new JLabel("");
		text = new JTextField("",20);
		JFrame frame = new JFrame("Using TextFields");
		frame.setLayout(new FlowLayout());
		frame.setSize(400,250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		text.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent occur){
				label.setText(text.getText());
			}
		});

		frame.add(label);
		frame.add(text);
		frame.setVisible(true);
	}

	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new TextFields();
			}
		});

		
	}
}