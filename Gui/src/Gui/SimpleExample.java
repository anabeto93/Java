package Gui;

import javax.swing.*;

public class SimpleExample{
	SimpleExample(){
		JFrame frame = new JFrame("THIS IS MY FIRST ADVANCED GUI");
		frame.setSize(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("DON'T YOU LOVE THIS!!!");

		frame.add(label);
		frame.setVisible(true);
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new SimpleExample();
			}
		});
	}
}